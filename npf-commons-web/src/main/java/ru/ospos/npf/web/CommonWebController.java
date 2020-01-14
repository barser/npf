package ru.ospos.npf.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonWebController {

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/")
    @Secured("ROLE_ADMIN")
    public String index(Authentication authentication) {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        securityContext.getAuthentication();
        return "index";
    }

}
