package ru.ospos.npf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ospos.npf.commons.config.SomeConfig;
import ru.ospos.npf.commons.dao.document.PocardRepository;
import ru.ospos.npf.commons.dao.user.OperatorRepository;

@SpringBootApplication
public class PocardApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PocardApplication.class);

    @Autowired
    private SomeConfig someConfig;

    @Autowired
    private PocardRepository pocardRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    public static void main(String[] args) {
        SpringApplication.run(PocardApplication.class, args);
    }

    @Override
    public void run(String... args) {

/*
        LOGGER.info(someConfig.check());

        Long maxId = pocardRepository.getMaxId();

        Long operatorCount = operatorRepository.count();

        List<Pocard> pocards = pocardRepository.findFirst3ByAmountGreaterThanEqual(BigDecimal.valueOf(100_000));

        BigDecimal t = pocards.get(0).getAmount();

        LOGGER.info("XXXXXXX: " + t.toString());

        LOGGER.info(maxId.toString());
 */
    }
}
