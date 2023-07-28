package com.skshieldus.esecurity.controller.view.secrtactvy;

import com.skshieldus.esecurity.aspects.MenuId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/secrtactvy")
public class OneDayCardViewController {

    @GetMapping("/onedaycard/list")
    @MenuId("P03020902")
    public String onedaycardList(Model model) {
        return "tiles:/secrtactvy/onedaycard/onedaycard_list";
    }

    @GetMapping("/onedaycard/request")
    @MenuId("P03020901")
    public String onedaycardRequest(Model model) {
        return "tiles:/secrtactvy/onedaycard/onedaycard_request";
    }

    @GetMapping("/onedaycard/detail")
    @MenuId("P03020903")
    public String onedaycardDetail(Model model) {
        return "tiles:/secrtactvy/onedaycard/onedaycard_detail";
    }

    @GetMapping("/onedaycardadmin/list")
    @MenuId("P01010923")
    public String admOnedaycardList(Model model) {
        return "tiles:/admin/secrtactvy/onedaycardadmin/admonedaycard_list_admin";
    }

}
