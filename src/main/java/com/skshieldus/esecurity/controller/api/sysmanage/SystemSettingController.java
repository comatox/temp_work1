package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.SystemSettingService;
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

@Tag(name = "환경설정 > 시스템설정 API")
@Controller
@RequestMapping(value = "/api/sysmanage/system", produces = { "application/json" })
public class SystemSettingController {

    @Autowired
    private Environment environment;

    @Autowired
    private SystemSettingService service;

    /**
     * 테마 관리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    @Operation(summary = "테마 조회", description = "테마 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/thema")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectThemaList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectThemaList(paramMap));
    }

    /**
     * 테마 변경
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 15.
     */
    @Operation(summary = "테마 변경", description = "테마를 변경한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/thema/update")
    public @ResponseBody ResponseModel<Boolean> updateThema(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateThema(paramMap));
    }

}

