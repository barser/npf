package ru.ospos.npf.commons.domain.payment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "PAY_PAYMENT_TYPES", schema = "CDM")
public class PaymentType implements Serializable {

    private static final long serialVersionUID = -4299529218648599442L;

    @Id
    private long id;

    private String description;
}
