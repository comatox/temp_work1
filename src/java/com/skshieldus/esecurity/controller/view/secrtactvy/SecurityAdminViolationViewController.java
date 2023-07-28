package com.skshieldus.esecurity.controller.view.secrtactvy;

import com.skshieldus.esecurity.aspects.MenuId;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secrtactvy/securityadminviolation")
public class SecurityAdminViolationViewController {

    /**
     * @title 구성원 위규자 조회
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 구성원 위규자 조회
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 05
     */
    @MenuId("P03020806")
    @GetMapping("/coviolation/list")
    public String coViolationList(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/coviolation_list";
    }

    /**
     * @title 구성원 위규자 입력
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 구성원 위규자 상세
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 10
     */
    @MenuId("P030208")
    @GetMapping("/coviolation/detail")
    public String coViolationDetail(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/coviolation_detail";
    }

    /**
     * @title 구성원 위규자 입력
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 구성원 위규자 입력
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 10
     */
    @MenuId("P03020805")
    @GetMapping("/coviolation/request")
    public String coViolationReqest(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/coviolation_request";
    }

    /**
     * @title 외부인 위규자 조회
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 외부인 위규자 조회
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 06
     */
    @MenuId("P03020808")
    @GetMapping("/ioviolation/list")
    public String ioViolationList(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/ioviolation_list";
    }

    /**
     * @title 외부인 위규자 입력
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 외부인 위규자 입력
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 06
     */
    @MenuId("P03020807")
    @GetMapping("/ioviolation/request")
    public String ioViolationRequest(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/ioviolation_request";
    }

    /**
     * @title 구성원 위규자 입력
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 외부인 위규자 상세
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 10
     */
    @MenuId("P030208")
    @GetMapping("/ioviolation/detail")
    public String ioViolationDetail(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/ioviolation_detail";
    }

    /**
     * @title 구성원 위규자 입력
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 구성원 징계의뢰 초과자 조회
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 24
     */
    @MenuId("P03020811")
    @GetMapping("/coviolationdeadline/list")
    public String coviolationdeadlineList(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/coviolationdeadline_list";
    }

    /**
     * @title 구성원 위규자 입력
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 구성원 징계의뢰 초과자 상세
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 24
     */
    @MenuId("P030208")
    @GetMapping("/coviolationdeadline/detail")
    public String coviolationdeadlineDetail(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/coviolation_detail";
    }

    /**
     * @title 구성원 위규자 조회
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 구성원 위규자 조회(보안요원)
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 24
     */
    @MenuId("P03020809")
    @GetMapping("/coviolationsec/list")
    public String coViolationSecList(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/coviolationsec_list";
    }

    /**
     * @title 구성원 위규자 조회
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 구성원 위규자 상세(보안요원)
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 24
     */
    @MenuId("P030208")
    @GetMapping("/coviolationsec/detail")
    public String coViolationSecDetail(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/coviolationsec_detail";
    }

    /**
     * @title 외부인 위규자 조회(보안요원)
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 외부인 위규자 조회(보안요원)
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 24
     */
    @MenuId("P03020810")
    @GetMapping("/ioviolationsec/list")
    public String ioViolationSecList(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/ioviolationsec_list";
    }

    /**
     * @title 외부인 위규자 상세(보안요원)
     * @menuDescription 보안활동 > 보안생활화 > 보안 관련자 > 보안 위규자 입력 > 외부인 위규자 상세(보안요원)
     * @param request
     * @param model
     *
     * @throws EsecurityException
     * @author hoon lee
     * @since : 2023.07. 24
     */
    @MenuId("P030208")
    @GetMapping("/ioviolationsec/detail")
    public String ioViolationSecDetail(HttpServletRequest request, Model model) {
        return "tiles:/secrtactvy/securityadminviolation/ioviolationsec_detail";
    }
}
