package ru.ospos.npf.commons.domain.document;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Document {

    @Id
    private Long id;

    private String title;

    private String barcode;

    private String abstractText;

    private String resolution;




}
