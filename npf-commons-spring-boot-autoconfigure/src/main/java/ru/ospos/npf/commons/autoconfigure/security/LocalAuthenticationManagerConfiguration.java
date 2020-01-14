package ru.ospos.npf.commons.autoconfigure.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@Profile("localconfig")
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class LocalAuthenticationManagerConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().withUser("test").password("{noop}test")
                .roles("ADMIN","USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login/**", "/pub/**",
                        "/actuator/health/**", "/actuator/info/**",
                        "/js/**", "/css/**", "/error/**", "/webjars/**", "/img/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new UnifondRedirectAuthenticationFilter("/unifond"),
                        BasicAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults());
    }
}
