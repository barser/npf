package ru.ospos.npf.officeaddin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ospos.npf.commons.util.OperationResult;
import ru.ospos.npf.officeaddin.dto.FileOperation;

@RestController
@RequestMapping("/pub/api/v1_0/process")
public class ProcessController {

    @PostMapping
    public OperationResult post(@RequestBody FileOperation fileOperation) {

        String fileName = fileOperation.getFilename();
        String subpath = fileOperation.getSubpath();

        return OperationResult.success("Ответ сервера: передано на обработку");
    }
}
