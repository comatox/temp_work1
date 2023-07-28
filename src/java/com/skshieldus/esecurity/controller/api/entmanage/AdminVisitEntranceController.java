package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.AdminVisitEntranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자 방문출입 API")
@RestController
@RequestMapping(value = "/api/entmanage/admin/visitEntrance", produces = { "application/json" })
public class AdminVisitEntranceController {

    @Autowired
    private Environment environment;

    @Autowired
    private AdminVisitEntranceService service;

    /**
     * 업체정보 정정 신청현황 - 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 18.
     */
    @Operation(summary = "업체정보 정정 신청현황 - 목록 조회", description = "업체정보 정정 신청 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/compInfoChg")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCompInfoChgReqList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectCompInfoChgReqList(paramMap), service.selectCompInfoChgReqListCnt(paramMap));
    }

    /**
     * 업체정보 정정 신청현황 - 상세 조회
     *
     * @param sessionInfoVO
     * @param ioCompApplNo
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 18.
     */
    @Operation(summary = "업체정보 정정 신청현황 - 상세 조회", description = "업체정보 정정 신청 현황을 상세 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/compInfoChg/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectCompInfoChgReqView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer ioCompApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectCompInfoChgReqView(ioCompApplNo));
    }

    /**
     * 업체정보 정정 신청현황 - 승인/반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 18.
     */
    @Operation(summary = "업체정보 정정 신청현황 - 승인/반려", description = "업체정보 정정 신청 현황을 승인/반려 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/compInfoChg/update")
    public @ResponseBody ResponseModel<Boolean> executeCompInfoChgReqUpdate(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeCompInfoChgReqUpdate(paramMap));
    }

    /**
     * 여권변경 신청현황 - 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "여권변경 신청현황 - 목록 조회", description = "여권변경 신청 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/passportChg")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectPassportChgReqList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectPassportChgReqList(paramMap), service.selectPassportChgReqListCnt(paramMap));
    }

    /**
     * 여권변경 신청현황 - 상세 조회
     *
     * @param sessionInfoVO
     * @param passportApplNo
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "여권변경 신청현황 - 상세 조회", description = "여권변경 신청 현황을 상세 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/passportChg/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectPassportChgReqView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer passportApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectPassportChgReqView(passportApplNo));
    }

    /**
     * 여권변경 신청현황 - 승인/반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "여권변경 신청현황 - 승인/반려", description = "여권변경 신청현황을 승인/반려 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/passportChg/update")
    public @ResponseBody ResponseModel<Boolean> executePassportChgReqUpdate(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executePassportChgReqUpdate(paramMap));
    }

    /**
     * 소속업체 이직 신청현황 - 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 20.
     */
    @Operation(summary = "소속업체 이직 신청현황 - 목록 조회", description = "소속업체 이직 신청 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitCompChg")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCompChgReqList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectCompChgReqList(paramMap), service.selectCompChgReqListCnt(paramMap));
    }

    /**
     * 소속업체 이직 신청현황 - 상세 조회
     *
     * @param sessionInfoVO
     * @param compApplNo
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "소속업체 이직 신청현황 - 상세 조회", description = "소속업체 이직 신청현황을 상세 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitCompChg/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectCompChgReqView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer compApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectCompChgReqView(compApplNo));
    }

    /**
     * 소속업체 이직 신청현황 - 승인/반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0122721<jeyeon.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "소속업체 이직 신청현황 - 승인/반려", description = "소속업체 이직 신청현황을 승인/반려 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/visitCompChg/update")
    public @ResponseBody ResponseModel<Boolean> executeCompChgReqUpdate(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.executeCompChgReqUpdate(paramMap));
    }

}
