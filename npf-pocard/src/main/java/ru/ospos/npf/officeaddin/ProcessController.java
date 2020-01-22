package ru.ospos.npf.officeaddin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.util.GenericNpfException;
import ru.ospos.npf.commons.util.OperationResult;
import ru.ospos.npf.officeaddin.domain.FileOperation;
import ru.ospos.npf.officeaddin.service.OfficeAddinService;

import javax.persistence.EntityManager;

@RestController
@RequestMapping("/pub/api/v1_0/process")
public class ProcessController {

    @Autowired
    private OfficeAddinService service;

    @Autowired
    private EntityManager em;

    @PostMapping
    @Transactional
    public OperationResult post(@RequestBody FileOperation fileOperation) {

        if (fileOperation.getPocardId() == null || fileOperation.getPocardId() <= 0) {
            throw new GenericNpfException("Выберите платежное поручение для передачи на исполнение!");
        }

        service.process(em.find(Pocard.class, fileOperation.getPocardId()));
        return OperationResult.success("Ответ сервера: передано на обработку");
    }
}
