package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;
import ru.ospos.npf.commons.domain.base.Action;
import ru.ospos.npf.commons.domain.user.Operator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "DOCUMENTS")
public class Document {

    @Id
    @SequenceGenerator(name = "documentSeqGenerator", sequenceName = "DOCUMENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentSeqGenerator")
    private Long id;

    /**
     * Тип документа.
     */
    @Column(name="FK_DOC_TYPE")
    private Integer documentType;

    /**
     * Дата документа.
     */
    @Column(name="DOC_DATE")
    private LocalDate date;

    /**
     * Ссылка на файл.
     */
    //TODO
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "FK_FILE")
//    private FileStorage file;

    /**
     * Наименование документа.
     */
    @Column(name = "TITLE", length = 1000)
    private String title;

    /**
     * Родительскй документ (если есть).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Document parent;

    /**
     * Регистрационный номер отдела.
     */
    @Column(name = "REG_DEPT_NUM", length = 20)
    private String regDeptNum;

    /**
     * Регистрационная папка в отделе.
     */
    @Column(name = "REG_ACT_NUM", length = 20)
    private String regActNum;

    /**
     * Регистрационный порядковый номер документа в папке.
     */
    @Column(name = "REG_DOC_NUM", length = 20)
    private String regDocNum;

    /**
     * Штрих-код.
     */
    @Column(name = "BARCODE", length = 100)
    private String barcode;

    /**
     * Резолюция (атрибут регистрационной карточки).
     */
    @Column(name = "RESOLUTION")
    private String resolution;

    /**
     * Действие "документ напечатан".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_PRINTED")
    private Action printed;

    /**
     * Действие "документ подписан".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SIGNED")
    private Action signed;


    /**
     * Действие "документ удален".
     * Вручную не исправлять!
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DELETED")
    private Action deleted;

    /**
     * Действие "документ взят на исполнение".
     * Вручную не исправлять!
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_HOLD")
    private Action hold;

    /**
     * Дейсвтие "документ исполнен".
     * Вручную не исправлять!
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DONE")
    private Action done;

    /**
     * Действие "документ отсканирован".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SCAN")
    private Action scan;

    /**
     * Действие "штрих-код оригинала документа отсканирован".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ORIG_BARCODE_SCANED")
    private Action originalBarcodeScaned;

//    @Deprecated
//    @ManyToOne
//    @JoinColumn(name = "FK_TREENODE")
//    private TreeNode treenode;
//    private String title;

    //TODO - реализовать.
//    /**
//     * Регистрационная карточка документа.
//     */
//    @ManyToOne
//    @JoinColumn(name = "FK_REGISTRATION_CARD")
//    private RegistrationCard registrationCard;

    // private DocumentCategory documentOut;
    // private DocumentCategory documentWithAccount;
    // private DocumentCategory documentWithCash;

    /**
     * Документ, связанный по регистрационной карте.
     */
    @ManyToOne
    @JoinColumn(name = "FK_DOC_LINKED")
    private Document linkedDocument;

    /**
     * Номер входящего документа.
     */
    @Column(name = "IN_DOC_NUMBER", length = 100)
    private String inDocNumber;

    /**
     * Дата входящего документа.
     */
    @Column(name = "IN_DOC_DATE")
    private LocalDate inDocDate;

    /**
     * Автор документа.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_OPERATOR")
    private Operator operator;

    /**
     * ID из MOTIW.
     */
    @Column(name = "MOTIW_ID")
    private Integer motiwId;

    /**
     * DOC из MOTIW.
     */
    @Column(name = "MOTIW_DOC")
    private Integer motiwDoc;

    /**
     * Дата создания документа.
     */
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationTs;

    /**
     * Дата изменения записи.
     */
    @Column(name = "CHANGE_DATE")
    private LocalDateTime changeTs;

    /**
     * Отметка об удалении документа.
     */
    @Column(name = "ISDELETE")
    private Boolean markedDeleted;

    /**
     * Дата последнего обновления (используется для синхронизации).
     */
    @Column(name = "LAST_UPDATE")
    private LocalDateTime lastUpdateTs;

    /**
     * Приоритет.
     */
    @Column(name = "PRIORITY")
    private Integer priority;

    /**
     * Краткое содержание. Атрибут регистрационной карточки.
     */
    @Column(name = "ABSTRACT")
    private String abstractText;

    // TODO реализовать.
//    private Company originNpf;

    /**
     * Статус документа.
     */
    @Column(name = "FK_DOCUMENT_STATE")
    private Integer documentState;
}
