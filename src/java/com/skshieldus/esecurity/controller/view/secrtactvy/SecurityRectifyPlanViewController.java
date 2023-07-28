package com.skshieldus.esecurity.controller.view.secrtactvy;

import com.skshieldus.esecurity.aspects.MenuId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/secrtactvy/securityrectifyplan/rectifyplan")
public class SecurityRectifyPlanViewController {

    @GetMapping("/index")
    @MenuId("P03010401")
    public String rectifyplanIndex(Model model) {
        return "tiles:/secrtactvy/securityrectifyplan/rectifyplan_index";
    }

    @GetMapping("/list")
    @MenuId("P03010403")
    public String rectifyplanList(Model model) {
        return "tiles:/secrtactvy/securityrectifyplan/rectifyplan_list";
    }

    @GetMapping("/detail")
    @MenuId("P03010404")
    public String rectifyplanDetail(Model model) {
        return "tiles:/secrtactvy/securityrectifyplan/rectifyplan_detail";
    }

    @GetMapping("/detail2")
    @MenuId("P03010404")
    public String rectifyplanDetail2(Model model) {
        return "tiles:/secrtactvy/securityrectifyplan/rectifyplan_detail2";
    }

    @GetMapping("/request")
    @MenuId("P03010402")
    public String rectifyplanRequest(Model model) {
        return "tiles:/secrtactvy/securityrectifyplan/rectifyplan_request";
    }

}
