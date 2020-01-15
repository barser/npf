package ru.ospos.npf.officeaddin;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.util.GenericNpfException;
import ru.ospos.npf.commons.util.OperationResult;
import ru.ospos.npf.officeaddin.domain.OfficeAttachmentMetadata;
import ru.ospos.npf.officeaddin.dto.FileOperation;
import ru.ospos.npf.officeaddin.service.OfficeAddinService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/pub/api/v1_0/bind")
public class BindController {

    private static Logger LOGGER = LoggerFactory.getLogger(BindController.class);

    @Autowired
    private PocardRepository pocardRepository;

    @Autowired
    private OfficeAddinService officeAddinService;

    @PostMapping
    @Transactional
    public OperationResult post(@RequestBody FileOperation fileOperation) {

        String fileName = fileOperation.getFilename();
        String subpath = fileOperation.getSubpath();

        File uploadedFile = getUploadedFile(subpath, fileName);

        int pocardId = fileOperation.getPocardId();
        Pocard pocard = pocardRepository.findById(pocardId)
                .orElseThrow(() -> new GenericNpfException("П/п, к которому осуществляется привязка, не найдено!"));

        OfficeAttachmentMetadata oam = createOfficeAttachmentMetadata(uploadedFile, fileOperation);
        officeAddinService.bind(oam, pocard, uploadedFile);
        return OperationResult.success("Ответ сервера: выполнена привязка");
    }

    private File getUploadedFile(String subpath, String fileName) {
        var file = FileUtils.getFile("R:\\", "TRANSFER", subpath, fileName);

        if (!file.exists()) {
            throw new GenericNpfException("Не удается прочитать файл " + fileName);
        }
        return file;
    }

    private OfficeAttachmentMetadata createOfficeAttachmentMetadata(File file, FileOperation fileOperation) {

        var oam = new OfficeAttachmentMetadata();
        try {
            oam.setChecksum(FileUtils.checksumCRC32(file));
        } catch (IOException e) {
            throw new GenericNpfException("Не удается вычислить контрольную сумму для файла "
                    + fileOperation.getFilename(), e);
        }
        oam.setUploadTs(LocalDateTime.now());
        oam.setSize(FileUtils.sizeOf(file));
        oam.setOriginPath(fileOperation.getSubpath());

        oam.setMsoDocumentAuthor(fileOperation.getAuthor());
        oam.setMsoCreationDate(fileOperation.getCreationDate());

        return oam;
    }
}
