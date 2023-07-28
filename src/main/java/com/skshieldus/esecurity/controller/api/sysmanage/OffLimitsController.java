package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.OffLimitsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "출입제한 관리 API")
@Controller
@RequestMapping(value = "/api/sysmanage/offlimits", produces = { "application/json" })
public class OffLimitsController {

    @Autowired
    private Environment environment;

    @Autowired
    private OffLimitsService service;

    /**
     * 사용자 목록 조회(방문객 관리)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 16.
     */
    @Operation(summary = "사용자 목록 조회(방문객 관리)", description = "사용자 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visit")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectOffLimitsList(@RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectOffLimitsList(paramMap));
    }

    /**
     * 사용자 상세 조회(방문객 관리 3344)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "사용자 상세 조회((방문객 관리 3344))", description = "사용자 상세를 조회한다.(방문객 관리 3344)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visit/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectOffLimitsVisitInfoView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectOffLimitsVisitInfoView(paramMap));
    }

    /**
     * 사용자 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "사용자 상세 조회", description = "사용자 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visit/info")
    public @ResponseBody ResponseModel<Map<String, Object>> selectOffLimitsView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectOffLimitsView(paramMap));
    }

}

