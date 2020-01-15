package ru.ospos.npf.commons.domain.document;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RegistrationCardFileId implements Serializable {

    private static final long serialVersionUID = -9007071586294146059L;

    @Column(name = "fk_registration_card")
    private Long registrationCardId;

    @Column(name = "fk_file")
    private Long filestorageId;

    protected RegistrationCardFileId() {
    }

    public RegistrationCardFileId(Long registrationCardId, Long filestorageId) {
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
