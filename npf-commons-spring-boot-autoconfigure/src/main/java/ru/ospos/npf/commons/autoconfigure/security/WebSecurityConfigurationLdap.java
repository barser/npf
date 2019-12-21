package ru.ospos.npf.commons.autoconfigure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import waffle.spring.NegotiateSecurityFilter;
import waffle.spring.NegotiateSecurityFilterEntryPoint;
import waffle.spring.WindowsAuthenticationProvider;
import waffle.windows.auth.impl.WindowsAuthProviderImpl;

@Profile("!external")
@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class WebSecurityConfigurationLdap extends WebSecurityConfigurerAdapter {

    @Value("${ospos.admin.login:admin}")
    private String adminLogin;

    @Value("${ospos.admin.password:admin}")
    private String adminPassword;

    @Autowired
    LdapContextSource ldapContextSource;

    @Autowired
    private NegotiateSecurityFilter negotiateSecurityFilter;

    @Autowired
    private NegotiateSecurityFilterEntryPoint entryPoint;

    @Autowired
    private WindowsAuthProviderImpl windowsAuthProviderImpl;

    @Bean
    public WindowsAuthenticationProvider windowsAuthenticationProvider() {
        WindowsAuthenticationProvider provider = new WindowsAuthenticationProvider();
        provider.setAuthProvider(windowsAuthProviderImpl);
        return provider;
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
                .addFilterBefore(negotiateSecurityFilter, BasicAuthenticationFilter.class)
                .httpBasic()
                .authenticationEntryPoint(entryPoint)
            .and()
                .authenticationProvider(windowsAuthenticationProvider())
                .formLogin(Customizer.withDefaults());
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.ldapAuthentication()
                .contextSource(ldapContextSource)
                .userSearchFilter("(&(sAMAccountName={0})(objectClass=user))")
                .userSearchBase("ou=users,ou=ospos")
                .groupSearchFilter("(member={0})")
                .groupSearchBase("ou=group,ou=ospos")
            .and()
                .inMemoryAuthentication()
                    .withUser(adminLogin)
                    .password("{noop}" + adminPassword)
                    .roles("ADMIN", "USER");
    }
}
