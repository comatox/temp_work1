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
@RequestMapping("/sysmanage/board/notice")
public class NoticeViewController {

    @GetMapping("/index")
    @MenuId("P0401")
    public String noticeIndex(Model model) {
        return "tiles:/sysmanage/board/notice_index";
    }

    @GetMapping("/list")
    @MenuId("P040101")
    public String noticeList(Model model) {
        return "tiles:/sysmanage/board/notice_list";
    }

    @GetMapping("/detail")
    @MenuId("P040106")
    public String noticeDetail(Model model) {
        return "tiles:/sysmanage/board/notice_detail";
    }

    @PostMapping("/request")
    @MenuId("P040109")
    public String noticeRequest(Model model) {
        return "tiles:/sysmanage/board/notice_request";
    }

    @GetMapping("/modify")
    @MenuId("P040107")
    public String noticeModify(Model model) {
        return "tiles:/sysmanage/board/notice_modify";
    }

    @GetMapping("/reply")
    @MenuId("P040108")
    public String noticeReply(Model model) {
        return "tiles:/sysmanage/board/notice_reply";
    }

}
