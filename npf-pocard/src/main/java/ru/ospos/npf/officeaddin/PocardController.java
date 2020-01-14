package ru.ospos.npf.officeaddin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.officeaddin.dto.PocardDto;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/pub/api/v1_0/pocard")
public class PocardController {

    @Autowired
    private PocardRepository pocardRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public DataResult<PocardDto> get(@RequestParam int pocardId) {

        Optional<Pocard> pocard = pocardRepository.findById(pocardId);
        if (pocard.isPresent()) {
            return DataResult.data(PocardDto
                    .fromDetailed(pocard.get(), Collections.singletonList("1111"), "OK", 123, "TEST"));
        }
        throw new RuntimeException("TODO");

    }
}
