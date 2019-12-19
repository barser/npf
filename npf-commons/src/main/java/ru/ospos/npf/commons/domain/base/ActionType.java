package ru.ospos.npf.commons.domain.base;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Тип действия.
 */
@Getter
@Entity
@Table(name = "ACTION_TYPES", schema = "CDM")
public class ActionType implements Serializable {

    private static final long serialVersionUID = -7240773661337041754L;
    /**
     * Уникальный идентификатор.
     */
    @Id
    private Long id;

    /**
     * Наименование действия.
     */
    private String description;
}
