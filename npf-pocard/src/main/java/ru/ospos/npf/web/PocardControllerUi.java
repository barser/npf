package ru.ospos.npf.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.ospos.npf.commons.dao.document.PocardRepository;

@Controller
public class PocardControllerUi {

    @Autowired
    private PocardRepository pocardRepository;

    @GetMapping("/pocard")
    public String index() {

        //long maxId = pocardRepository.getMaxId();

        //return "index";
        return "common";
    }
}
