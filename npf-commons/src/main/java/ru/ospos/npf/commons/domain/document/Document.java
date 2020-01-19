package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;
import ru.ospos.npf.commons.domain.base.Action;
import ru.ospos.npf.commons.domain.base.FileStorage;
import ru.ospos.npf.commons.domain.base.TreeNode;
import ru.ospos.npf.commons.domain.document.regcard.RegistrationCard;
import ru.ospos.npf.commons.domain.user.Operator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Документ.
 * Общий базовый класс для различных видов документов.
 */
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "DOCUMENTS")
public class Document implements Serializable {

    private static final long serialVersionUID = 7298229790243852205L;

    @Id
    @SequenceGenerator(name = "g_document", sequenceName = "DOCUMENT_SEQ", schema = "CDM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "g_document")
    private Integer id;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_FILE")
    private FileStorage file;

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
    private void setPrinted(Action action) {
        // Сделано намеренно. Для добавления действий определенного типа используйте addAction(Action action).
    };

    /**
     * Действие "документ подписан".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SIGNED")
    private Action signed;
    private void setSigned(Action action) {
        // Сделано намеренно. Для добавления действий определенного типа используйте addAction(Action action).
    };


    /**
     * Действие "документ удален".
     * Вручную не исправлять!
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DELETED")
    private Action deleted;
    private void setDeleted(Action action) {
        // Сделано намеренно. Для добавления действий определенного типа используйте addAction(Action action).
    };

    /**
     * Действие "документ взят на исполнение".
     * Вручную не исправлять!
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_HOLD")
    private Action hold;
    private void setHold(Action action) {
        // Сделано намеренно. Для добавления действий определенного типа используйте addAction(Action action).
    };

    /**
     * Дейсвтие "документ исполнен".
     * Вручную не исправлять!
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_DONE")
    private Action done;
    private void setDone(Action action) {
        // Сделано намеренно. Для добавления действий определенного типа используйте addAction(Action action).
    };

    /**
     * Действие "документ отсканирован".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_SCAN")
    private Action scan;
    private void setScan(Action action) {
        // Сделано намеренно. Для добавления действий определенного типа используйте addAction(Action action).
    };

    /**
     * Действие "штрих-код оригинала документа отсканирован".
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ORIG_BARCODE_SCANED")
    private Action originalBarcodeScaned;

    /**
     * Регистрационная карточка документа.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_REGISTRATION_CARD")
    private RegistrationCard registrationCard;

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

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "DOCUMENT_TREENODES",
            joinColumns = @JoinColumn(name = "FK_DOCUMENT"),
            inverseJoinColumns = @JoinColumn(name = "FK_TREENODE")
    )
    private Set<TreeNode> treeNodes = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "DOCUMENT_ACTIONS",
            joinColumns = @JoinColumn(name = "FK_DOCUMENT"),
            inverseJoinColumns = @JoinColumn(name = "FK_ACTION")
    )
    private Set<Action> actions = new HashSet<>();

    public void addTreeNode(TreeNode treeNode) {
        treeNodes.add(treeNode);
    }

    public void removeTreeNode(TreeNode treeNode) {
        treeNodes.remove(treeNode);
    }

    public void addAction(Action action) {
        actions.add(action);
    }

    public void removeAction(Action action) {
        actions.remove(action);
    }
}
