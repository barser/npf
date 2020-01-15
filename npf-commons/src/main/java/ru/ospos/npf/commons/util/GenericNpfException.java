package ru.ospos.npf.commons.util;

/**
 * Общий класс исключений.
 */
public class GenericNpfException extends RuntimeException {
    public GenericNpfException(String message) {
        super(message);
    }

    public GenericNpfException(String message, Throwable e) {
        super(message, e);
    }

}
