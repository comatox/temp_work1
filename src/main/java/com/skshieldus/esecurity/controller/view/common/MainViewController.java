package com.skshieldus.esecurity.controller.view.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainViewController {

    @GetMapping("")
    public String mainIndex(Model model) {
        return "tiles:/main/main";
    }

    @GetMapping("/test")
    public String testIndex(Model model) {
        return "tiles:/main/test";
    }
}
