package ru.ospos.npf.officeaddin;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.domain.document.QPocard;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.officeaddin.domain.OfficeAttachmentMetadata;
import ru.ospos.npf.officeaddin.dto.PocardDto;
import ru.ospos.npf.officeaddin.dto.Search;
import ru.ospos.npf.officeaddin.repository.OfficeAttachmentMetadataRepository;
import ru.ospos.npf.officeaddin.service.OfficeAddinService;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pub/api/v1_0/search")
public class SearchController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private OfficeAttachmentMetadataRepository officeAttachmentMetadataRepository;

    @Autowired
    private OfficeAddinService service;

    @PostMapping
    @Transactional(readOnly = true)
    public DataResult<Search> post(@RequestBody Search search) {

        var pocards = service.search(
                search.getParsedNumber(),
                search.getParsedDate(),
                search.getParsedAmount(),
                search.getContragent()
        );

        Map<Integer, List<OfficeAttachmentMetadata>> meta =
                officeAttachmentMetadataRepository.findByPocardIn(pocards)
                    .stream()
                    .collect(Collectors.groupingBy(oam -> oam.getPocard().getId()));

        search.setFoundPocards(PocardDto.from(pocards, meta));

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
