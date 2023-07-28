package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityDailyLifeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "관리자 > 보안생활화")
@Controller
@RequestMapping(value = "/api/secrtactvy/securityDailyLife", produces = { "application/json" })
public class SecurityDailyLifeController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityDailyLifeService service;

    /**
     * 인력자원 관리 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 07.
     */
    @Operation(summary = "인력자원 관리 목록 조회", description = "인력자원 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/licenseManage/adminList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> licenseManageAdminManageList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.licenseManageAdminManageList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, resultList.size());
    }

    /**
     * 인력자원 관리 상세목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 07.
     */
    @Operation(summary = "인력자원 관리 상세목록 조회", description = "인력자원 관리 상세목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/licenseManage/empList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> licenseManageAdminManageEmpList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.licenseManageAdminManageEmpList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, service.licenseManageAdminManageEmpListCnt(paramMap));
    }

    /**
     * 인력자원 관리 상세목록 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 07.
     */
    @Operation(summary = "인력자원 관리 상세목록 삭제", description = "인력자원 관리 상세목록을 삭제한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/licenseManage/empDelete")
    public @ResponseBody ResponseModel<Boolean> licenseManageAdminManageEmpDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.licenseManageAdminManageEmpDelete(paramMap));
    }

    /**
     * 인력자원 조회 목록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 08.
     */
    @Operation(summary = "인력자원 조회 목록 조회", description = "인력자원 조회 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/licenseManage/adminEmpList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> licenseManageAdminEmpList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.licenseManageAdminEmpList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, resultList.size());
    }

    /**
     * 인력자원 조회 목록 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 08.
     */
    @Operation(summary = "인력자원 조회 목록 엑셀 다운로드", description = "인력자원 조회 목록 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/licenseManage/download")
    public String licenseManageAdminEmpListExcel(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.licenseManageAdminEmpListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 자격증 관리 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 09.
     */
    @Operation(summary = "자격증 관리 목록 조회", description = "자격증 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/licenseManage/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> licenseManageList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.licenseManageList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, service.licenseManageListCnt(paramMap));
    }

    /**
     * 자격증 관리 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 09.
     */
    @Operation(summary = "자격증 삭제", description = "자격증을 삭제한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/licenseManage/remove")
    public @ResponseBody ResponseModel<Boolean> licenseManageRemove(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.licenseManageRemove(paramMap));
    }

    /**
     * 자격증 관리 목록 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 09.
     */
    @Operation(summary = "자격증 등록", description = "자격증을 등록 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/licenseManage/insert")
    public @ResponseBody ResponseModel<Boolean> licenseManageInsert(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.licenseManageInsert(paramMap));
    }

}

