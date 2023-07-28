package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityAdminViolationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "메인_보안활동 API")
@RestController
@RequestMapping(value = "/api/secrtactvy/main/scStatInfo", produces = { "application/json" })
public class MainScStatInfoController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityAdminViolationService service;

    /**
     * 구성원 위규 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 16.
     */
    @Operation(summary = "구성원 위규 목록 조회", description = "구성원 위규 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/coOfend")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectMainScIoOfendList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectMainScCoOfendList(paramMap), service.selectMainScCoOfendListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectMainScCoOfendList(paramMap));
        }
    }

    /**
     * 외부인 위규 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 16.
     */
    @Operation(summary = "외부인 위규 목록 조회", description = "외부인 위규 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioOfend")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectMainIoViolationList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectMainScIoOfendList(paramMap), service.selectMainScIoOfendListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectMainScIoOfendList(paramMap));
        }
    }

}
