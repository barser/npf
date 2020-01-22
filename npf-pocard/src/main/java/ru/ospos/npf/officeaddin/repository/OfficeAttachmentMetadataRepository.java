package ru.ospos.npf.officeaddin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.officeaddin.domain.OfficeAttachmentMetadata;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface OfficeAttachmentMetadataRepository extends JpaRepository<OfficeAttachmentMetadata, UUID> {

    List<OfficeAttachmentMetadata> findByPocardIn(Collection<Pocard> pocards);

}
