package com.skshieldus.esecurity.controller.view.sample;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sample")
public class SampleViewController {

    @RequestMapping("/list")
    public String main(Model model) {
        return "tiles:/sample/sample_list";
    }

    @RequestMapping("/view")
    public String view(Model model, HttpServletRequest request) {
        String parameter = request.getParameter("rnum");
        model.addAttribute("rnum", parameter);
        return "tiles:/sample/sample_view";
    }

}

