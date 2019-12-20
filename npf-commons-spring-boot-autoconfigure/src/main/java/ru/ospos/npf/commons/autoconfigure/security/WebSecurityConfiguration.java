//package ru.ospos.npf.commons.autoconfigure;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.ldap.core.DirContextOperations;
//import org.springframework.ldap.core.NameAwareAttribute;
//import org.springframework.ldap.core.support.LdapContextSource;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationManagerResolver;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
//import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
//import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
//import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
//import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
//import org.springframework.security.web.authentication.AuthenticationFilter;
//import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
////@Profile("dev,prod")
////@Configuration
//public class WebSecurityConfiguration /*extends WebSecurityConfigurerAdapter*/ {
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
//    @Bean
//    public LdapUserDetailsMapper userDetailsMapper() {
//        return new LdapUserDetailsMapper() {
//            @Override
//            public UserDetails mapUserFromContext(DirContextOperations ldapContext, String username,
//                                                  Collection<? extends GrantedAuthority> authorities) {
//
//                List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authorities);
////                NameAwareAttribute mail = (NameAwareAttribute) ldapContext.getAttributes().get("mail");
////
////                if (mail != null) {
////                    for (Object aMail : mail) {
////                        grantedAuthorities.addAll(authorityService.getAuthoritiesFromAclFiles((String) aMail));
////                    }
////                }
//                return super.mapUserFromContext(ldapContext, username, grantedAuthorities);
//            }
//        };
//    }
//
//    @Bean
//    public LdapUserDetailsService ldapUserDetailsService(@Autowired LdapContextSource ldapContextSource,
//                                                         @Autowired LdapUserDetailsMapper userDetailsMapper) {
//
//        FilterBasedLdapUserSearch search = new FilterBasedLdapUserSearch(
//                "ou=users,ou=ospos,ou=group of companies,DC=gazfond,DC=local",
//                "(&(userPrincipalName={0})(objectClass=user))", ldapContextSource);
//        search.setSearchSubtree(true);
//
//        LdapUserDetailsService userDetailsService = null;
//
//        userDetailsService = new LdapUserDetailsService(search) {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return super.loadUserByUsername(username + "@gazfond.local"); // Login with remember-me hack
//            }
//        };
//
//        userDetailsService.setUserDetailsMapper(userDetailsMapper);
//        return userDetailsService;
//    }
//
//
//
//
//    private boolean isCustomer(Authentication auth) {
//        return false;
//    }
//    private boolean isEmployee(Authentication auth) {
//        return false;
//    }
//
//    AuthenticationManager customersAuthenticationManager() {
//        return authentication -> {
//            if (isCustomer(authentication)) {
//                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
//            }
//            throw new UsernameNotFoundException(authentication.getPrincipal().toString());
//        };
//    }
//
//    AuthenticationManager employeesAuthenticationManager() {
//        return authentication -> {
//            if (isEmployee(authentication)) {
//                return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials());
//            }
//            throw new UsernameNotFoundException(authentication.getPrincipal().toString());
//        };
//    }
//
//    AuthenticationManagerResolver<HttpServletRequest> resolver() {
//        return request -> {
//            if (request.getPathInfo().startsWith("/employee")) {
//                return employeesAuthenticationManager();
//            }
//            return customersAuthenticationManager();
//        };
//    }
//
//    private AuthenticationFilter authenticationFilter() {
//        AuthenticationFilter filter = new AuthenticationFilter(
//                resolver(), new BasicAuthenticationConverter());
//        filter.setSuccessHandler((request, response, auth) -> {
//        });
//        return filter;
//    }
//
//    @Bean
//    ActiveDirectoryLdapAuthenticationProvider ldapAuthenticationProvider() {
//
//        ActiveDirectoryLdapAuthenticationProvider ldapAuthenticationProvider =
//                new ActiveDirectoryLdapAuthenticationProvider("gazfond.local", "ldap://10.1.1.20:389");
//
//        ldapAuthenticationProvider.setConvertSubErrorCodesToExceptions(true);
//        ldapAuthenticationProvider.setUseAuthenticationRequestCredentials(true);
//        ldapAuthenticationProvider.setUserDetailsContextMapper(userDetailsMapper());
//
//        return ldapAuthenticationProvider;
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(ldapAuthenticationProvider());
//    }
//
//    //@Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.authenticationProvider(ldapAuthenticationProvider()).authorizeRequests();
//
//        //http.addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class);
//    }
//
//    /*
//    private static final String LDAP_URL = "ldap://10.1.1.20:389";
//    private static final String LDAP_USERDN = "sw_server@gazfond";
//    private static final String LDAP_PASSWORD = "$J1ntR0p1n";
//    private static final String LDAP_BASE = "ou=users,ou=ospos,ou=group of companies,DC=gazfond,DC=local";
//    private static final String LDAP_SEARCH_FILTER = "(&(userPrincipalName={0})(objectClass=user))";
//
//
//
//
//
//
//    private final AuthorityService authorityService;
//    private final AccessDeniedHandler accessDeniedHandler;
//
//    @Autowired
//    public WebSecurityConfiguration(AuthorityService authorityService, AccessDeniedHandler accessDeniedHandler) {
//        this.authorityService = authorityService;
//        this.accessDeniedHandler = accessDeniedHandler;
//    }
//
//    @Bean
//    public LdapContextSource getLdapContext() {
//
//        LdapContextSource source = new LdapContextSource();
//
//        source.setUrl(LDAP_URL);
//        source.setUserDn(LDAP_USERDN);
//        source.setPassword(LDAP_PASSWORD);
//
//        return source;
//    }
//
//    // roles admin allow to access /admin/**
//    // roles user allow to access /user/**
//    // custom 403 access denied handler
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/js/**", "/css/**", "/error/**", "/webjars/**", "/img/**", "/favicon.ico").permitAll()
//                .antMatchers("/index", "/config", "/", "/report/**", "/template/**", "/period/**")
//                .hasAnyAuthority(AuthorityService.AUTHORITY_USER, AuthorityService.AUTHORITY_ADMIN)
//                .anyRequest().authenticated()
//
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll()
//                .and()
//
//                .rememberMe()
//                .rememberMeServices(rememberMeServices(ldapUserDetailsService(), persistentTokenRepository()))
//                .key("reportAppSecretKey")
//                .alwaysRemember(true)
//                .useSecureCookie(true)
//                .tokenValiditySeconds(2 * 31 * 24 * 60 * 60)
//
//                .and()
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
//    }
//
//    @Bean
//    public LdapUserDetailsService ldapUserDetailsService() {
//
//        LdapContextSource ldapContext = getLdapContext();
//        FilterBasedLdapUserSearch search = new FilterBasedLdapUserSearch(LDAP_BASE, LDAP_SEARCH_FILTER, ldapContext);
//        search.setSearchSubtree(true);
//
//        LdapUserDetailsService userDetailsService = null;
//
//        userDetailsService = new LdapUserDetailsService(search) {
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                return super.loadUserByUsername(username + "@gazfond.local"); // Login with remember-me hack
//            }
//        };
//
//        userDetailsService.setUserDetailsMapper(userDetailsMapper());
//        return userDetailsService;
//    }
//
//    @Bean
//    public PersistentTokenBasedRememberMeServices rememberMeServices(LdapUserDetailsService userDetailsService,
//                                                                     PersistentTokenRepository repository) {
//
//        PersistentTokenBasedRememberMeServices services =
//                new PersistentTokenBasedRememberMeServices("reportAppSecretKey", userDetailsService, repository);
//
//        services.setAlwaysRemember(true);
//        return services;
//    }
//
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        //JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
//        InMemoryTokenRepositoryImpl tokenRepository = new InMemoryTokenRepositoryImpl();
//        return tokenRepository;
//    }
//
//    @Bean
//    public ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
//
//        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider("gazfond.local", LDAP_URL);
//        provider.setConvertSubErrorCodesToExceptions(true);
//        provider.setUseAuthenticationRequestCredentials(true);
//        provider.setUserDetailsContextMapper(userDetailsContextMapper());
//        return provider;
//    }
//
//    @Bean
//    public UserDetailsContextMapper userDetailsContextMapper() {
//        UserDetailsContextMapper contextMapper = userDetailsMapper();
//        return contextMapper;
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//        auth.authenticationProvider(activeDirectoryLdapAuthenticationProvider());
//
//// For testing purposes: create two users, admin and user
//
////        auth.inMemoryAuthentication()
////                .withUser("user").password("password").roles("USER").authorities(AuthorityService.AUTHORITY_USER)
////                .and()
////                .withUser("admin").password("password").roles("ADMIN").authorities(AuthorityService.AUTHORITY_ADMIN);
//    }
//
//    @Bean
//    public LdapUserDetailsMapper userDetailsMapper() {
//        return new LdapUserDetailsMapper() {
//            @Override
//            public UserDetails mapUserFromContext(DirContextOperations ldapContext, String username, Collection<? extends GrantedAuthority> authorities) {
//
//                List<GrantedAuthority> grantedAuthorities = new ArrayList<>(authorities);
//                NameAwareAttribute mail = (NameAwareAttribute) ldapContext.getAttributes().get("mail");
//
//                if (mail != null) {
//                    for (Object aMail : mail) {
//                        grantedAuthorities.addAll(authorityService.getAuthoritiesFromAclFiles((String) aMail));
//                    }
//                }
//                return super.mapUserFromContext(ldapContext, username, grantedAuthorities);
//            }
//        };
//    }
//
//    */
//}
