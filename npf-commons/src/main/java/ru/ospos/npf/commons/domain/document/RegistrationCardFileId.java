package ru.ospos.npf.commons.domain.document;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RegistrationCardFileId implements Serializable {

    private static final long serialVersionUID = 3058234834751235530L;

    @Column(name = "fk_registration_card")
    private Integer registrationCardId;

    @Column(name = "fk_file")
    private Integer filestorageId;

    protected RegistrationCardFileId() {
    }

    public RegistrationCardFileId(Integer registrationCardId, Integer filestorageId) {
        this.registrationCardId = registrationCardId;
        this.filestorageId = filestorageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        RegistrationCardFileId that = (RegistrationCardFileId) o;
        return Objects.equals(registrationCardId, that.registrationCardId) &&
                Objects.equals(filestorageId, that.filestorageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationCardId, filestorageId);
    }
}
