package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;

import ru.ospos.npf.commons.domain.payment.PaymentType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Платежное поручение.
 */
@Getter
@Setter
@Entity
@Table(name = "POCARDS")
@PrimaryKeyJoinColumn(name = "FK_DOCUMENT")
public class Pocard extends Document {

    private static final long serialVersionUID = 2753288901641755016L;

    /**
     * Номер платежного поручения.
     */
    @Column(name = "MDOCNUMBER")
    private Long number;

    /**
     * Дата платежного поручения.
     */
    @Column(name="MDOCDATE")
    private LocalDate documentDate;

    /**
     * Дата операции.
     */
    @Column(name="MDATEOP")
    private LocalDate operationDate;

    /**
     * Сумма.
     */
    @Column(name = "MSUMMA", columnDefinition = "FLOAT")
    private BigDecimal amount;

    /**
     * Назначение платежа.
     */
    @Column(name = "MCOMMENTS")
    private String comments;

    /**
     * Наименование валюты.
     */
    @Column(name = "MVALUTA", length = 5)
    private String currency;

    /**
     * Наименование отправителя.
     */
    @Column(name = "MFROMNAME")
    private String fromName;

    /**
     * Счет отправителя.
     */
    @Column(name = "MFROMACCOUNT", length = 30)
    private String fromAccount;

    /**
     * ИНН отправителя.
     */
    @Column(name = "MFROMINN")
    private String fromInn;

    /**
     * Адрес отправителя.
     */
    @Column(name = "MFROMADDRESS", length = 50)
    private String fromAddress;

    /**
     * Банк отправителя.
     */
    @Column(name = "MFROMNAMEBANK", length = 200)
    private String fromNameBank;

    /**
     * Корр. счет банка отправителя.
     */
    @Column(name = "MFROMKSBANK", length = 30)
    private String fromKsBank;

    /**
     * БИК банка отправителя.
     */
    @Column(name = "MFROMBIKBANK", length = 15)
    private String fromBikBank;

    /**
     * КПП отправителя.
     */
    @Column(name = "MFROMKPP", length = 9)
    private String fromKpp;

    /**
     * Наименование получателя.
     */
    @Column(name = "MTONAME")
    private String toName;

    /**
     * Счет получателя.
     */
    @Column(name = "MTOACCOUNT", length = 30)
    private String toAccount;

    /**
     * ИНН получателя.
     */
    @Column(name = "MTOINN")
    private String toInn;

    /**
     * Адрес получателя.
     */
    @Column(name = "MTOADDRESS", length = 50)
    private String toAddress;

    /**
     * Наименование банка получателя.
     */
    @Column(name = "MTONAMEBANK", length = 200)
    private String toNameBank;

    /**
     * Корр.счет банка получателя.
     */
    @Column(name = "MTOKSBANK", length = 30)
    private String toKsBank;

    /**
     * БИК банка получателя.
     */
    @Column(name = "MTOBIKBANK", length = 15)
    private String toBikBank;

    /**
     * КПП получателя.
     */
    @Column(name = "MTOKPP", length = 9)
    private String toKpp;

    /**
     * Код бюджетной классификации.
     */
    @Column(name = "MKBK", length = 20)
    private String kbk;

    /**
     * Общероссийский классификатор территорий муниципальных образований.
     */
    @Column(name = "MOKTMO", length = 8)
    private String oktmo;

    /**
     * Направление платежа.
     */
    @Column(name = "MDIRECTION")
    private int direction;

    /**
     * Код статьи движения денежных средств.
     */
    @Column(name = "OTYPE", length = 9)
    private String otype;

    /**
     * Название статьи движения денежных средств.
     */
    @Column(name = "OTYPE_NAME", length = 80)
    private String otypeName;

    /**
     * Код разделителя учета.
     */
    @Column(name = "STOCKTYPE", length = 9)
    private String stocktype;

    /**
     * Название разделителя учета.
     */
    @Column(name = "STOCKTYPE_NAME", length = 50)
    private String stocktypeName;

    /**
     * ID котрагента СБУ.
     */
    @Column(name = "CONTRID", length = 50)
    private String contrid;

    /**
     * Название контрагента СБУ.
     */
    @Column(name = "CONTRAGENT", length = 150)
    private String contragent;

    /**
     * ID контрагента СУО.
     */
    @Column(name = "DWMCONTRID", length =  50)
    private String dwmContrid;

    /**
     * Название контрагента СУО.
     */
    @Column(name = "DWMCONTRAGENT", length = 150)
    private String dwmContragent;

    /**
     * Статус платежа.
     */
    @Column(name = "STATUS")
    private int status;

    /**
     * Название договора с контрагентом.
     */
    @Column(name = "DOCNAME", length = 150)
    private String docName;

    /**
     * Дополнительный комментарий, вводится при разбивке сумм по фондам.
     */
    @Column(name = "COMM", length = 1024)
    private String comm;

    /**
     * ID записи из портала Бухгалтерия.
     */
    @Column(name = "RID", length = 50)
    private String rid;

    /**
     * ID платежа в СБУ.
     */
    @Column(name = "MDOCID", length = 50)
    private String mDocId;

    /**
     * Код договора.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_CONTRACT")
    private Contract contract;

    /**
     * ID фонда из портала бухгалтерии 1 - ГФ / 2 - АПФ.
     */
    @Column(name = "FOND")
    private Integer fond;

    /**
     * Комиссия.
     */
    @Column(name = "COMMISSION", columnDefinition = "FLOAT")
    private BigDecimal commission;

    /**
     * Тип платежа.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "FK_PAY_PAYMENT_TYPE")
    private PaymentType paymentType;

    /**
     * Часть суммы для ГФПН.
     */
    @Column(name = "GFPN_SUM", nullable = false, columnDefinition = "FLOAT")
    private BigDecimal gfpnAmount;

    /**
     * Часть суммы для КИТФ.
     */
    @Column(name = "KITF_SUM", nullable = false, columnDefinition = "FLOAT")
    private BigDecimal kitfAmount;

    /**
     * Часть суммы для НАСЛ.
     */
    @Column(name = "NASL_SUM", nullable = false, columnDefinition = "FLOAT")
    private BigDecimal naslAmount;

    /**
     * Часть суммы для ПРАФ.
     */
    @Column(name = "PRAF_SUM", nullable = false, columnDefinition = "FLOAT")
    private BigDecimal prafAmount;
}
