package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.EmergencyService;
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

@Tag(name = "긴급출입 API")
@Controller
@RequestMapping(value = "/api/entmanage/emergency", produces = { "application/json" })
public class EmergencyController {

    @Autowired
    private Environment environment;

    @Autowired
    private EmergencyService service;

    /**
     * 긴급출입 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "긴급출입 등록", description = "긴급출입 정보를 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Boolean> insertEmergencyReg(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertEmergencyReg(paramMap));
    }

    /**
     * 긴급출입 수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "긴급출입 수정", description = "긴급출입 정보를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/update")
    public @ResponseBody ResponseModel<Boolean> updateEmergency(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateEmergency(paramMap));
    }

    /**
     * 긴급출입 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "긴급출입 삭제", description = "긴급출입 정보를 삭제한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/delete")
    public @ResponseBody ResponseModel<Boolean> deleteEmergency(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.deleteEmergency(paramMap));
    }

    /**
     * 긴급출입 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "긴급출입 목록 조회", description = "긴급출입 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectEmergencyList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectEmergencyList(paramMap));
    }

    /**
     * 긴급출입 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "긴급출입 상세 조회", description = "긴급출입 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/{emergencyNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectEmerencyView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer emergencyNo
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectEmerencyView(emergencyNo));
    }

    /**
     * 긴급출입(VIP) 건수 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "긴급출입(VIP) 건수 조회", description = "긴급출입(VIP) 건수를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/vipCount")
    public @ResponseBody ResponseModel<Integer> selectEmerencyVipCnt(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectEmerencyVipCnt(paramMap));
    }

}
