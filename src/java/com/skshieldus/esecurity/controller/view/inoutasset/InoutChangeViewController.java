package com.skshieldus.esecurity.controller.view.inoutasset;

import com.skshieldus.esecurity.aspects.MenuId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inoutasset/inoutchange")
public class InoutChangeViewController {

    @GetMapping("/indatechange/list")
    @MenuId("P08010601")
    public String indatechangeList(Model model) {
        return "tiles:/inoutasset/inoutchange/indatechange_list";
    }

    @GetMapping("/indatechange/detail")
    @MenuId("P08010601")
    public String indatechangeDetail(Model model) {
        return "tiles:/inoutasset/inoutchange/indatechange_detail";
    }

    @GetMapping("/indatechange/request")
    @MenuId("P08010608")
    public String indatechangeRequest(Model model) {
        return "tiles:/inoutasset/inoutchange/indatechange_request";
    }

    @GetMapping("/empchange/list")
    @MenuId("P08010603")
    public String empchangeList(Model model) {
        return "tiles:/inoutasset/inoutchange/empchange_list";
    }

    @GetMapping("/empchange/detail")
    @MenuId("P08010603")
    public String empchangeDetail(Model model) {
        return "tiles:/inoutasset/inoutchange/empchange_detail";
    }

    @GetMapping("/empchange/request")
    @MenuId("P08010609")
    public String empchangeRequest(Model model) {
        return "tiles:/inoutasset/inoutchange/empchange_request";
    }

    @GetMapping("/inoutkndchange/list")
    @MenuId("P08010604")
    public String inoutkndchangeList(Model model) {
        return "tiles:/inoutasset/inoutchange/inoutkndchange_list";
    }

    @GetMapping("/inoutkndchange/detail")
    @MenuId("P08010604")
    public String inoutkndchangeDetail(Model model) {
        return "tiles:/inoutasset/inoutchange/inoutkndchange_detail";
    }

    @GetMapping("/inoutkndchange/request")
    @MenuId("P08010610")
    public String inoutkndchangeRequest(Model model) {
        return "tiles:/inoutasset/inoutchange/inoutkndchange_request";
    }

    @GetMapping("/inoutkndchange2/list")
    @MenuId("P08010611")
    public String inoutkndchange2List(Model model) {
        return "tiles:/inoutasset/inoutchange/inoutkndchange2_list";
    }

    @GetMapping("/inoutkndchange2/detail")
    @MenuId("P08010611")
    public String inoutkndchange2Detail(Model model) {
        return "tiles:/inoutasset/inoutchange/inoutkndchange2_detail";
    }

    @GetMapping("/inoutkndchange2/request")
    @MenuId("P08010612")
    public String inoutkndchange2Request(Model model) {
        return "tiles:/inoutasset/inoutchange/inoutkndchange2_request";
    }

}