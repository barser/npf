package ru.ospos.npf.commons.domain.base;

import lombok.Getter;
import lombok.Setter;
import ru.ospos.npf.commons.domain.user.Operator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Действие в системе.
 */
@Getter
@Setter
@Entity
@Table(name = "ACTIONS")
public class Action implements Serializable {

    private static final long serialVersionUID = 714108891827742362L;

    @Id
    @SequenceGenerator(name = "g_action", sequenceName = "ACTION_SEQ", schema = "CDM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "g_action")
    private Integer id;

    /**
     * Тип действия.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ACTION_TYPE", nullable = false)
    private ActionType type;

    /**
     * Дата и время действия.
     */
    @Basic(optional = false)
    @Column(name = "ACTION_DATE", nullable = false)
    private LocalDateTime date;

    /**
     * Комментарий.
     */
    @Column(name = "COMMENTARY")
    private String commentary;

    /**
     * Дата создания.
     */
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationTs;

    /**
     * Пользователь, совершивший действие.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_OPERATOR")
    private Operator operator;
}
