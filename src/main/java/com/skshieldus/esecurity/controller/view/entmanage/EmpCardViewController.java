package com.skshieldus.esecurity.controller.view.entmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entmanage/empcard")
public class EmpCardViewController {

    @GetMapping("/index")
    @MenuId("P0101")
    public String empcardIndex(Model model) {
        return "tiles:/entmanage/empcard_index";
    }

    @GetMapping("/request")
    @MenuId("P01011001")
    public String empcardRequest(Model model) {
        return "tiles:/entmanage/empcard_request";
    }

}
