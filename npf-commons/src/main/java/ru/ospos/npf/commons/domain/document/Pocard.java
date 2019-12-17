package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "POCARDS")
public class Pocard {

    @Id
    @Column(name = "FK_DOCUMENT")
    private Long id;

    @Column(name = "MSUMMA")
    private BigDecimal amount;
}
