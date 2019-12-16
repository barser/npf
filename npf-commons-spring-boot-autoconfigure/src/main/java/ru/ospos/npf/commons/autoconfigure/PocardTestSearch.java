package ru.ospos.npf.commons.autoconfigure;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.ospos.npf.commons.domain.Pocard;

import java.math.BigDecimal;
import java.util.List;

public interface PocardTestSearch extends CrudRepository<Pocard, Long> {

    List<Pocard> findByAmountGreaterThanEqual(BigDecimal amount);

    @Query(value = "SELECT coalesce(max(p.fk_document), 0) FROM pocards p", nativeQuery = true)
    Long getMaxId();
}
