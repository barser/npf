package ru.ospos.npf.commons.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ospos.npf.commons.config.SomeConfig;

@Configuration
@ConditionalOnMissingClass(value = "oracle.jdbc.OracleDriver")
public class GreeterAutoConfigurationNoOra {

    @Bean
    @ConditionalOnMissingClass
    public SomeConfig greetingConfig() {

        return new SomeConfig("NO ORA DRIVER FOUND - USE POSTGRES");
    }
}
