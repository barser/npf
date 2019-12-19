package ru.ospos.npf.commons.domain.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Пользователь системы.
 */
@Getter
@Setter
@Entity
@Table(name = "OPERATORS")
public class Operator implements Serializable {

    private static final long serialVersionUID = 8035954403056027194L;

    @Id
    @Column(name = "FK_EMPLOYEE")
    private Integer id;

    /**
     * Логин пользователя.
     */
    @Column(name = "LOGIN")
    private String login;

}
