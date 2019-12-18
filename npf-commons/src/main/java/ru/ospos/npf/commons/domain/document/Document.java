package ru.ospos.npf.commons.domain.document;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "DOCUMENTS")
public class Document {

    @Id
    @SequenceGenerator(name = "documentSeqGenerator", sequenceName = "DOCUMENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documentSeqGenerator")
    private Long id;

    private String title;

    private String barcode;

    @Column(name = "ABSTRACT")
    private String abstractText;

    private String resolution;




}
