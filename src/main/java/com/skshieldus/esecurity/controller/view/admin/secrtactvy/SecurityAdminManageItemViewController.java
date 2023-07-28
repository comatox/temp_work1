package com.skshieldus.esecurity.controller.view.admin.secrtactvy;

import com.skshieldus.esecurity.aspects.MenuId;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/secrtactvy/securityadminmanageitem")
public class SecurityAdminManageItemViewController {

    /**
     * @menuDescription 관리자 > 보안활동 > 보안 위규 담당자
     *
     * @author hoon lee
     * @since : 2023.07. 26
     */
    @MenuId("P03020713")
    @GetMapping("/secdetlemp/list")
    public String secDetlempList(HttpServletRequest request, Model model) {
        return "tiles:/admin/secrtactvy/securityadminmanageitem/secdetlemp_list_admin";
    }

    /**
     * @menuDescription 관리자 > 보안활동 > 보안 위규 담당자 상세
     *
     * @author hoon lee
     * @since : 2023.07. 26
     */
    @MenuId("P030207")
    @GetMapping("/secdetlemp/detail")
    public String secDetlempDetail(HttpServletRequest request, Model model) {
        return "tiles:/admin/secrtactvy/securityadminmanageitem/secdetlemp_detail_admin";
    }

    /**
     * @menuDescription 관리자 > 보안활동 > 구성원 보안 위규자 조회
     *
     * @author hoon lee
     * @since : 2023.07. 26
     */
    @MenuId("P03020704")
    @GetMapping("/coviolation/list")
    public String coViolationList(HttpServletRequest request, Model model) {
        return "tiles:/admin/secrtactvy/securityadminmanageitem/coviolation_list_admin";
    }

    /**
     * @menuDescription 관리자 > 보안활동 > 구성원 보안 위규자 상세
     *
     * @author hoon lee
     * @since : 2023.07. 26
     */
    @MenuId("P030207")
    @GetMapping("/coviolation/detail")
    public String coViolationDetail(HttpServletRequest request, Model model) {
        return "tiles:/admin/secrtactvy/securityadminmanageitem/coviolation_detail_admin";
    }

    /**
     * @menuDescription 관리자 > 보안활동 > 외부인 보안 위규자 조회
     *
     * @author hoon lee
     * @since : 2023.07. 26
     */
    @MenuId("P03020708")
    @GetMapping("/ioviolation/list")
    public String ioViolationList(HttpServletRequest request, Model model) {
        return "tiles:/admin/secrtactvy/securityadminmanageitem/ioviolation_list_admin";
    }

    /**
     * @menuDescription 관리자 > 보안활동 > 외부인 보안 위규자 상세
     *
     * @author hoon lee
     * @since : 2023.07. 26
     */
    @MenuId("P030207")
    @GetMapping("/ioviolation/detail")
    public String ioViolationDetail(HttpServletRequest request, Model model) {
        return "tiles:/admin/secrtactvy/securityadminmanageitem/ioviolation_detail_admin";
    }
}
