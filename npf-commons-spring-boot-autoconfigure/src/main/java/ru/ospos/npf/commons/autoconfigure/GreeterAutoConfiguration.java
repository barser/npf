package ru.ospos.npf.commons.autoconfigure;

import oracle.jdbc.OracleDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ospos.npf.commons.SomeConfig;

@Configuration
@ConditionalOnClass(OracleDriver.class)
@EnableConfigurationProperties(GreeterProperties.class)
public class GreeterAutoConfiguration {

    @Autowired
    private GreeterProperties greeterProperties;

    @Bean
    @ConditionalOnMissingClass
    public SomeConfig greetingConfig() {

        String url = greeterProperties.getUrl() == null
                ? System.getProperty("ospos.greeter.url")
                : greeterProperties.getUrl();

        return new SomeConfig(url);
    }
}
