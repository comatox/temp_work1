package com.skshieldus.esecurity.controller.view.secrtactvy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secrtactvy/securitymanagereport")
public class SecurityManageReportViewController {

    @GetMapping("/list")
    public String mainIndex(Model model) {
        return "tiles:/secrtactvy/securitymanagereport_list";
    }

}
