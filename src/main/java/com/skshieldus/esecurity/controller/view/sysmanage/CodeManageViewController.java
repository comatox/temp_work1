package com.skshieldus.esecurity.controller.view.sysmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysmanage/codemanage")
public class CodeManageViewController {

    @GetMapping("/groupmanage/list")
    @MenuId("P060401")
    public String groupmanageList(Model model) {
        return "tiles:/sysmanage/codemanage/groupmanage_list";
    }

    @GetMapping("/detailmanage/list")
    @MenuId("P060402")
    public String detailmanageList(Model model) {
        return "tiles:/sysmanage/codemanage/detailmanage_list";
    }

}
