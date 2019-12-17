package ru.ospos.npf.commons.util;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OperationResult {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationResult.class);
    private static final int CODE_SUCCESS = 0;
    private static final int CODE_ERROR_DEFAULT = -1;

    OperationResult() {}

    @Getter
    private int code;

    @Getter
    private String message;

    @Getter
    private String details;

    private boolean success;

    public boolean isSuccess() {
        return code == 0;
    }

    public static OperationResult success(String message, String details) {

        OperationResult operationResult = new OperationResult();
        operationResult.message = message;
        operationResult.details = details;

        LOGGER.debug("SUCCESS: {}", operationResult);
        return operationResult;
    }

    public static OperationResult success(String message) {
        return success(message, null);
    }

    public static OperationResult success() {
        return success(null);
    }

    public static OperationResult error(int code, String message, String details) {

        if (code == CODE_SUCCESS) {
            throw new GenericNpfException("Invalid code for error!");
        }

        OperationResult operationResult = new OperationResult();
        operationResult.code = code;
        operationResult.message = message;
        operationResult.details = details;

        LOGGER.info("ERROR: {}", operationResult);
        return operationResult;
    }

    public static OperationResult error(int code, String message) {
        return error(code, message, null);
    }

    public static OperationResult error(int code) {
        return error(code, null);
    }

    public static OperationResult error(String errorMessage) {
        return error(CODE_ERROR_DEFAULT, errorMessage);
    }
}
