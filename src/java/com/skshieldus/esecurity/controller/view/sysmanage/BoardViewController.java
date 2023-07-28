package com.skshieldus.esecurity.controller.view.sysmanage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysmanage/board")
public class BoardViewController {

    @GetMapping("/list")
    public String mainIndex(Model model) {
        return "tiles:/sysmanage/board_list";
    }

}
