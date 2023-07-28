package com.skshieldus.esecurity.controller.view.entmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/entmanage/carpassadmin")
public class CarPassAdminViewController {

    @GetMapping("/iocarpassprogress/list")
    @MenuId("P01040805")
    public String iocarpassprogressList(Model model) { return "tiles:/admin/entmanage/carpassadmin/iocarpassprogress_list"; }

    @GetMapping("/iocarpassprogress/carpassreqst/popup")
    @MenuId("P01040806")
    public String iocarpassprogressPopup(Model model) { return "tiles:/popup/admin/entmanage/carpassadmin/carpassreqst_popup"; }

    @GetMapping("/carpassadmin/list")
    @MenuId("P01040809")
    public String carpassadminList(Model model) { return "tiles:/admin/entmanage/carpassadmin/carpassadmin_list"; }

    @GetMapping("/ioiccarquota/list")
    @MenuId("P01040808")
    public String ioiccarquotaList(Model model) { return "tiles:/admin/entmanage/carpassadmin/ioiccarquota_list"; }

    @GetMapping("/ioiccarquota/popup")
    public String ioiccarquotaPopup(Model model) { return "tiles:/popup/admin/entmanage/carpassadmin/ioiccarquota_popup"; }

    @GetMapping("/iocarpassinoutstts/list")
    @MenuId("P01040807")
    public String iocarpassinoutsttsList(Model model) { return "tiles:/admin/entmanage/carpassadmin/iocarpassinoutstts_list"; }

    @GetMapping("/safetycarpassprogress/list")
    @MenuId("P01040810")
    public String safetycarpassprogressList(Model model) { return "tiles:/admin/entmanage/carpassadmin/safetycarpassprogress_list"; }

    @GetMapping("/tmpcarpassprogress/list")
    @MenuId("P01040801")
    public String tmpcarpassprogressList(Model model) { return "tiles:/admin/entmanage/carpassadmin/tmpcarpassprogress_list"; }

}
