package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecretBoxCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "비밀문서함 점검결과")
@Controller
@Slf4j
@RequestMapping(value = "/api/secrtactvy/secretBoxCheck", produces = { "application/json" })
public class SecretBoxCheckController {

    @Autowired
    private SecretBoxCheckService service;

    /**
     * 비밀문서함 점검결과 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    @Operation(summary = "비밀문서함 점검결과 목록 조회", description = "비밀문서함 점검결과 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecretBoxCheckList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        List<Map<String, Object>> resultList = null;

        resultList = service.selectSecretBoxCheckList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, resultList.size());
    }

    /**
     * 비밀문서함 점검결과 (상신용) EGSS 정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    @Operation(summary = "비밀문서함 점검결과 (상신용) EGSS 정보 조회", description = "비밀문서함 점검결과 (상신용) EGSS 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/egss")
    public @ResponseBody ResponseModel<Map<String, Object>> selectEGSSSecretBoxCheck(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectEGSSSecretBoxCheck(paramMap));
    }

    /**
     * 비밀문서함 점검결과 목록 조회 엑셀 저장
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    @Operation(summary = "비밀문서함 점검결과 목록 조회 엑셀 저장", description = "비밀문서함 점검결과 목록 조회 엑셀 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/excel")
    public String selectSecretBoxCheckExcelList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap,
        Model model
    ) throws EsecurityException {

        CommonXlsViewDTO commonXlsViewDTO = service.selectSecretBoxCheckExcelList(paramMap);

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 비밀문서함 점검결과 단건 점수 목록 EGSS 조회
     * TODO: [김재훈]이 API는 사용 안할 수도 있음. 확인 후 제거 요망 2022-02-18까지
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    @Operation(summary = "비밀문서함 점검결과 단건 점수 목록 EGSS 조회", description = "비밀문서함 점검결과 단건 점수 목록을 EGSS로부터 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/summary")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectEGSSSecretBoxCheckSummary(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectEGSSSecretBoxCheckSummary(paramMap));
    }

    /**
     * 비밀문서함 점검결과 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    @Operation(summary = "비밀문서함 점검결과 상세 조회", description = "비밀문서함 점검결과 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/{scboxChkApplNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecretBoxCheck(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @PathVariable("scboxChkApplNo") Integer scboxChkApplNo
    ) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, service.selectSecretBoxCheck(scboxChkApplNo));
    }

    /**
     * 비밀문서함 점검결과 상신
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    @Operation(summary = "비밀문서함 점검결과 상신", description = "비밀문서함 점검결과를 상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Boolean> insertSecretBoxCheck(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {

        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertSecretBoxCheck(paramMap));
    }

    /**
     * 비밀문서함 점검결과 승인/반려 후처리
     *
     * @author : X0115378<jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 14.
     * @param sessionInfoVO
     * @param pcScrtApplNo
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "비밀문서함 점검결과 신청 승인/반려 후처리", description = "비밀문서함 점검결과 승인/반려 후처리를 수행한다.")
    //	@ApiResponses(value = { 
    //      @ApiResponse(responseCode = "200", description = "Successfully search data"),
    //			@ApiResponse(responseCode = "404", description = "error") })
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO>  processSecretBoxCheckApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO,
    //			@RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //
    //		log.warn("SecretBoxCheck approvalProcess: " + requestDTO);
    //		return new ResponseModel<>(HttpStatus.OK, approvalPostProcess.postProcess(requestDTO));
    //	}
}

