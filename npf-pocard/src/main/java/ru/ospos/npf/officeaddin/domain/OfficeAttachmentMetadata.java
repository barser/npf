package ru.ospos.npf.officeaddin.domain;

import ru.ospos.npf.commons.domain.base.FileStorage;
import ru.ospos.npf.commons.domain.document.Pocard;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Файл, передаваемый из надстройки Microsoft Office.
 */
public class OfficeAttachmentMetadata implements Serializable {

    private static final long serialVersionUID = -4527277548020979941L;

    private Pocard pocard;
    private FileStorage fileStorage;
    private String checkSum;
    private Long size;
    private String originPath;
    private LocalDateTime creationTs;
    private String state; // TODO Enum
    private Boolean processed;
}
