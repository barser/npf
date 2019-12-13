package ru.ospos.npf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ospos.npf.commons.SomeConfig;

@SpringBootApplication
public class PocardApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PocardApplication.class);

    @Autowired
    private SomeConfig someConfig;

    public static void main(String[] args) {
        SpringApplication.run(PocardApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info(someConfig.check());
    }
}
