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

    @Column(name = "checksum")
    private Long checksum;

    @Column(name = "size_bytes")
    private Long size;

    @Column(name = "origin_path")
    private String originPath;

    @Column(name = "origin_name")
    private String originName;

    @Column(name = "mso_document_author")
    private String msoDocumentAuthor;

    @Column(name = "mso_creation_date")
    private String msoCreationDate;

    @Column(name = "upload_ts")
    private LocalDateTime uploadTs;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OfficeAttachmentState state;

    @Column(name = "processed")
    private Boolean processed;

}
