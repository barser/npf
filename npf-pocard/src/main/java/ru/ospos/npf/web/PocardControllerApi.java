package ru.ospos.npf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.domain.base.Action;
import ru.ospos.npf.commons.domain.document.Pocard;
import ru.ospos.npf.commons.domain.user.Operator;
import ru.ospos.npf.commons.util.DataResult;
import ru.ospos.npf.commons.util.OperationResult;
import ru.ospos.npf.dto.PocardDto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class PocardControllerApi {

    private final EntityManager entityManager;
    private final PocardRepository pocardRepository;

    public PocardControllerApi(@Autowired EntityManager entityManager, @Autowired PocardRepository pocardRepository) {
        this.entityManager = entityManager;
        this.pocardRepository = pocardRepository;
    }

    @GetMapping("/list")
    public DataResult<Pocard> list() {
        List<Pocard> pocards = pocardRepository.findFirst3ByAmountGreaterThanEqual(BigDecimal.valueOf(100000));
        //return DataResult.data(PocardDto.from(pocards));
        return DataResult.data(pocards);
    }


    /**
     * Поиск платежных поручений.
     *
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
     *
     * @param pocardId
     * @return
     */
    @GetMapping("/details")
    public DataResult<PocardDto> getDetails(long pocardId) {
        return DataResult.data(PocardDto.from(new Pocard()));
    }

    /**
     * Привязка файла Excel к платежному поручению.
     *
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
     *
     * @param pocardId
     * @return
     */
    @PostMapping("/ready")
    public OperationResult setReady(long pocardId) {
        return OperationResult.error("Не реализовано!");
    }

    @Transactional
    @GetMapping("/test")
    public OperationResult test() {

        new Action();

        List<Pocard> first3ByAmountGreaterThanEqual = pocardRepository.findFirst3ByAmountGreaterThanEqual(BigDecimal.valueOf(100_000));
        Operator baranov = entityManager.find(Operator.class, 172);
        baranov.setLogin("BARANOV");
        entityManager.createQuery("select d from Document d order by d.id desc")
                .setMaxResults(100)
                .getResultList();

        return OperationResult.success();
    }
}
