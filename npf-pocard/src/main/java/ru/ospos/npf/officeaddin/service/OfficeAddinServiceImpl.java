package ru.ospos.npf.officeaddin.service;

import liquibase.util.file.FilenameUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ospos.npf.commons.domain.base.FileStorage;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.domain.document.RegistrationCard;
import ru.ospos.npf.commons.util.GenericNpfException;
import ru.ospos.npf.officeaddin.domain.OfficeAttachmentMetadata;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
public class OfficeAddinServiceImpl implements OfficeAddinService {

    private final Logger LOGGER = LoggerFactory.getLogger(OfficeAddinServiceImpl.class);
    private final String FS_PREFIX = "T:\\rd\\"; // TODO вынести в настройки!

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void bind(OfficeAttachmentMetadata officeAttachmentMetadata, Pocard pocard, File uploadedFile) {

        if (!entityManager.contains(officeAttachmentMetadata)) entityManager.persist(officeAttachmentMetadata);

        // copy file, create FileStorage and register it with Pocard
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

        fileStorage.setPath(newFile.getAbsolutePath().replace(FS_PREFIX, ""));
        RegistrationCard registrationCard = pocard.getRegistrationCard();
        if (registrationCard == null) {
            throw new GenericNpfException("У выбранного платежного поручения отсутствует регистрационная карта");
        }
        registrationCard.addFile(fileStorage);
    }

    private File tryCreateNewFile(String extension, Integer fileStorageId) {

        StringBuilder sb = new StringBuilder(FS_PREFIX);

        sb.append("pocard_office_addins\\");
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
    public void process(OfficeAttachmentMetadata officeAttachmentMetadata) {

    }
}
