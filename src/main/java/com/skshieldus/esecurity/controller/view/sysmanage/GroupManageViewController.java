package com.skshieldus.esecurity.controller.view.sysmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysmanage/groupmanage")
public class GroupManageViewController {

    @GetMapping("/corpmanage/list")
    @MenuId("P06020102")
    public String corpmanageList(Model model) {
        return "tiles:/sysmanage/groupmanage/corpmanage_list";
    }

    @GetMapping("/deptmanage/list")
    @MenuId("P06020202")
    public String deptmanageList(Model model) {
        return "tiles:/sysmanage/groupmanage/deptmanage_list";
    }

    @GetMapping("/deptmanage/modify")
    @MenuId("P06020201")
    public String deptmanageModify(Model model) {
        return "tiles:/sysmanage/groupmanage/deptmanage_modify";
    }

    @GetMapping("/jwmanage/list")
    @MenuId("P06020402")
    public String jwmanageList(Model model) {
        return "tiles:/sysmanage/groupmanage/jwmanage_list";
    }

    @GetMapping("/usermanage/list")
    @MenuId("P06020301")
    public String usermanageList(Model model) {
        return "tiles:/sysmanage/groupmanage/usermanage_list";
    }

    @GetMapping("/usermanage/view")
    @MenuId("P06020302")
    public String usermanageDetail(Model model) {
        return "tiles:/sysmanage/groupmanage/usermanage_view";
    }

    @GetMapping("/usermanage/request")
    @MenuId("P06020303")
    public String usermanageRequest(Model model) {
        return "tiles:/sysmanage/groupmanage/usermanage_request";
    }

    @GetMapping("/usermanage/modify")
    @MenuId("P06020303")
    public String usermanageModify(Model model) { return "tiles:/sysmanage/groupmanage/usermanage_modify"; }

    @GetMapping("/hynixuser/request")
    @MenuId("P06020307")
    public String hynixuserRequest(Model model) {
        return "tiles:/sysmanage/groupmanage/hynixuser_request";
    }

    @GetMapping("/cardkeyuser/request")
    @MenuId("P06020309")
    public String cardkeyuserRequest(Model model) {
        return "tiles:/sysmanage/groupmanage/cardkeyuser_request";
    }

    @GetMapping("/cardkeyapprmail/modify")
    @MenuId("P060205")
    public String cardkeyapprmailModify(Model model) {
        return "tiles:/sysmanage/groupmanage/cardkeyapprmail_modify";
    }

}
