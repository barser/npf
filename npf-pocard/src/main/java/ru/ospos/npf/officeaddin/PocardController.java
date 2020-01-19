package ru.ospos.npf.officeaddin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.commons.util.GenericNpfException;
import ru.ospos.npf.officeaddin.dto.PocardDto;
import ru.ospos.npf.officeaddin.repository.OfficeAttachmentMetadataRepository;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/pub/api/v1_0/pocard")
public class PocardController {

    @Autowired
    private PocardRepository pocardRepository;

    @Autowired
    private OfficeAttachmentMetadataRepository officeAttachmentMetadataRepository;

    @GetMapping
    @Transactional(readOnly = true)
    public DataResult<PocardDto> get(@RequestParam int pocardId) {

        Optional<Pocard> pocard = pocardRepository.findById(pocardId);
        if (pocard.isPresent()) {

            var oam = officeAttachmentMetadataRepository.findByPocardIn(Collections.singletonList(pocard.get()));

            return DataResult.data(PocardDto
                    .from(pocard.get(), true, oam));
        }
        throw new GenericNpfException("Ошибка при загрузке данных платежного поручения!");
    }
}
