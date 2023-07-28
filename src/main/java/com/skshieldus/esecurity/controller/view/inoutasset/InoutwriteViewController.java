package com.skshieldus.esecurity.controller.view.inoutasset;

import com.skshieldus.esecurity.aspects.MenuId;
import java.util.HashMap;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inoutasset/inoutwrite")
public class InoutwriteViewController {

    @GetMapping("/info")
    @MenuId("P08010108")
    public String info() {
        return "tiles:/inoutasset/inoutwrite/inoutwrite_info";
    }

    @GetMapping("/request")
    @MenuId("P08010103")
    public String request() {
        return "tiles:/inoutasset/inoutwrite/inoutwrite_request";
    }

    @PostMapping("/modify")
    @MenuId("P08010103")
    public String modify(@RequestBody HashMap<String, Object> paramMap, Model model) {
        if (ObjectUtils.isEmpty(paramMap.get("inoutApplNo")))
            return "redirect:/inoutasset/inoutwrite/request";

        model.addAttribute("inoutApplNo", paramMap.get("inoutApplNo"));
        return "tiles:/inoutasset/inoutwrite/inoutwrite_request";
    }

}