package com.skshieldus.esecurity.controller.view.sysmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysmanage/authmanage")
public class AuthManageViewController {

    @GetMapping("/menumanage/list")
    @MenuId("P060301")
    public String menumanageList(Model model) {
        return "tiles:/sysmanage/authmanage/menumanage_list";
    }

    @MenuId("P060307")
    @GetMapping("/authmanage/list")
    public String authList(Model model) {
        return "tiles:/sysmanage/authmanage/authmanage_list";
    }

    @MenuId("P060306")
    @GetMapping("/userauth/list")
    public String userAuthList(Model model) {
        return "tiles:/sysmanage/authmanage/userauth_list";
    }

    @GetMapping("/userauth/userauthpopup")
    public String authmanagepopup(Model model) {
        return "tiles:/popup/sysmanage/authmanage/userauth_popup";
    }

    @MenuId("P060308")
    @GetMapping("/userapprauth/list")
    public String userApprAuthList(Model model) {
        return "tiles:/sysmanage/authmanage/userapprauth_list";
    }

    @MenuId("P060302")
    @GetMapping("/authmenumanage/modify")
    public String authMenuManageModify(Model model) {
        return "tiles:/sysmanage/authmanage/authmenumanage_modify";
    }

}
