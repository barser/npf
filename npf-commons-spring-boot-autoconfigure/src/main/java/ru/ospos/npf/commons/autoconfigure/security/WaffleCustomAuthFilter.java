package ru.ospos.npf.commons.autoconfigure.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import waffle.spring.WindowsAuthenticationToken;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class WaffleCustomAuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final WindowsAuthenticationToken authentication = (WindowsAuthenticationToken) securityContext.getAuthentication();

        if (authentication != null) {

            authentication.getAuthorities().clear(); // before clearing authorities is correct user groups from Active Directory.
                                                     // Clearing is for the logic of this concrete mvp app
                                                     // (since common ROLE_USER is used for security)...
            authentication.getAuthorities().add(new SimpleGrantedAuthority("O"));
        }

        chain.doFilter(request, response);
    }
}
