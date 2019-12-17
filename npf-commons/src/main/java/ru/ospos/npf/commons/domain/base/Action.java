package ru.ospos.npf.commons.domain.base;

import ru.ospos.npf.commons.domain.user.Operator;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Action {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private ActionType type;

    private String commentary;

    private LocalDateTime creationTs;

    @ManyToOne(fetch = FetchType.EAGER)
    private Operator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Operator creator;
}
