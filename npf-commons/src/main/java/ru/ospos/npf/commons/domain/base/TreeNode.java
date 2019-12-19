package ru.ospos.npf.commons.domain.base;

import lombok.Getter;
import lombok.Setter;
import ru.ospos.npf.commons.domain.document.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * Узел дерева папок и работ, с которым связаны документы.
 */
@Getter
@Setter
@Entity
@Table(name = "TREENODES", schema = "CDM")
public class TreeNode implements Serializable {

    @Id
    @Column(name = "ID")
    private Integer id;

    /**
     * Класс узла дерева.
     */
    @Column(name = "SUBCLASS", length = 100)
    private String subclass;

    /**
     * Название узла дерева.
     */
    @Column(name = "TITLE")
    private String title;

    /**
     * Родительский узел.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private TreeNode parent;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "PARENT_ID")
    private Set<TreeNode> children;

    /**
     * Путь к документам папки в файловой системе.
     */
    @Column(name = "VIRTUAL_PATH")
    private String virtualPath;

    /**
     * Порядковый номер узла дерева.
     */
    @Column(name = "NODE_ORDER")
    private Integer order;

    /**
     * Тип ярлыка узла дерева.
     */
    @Column(name = "ICON_TYPE")
    private Integer iconType;

    /**
     * Флаг проверки выбранного документа для действия.
     */
    @Column(name = "ISSELECTED")
    private Boolean requiresDocumentSelection;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeNode)) return false;
        TreeNode treeNode = (TreeNode) o;
        return Objects.equals(id, treeNode.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
