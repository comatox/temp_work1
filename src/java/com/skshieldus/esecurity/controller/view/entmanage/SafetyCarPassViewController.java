package com.skshieldus.esecurity.controller.view.entmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/entmanage/safetycarpass")
public class SafetyCarPassViewController {

    @GetMapping("/request")
    @MenuId("P01041301")
    public String safetycarpassRequest(Model model) { return "tiles:/entmanage/safetycarpass/safetycarpass_request"; }

    @GetMapping("/list")
    @MenuId("P01041302")
    public String safetycarpassList(Model model) { return "tiles:/entmanage/safetycarpass/safetycarpass_list"; }

    @GetMapping("/view")
    @MenuId("P01041302")
    public String safetycarpassView(Model model) { return "tiles:/entmanage/safetycarpass/safetycarpass_view"; }

}
