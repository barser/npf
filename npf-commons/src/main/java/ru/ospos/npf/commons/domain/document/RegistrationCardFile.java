package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;
import ru.ospos.npf.commons.domain.base.FileStorage;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "RegistrationCardFile")
@Table(name = "registration_card_files")
public class RegistrationCardFile implements Serializable {

    @EmbeddedId
    private RegistrationCardFileId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("registrationCardId")
    @JoinColumn(name = "fk_registration_card")
    private RegistrationCard registrationCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("filestorageId")
    @JoinColumn(name = "fk_file")
    private FileStorage fileStorage;

    protected RegistrationCardFile() {}

    public RegistrationCardFile(RegistrationCard registrationCard, FileStorage fileStorage) {
        this.registrationCard = registrationCard;
        this.fileStorage = fileStorage;
        this.id = new RegistrationCardFileId(registrationCard.getId(), fileStorage.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RegistrationCardFile that = (RegistrationCardFile) o;
        return Objects.equals(registrationCard, that.registrationCard) &&
                Objects.equals(fileStorage, that.fileStorage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationCard, fileStorage);
    }

    private LocalDateTime creationDate;

    private Long fkOperator; // TODO
}
