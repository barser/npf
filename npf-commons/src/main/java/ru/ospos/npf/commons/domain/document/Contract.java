package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "CONTRACTS")
@PrimaryKeyJoinColumn(name = "FK_DOCUMENT")
public class Contract extends Document{

    @Column(name = "TITLE")
    private String title;

}
