package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import ru.ospos.npf.commons.domain.base.FileStorage;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Регистрационная карта документа.
 */
@Getter
@Setter
@Entity(name = "RegistrationCard")
@Table(name = "REGISTRATION_CARDS")
@DynamicUpdate
public class RegistrationCard implements Serializable {

    private static final long serialVersionUID = 2562876180854522677L;

    @Id
    @SequenceGenerator(name = "g_registration_card", sequenceName = "REGISTRATION_CARD_SEQ", schema = "CDM", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "g_registration_card")
    private Integer id;

    /**
     * Дата создания.
     */
    @Column(name = "CREATED_DATE")
    private LocalDateTime creationTs;

    /**
     * Статус карточки.
     */
    @Column(name = "STATUS")
    private Integer state;

    private String docLinked;

    private String answerToNum;

    private LocalDateTime answerToDate;

    private LocalDateTime deadline;

    private Long regCardTempId;

    private Boolean manual;

    private Long fkDocumentTemplate;

    private Boolean ki;

    private Integer countSheetsDoc;

    private Integer countSheetsApp;

    private String themeLetter;

    private String contentLetter;

    private String recipientFio;

    private Long fkDocumentPriority;

    private Long fkDispatch;

    private Long fkDocumentSource;

    private Long fkOperator;

    private Long fkOffice;

    private Long fromOrgPerson;

    private Long recipient;

    private Integer fromOrgPersonType;

    private Integer recipientType;

    private Long fkCompany;

    private Long fkExec;

    private Long toDirect;

    private String motiwnum;

    private LocalDate motiwdate;

    @OneToMany(mappedBy = "registrationCard", cascade = CascadeType.ALL, orphanRemoval = true) // TODO уточнить
    private List<RegistrationCardFile> files = new ArrayList<>();

    public void addFile(FileStorage fileStorage) {
        var registrationCardFile = new RegistrationCardFile(this, fileStorage);
        registrationCardFile.setCreationDate(LocalDateTime.now());
        registrationCardFile.setFkOperator(0L);
        files.add(registrationCardFile);
    }

    public void removeFile(FileStorage fileStorage) {
        for (Iterator<RegistrationCardFile> it = files.iterator(); it.hasNext(); ) {
            RegistrationCardFile rcf = it.next();

            if (rcf.getRegistrationCard().equals(this) && rcf.getFileStorage().equals(fileStorage)) {
                it.remove();
                rcf.setFileStorage(null);
                rcf.setRegistrationCard(null);
            }
        }
    }
}
