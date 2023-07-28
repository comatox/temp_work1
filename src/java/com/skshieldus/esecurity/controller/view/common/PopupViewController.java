package com.skshieldus.esecurity.controller.view.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/popup")
public class PopupViewController {

    @GetMapping("/findemployee")
    public String findemployeeIndex(Model model) {
        return "tiles:/common/popup/findemployee";
    }

    @GetMapping("/findioemployeeviolation")
    public String findioemployeeIndex(Model model) {
        return "tiles:/common/popup/findioemployee_violation";
    }

    @GetMapping("/findapproval")
    public String findapproval(Model model) {
        return "tiles:/common/popup/findapproval";
    }

    @GetMapping("/searchzipcode")
    public String searchzipcodeIndex(Model model) {
        return "tiles:/common/popup/searchzipcode";
    }

}
