package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityManageItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "보안 관리사항 조회 API")
//@RestController
@Controller
@RequestMapping(value = "/api/secrtactvy/securityManageItem", produces = { "application/json" })
public class SecurityManageItemController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityManageItemService service;

    /**
     * 팀내 보안 위규자 목록조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 07
     */
    @Operation(summary = "팀내 보안 위규자 목록조회", description = "팀내 보안 위규자 목록조회 한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityConcernCoEmpViolation")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityConcernCoEmpViolation(
        @Parameter(hidden = true)
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.selectSecurityConcernCoEmpViolation(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 팀내 보안 위규자 상세
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 08
     */
    @Operation(summary = "팀내 보안 위규자 상세조회", description = "팀내 보안 위규자 상세조회 한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityConcernCoEmpViolationView")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecurityConcernCoEmpViolationView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityConcernCoEmpViolationView(paramMap));
    }

    //

    /**
     * 팀내보안 위규자 목록 엑셀다운 - securityConcernCoEmpViolationExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 17
     */
    @Operation(summary = "팀내보안 위규자 목록 엑셀다운", description = "팀내보안 위규자 목록 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityConcernCoEmpViolation/download")
    public String securityConcernCoEmpViolationExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        CommonXlsViewDTO commonXlsViewDTO = service.securityConcernCoEmpViolationExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 타팀 보안담당자 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 12
     */
    @Operation(summary = "타팀 보안담당자 조회", description = "타팀 보안담당자 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityDeputyOtherTeamSecList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityDeputyOtherTeamSecList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        //System.out.println("# paramMap:" + paramMap.toString());
        List<Map<String, Object>> resultList = null;
        // String ndeptNm = paramMap.get("ndeptNm") != null ? paramMap.get("ndeptNm").toString() : null;

        if (StringUtils.isNotBlank((String) paramMap.get("ndeptNm"))) {
            resultList = service.securityDeputyOtherTeamSecList(paramMap);
        }

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    // 타팀 휴대용 전산저장장치 조회
    // List<Map<String, Object>> otherTeamPortableStorageList(Map<String, Object> paramMap);

    /**
     * 타팀 휴대용 전산저장장치 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 13
     */
    @Operation(summary = "타팀 휴대용 전산저장장치 조회", description = "타팀 휴대용 전산저장장치 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/otherTeamPortableStorageList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> otherTeamPortableStorageList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        //System.out.println("# paramMap:" + paramMap.toString());
        List<Map<String, Object>> resultList = null;
        String searchAcSerialNo = paramMap.get("searchAcSerialNo") != null
            ? paramMap.get("searchAcSerialNo").toString()
            : null; // 관리번호
        String searchSerialNo = paramMap.get("searchSerialNo") != null
            ? paramMap.get("searchSerialNo").toString()
            : null; // 시리얼번호

        if (StringUtils.isNotBlank(searchAcSerialNo) || StringUtils.isNotBlank(searchSerialNo)) {
            resultList = service.otherTeamPortableStorageList(paramMap);
        }

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    // 외부인 보안 위규자 조회

    /**
     * 외부인 보안 위규자 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 13
     */
    @Operation(summary = "외부인 보안 위규자 조회", description = "외부인 보안 위규자 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityConcernTeamViolationIoEmpMeetList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityConcernTeamViolationIoEmpMeetList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityConcernTeamViolationIoEmpMeetList(paramMap));
    }

    /**
     * 외부인 보안 위규자 조회 엑셀다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 14
     */
    @Operation(summary = "외부인 보안 위규자 조회 엑셀다운로드", description = "외부인 보안 위규자 조회 엑셀다운로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityConcernTeamViolationIoEmpMeetList/download")
    public String securityConcernTeamViolationIoEmpMeetListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.securityConcernTeamViolationIoEmpMeetListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 외부인 보안 위규자 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 13
     */
    @Operation(summary = "외부인 보안 위규자 상세 조회", description = "외부인 보안 위규자 상세 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityConcernTeamViolationIoEmpMeetView/{scIoDocNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> securityConcernTeamViolationIoEmpMeetView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String scIoDocNo
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("scIoDocNo", scIoDocNo);
        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityConcernTeamViolationIoEmpMeetView(paramMap));
    }

    // 정보보호서약서 조회
    // List<Map<String, Object>> securityPledgeList(Map<String, Object> paramMap);

    /**
     * 정보보호서약서 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 14
     */
    @Operation(summary = "정보보호서약서 조회", description = "정보보호서약서 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityPledgeList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityPledgeList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityPledgeList(paramMap));
    }

    /**
     * 정보보호서약서 조회 엑셀다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 14
     */
    @Operation(summary = "정보보호서약서 조회 엑셀다운로드", description = "정보보호서약서 조회 엑셀다운로드")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityPledgeList/download")
    public String securityPledgeListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.securityPledgeListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 구성원 정보보호서약서 동의 현황 조회
     *
     * @param sessionInfoVO
     * @param empId
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 20.
     */
    @Operation(summary = "구성원 정보보호서약서 동의 현황 조회", description = "구성원 정보보호서약서 동의 현황 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityPledge/status")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecurityPledgeStatus(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        

        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityPledgeStatusList(paramMap));
    }

    /**
     * 보안담당부서List
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 14
     */
    @Operation(summary = "보안담당부서List", description = "보안담당부서List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secDeptList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secDeptList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secDeptList(paramMap));
    }

    /**
     * 팀 보안감점 항목 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 15
     */
    @Operation(summary = "팀 보안감점 항목 조회", description = "팀 보안감점 항목 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityDeptSecMinList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityDeptSecMinList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityDeptSecMinList(paramMap));
    }

    // 팀 보안감점 항목 상세
    // Map<String, Object> securityDeptSecMinView(Map<String, Object> paramMap);

    /**
     * 팀 보안감점 항목 상세
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 15
     */
    @Operation(summary = "팀 보안감점 항목 상세 ", description = "팀 보안감점 항목 상세 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityDeptSecMinView/{scDeptSecMinNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> securityDeptSecMinView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String scDeptSecMinNo
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("scDeptSecMinNo", scDeptSecMinNo);
        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityDeptSecMinView(paramMap));
    }

    /**
     * 팀 보안감점 항목 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 15
     */
    @Operation(summary = "팀 보안감점 항목 삭제", description = "팀 보안감점 항목 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/securityDeptSecMinDelete/{scDeptSecMinNo}")
    public @ResponseBody ResponseModel<Boolean> securityDeptSecMinDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String scDeptSecMinNo
    ) {

        
        paramMap.put("scDeptSecMinNo", scDeptSecMinNo);
        System.out.println("# paramMap:" + paramMap.toString());
        int updateRows = service.securityDeptSecMinDelete(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 팀내 무선기기 조회 - wirelessReqList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 19
     */
    @Operation(summary = "팀내 무선기기 조회", description = "팀내 무선기기 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/wirelessReqList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> wirelessReqAdmList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.wirelessReqList(paramMap));
    }

    /**
     * 팀내 무선기기 조회  엑셀다운 - wirelessReqListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 19
     */
    @Operation(summary = "팀내 무선기기 조회  엑셀다운", description = "팀내 무선기기 조회  엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/wirelessReqList/download")
    public String wirelessReqListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.wirelessReqListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 팀내 무선기기 조회 상세 > 무선기기사용 신청 목록 - wirelessEqInfoReqList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 19
     */
    @Operation(summary = "팀내 무선기기 조회 상세 > 무선기기사용 신청 목록", description = "팀내 무선기기 조회 상세 > 무선기기사용 신청 목록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/wirelessEqInfoReqList/{wirelessApplNo}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> wirelessEqInfoReqList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String wirelessApplNo
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        
        paramMap.put("wirelessApplNo", wirelessApplNo);
        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.wirelessEqInfoReqList(paramMap));
    }

    /**
     * 팀 보안점수 조회 - secureEvalTeamScoreList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 20
     */
    @Operation(summary = "팀 보안점수 조회", description = "팀 보안점수 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalTeamScoreList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureEvalTeamScoreList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secureEvalTeamScoreList(paramMap));
    }

    /**
     * 팀 보안점수 조회 엑셀다운 - secureEvalTeamScoreListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 20
     */
    @Operation(summary = "팀 보안점수 조회 엑셀다운", description = "팀 보안점수 조회 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalTeamScoreList/download")
    public String secureEvalTeamScoreListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.secureEvalTeamScoreListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        /* 팀 보안점수 조회 엑셀다운로드 별도  Layer 구성  : XlsViewCustom.java */
        return "xlsViewCustom";
    }

    /**
     * 팀 보안점수 상세 점검항목 List - secureEvalItemTargetList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 21
     */
    @Operation(summary = "팀 보안점수 상세 점검항목 List", description = "팀 보안점수 상세 점검항목 List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalItemTargetList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureEvalItemTargetList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secureEvalItemTargetList(paramMap));
    }

    /**
     * 팀 보안점수 상세 점검항목 상세 - secureEvalItemDetail
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 21
     */
    @Operation(summary = "팀 보안점수 상세 점검항목 상세", description = "팀 보안점수 상세 점검항목 상세")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureEvalItemDetail/{itemSeq}")
    public @ResponseBody ResponseModel<Map<String, Object>> secureEvalItemDetail(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String itemSeq
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("itemSeq", itemSeq);
        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secureEvalItemDetail(paramMap));
    }

    /**
     * 팀내 전산저장장치조회 - secureStorageManageList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 22
     */
    @Operation(summary = "팀내 전산저장장치 조회", description = "팀내 전산저장장치 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureStorageManageList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureStorageManageList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        List<Map<String, Object>> resultList = service.secureStorageManageList(paramMap);

        //		System.out.println("# resultList:" + Integer.toString(resultList.size()));

        int totalCount = 0;
        if (resultList.size() > 0) {
            //			System.out.println("# resultList.get(0).toString:" + resultList.get(0).toString());
            //			System.out.println("# resultList.get(0).toString containsKey:" + resultList.get(0).containsKey("totalCount"));
            //			System.out.println("# resultList.get(0).toString type:" + resultList.get(0).get("totalCount").getClass().getName());
            //			System.out.println("# resultList.get(0).toString2:" + resultList.get(0).get("totalCount"));
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
            //			System.out.println("# totalCnt:" + Integer.toString(totalCount));
        }

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 팀내 전산저장장치 조회 엑셀다운 - secureStorageManageListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 22
     */
    @Operation(summary = "팀내 전산저장장치 조회 엑셀다운", description = "팀내 전산저장장치 조회 엑셀다운운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureStorageManageList/download")
    public String secureStorageManageListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.secureStorageManageListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    // 팀내 전산저장장치 실사결과 저장 - saveActualInspection

    /**
     * 팀내 전산저장장치 실사결과 저장 - saveActualInspection
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 10. 22
     */
    @Operation(summary = "팀내 전산저장장치 실사결과 저장", description = "팀내 전산저장장치 실사결과 저장")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/saveActualInspection")
    public @ResponseBody ResponseModel<Boolean> saveActualInspection(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody List<HashMap<String, Object>> paramMaps
    ) {

        

        System.out.println("# paramMaps:" + paramMaps.toString());
        int updateRows = service.saveActualInspection(paramMaps);

        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 팀내 생활보안점검 조회(보안담당자) - secureCoEmpTeamViolationList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 03
     */
    @Operation(summary = "팀내 생활보안점검 조회(보안담당자)", description = "팀내 생활보안점검 조회(보안담당자)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureCoEmpTeamViolationList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureCoEmpTeamViolationList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        List<Map<String, Object>> resultList = service.secureCoEmpTeamViolationList(paramMap);

        int totalCount = 0;
        if (resultList.size() > 0) {
            totalCount = ((BigDecimal) resultList.get(0).get("totalCount")).intValue();
        }

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
        //return new ResponseModel<>(HttpStatus.OK, service.secureCoEmpTeamViolationList(paramMap));
    }

    /**
     * 팀내 생활보안점검 조회(보안담당자 ) 엑셀다운  - secureCoEmpTeamViolationListExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 03
     */
    @Operation(summary = "팀내 생활보안점검 조회(보안담당자 ) 엑셀다운", description = "팀내 생활보안점검 조회(보안담당자 ) 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureCoEmpTeamViolationList/download")
    public String secureCoEmpTeamViolationListExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.secureCoEmpTeamViolationListExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 팀내 생활보안점검 조회(보안담당자 ) 상세 - secureCoEmpTeamViolationView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scIoDocNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 03
     */
    @Operation(summary = "팀내 생활보안점검 조회(보안담당자 ) 상세", description = "팀내 생활보안점검 조회(보안담당자 ) 상세")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureCoEmpTeamViolationView/{scDocNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> secureCoEmpTeamViolationView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String scDocNo
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("scDocNo", scDocNo);
        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secureCoEmpTeamViolationView(paramMap));
    }

    /**
     * 팀내 생활보안점검 조회(보안담당자) 이력조회 - secureCoEmpTeamViolationHist
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 03
     */
    @Operation(summary = "팀내 생활보안점검 조회(보안담당자) 이력조회", description = "팀내 생활보안점검 조회(보안담당자) 이력조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureCoEmpTeamViolationHist")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureCoEmpTeamViolationHist(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secureCoEmpTeamViolationHist(paramMap));
    }

    /**
     * 팀내 생활보안점검 조회(보안담당자) 삭제  - secureCoEmpTeamViolationDelete
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 03
     */
    @Operation(summary = "팀내 생활보안점검 조회(보안담당자) 삭제", description = "팀내 생활보안점검 조회(보안담당자) 삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secureCoEmpTeamViolationDelete/{scDocNo}")
    public @ResponseBody ResponseModel<Boolean> secureCoEmpTeamViolationDelete(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap,
        @PathVariable String scDocNo
    ) {

        
        paramMap.put("scDocNo", scDocNo);
        System.out.println("# paramMap:" + paramMap.toString());
        int updateRows = service.secureCoEmpTeamViolationDelete(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 팀내 생활보안점검 조회(보안담당자) 일괄삭제  - secureCoEmpTeamViolation/del
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author :
     * @since : 2021. 11. 03
     */
    @Operation(summary = "팀내 생활보안점검 조회(보안담당자) 일괄삭제", description = "팀내 생활보안점검 조회(보안담당자) 일괄삭제")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/SecureCoEmpTeamViolation/del")
    public @ResponseBody ResponseModel<Boolean> secureCoEmpTeamViolationDel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.secureCoEmpTeamViolationDel(paramMap));
    }

    /**
     * 팀내 생활보안점검 결과(보안담당자) 조회 - secureCoEmpTeamViolationResultList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 04
     */
    @Operation(summary = "팀내 생활보안점검 결과(보안담당자) 조회", description = "팀내 생활보안점검 결과(보안담당자) 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secureCoEmpTeamViolationResultList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secureCoEmpTeamViolationResultList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secureCoEmpTeamViolationResultList(paramMap));
    }

    /**
     * 팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항없음) - secCoEmpTeamNoViolationInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 23
     */
    @Operation(summary = "팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항없음)", description = "팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항없음)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secCoEmpTeamNoViolationInsert")
    public @ResponseBody ResponseModel<Boolean> secCoEmpTeamNoViolationInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        
        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.secCoEmpTeamNoViolationInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항 있음) - secCoEmpTeamViolationInsert
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 23
     */
    @Operation(summary = "팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항 있음)", description = "팀내 생활보안점검 등록(일반사용자) > 점검결과 저장 (지적사항 있음)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/secCoEmpTeamViolationInsert")
    public @ResponseBody ResponseModel<Boolean> secCoEmpTeamViolationInsert(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        
        paramMap.put("acIp", sessionInfoVO.getIp());
        int updateRows = service.secCoEmpTeamViolationInsert(paramMap);
        Boolean isSuccess = updateRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 문서출력량조회 List - selectSecurityPrintingList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException PrintChaserRepository (MSSQL DB)
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 14
     */
    @Operation(summary = "문서출력량조회 List", description = "문서출력량조회 List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/selectSecurityPrintingList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityPrintingList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        List<Map<String, Object>> resultList = service.selectSecurityPrintingList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 문서출력량조회 > 부서 List - selectSecurityPrintingDeptList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 14
     */
    @Operation(summary = "문서출력량조회 > 부서 List", description = "문서출력량조회 > 부서 List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/selectSecurityPrintingDeptList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityPrintingDeptList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityPrintingDeptList(paramMap));
    }

    /**
     * 문서출력량조회 List 엑셀다운 - selectSecurityPrintingExcel
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 12. 16
     */
    @Operation(summary = "문서출력량조회 List 엑셀다운", description = "문서출력량조회 List 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/selectSecurityPrintingList/download")
    public String selectSecurityPrintingExcel(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        CommonXlsViewDTO commonXlsViewDTO = service.selectSecurityPrintingExcel(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

}
