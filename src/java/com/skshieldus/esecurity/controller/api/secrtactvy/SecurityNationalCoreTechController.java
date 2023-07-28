package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityNationalCoreTechService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "국가핵심기술 보안진단 API")
@Controller
@RequestMapping(value = "/api/secrtactvy/securityNationalCoreTech", produces = { "application/json" })
public class SecurityNationalCoreTechController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityNationalCoreTechService service;

    /**
     * 국가핵심기술 보안진단 체크리스트 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2022. 1. 18.
     */
    @Operation(summary = "국가핵심기술 보안진단 체크리스트 목록 조회", description = "국가핵심기술 보안진단 체크리스트 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/checklist")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityNationalCoreTechChecklist(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.selectSecurityNationalCoreTechChecklist(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 국가핵심기술 보안진단 체크리스트 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2022. 1. 18.
     */
    @Operation(summary = "국가핵심기술 보안진단 체크리스트 상세 조회", description = "국가핵심기술 보안진단 체크리스트 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/checklist/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecurityNationalCoreTechChecklistView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityNationalCoreTechChecklistView(paramMap));
    }

    /**
     * 국가핵심기술 보안진단 체크리스트 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2022. 1. 18.
     */
    @Operation(summary = "국가핵심기술 보안진단 체크리스트 등록", description = "국가핵심기술 보안진단 체크리스트 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/checklist")
    public @ResponseBody ResponseModel<Boolean> insertNationalCoreTechChecklist(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertNationalCoreTechChecklist(paramMap));
    }

    /**
     * 국가핵심기술 보안진단 결과 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2022. 1. 18.
     */
    @Operation(summary = "국가핵심기술 보안진단 결과 목록 조회", description = "국가핵심기술 보안진단 결과 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityNationalCoreTechList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.selectSecurityNationalCoreTechList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 국가핵심기술 보안진단 결과 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2022. 1. 18.
     */
    @Operation(summary = "국가핵심기술 보안진단 결과 상세 조회", description = "국가핵심기술 보안진단 결과 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/view")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecurityNationalCoreTechView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityNationalCoreTechView(paramMap));
    }

    /**
     * 국가핵심기술 보안진단 결과 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2022. 1. 18.
     */
    @Operation(summary = "국가핵심기술 보안진단 결과 등록", description = "국가핵심기술 보안진단 결과 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Boolean> insertNationalCoreTech(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("crtBy", sessionInfoVO.getEmpNo());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertNationalCoreTech(paramMap));
    }

    /**
     * 국가핵심기술 보안진단 통합결재 후처리
     *
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since  : 2022. 1. 19.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "국가핵심기술 보안진단 통합결재 후처리", description = "국가핵심기술 보안진단 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processSecurityNationalCoreTechApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, securityNationalCoreTechPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 국가핵심기술 보안진단 최종 체크리스트 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121188<jinsoo2.ahn@partner.sk.com>
     * @since : 2022. 1. 20.
     */
    @Operation(summary = "국가핵심기술 보안진단 최종 체크리스트 목록 조회", description = "국가핵심기술 보안진단 최종 체크리스트 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/last")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectLastChecklist(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.selectLastChecklist(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    @Operation(summary = "기술수출유형 코드 수정", description = "기술수출유형 코드를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully update data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/code/update")
    public @ResponseBody ResponseModel<Boolean> updateCode(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        

        log.info(">>>> updateCode paramMap : " + paramMap);
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateCodeInfo(paramMap));
    }

}

