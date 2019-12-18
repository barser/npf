package ru.ospos.npf.commons.domain.payment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "PAY_PAYMENT_TYPES")
public class PaymentType {

    @Id
    private long id;

    private String description;
}
