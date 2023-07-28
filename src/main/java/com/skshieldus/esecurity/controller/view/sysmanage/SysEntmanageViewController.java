package com.skshieldus.esecurity.controller.view.sysmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysmanage/sysentmanage")
public class SysEntmanageViewController {

    @GetMapping("/offlimits/list")
    @MenuId("P060101")
    public String offlimitsList(Model model) {
        return "tiles:/sysmanage/sysentmanage/offlimits_list";
    }

    @GetMapping("/offlimits/view")
    @MenuId("P06010103")
    public String offlimitsView(Model model) {
        return "tiles:/sysmanage/sysentmanage/offlimits_view";
    }

    @GetMapping("/secedunotiemailmng/modify")
    @MenuId("P060106")
    public String secedunotiemailmngModify(Model model) {
        return "tiles:/sysmanage/sysentmanage/secedunotiemailmng_modify";
    }

    @GetMapping("/building/list")
    @MenuId("P06010302")
    public String buildingList(Model model) {
        return "tiles:/sysmanage/sysentmanage/building_list";
    }

    @GetMapping("/building/modify")
    @MenuId("P06010303")
    public String buildingModify(Model model) {
        return "tiles:/sysmanage/sysentmanage/building_modify";
    }

    @GetMapping("/carlimits/list")
    @MenuId("P06010202")
    public String carlimitsList(Model model) {
        return "tiles:/sysmanage/sysentmanage/carlimits_list";
    }

    @GetMapping("/carlimits/modify")
    @MenuId("P06010203")
    public String carlimitsModify(Model model) {
        return "tiles:/sysmanage/sysentmanage/carlimits_modify";
    }

}
