package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityManageReportService;
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
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "현장 보안운영  API")
//@RestController
@Controller
@RequestMapping(value = "/api/secrtactvy/securityManageReport", produces = { "application/json" })
public class SecurityManageReportController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityManageReportService service;

    /**
     * 현장 보안운영 확인 등록 현황 조회 - securityReportList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 05
     */
    @Operation(summary = "현장 보안운영 확인 등록 현황 조회", description = "현장 보안운영 확인 등록 현황 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityReportList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityReportList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityReportList(paramMap));
    }

    /**
     * 현장 보안운영 확인 등록 현황 상세 - securityReportView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 09
     */
    @Operation(summary = "현장 보안운영 확인 등록 현황 상세", description = "현장 보안운영 확인 등록 현황 상세")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityReportView/{strtRptNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> securityReportView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        @PathVariable String strtRptNo
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        paramMap.put("strtRptNo", strtRptNo);
        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityReportView(paramMap));
    }

    /**
     * 현장 보안운영 확인 등록 - insertSecurityReport
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 08
     */
    @Operation(summary = "현장 보안운영 확인 등록 ", description = "현장 보안운영 확인 등록 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/insertSecurityReport")
    public @ResponseBody ResponseModel<Boolean> insertSecurityReport(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int insertRows = service.insertSecurityReport(paramMap);

        Boolean isSuccess = insertRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

    /**
     * 현장 보안운영 확인 수정/완료 - updateSecurityReport
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 08
     */
    @Operation(summary = "현장 보안운영 확인 수정/완료 ", description = "현장 보안운영 확인 수정/완료 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/updateSecurityReport")
    public @ResponseBody ResponseModel<Boolean> updateSecurityReport(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {

        

        int updatedRows = service.updateSecurityReport(paramMap);

        Boolean isSuccess = updatedRows > 0;
        System.out.println("# isSuccess:" + isSuccess.toString());

        return new ResponseModel<Boolean>(HttpStatus.OK, isSuccess);
    }

}
