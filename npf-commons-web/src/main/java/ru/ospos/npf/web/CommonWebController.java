package ru.ospos.npf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonWebController {

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
