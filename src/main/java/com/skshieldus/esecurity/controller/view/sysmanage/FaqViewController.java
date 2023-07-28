package com.skshieldus.esecurity.controller.view.sysmanage;

import com.skshieldus.esecurity.aspects.MenuId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sysmanage/board/faq")
public class FaqViewController {

    @GetMapping("/list")
    @MenuId("P040102")
    public String faqList(Model model) {
        return "tiles:/sysmanage/board/faq_list";
    }

    @GetMapping("/detail")
    @MenuId("P040113")
    public String faqDetail(Model model) {
        return "tiles:/sysmanage/board/faq_detail";
    }

    @PostMapping("/request")
    @MenuId("P040109")
    public String faqRequest(Model model) {
        return "tiles:/sysmanage/board/faq_request";
    }

    @GetMapping("/modify")
    @MenuId("P040111")
    public String faqUpdate(Model model) {
        return "tiles:/sysmanage/board/faq_modify";
    }

    @GetMapping("/reply")
    @MenuId("P040112")
    public String faqReply(Model model) {
        return "tiles:/sysmanage/board/faq_reply";
    }

}
