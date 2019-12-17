package ru.ospos.npf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.commons.util.OperationResult;
import ru.ospos.npf.dto.PocardDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PocardControllerApi {

    private final PocardRepository pocardRepository;

    public PocardControllerApi(@Autowired PocardRepository pocardRepository) {
        this.pocardRepository = pocardRepository;
    }

    /**
     * Поиск платежных поручений.
     * @param pocardNumber
     * @param pocardDate
     * @return
     */
    @PostMapping("/find")
    public DataResult<PocardDto> find(String pocardNumber, LocalDate pocardDate) {

        List<Pocard> pocards = pocardRepository.findFirst3ByAmountGreaterThanEqual(BigDecimal.valueOf(100000));
        return DataResult.data(PocardDto.from(pocards));
    }

    /**
     * Загрузка подробных данных по платежному поручению.
     * @param pocardId
     * @return
     */
    @GetMapping("/details")
    public DataResult<PocardDto> getDetails(long pocardId) {
        return DataResult.data(PocardDto.from(new Pocard()));
    }

    /**
     * Привязка файла Excel к платежному поручению.
     * @param pathToExcel
     * @param pocardId
     * @return
     */
    @PostMapping("/bind")
    public OperationResult bindExcelToPocard(String pathToExcel, long pocardId) {
        return OperationResult.error("Не реализовано!");
    }

    /**
     * Передать на дальнейшую обработку.
     * @param pocardId
     * @return
     */
    @PostMapping("/ready")
    public OperationResult setReady(long pocardId) {
        return OperationResult.error("Не реализовано!");
    }
}
