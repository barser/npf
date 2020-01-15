package ru.ospos.npf.officeaddin.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import ru.ospos.npf.commons.domain.base.FileStorage;
import ru.ospos.npf.commons.domain.document.Pocard;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Файл, передаваемый из надстройки Microsoft Office.
 */
@Getter
@Setter
@Entity
@Table(name = "POCARD_OFFICE_ATTACH_META")
public class OfficeAttachmentMetadata implements Serializable {

    private static final long serialVersionUID = -4527277548020979941L;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Id
    private UUID id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "POCARD_ID", nullable = false)
    private Pocard pocard;

    @ManyToOne
    @JoinColumn(name = "FILESTORAGE_ID", nullable = false)
    private FileStorage fileStorage;

    private Long checkSum;
    private Long size;
    private String originPath;
    private LocalDateTime creationTs;
    private String state; // TODO Enum
    private Boolean processed;
}
