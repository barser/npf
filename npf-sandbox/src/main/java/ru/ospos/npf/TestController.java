package ru.ospos.npf;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ospos.npf.commons.util.OperationResult;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping
    @Transactional(readOnly = true)
    public OperationResult post(@RequestBody TestDto testDto) {

        return OperationResult.success(testDto.toString());

    }
}
