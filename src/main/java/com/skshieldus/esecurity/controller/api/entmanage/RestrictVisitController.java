package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.RestrictVisitService;
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

@Tag(name = "거래제제 제한 API")
@Controller
@RequestMapping(value = "/api/entmanage/restrictVisit", produces = { "application/json" })
public class RestrictVisitController {

    @Autowired
    private Environment environment;

    @Autowired
    private RestrictVisitService service;

    /**
     * 업체등록 신청현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "업체등록 신청현황 목록 조회", description = "업체등록 신청현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/restrictComp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRestrictCompList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectRestrictCompList(paramMap));
    }

    /**
     * 업체등록 신청현황 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "업체등록 신청현황 상세 조회", description = "업체등록 신청현황 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/restrictComp/{ioCompId}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectRestrictCompView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable String ioCompId
    ) throws EsecurityException {
        
        paramMap.put("ioCompId", ioCompId);
        return new ResponseModel<>(HttpStatus.OK, service.selectRestrictCompView(paramMap));
    }

    /**
     * 업체등록 신청현황 제제대상 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "업체등록 신청현황 제제대상 목록 조회", description = "업체등록 신청현황 제제대상 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/restrictComp/{ioCompId}/history")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRestrictHistCompList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable String ioCompId
    ) throws EsecurityException {
        
        paramMap.put("ioCompId", ioCompId);
        return new ResponseModel<>(HttpStatus.OK, service.selectRestrictHistCompList(paramMap));
    }

    /**
     * 회원가입 신청현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "회원가입 신청현황 목록 조회", description = "회원가입 신청현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/restrictEmp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRestrictEmpList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectRestrictEmpList(paramMap));
    }

    /**
     * 회원가입 신청현황 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "회원가입 신청현황 상세 조회", description = "회원가입 신청현황 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/restrictEmp/{ioEmpId}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectRestrictEmpView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable String ioEmpId
    ) throws EsecurityException {
        
        paramMap.put("ioEmpId", ioEmpId);
        return new ResponseModel<>(HttpStatus.OK, service.selectRestrictEmpView(paramMap));
    }

    /**
     * 회원가입 신청현황 제제대상 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "회원가입 신청현황 제제대상 목록 조회", description = "회원가입 신청현황 제제대상 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/restrictEmp/{ioEmpId}/history")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRestrictHistEmpList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable String ioEmpId
    ) throws EsecurityException {
        
        paramMap.put("ioEmpId", ioEmpId);
        return new ResponseModel<>(HttpStatus.OK, service.selectRestrictHistEmpList(paramMap));
    }

    /**
     * 업체등록 해제여부 수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "업체등록 해제여부 수정", description = "업체등록 해제여부를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/restrictComp/{ioCompId}/restYn")
    public @ResponseBody ResponseModel<Boolean> updateRestrictCompResolveComp(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable String ioCompId
    ) {
        
        paramMap.put("ioCompId", ioCompId);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateRestrictCompResolveComp(paramMap));
    }

    /**
     * 회원가입 해제여부 수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    @Operation(summary = "회원가입 해제여부 수정", description = "회원가입 해제여부를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/restrictEmp/{ioEmpId}/restYn")
    public @ResponseBody ResponseModel<Boolean> updateRestrictEmpResolveEmp(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable String ioEmpId
    ) {
        
        paramMap.put("ioEmpId", ioEmpId);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateRestrictEmpResolveEmp(paramMap));
    }

}
