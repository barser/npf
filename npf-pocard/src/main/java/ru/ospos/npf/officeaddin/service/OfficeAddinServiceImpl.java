package ru.ospos.npf.officeaddin.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import liquibase.util.file.FilenameUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.ospos.npf.commons.domain.base.Action;
import ru.ospos.npf.commons.domain.base.ActionType;
import ru.ospos.npf.commons.domain.base.FileStorage;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.domain.document.QPocard;
import ru.ospos.npf.commons.domain.document.regcard.RegistrationCard;
import ru.ospos.npf.commons.domain.user.Operator;
import ru.ospos.npf.commons.util.GenericNpfException;
import ru.ospos.npf.officeaddin.domain.FileOperation;
import ru.ospos.npf.officeaddin.domain.OfficeAttachmentMetadata;
import ru.ospos.npf.officeaddin.domain.OfficeAttachmentState;
import ru.ospos.npf.officeaddin.repository.OfficeAttachmentMetadataRepository;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class OfficeAddinServiceImpl implements OfficeAddinService {

    private final Logger LOGGER = LoggerFactory.getLogger(OfficeAddinServiceImpl.class);
    private final String FS_PREFIX = "T:\\rd\\"; // TODO вынести в настройки!

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OfficeAttachmentMetadataRepository officeAttachmentMetadataRepository;

    @Autowired
    private UploadedFileService uploadedFileService;

    @Override
    @Transactional(readOnly = true)
    public List<Pocard> search(Long number, LocalDate date, BigDecimal amount, String contragent) {

        var qPocard = QPocard.pocard;

        BooleanBuilder builder = new BooleanBuilder();

        if (number != null) builder.and(qPocard.number.eq(number));
        if (date != null) builder.and(qPocard.date.eq(date));

        if (amount != null) builder.and(qPocard.amount.eq(amount));
        if (!StringUtils.isEmpty(contragent)) builder.and(qPocard.fromName.likeIgnoreCase(contragent));

        builder.and(qPocard.date.isNotNull());
        builder.and(qPocard.amount.isNotNull());
        builder.and(qPocard.number.isNotNull());
        builder.and(qPocard.fromName.isNotNull());
        builder.and(qPocard.comments.isNotNull());

        var query = new JPAQuery<Pocard>(entityManager);

        query.from(qPocard);
        query.where(builder);

        query.orderBy(qPocard.id.desc());
        query.limit(25);

        return query.fetch();
    }

    private OfficeAttachmentMetadata createOfficeAttachmentMetadata(File file, FileOperation fileOperation) {

        var oam = new OfficeAttachmentMetadata();

        oam.setChecksum(uploadedFileService.getChecksum(file));
        oam.setUploadTs(LocalDateTime.now());

        oam.setSize(uploadedFileService.getSize(file));
        oam.setOriginPath(fileOperation.getSubpath());

        oam.setOriginName(file.getName());
        oam.setMsoDocumentAuthor(fileOperation.getAuthor());

        oam.setMsoCreationDate(fileOperation.getCreationDate());
        oam.setProcessed(false);

        entityManager.persist(oam);
        return oam;
    }

    @Override
    public void bind(Pocard pocard, File uploadedFile, FileOperation fileOperation) {

        var officeAttachmentMetadata = createOfficeAttachmentMetadata(uploadedFile, fileOperation);

        var oam = officeAttachmentMetadataRepository.findByPocardIn(Collections.singleton(pocard));
        var invalidAttachmentStates = Arrays.asList(OfficeAttachmentState.DONE, OfficeAttachmentState.ON_EXECUTION);
        for (var doc :
                oam) {
            if (invalidAttachmentStates.contains(doc.getState())) {
                throw new GenericNpfException("Не удается прикрепить документ: п/п уже передано на исполнение.");
            }
        }

        // Загруженный файл копируется из временной папки в общее файловое хранилище.
        var fileStorage = new FileStorage();
        var extension = FilenameUtils.getExtension(uploadedFile.getName());

        fileStorage.setCreationTs(officeAttachmentMetadata.getUploadTs());
        fileStorage.setExtension("." + extension);
        fileStorage.setOldFileName(uploadedFile.getAbsolutePath());
        fileStorage.setPath(uploadedFile.getAbsolutePath());
        entityManager.persist(fileStorage);

        var newFile = tryCreateNewFile(extension, fileStorage.getId());

        try {
            FileUtils.copyFile(uploadedFile, newFile);
            LOGGER.info("Скопирован файл: {} -> {}",
                    uploadedFile.getAbsolutePath(), newFile.getAbsolutePath());

        } catch (IOException e) {
            throw new GenericNpfException("Произошла ошибка при копировании файла.", e);
        }

        // Выполняется привязка файла к регистрационной карте платежного поручения.
        // Если у платежного поручения нет регистрационной карты происходит ошибка.
        fileStorage.setPath(newFile.getAbsolutePath().replace(FS_PREFIX, ""));

        RegistrationCard registrationCard = pocard.getRegistrationCard();
        if (registrationCard == null) {
            throw new GenericNpfException("У выбранного платежного поручения отсутствует регистрационная карта");
        }
        registrationCard.addFile(fileStorage);

        officeAttachmentMetadata.setState(OfficeAttachmentState.BINDED);
        officeAttachmentMetadata.setPocard(pocard);
        officeAttachmentMetadata.setFileStorage(fileStorage);
    }

    private File tryCreateNewFile(String extension, Integer fileStorageId) {

        StringBuilder sb = new StringBuilder(FS_PREFIX);

        sb.append("pocard_office_addins\\"); // Подпапка, в которой хранятся все файлы, прикрепляемые этим сервисом.
        sb.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM")));
        sb.append("\\fs_");

        File newFile;
        int counter = 0;
        do {
            StringBuilder ssb = new StringBuilder(sb);

            ssb.append(fileStorageId);
            ssb.append("_");

            if (counter > 0) {
                ssb.append(counter);
            }

            ssb.append(".");
            ssb.append(extension);

            newFile = new File(ssb.toString());
            counter++;

        } while (!newFile.exists() && counter < 2 /* столько повторных попыток, если файл существует */);

        if (newFile.exists()) {
            throw new GenericNpfException("Не удается создать целевой файл: файл с таким именем уже существует."
                    + newFile.getAbsolutePath());
        }
        return newFile;
    }

    @Override
    public void process(Pocard pocard) {

        List<OfficeAttachmentMetadata> oam = officeAttachmentMetadataRepository
                .findByPocardIn(Collections.singletonList(pocard));

        for (OfficeAttachmentMetadata officeAttachmentMetadata :
                oam) {
            if (officeAttachmentMetadata.getProcessed() == null
                    || Boolean.TRUE.equals(officeAttachmentMetadata.getProcessed())
                    || Arrays.asList(OfficeAttachmentState.DONE, OfficeAttachmentState.ON_EXECUTION)
                    .contains(officeAttachmentMetadata.getState())) {
                throw new GenericNpfException("Ошибка сервера: один из привязанных ранее к платежному поручению " +
                        "находится в некорректном состоянии! Пожалуйста, обратитесь в тех.поддержку.");
            }
            officeAttachmentMetadata.setState(OfficeAttachmentState.ON_EXECUTION);
            officeAttachmentMetadata.setProcessed(true);
        }

        var hold = new Action();
        hold.setCommentary("Установлено из надстройки Excel.");
        hold.setCreationTs(LocalDateTime.now());
        hold.setDate(hold.getCreationTs());

        var prevHold = pocard.getHold();

        // ActionType: 10 - DocumentHold, 12 - DocumentCoHold
        hold.setType(entityManager.getReference(ActionType.class, prevHold == null ? 10 : 12));

        // Employees: 172 - Баранов, 140 - Степанова TODO: в настройки
        var executor = entityManager.getReference(Operator.class, 140);
        hold.setOperator(executor);
        // Замена предыдущего исполнителя (при регистрации автоматически назнач. Глухова)
        if (prevHold != null) {
            prevHold.setOperator(executor);
        }
        pocard.addAction(hold);
    }
}
