package ru.ospos.npf.officeaddin.domain;

public enum OfficeAttachmentState {

    /**
     * Привязан к п/п.
     */
    BINDED,

    /**
     * Передан на исполнение.
     */
    ON_EXECUTION,

    /**
     * Исполнен (опционально).
     */
    DONE
}
