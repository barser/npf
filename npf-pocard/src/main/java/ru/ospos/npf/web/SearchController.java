package ru.ospos.npf.web;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.domain.document.QPocard;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.dto.Search;

import javax.persistence.EntityManager;
import java.util.UUID;

@RestController
@RequestMapping("/pub/api/v1_0/search")
public class SearchController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PocardRepository pocardRepository;

    @PostMapping
    @Transactional(readOnly = true)
    public DataResult<Search> create(@RequestBody Search search) {

        var qPocard = QPocard.pocard;

        BooleanBuilder builder = new BooleanBuilder();

        if (search.getNumber() > 0) builder.and(qPocard.number.eq(search.getNumber()));
        if (search.getDate() != null) builder.and(qPocard.date.eq(search.getDate()));

        if (search.getAmount() != null) builder.and(qPocard.amount.eq(search.getAmount()));
        if (search.getContragent() != null) builder.and(qPocard.fromName.likeIgnoreCase(search.getContragent()));

        var query = new JPAQuery<Pocard>(entityManager);

        query.from(qPocard);
        query.where(builder);

        query.orderBy(qPocard.id.desc());
        query.limit(100);

        var pocards = query.fetch();
        search.setFoundPocards(pocards);

        search.setId(UUID.randomUUID());
        search.setState("Поиск завершен.");

        return DataResult.data(search);
    }

    @GetMapping("{id}")
    public DataResult<Search> read(@PathVariable String id) {
        Search search = new Search();
        search.setId(UUID.fromString(id));
        search.setState("Поиск завершен.");
        return DataResult.data(search);
    }
}
