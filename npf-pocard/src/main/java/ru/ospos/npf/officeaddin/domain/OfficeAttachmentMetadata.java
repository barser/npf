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
 * Сведения о файле, передаваемом из надстройки Microsoft Office для привязки к платежному проучению.
 */
@Getter
@Setter
@Entity
@Table(name = "pocard_office_attach_meta")
public class OfficeAttachmentMetadata implements Serializable {

    private static final long serialVersionUID = -4633662173860494284L;

    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Id
    private UUID id;

    @Version
    private int version;

    @ManyToOne
    @JoinColumn(name = "pocard_id", nullable = false)
    private Pocard pocard;

    @ManyToOne
    @JoinColumn(name = "filestorage_id", nullable = false)
    private FileStorage fileStorage;

    private Long checksum;

    @Column(name = "size_bytes")
    private Long size;

    private String originPath;

    private String originName;

    private String msoDocumentAuthor;

    private String msoCreationDate;

    private LocalDateTime uploadTs;

    @Enumerated(EnumType.STRING)
    private OfficeAttachmentState state;

    private Boolean processed;

}
