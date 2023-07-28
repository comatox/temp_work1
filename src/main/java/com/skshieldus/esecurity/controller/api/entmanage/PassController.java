package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.PassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "사원증 출입증 접수/관리 API")
@RestController
@RequestMapping(value = "/api/entmanage/pass", produces = { "application/json" })
public class PassController {

    @Autowired
    private Environment environment;

    @Autowired
    private PassService service;

    /**
     * 상시출입증 접수현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 18.
     */
    @Operation(summary = "상시출입증 접수현황 목록 조회", description = "상시출입증 접수현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPass")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRegularPassList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassList(paramMap), service.selectRegularPassListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassList(paramMap));
        }
    }

    /**
     * 상시출입증 제재여부 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 18.
     */
    @Operation(summary = "상시출입증 제재여부 조회 및 처리", description = "상시출입증 제재여부에 대해 조회 및 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPass/chkRestrict")
    public @ResponseBody ResponseModel<String> selectRegularPassChkRestrict(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassChkRestrict(paramMap));
    }

    /**
     * 상시출입증 접수 상신
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 20.
     */
    @Operation(summary = "상시출입증 접수 상신", description = "상시출입증 접수 정보를 상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPass")
    public @ResponseBody ResponseModel<Boolean> approvalRegularPass(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.approvalRegularPass(paramMap));
    }

    /**
     * 상시출입증 접수 반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 20.
     */
    @Operation(summary = "상시출입증 접수 반려", description = "상시출입증 접수 정보를 반려한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPass/reject")
    public @ResponseBody ResponseModel<Boolean> rejectRegularPass(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.rejectRegularPass(paramMap));
    }

    /**
     * 상시출입증 접수 통합결재 후처리
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     */
    //	@Operation(summary = "상시출입증 접수 통합결재 후처리", description = "상시출입증 접수 통합결재 후처리")
    //	@ApiResponses(value = {
    // @ApiResponse(responseCode = "200", description = "Successfully insert data"),
    // @ApiResponse(responseCode = "404", description = "error") })
    //	@PostMapping(value = "/regularPass/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processRegularPassApproval(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO) throws EsecurityException {
    //		return new ResponseModel<>(HttpStatus.OK, approvalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 상시출입증 관리 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "상시출입증 관리 목록 조회", description = "상시출입증 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassMng")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRegularPassMngList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassMngList(paramMap), service.selectRegularPassMngListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassMngList(paramMap));
        }
    }

    /**
     * 상시출입증 관리 상세 조회
     *
     * @param sessionInfoVO
     * @param passApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    @Operation(summary = "상시출입증 관리 상세 조회", description = "상시출입증 관리 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassMng/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectRegularPassMng(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer passApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassMng(passApplNo));
    }

    /**
     * 상시출입증 관리 변경 이력 조회
     *
     * @param sessionInfoVO
     * @param cardNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    @Operation(summary = "상시출입증 관리 변경 이력 조회", description = "상시출입증 관리 변경 이력 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassMng/history/{no}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRegularPassMngChangeHistory(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") String cardNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassMngChangeHistory(cardNo));
    }

    /**
     * 상시출입증 강제만료 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    @Operation(summary = "상시출입증 강제만료 처리", description = "상시출입증을 강제만료 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPassMng/expired")
    public @ResponseBody ResponseModel<Boolean> expiredRegularPassMng(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.expiredRegularPassMng(paramMap));
    }

    /**
     * 상시출입증 1달 기간연장 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    @Operation(summary = "상시출입증 강제만료 처리", description = "상시출입증을 강제만료 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPassMng/extend")
    public @ResponseBody ResponseModel<Boolean> extendRegularPassMng(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.extendRegularPassMng(paramMap));
    }

    /**
     * 사전 정지예외 신청현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "사전 정지예외 신청현황 목록 조회", description = "사전 정지예외 신청현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassExcpt")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRegularPassExcptList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExcptList(paramMap), service.selectRegularPassExcptListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExcptList(paramMap));
        }
    }

    /**
     * 사전 정지예외 신청현황 상세 조회
     *
     * @param sessionInfoVO
     * @param excptApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "사전 정지예외 신청현황 상세 조회", description = "사전 정지예외 신청현황 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassExcpt/detail/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectRegularPassExcptDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer excptApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExcptDetail(excptApplNo));
    }

    /**
     * 사전 정지예외 신청현황 정보 조회
     *
     * @param sessionInfoVO
     * @param excptApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "사전 정지예외 신청현황 조회(결재 상신 시)", description = "사전 정지예외 신청현황 정보(결재 상신 시)를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassExcpt/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectRegularPassExcpt(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer excptApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExcpt(excptApplNo));
    }

    /**
     * 사전 정지예외 신청 상신
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    @Operation(summary = "사전 정지예외 접수 상신", description = "사전 정지예외 신청 정보를 상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPassExcpt")
    public @ResponseBody ResponseModel<Boolean> approvalRegularPassExcpt(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.approvalRegularPassExcpt(paramMap));
    }

    /**
     * 사전 정지예외 신청 반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     */
    @Operation(summary = "사전 정지예외 신청 반려", description = "사전 정지예외 신청 정보를 반려한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPassExcpt/reject")
    public @ResponseBody ResponseModel<Boolean> rejectRegularPassExcpt(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.rejectRegularPassExcpt(paramMap));
    }

    /**
     * 사전 정지예외 신청 통합결재 후처리
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 27.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     */
    //	@Operation(summary = "사전 정지예외 신청 통합결재 후처리", description = "사전 정지예외 신청 통합결재 후처리")
    //	@ApiResponses(value = {
    // @ApiResponse(responseCode = "200", description = "Successfully insert data"),
    // @ApiResponse(responseCode = "404", description = "error") })
    //	@PostMapping(value = "/regularPassExcpt/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processRegularPassExcptApproval(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO) throws EsecurityException {
    //		return new ResponseModel<>(HttpStatus.OK, excptApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 사후 정지예외 신청현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "사후 정지예외 신청현황 목록 조회", description = "사후 정지예외 신청현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassCancel")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRegularPassCancelList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassCancelList(paramMap), service.selectRegularPassCancelListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassCancelList(paramMap));
        }
    }

    /**
     * 사후 정지예외 신청현황 정보 조회
     *
     * @param sessionInfoVO
     * @param cancelApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "사후 정지예외 신청현황 조회(결재 상신 시)", description = "사후 정지예외 신청현황 정보(결재 상신 시)를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassCancel/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectRegularPassCancel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer cancelApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassCancel(cancelApplNo));
    }

    /**
     * 사후 정지예외 신청 상신
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "사후 정지예외 상신", description = "사후 정지예외 신청 정보를 상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPassCancel")
    public @ResponseBody ResponseModel<Boolean> approvalRegularPassCancel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.approvalRegularPassCancel(paramMap));
    }

    /**
     * 사후 정지예외 신청 반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "사후 정지예외 신청 반려", description = "사후 정지예외 신청 정보를 반려한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPassCancel/reject")
    public @ResponseBody ResponseModel<Boolean> rejectRegularPassCancel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.rejectRegularPassCancel(paramMap));
    }

    /**
     * 사후 정지예외 신청 통합결재 후처리
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     */
    //	@Operation(summary = "사후 정지예외 신청 통합결재 후처리", description = "사후 정지예외 신청 통합결재 후처리")
    //	@ApiResponses(value = {
    // @ApiResponse(responseCode = "200", description = "Successfully insert data"),
    // @ApiResponse(responseCode = "404", description = "error") })
    //	@PostMapping(value = "/regularPassCancel/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processRegularPassCancelApproval(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO) throws EsecurityException {
    //		return new ResponseModel<>(HttpStatus.OK, cancelApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 도급업체 인력변경 신청현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "도급업체 인력변경 신청현황 목록 조회", description = "도급업체 인력변경 신청현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/insSubcontMove")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectInsSubcontMoveList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectInsSubcontMoveList(paramMap), service.selectInsSubcontMoveListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectInsSubcontMoveList(paramMap));
        }
    }

    /**
     * 도급업체 인력변경 신청현황 정보 조회
     *
     * @param sessionInfoVO
     * @param moveApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "도급업체 인력변경 신청현황 조회", description = "도급업체 인력변경 신청현황 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/insSubcontMove/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectInsSubcontMove(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer moveApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectInsSubcontMove(moveApplNo));
    }

    /**
     * 도급업체 인력변경 신청 승인
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "도급업체 인력변경 신청 승인", description = "도급업체 인력변경 신청 정보를 승인한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/insSubcontMove/approve")
    public @ResponseBody ResponseModel<Boolean> approveInsSubcontMove(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.approveInsSubcontMove(paramMap));
    }

    /**
     * 도급업체 인력변경 신청 반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "도급업체 인력변경 신청 반려", description = "도급업체 인력변경 신청 정보를 반려한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/insSubcontMove/reject")
    public @ResponseBody ResponseModel<Boolean> rejectInsSubcontMove(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.rejectInsSubcontMove(paramMap));
    }

    /**
     * 대표관리자 접수상신 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    @Operation(summary = "대표관리자 접수상신 목록 조회", description = "대표관리자 접수상신 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioCompCoorpVendor")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoCompCoorpVendorList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectIoCompCoorpVendorList(paramMap), service.selectIoCompCoorpVendorListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectIoCompCoorpVendorList(paramMap));
        }
    }

    /**
     * 대표관리자 접수상신 정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    @Operation(summary = "대표관리자 접수상신 조회", description = "대표관리자 접수상신 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/ioCompCoorpVendor/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectIoCompCoorpVendor(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectIoCompCoorpVendor(paramMap));
    }

    /**
     * 대표관리자 접수상신 승인
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    @Operation(summary = "대표관리자 접수상신 승인", description = "대표관리자 접수상신 정보를 승인한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/ioCompCoorpVendor/approve")
    public @ResponseBody ResponseModel<Boolean> approveIoCompCoorpVendor(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.approveIoCompCoorpVendor(paramMap));
    }

    /**
     * 대표관리자 접수상신 반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    @Operation(summary = "대표관리자 접수상신 반려", description = "대표관리자 접수상신 정보를 반려한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/ioCompCoorpVendor/reject")
    public @ResponseBody ResponseModel<Boolean> rejectIoCompCoorpVendor(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.rejectIoCompCoorpVendor(paramMap));
    }

    /**
     * 상시출입 건수 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 30.
     */
    @Operation(summary = "상시출입 사용건수 조회", description = "상시출입 건수를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/count/idCard")
    public @ResponseBody ResponseModel<Integer> selectCountIdCard(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectCountIdCard(paramMap));
    }

    /**
     * (관리자) 출입증 발급현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "(관리자) 출입증 발급현황 목록 조회", description = "(관리자) 출입증 발급현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admPass")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAdmPassList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassList(paramMap), service.selectAdmPassListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassList(paramMap));
        }
    }

    /**
     * (관리자) 협력업체 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "(관리자) 협력업체 현황 목록 조회", description = "(관리자) 협력업체 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admIoCompCoorp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAdmIoCompCoorpList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmIoCompCoorpList(paramMap), service.selectAdmIoCompCoorpListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmIoCompCoorpList(paramMap));
        }
    }

    /**
     * (관리자) 협력업체 현황 상세 조회
     *
     * @param sessionInfoVO
     * @param ioCompId
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "(관리자) 협력업체 현황 상세 조회", description = "(관리자) 협력업체 현황 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admIoCompCoorp/{id}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectAdmIoCompCoorp(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String ioCompId) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectAdmIoCompCoorp(ioCompId));
    }

    /**
     * (관리자) 협력업체 현황 > 도급업체여부 변경
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "(관리자) 협력업체 현황 > 도급업체여부 변경", description = "(관리자) 협력업체 현황 > 도급업체여부 변경")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/admIoCompCoorp/subcont")
    public @ResponseBody ResponseModel<Boolean> updateAdmIoCompCoorpSubcont(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.updateAdmIoCompCoorpSubcont(paramMap));
    }

    /**
     * (관리자) 출입증 강제만료 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "(관리자) 출입증 강제만료 현황 목록 조회", description = "(관리자) 출입증 강제만료 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admPassExpire")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAdmPassExpireList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassExpireList(paramMap), service.selectAdmPassExpireListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassExpireList(paramMap));
        }
    }

    /**
     * (관리자) 구성원 기간연장 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "(관리자) 구성원 기간연장 현황 목록 조회", description = "(관리자) 구성원 기간연장 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admPassExtend")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAdmPassExtendList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassExtendList(paramMap), service.selectAdmPassExtendListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassExtendList(paramMap));
        }
    }

    /**
     * 장기예외신청현황(산업보안) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    @Operation(summary = "장기예외신청현황(산업보안) 목록 조회", description = "장기예외신청현황(산업보안) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassExcptJang")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRegularPassExcptJangList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        paramMap.put("sLGbn", "L");
        paramMap.put("isAdmin", "Y");

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExcptJangList(paramMap), service.selectRegularPassExcptJangListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExcptJangList(paramMap));
        }
    }

    /**
     * 장기예외신청현황(산업보안) 상세 조회
     *
     * @param sessionInfoVO
     * @param excptApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    @Operation(summary = "장기예외신청현황(산업보안) 상세 조회", description = "장기예외신청현황(산업보안) 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassExcptJang/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectRegularPassExcptJang(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer excptApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExcptJang(excptApplNo));
    }

    /**
     * 장기예외신청현황(산업보안) 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    @Operation(summary = "장기예외신청현황(산업보안) 삭제", description = "장기예외신청현황(산업보안) 정보를 삭제한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPassExcptJang/delete")
    public @ResponseBody ResponseModel<Boolean> deleteRegularPassExcptJang(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.deleteRegularPassExcptJang(paramMap));
    }

    /**
     * 상시출입증 장기예외신청 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 3.
     */
    @Operation(summary = "상시출입증 장기예외신청 등록", description = "(관리자) 상시출입증 장기예외신청 정보를 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/regularPassExcptJang")
    public @ResponseBody ResponseModel<Boolean> insertRegularPassExcptJang(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertRegularPassExcptJang(paramMap));
    }

    /**
     * (관리자) 상시출입증 보안교육 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 5.
     */
    @Operation(summary = "(관리자) 상시출입증 보안교육 현황 목록 조회", description = "(관리자) 상시출입증 보안교육 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admPassSecEdu")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAdmPassSecEduList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassSecEduList(paramMap), service.selectAdmPassSecEduListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassSecEduList(paramMap));
        }
    }

    /**
     * (관리자) 상시출입증 보안교육 상세 조회
     *
     * @param sessionInfoVO
     * @param passApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 5.
     */
    @Operation(summary = "(관리자) 상시출입증 보안교육 상세 조회", description = "(관리자) 상시출입증 보안교육 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admPassSecEdu/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectAdmPassSecEdu(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer passApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectAdmPassSecEdu(passApplNo));
    }

    /**
     * (관리자) 상시출입증 보안교육 - 교육이수처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 7.
     */
    @Operation(summary = "(관리자) 상시출입증 보안교육 - 교육이수처리", description = "(관리자) 상시출입증 보안교육 - 교육이수 처리 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/admPassSecEdu/isuProc/update")
    public @ResponseBody ResponseModel<Boolean> updateAdmPassSecEduIsuProc(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.updateAdmPassSecEduIsuProc(paramMap));
    }

    /**
     * (관리자) 상시출입증 보안교육 - 위규등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 7.
     */
    @Operation(summary = "(관리자) 상시출입증 보안교육 - 위규등록", description = "(관리자) 상시출입증 보안교육 - 위규등록 처리 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/admPassSecEdu/violation/regist")
    public @ResponseBody ResponseModel<Boolean> registAdmPassSecEduViolation(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.registAdmPassSecEduViolation(paramMap));
    }

    /**
     * (관리자) 상시출입증 보안교육 - 위규취소
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 7.
     */
    @Operation(summary = "(관리자) 상시출입증 보안교육 - 위규취소", description = "(관리자) 상시출입증 보안교육 - 위규취소 처리 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/admPassSecEdu/violation/cancel")
    public @ResponseBody ResponseModel<Boolean> cancelAdmPassSecEduViolation(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.cancelAdmPassSecEduViolation(paramMap));
    }

    /**
     * 출입증 정지/해지 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    @Operation(summary = "출입증 정지/해지 현황 목록 조회", description = "출입증 정지/해지 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassExprHist")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRegularPassExprHistList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExprHistList(paramMap), service.selectRegularPassExprHistListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExprHistList(paramMap));
        }
    }

    /**
     * 출입증 정지/해지 현황 상세 조회
     *
     * @param sessionInfoVO
     * @param exprApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    @Operation(summary = "출입증 정지/해지 현황 상세 조회", description = "출입증 정지/해지 현황 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/regularPassExprHist/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectRegularPassExprHist(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer exprApplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectRegularPassExprHist(exprApplNo));
    }

    /**
     * 통제구역 출입권한 삭제 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    @Operation(summary = "통제구역 출입권한 삭제 현황 목록 조회", description = "통제구역 출입권한 삭제 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/specialPassCancList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSpecialPassCancList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectSpecialPassCancList(paramMap), service.selectSpecialPassCancListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectSpecialPassCancList(paramMap));
        }
    }

    /**
     * 통제구역 출입권한 자동 삭제 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    @Operation(summary = "통제구역 출입권한 자동 삭제 현황 목록 조회", description = "통제구역 출입권한 자동 삭제 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/specialPassAutoCancList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSpecialPassAutoCancList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectSpecialPassAutoCancList(paramMap), service.selectSpecialPassAutoCancListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectSpecialPassAutoCancList(paramMap));
        }
    }

}