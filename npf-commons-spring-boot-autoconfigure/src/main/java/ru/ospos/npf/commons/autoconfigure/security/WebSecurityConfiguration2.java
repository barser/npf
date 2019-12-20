//package ru.ospos.npf.commons.autoconfigure;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.ldap.core.LdapTemplate;
//import org.springframework.ldap.core.support.LdapContextSource;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
//
//@Configuration
//@Profile("dev,prod")
//public class WebSecurityConfiguration2 extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().fullyAuthenticated()
//                .and()
//                .formLogin();
//    }
//
//    @Bean
//    public LdapTemplate ldapTemplate(@Autowired LdapContextSource ldapContextSource) {
//        return new LdapTemplate(ldapContextSource);
//    }
//
//    @Bean
//    public LdapContextSource ldapContextSource() {
//        LdapContextSource ldapContextSource = new LdapContextSource();
//        ldapContextSource.setUrl("ldap://10.1.1.20:389");
//        ldapContextSource.setUserDn("sw_server@gazfond");
//        ldapContextSource.setPassword("$J1ntR0p1n");
//        return  ldapContextSource;
//    }
//
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.ldapAuthentication()
//                .userDnPatterns("(&(userPrincipalName={0})(objectClass=user))")
//                .groupSearchBase("ou=users,ou=ospos,ou=group of companies,dc=gazfond,dc=local")
//                .groupSearchFilter("member={0}")
//                .contextSource(ldapContextSource());
//
////
////
////        private static final String LDAP_URL = "ldap://10.1.1.20:389";
////        private static final String LDAP_USERDN = "sw_server@gazfond";
////        private static final String LDAP_PASSWORD = "$J1ntR0p1n";
////        private static final String LDAP_BASE = "ou=users,ou=ospos,ou=group of companies,DC=gazfond,DC=local";
////        private static final String LDAP_SEARCH_FILTER = "(&(userPrincipalName={0})(objectClass=user))";
//    }
//}
