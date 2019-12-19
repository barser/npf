package ru.ospos.npf.commons.autoconfigure;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Дополнительная конфигурация Jackson (JSON)
 */
@Configuration
public class JacksonDatatypeConfiguration {

    @ConditionalOnMissingClass
    @Bean
    public Hibernate5Module datatypeHibernateModule() {
        return new Hibernate5Module();
    }
}
