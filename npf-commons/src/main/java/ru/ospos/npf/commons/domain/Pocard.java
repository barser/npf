package ru.ospos.npf.commons.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "POCARDS")
public class Pocard {

    @Id
    @Column(name = "FK_DOCUMENT")
    private Long id;

    @Column(name = "MSUMMA")
    private BigDecimal amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
