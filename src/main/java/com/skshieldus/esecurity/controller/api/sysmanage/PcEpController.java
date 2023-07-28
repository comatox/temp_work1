package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.PcEpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "EP서비스센터 관리 API")
@Controller
@RequestMapping(value = "/api/sysmanage/pcep", produces = { "application/json" })
public class PcEpController {

    @Autowired
    private Environment environment;

    @Autowired
    private PcEpService service;

    @Operation(summary = "EP서비스센터 점검 목록 조회", description = "EP서비스센터 점검 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectPcEpList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectPcEpList(paramMap));
    }

    /**
     * EP서비스센터 점검 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "EP서비스센터 점검 상세 조회", description = "EP서비스센터 점검 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectPcEpView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectPcEpView(paramMap));
    }

    /**
     * EP서비스센터 반입 결과 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "EP서비스센터 반입 결과 조회", description = "EP서비스센터 반입 결과를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/in/result")
    public @ResponseBody ResponseModel<Map<String, Object>> selectInPcEpResult(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectInPcEpResult(paramMap));
    }

    /**
     * EP서비스센터 반출 결과 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "EP서비스센터 반출 결과 조회", description = "EP서비스센터 반출 결과를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/out/result")
    public @ResponseBody ResponseModel<Map<String, Object>> selectOutPcEpResult(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectOutPcEpResult(paramMap));
    }

    /**
     * EP서비스센터 반입 신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    @Operation(summary = "EP서비스센터 반입 신청", description = "EP서비스센터 반입 신청한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/in", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Boolean> saveInPcEp(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertInPcEp(paramMap));
    }

    /**
     * EP서비스센터 반출 신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    @Operation(summary = "EP서비스센터 반출 신청", description = "EP서비스센터 반출 신청한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/out", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Boolean> saveOutPcEp(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertOutPcEp(paramMap));
    }

    /**
     * EP서비스센터 업체물품 PC 건물간 이동 반출처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "EP서비스센터 업체물품 PC 건물간 이동 반출처리", description = "EP서비스센터 업체물품 PC 건물간 이동 반출처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/buildingout", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Boolean> pcEpBuildingOut(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertPcEpBuildingOut(paramMap));
    }

    /**
     * EP서비스센터 업체물품 PC 건물간 이동 반입처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "EP서비스센터 업체물품 PC 건물간 이동 반입처리", description = "EP서비스센터 업체물품 PC 건물간 이동 반입처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/buildingin", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Boolean> pcEpBuildingIn(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertPcEpBuildingIn(paramMap));
    }

    /**
     * EP서비스센터 반입점검 결과취소
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "EP서비스센터 반입점검 결과취소", description = "EP서비스센터 반입점검 결과취소한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/cancel/result", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Boolean> saveOutPcEpResultCancel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.updatePcEpResultCancel(paramMap));
    }

    /**
     * EP서비스센터 반입점검취소
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 23.
     */
    @Operation(summary = "EP서비스센터 반입점검취소", description = "EP서비스센터 반입점검취소한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/cancel/check", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<Boolean> saveOutPcEpCancelCheck(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.updatePcEpCancelCheck(paramMap));
    }

    /**
     * EP서비스센터 수령 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "EP서비스센터 수령 상세 조회", description = "EP서비스센터 수령 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/detail/receive")
    public @ResponseBody ResponseModel<Map<String, Object>> selectPcEpReceiveView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectPcEpReceiveView(paramMap));
    }

}

