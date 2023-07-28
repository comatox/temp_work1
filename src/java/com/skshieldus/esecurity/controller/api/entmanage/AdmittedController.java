package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.AdmittedService;
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

@Tag(name = "통제구역 입퇴실 API")
@Controller
@RequestMapping(value = "/api/entmanage/admitted", produces = { "application/json" })
public class AdmittedController {

    @Autowired
    private Environment environment;

    @Autowired
    private AdmittedService service;

    /**
     * 통제구역 입실 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 6.
     */
    @Operation(summary = "통제구역 입실 처리", description = "통제구역 입실 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/checkin")
    public @ResponseBody ResponseModel<Boolean> insertAdmittedReg(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertAdmittedReg(paramMap));
    }

    /**
     * 통제구역 입실 처리(방문자)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 14.
     */
    @Operation(summary = "통제구역 입실 처리(방문자)", description = "통제구역 입실 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/checkin/visitor")
    public @ResponseBody ResponseModel<Boolean> insertVisitorAdmittedReg(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertVisitorAdmittedReg(paramMap));
    }

    /**
     * 임직원 입실 체크
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 6.
     */
    @Operation(summary = "임직원 입실 체크", description = "임직원 입실여부를 체크한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/staff/check")
    public @ResponseBody ResponseModel<String> selectStaffAdmittedChk(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectStaffAdmittedChk(paramMap));
    }

    /**
     * 상위 건물 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 6.
     */
    @Operation(summary = "상위 건물 목록 조회", description = "상위 건물 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildingcontrol/up")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingControlUp(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectBuildingControlUp(paramMap));
    }

    /**
     * 상위 건물 목록 조회(상시출입객)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    @Operation(summary = "상위 건물 목록 조회(상시출입객)", description = "상위 건물 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildingcontrol/up/ext")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingControlUpExt(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectBuildingControlUpExt(paramMap));
    }

    /**
     * 하위 건물(Bay) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 6.
     */
    @Operation(summary = "하위 건물(Bay) 목록 조회", description = "하위 건물(Bay) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildingcontrol/down")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingControlDownList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectBuildingControlDownList(paramMap));
    }

    /**
     * 통제구역 퇴실 처리(임직원)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 10.
     */
    @Operation(summary = "통제구역 퇴실 처리", description = "통제구역 퇴실 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/checkout/staff")
    public @ResponseBody ResponseModel<Boolean> updateStaffCheckout(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateStaffCheckout(paramMap));
    }

    /**
     * 상시출입객 입실 정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 6.
     */
    @Operation(summary = "상시출입객 입실 정보 조회", description = "상시출입객 입실 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/always/check")
    public @ResponseBody ResponseModel<Map<String, Object>> selectAlwaysAdmittedChk(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysAdmittedChk(paramMap));
    }

    /**
     * 상시출입객퇴실 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    @Operation(summary = "상시출입객퇴실 목록 조회", description = "상시출입객퇴실 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/currentLine/always")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAlwaysCurrentLineList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectAlwaysCurrentLineList(paramMap));
    }

    /**
     * 방문객퇴실 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    @Operation(summary = "방문객퇴실 목록 조회", description = "방문객퇴실 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/currentLine/visitor")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectVisitorCurrentLineList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectVisitorCurrentLineList(paramMap));
    }

    /**
     * 통제구역 퇴실 처리(상시출입객)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 10.
     */
    @Operation(summary = "통제구역 퇴실 처리(상시출입객)", description = "통제구역 퇴실 처리를 수행한다.(multiple 처리)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/checkout/always")
    public @ResponseBody ResponseModel<Boolean> updateAlwaysCheckout(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateAlwaysCheckout(paramMap));
    }

    /**
     * 통제구역 퇴실 처리(방문객)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 10.
     */
    @Operation(summary = "통제구역 퇴실 처리(방문객)", description = "통제구역 퇴실 처리를 수행한다.(multiple 처리)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/checkout/visitor")
    public @ResponseBody ResponseModel<Boolean> updateVisitorCheckout(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateVisitorCheckout(paramMap));
    }

    /**
     * 방문객입실 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    @Operation(summary = "방문객입실 목록 조회", description = "방문객입실 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/checkin/visitor")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectVisitorAdmittedList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectVisitorAdmittedList(paramMap));
    }

    /**
     * 물품반입 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 17.
     */
    @Operation(summary = "물품반입 등록", description = "물품반입 등록을 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/items/import")
    public @ResponseBody ResponseModel<Boolean> insertItemsImport(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertItemsImport(paramMap));
    }

    /**
     * 물품반출 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 12.
     */
    @Operation(summary = "물품반출 목록 조회", description = "물품반출 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/items/export")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectItemsExportList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectItemsExportList(paramMap));
    }

    /**
     * 물품반출 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 18.
     */
    @Operation(summary = "물품반출 처리", description = "물품반출 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/items/export")
    public @ResponseBody ResponseModel<Boolean> updateItemsExport(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateItemsExport(paramMap));
    }

}
