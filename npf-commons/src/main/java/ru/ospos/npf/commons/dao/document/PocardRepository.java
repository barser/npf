package ru.ospos.npf.commons.dao.document;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ospos.npf.commons.domain.document.Pocard;

import java.math.BigDecimal;
import java.util.List;

public interface PocardRepository extends CrudRepository<Pocard, Long> {

    List<Pocard> findFirst3ByAmountGreaterThanEqual(BigDecimal amount);

    @Query(value = "SELECT coalesce(max(p.fk_document), 0) FROM pocards p", nativeQuery = true)
    Long getMaxId();
}
