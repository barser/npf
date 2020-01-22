package ru.ospos.npf.officeaddin;

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
import ru.ospos.npf.officeaddin.domain.FileOperation;
import ru.ospos.npf.officeaddin.service.OfficeAddinService;
import ru.ospos.npf.officeaddin.service.UploadedFileService;

import java.io.File;

@RestController
@RequestMapping("/pub/api/v1_0/bind")
public class BindController {

    private static Logger LOGGER = LoggerFactory.getLogger(BindController.class);

    @Autowired
    private PocardRepository pocardRepository;

    @Autowired
    private OfficeAddinService officeAddinService;

    @Autowired
    private UploadedFileService uploadedFileService;

    @PostMapping
    @Transactional
    public OperationResult post(@RequestBody FileOperation fileOperation) {

        File uploadedFile = uploadedFileService.getUploadedFile(fileOperation);

        int pocardId = fileOperation.getPocardId();
        Pocard pocard = pocardRepository.findById(pocardId)
                .orElseThrow(() -> new GenericNpfException("П/п, к которому осуществляется привязка, не найдено!"));

        officeAddinService.bind(pocard, uploadedFile, fileOperation);
        return OperationResult.success("Ответ сервера: выполнена привязка");
    }
}
