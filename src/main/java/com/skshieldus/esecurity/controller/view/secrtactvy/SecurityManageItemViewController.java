package com.skshieldus.esecurity.controller.view.secrtactvy;

import com.skshieldus.esecurity.aspects.MenuId;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secrtactvy/securitymanageitem")
public class SecurityManageItemViewController {

    /**
     * @title 구성원 위규자 조회
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 관리사항 조회 > 사내 보안 위규자 조회
     *
     * @author hoon lee
     * @since : 2023.07. 20
     */
    @MenuId("P03020301")
    @GetMapping("/securityconcerncoemp/list")
    public String coViolationList(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securitymanageitem/securityconcerncoemp_list";
    }

    /**
     * @title 구성원 위규자 상세
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 관리사항 조회 > 사내 보안 위규자 상세
     *
     * @author hoon lee
     * @since : 2023.07. 21
     */
    @MenuId("P030203")
    @GetMapping("/securityconcerncoemp/detail")
    public String coViolationDetail(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securitymanageitem/securityconcerncoemp_detail";
    }

}
