package ru.ospos.npf.web;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.ospos.npf.commons.util.GenericNpfException;
import ru.ospos.npf.commons.util.OperationResult;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CommonExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            GenericNpfException.class
    })
    @ResponseBody
    protected OperationResult handleExceptions(Exception e) {
        return OperationResult.haltedByException(e.getMessage(), e);
    }
}
