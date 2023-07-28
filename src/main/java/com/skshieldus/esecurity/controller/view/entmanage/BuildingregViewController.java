package com.skshieldus.esecurity.controller.view.entmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/entmanage/buildingreg")
public class BuildingregViewController {

    @GetMapping("/list")
    @MenuId("P01010801")
    public String buildingregList(Model model) {
        return "tiles:/entmanage/buildingreg_list";
    }

}
