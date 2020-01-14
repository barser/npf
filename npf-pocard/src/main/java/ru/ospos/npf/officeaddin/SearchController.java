package ru.ospos.npf.officeaddin;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.domain.document.QPocard;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.officeaddin.dto.PocardDto;
import ru.ospos.npf.officeaddin.dto.Search;

import javax.persistence.EntityManager;
import java.util.UUID;

@RestController
@RequestMapping("/pub/api/v1_0/search")
public class SearchController {

    @Autowired
    private EntityManager entityManager;

    @PostMapping
    @Transactional(readOnly = true)
    public DataResult<Search> post(@RequestBody Search search) {

        var qPocard = QPocard.pocard;

        BooleanBuilder builder = new BooleanBuilder();

        if (search.getParsedNumber() != null) builder.and(qPocard.number.eq(search.getParsedNumber()));
        if (search.getParsedDate() != null) builder.and(qPocard.date.eq(search.getParsedDate()));

        if (search.getParsedAmount() != null) builder.and(qPocard.amount.eq(search.getParsedAmount()));
        if (!StringUtils.isEmpty(search.getContragent())) builder.and(qPocard.fromName.likeIgnoreCase(search.getContragent()));

        builder.and(qPocard.date.isNotNull());
        builder.and(qPocard.amount.isNotNull());
        builder.and(qPocard.number.isNotNull());
        builder.and(qPocard.fromName.isNotNull());
        builder.and(qPocard.comments.isNotNull());

        var query = new JPAQuery<Pocard>(entityManager);

        query.from(qPocard);
        query.where(builder);

        query.orderBy(qPocard.id.desc());
        query.limit(100);

        var pocards = query.fetch();

        search.setFoundPocards(PocardDto.from(pocards));

        search.setId(UUID.randomUUID());
        search.setState("Поиск завершен.");

        return DataResult.data(search);
    }

    @GetMapping("{id}")
    public DataResult<Search> get(@PathVariable String id) {
        Search search = new Search();
        search.setId(UUID.fromString(id));
        search.setState("Поиск завершен.");
        return DataResult.data(search);
    }
}
