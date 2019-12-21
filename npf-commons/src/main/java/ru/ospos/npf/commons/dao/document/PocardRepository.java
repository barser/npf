package ru.ospos.npf.commons.dao.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.ospos.npf.commons.domain.document.Pocard;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PocardRepository extends JpaRepository<Pocard, Long>, QuerydslPredicateExecutor<Pocard> {

    List<Pocard> findFirst3ByAmountGreaterThanEqual(BigDecimal amount);

    @Query(value = "SELECT coalesce(max(p.fk_document), 0) FROM pocards p", nativeQuery = true)
    Long getMaxId();
}
