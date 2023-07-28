package com.skshieldus.esecurity.controller.view.sysmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysmanage/sysseceducation")
public class SysSecEducationViewController {

    @GetMapping("/admeducationinfo/list")
    @MenuId("P060501")
    public String offlimitsList(Model model) {
        return "tiles:/sysmanage/sysseceducation/admeducationinfo_list";
    }

    @GetMapping("/admeducationemail/modify")
    @MenuId("P060502")
    public String offlimitsView(Model model) {
        return "tiles:/sysmanage/sysseceducation/admeducationemail_modify";
    }

}
