package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.ReserveVisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "[출입관리] 방문객 관리 API")
@Controller
@RequestMapping(value = "/api/entmanage/reserveVisit", produces = { "application/json" })
public class ReserveVisitController {

    @Autowired
    private Environment environment;

    @Autowired
    private ReserveVisitService service;

    /**
     * 방문객관리(구성원 인증) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "방문객관리(구성원 인증) 목록 조회", description = "방문객관리(구성원 인증) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitCerti")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectVisitorCertiList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectVisitorCertiList(paramMap));
    }

    /**
     * 방문객관리(구성원 인증) 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "방문객관리(구성원 인증) 상세 조회", description = "방문객관리(구성원 인증) 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitCerti/{ioEmpId}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectVisitorCertiView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable String ioEmpId
    ) throws EsecurityException {
        
        paramMap.put("ioEmpId", ioEmpId);
        return new ResponseModel<>(HttpStatus.OK, service.selectVisitorCertiView(paramMap));
    }

    /**
     * 방문객관리(구성원 인증) 승인/반려 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "방문객관리(구성원 인증) 승인/반려 처리", description = "방문객관리(구성원 인증) 승인/반려를 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitCerti")
    public @ResponseBody ResponseModel<Boolean> insertEmpcardBuildingReg(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        // 방문객관리(구성원 인증) 승인/반려 처리
        service.updateVisitorCerti(paramMap);
        return new ResponseModel<Boolean>(HttpStatus.OK, true);
    }

    /**
     * 방문예약 신청/접수 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "방문예약 신청/접수 현황 목록 조회", description = "방문예약 신청/접수 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectVisitorProgressList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectVisitorProgressList(paramMap));
    }

    /**
     * 방문예약 신청/접수 현황 목록 조회(관리자)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "방문예약 신청/접수 현황 목록 조회(관리자)", description = "방문예약 신청/접수 현황(관리자) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/admin")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectVisitorProgressAdminList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectVisitorProgressAdminList(paramMap), service.selectVisitorProgressAdminCount(paramMap));
    }

    /**
     * 방문예약 취소 처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "방문예약 취소 처리", description = "방문예약을 취소 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitorProgress/{vstApplNo}/delete")
    public @ResponseBody ResponseModel<Boolean> deleteVisitorProgress(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.deleteVisitorProgress(paramMap));
    }

    /**
     * 방문예약 신청/접수 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "방문예약 신청/접수 상세 조회", description = "방문예약 신청/접수 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/{vstApplNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectReserveVisitViewReIO(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) throws EsecurityException {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectReserveVisitViewReIO(paramMap));
    }

    /**
     * 방문예약 신청/접수 방문객 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "방문예약 신청/접수 방문객 목록 조회", description = "방문예약 신청/접수 방문객 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/{vstApplNo}/man")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectReserveVisitVstManListIO(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) throws EsecurityException {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectReserveVisitVstManListIO(paramMap));
    }

    /**
     * 출입건물지정 목록 조회(상위)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 9.
     */
    @Operation(summary = "출입건물지정 목록 조회(상위)", description = "출입건물지정 목록을 조회한다.(상위).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/entrybuilding/top")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectInEntrytheBuildingsTop(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectInEntrytheBuildingsTop(paramMap));
    }

    /**
     * 출입건물지정 목록 조회(하위)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 9.
     */
    @Operation(summary = "출입건물지정 목록 조회(하위)", description = "출입건물지정 목록을 조회한다.(하위)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/entrybuilding/sub")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectInEntrytheBuildingsList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectInEntrytheBuildingsList(paramMap));
    }

    /**
     * 전산저장장치 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 9.
     */
    @Operation(summary = "전산저장장치 목록 조회", description = "전산저장장치 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/{vstApplNo}/selectio")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectReserveVisitSelectIOList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) throws EsecurityException {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectReserveVisitSelectIOList(paramMap));
    }

    /**
     * 출입건물 저장 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "출입건물 저장 목록 조회", description = "출입건물 저장 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/{vstApplNo}/buildio")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectReserveVisitBuildingIOList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) throws EsecurityException {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectReserveVisitBuildingIOList(paramMap));
    }

    /**
     * 청주 게이트 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "청주 게이트 목록 조회", description = "청주 게이트 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/cjgate")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectReserveVisitBuildingIOList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCjGateList(paramMap));
    }

    /**
     * 출입구역 선택 여부 수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "출입구역 선택 여부 수정", description = "출입구역 선택 여부를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitorProgress/{vstApplNo}/buildchk")
    public @ResponseBody ResponseModel<Boolean> updateReserveVisitBuildChkChg(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateReserveVisitBuildChkChg(paramMap));
    }

    /**
     * 출입건물 수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "출입건물 수정", description = "출입건물 정보를 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitorProgress/{vstApplNo}/buildio")
    public @ResponseBody ResponseModel<Boolean> updateReserveVisitBuild(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateReserveVisitBuild(paramMap));
    }

    /**
     * 방문예약 접수 반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "방문예약 접수 반려", description = "방문예약 접수를 반려 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitorProgress/{vstApplNo}/reject")
    public @ResponseBody ResponseModel<Boolean> rejectReserveVisit(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.rejectReserveVisit(paramMap));
    }

    /**
     * 방문예약 상신/승인
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 15.
     */
    @Operation(summary = "방문예약 상신/승인", description = "방문예약에 대한 상신/승인 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitorProgress/{vstApplNo}")
    public @ResponseBody ResponseModel<Boolean> insertReserveVisitApproval(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertReserveVisitApproval(paramMap));
    }

    /**
     * 방문예약 통합결재 후처리
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 16.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "방문예약 통합결재 후처리", description = "방문예약 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/visitorProgress/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processReserveVisitApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO) throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, reserveVisitApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 방문객 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "방문객 삭제", description = "방문객 정보를 삭제 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitorProgress/{vstApplNo}/{ioEmpId}/delete")
    public @ResponseBody ResponseModel<Boolean> deleteRestrictVisitVisitor(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.deleteRestrictVisitVisitor(paramMap));
    }

    /**
     * 가족방문예약 상신/승인
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "가족방문예약 상신/승인", description = "가족방문예약에 대한 상신/승인 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitorProgress/fam")
    public @ResponseBody ResponseModel<Boolean> insertReserveVisitFamApproval(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertReserveVisitFamApproval(paramMap));
    }

    /**
     * 구성원 가족 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "구성원 가족 목록 조회", description = "구성원 가족 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/fam/{empId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectFamReserveVisit(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable String empId
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectFamReserveVisit(empId));
    }

    /**
     * 가족방문예약 가족 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "가족방문예약 가족 목록 조회", description = "가족방문예약 가족 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/{vstApplNo}/fam/famlist")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectFamReserveVisit(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) throws EsecurityException {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectFamReserveVisitViewFamList(paramMap));
    }

    /**
     * 가족방문예약 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    @Operation(summary = "가족방문예약 상세 조회", description = "가족방문예약 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/{vstApplNo}/fam/info")
    public @ResponseBody ResponseModel<Map<String, Object>> selectFamReserveVisitView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) throws EsecurityException {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectFamReserveVisitView(paramMap));
    }

    /**
     * VIP방문예약 업체 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "VIP방문예약 업체 목록 조회", description = "VIP방문예약 업체 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/vip/partner")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectVipReserveVisitParnterList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectVipReserveVisitParnterList());
    }

    /**
     * VIP방문예약 상신/승인
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "VIP방문예약 상신/승인", description = "VIP방문예약에 대한 상신/승인 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitorProgress/vip")
    public @ResponseBody ResponseModel<Boolean> insertReserveVisitVipApproval(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertReserveVisitVipApproval(paramMap));
    }

    /**
     * VIP방문예약 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    @Operation(summary = "VIP방문예약 상세 조회", description = "VIP방문예약 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/visitorProgress/{vstApplNo}/vip")
    public @ResponseBody ResponseModel<Map<String, Object>> selectVipReserveVisitView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) throws EsecurityException {
        
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectVipReserveVisitView(paramMap));
    }

    /**
     * 단체방문 상신/승인
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 18.
     */
    @Operation(summary = "단체방문 상신/승인", description = "단체방문에 대한 상신/승인 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/orgVisit")
    public @ResponseBody ResponseModel<Boolean> insertOrgVisitApproval(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertOrgVisitApproval(paramMap));
    }

    /**
     * 단체방문 통합결재 후처리
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 16.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "단체방문 통합결재 후처리", description = "단체방문 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/orgVisit/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processReserveVisitOrgApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO) throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, reserveVisitOrgApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 단체방문 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 29.
     */
    @Operation(summary = "단체방문 현황 목록 조회", description = "단체방문 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/orgVisit")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectOrgVisitList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectOrgVisitList(paramMap));
    }

    /**
     * 단체방문 현황 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 29.
     */
    @Operation(summary = "단체방문 현황 상세 조회", description = "단체방문 현황 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/orgVisit/{groupNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectOrgVisitView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer groupNo
    ) throws EsecurityException {
        
        paramMap.put("groupNo", groupNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectOrgVisitView(paramMap));
    }

    /**
     * VIP 방문증 번호 저장
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    @Operation(summary = "VIP 방문증 번호 저장", description = "VIP 방문증 번호 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/visitorProgress/vip/ioCardno")
    public @ResponseBody ResponseModel<Boolean> saveVipIoCardno(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.saveVipIoCardno(paramMap));
    }
    //insertGlobalStaffVstMan

    /**
     * 방문예약ID 생성
     *
     * @param sessionInfoVO
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 28.
     */
    @Operation(summary = "방문예약ID 생성", description = "방문예약ID를 생성한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/vstApplNo")
    public @ResponseBody ResponseModel<Integer> selectReserveVisitVstApplNo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectReserveVisitVstApplNo());
    }

    /**
     * GlobalStaff 방문객 추가
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param vstApplNo
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 28.
     */
    @Operation(summary = "GlobalStaff 방문객 추가", description = "GlobalStaff 방문객을 추가한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/globalstaff/{vstApplNo}/man")
    public @ResponseBody ResponseModel<Boolean> insertGlobalStaffVstMan(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertGlobalStaffVstMan(paramMap));
    }

    /**
     * GlobalStaff 방문예약 상신/승인
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 15.
     */
    @Operation(summary = "GlobalStaff 방문예약 상신/승인", description = "GlobalStaff 방문예약에 대한 상신/승인 처리를 수행한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/globalstaff/{vstApplNo}")
    public @ResponseBody ResponseModel<Boolean> insertReserveVisitGsApproval(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("vstApplNo", vstApplNo);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertReserveVisitGsApproval(paramMap));
    }

    /**
     * 방문객 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 3.
     */
    @Operation(summary = "방문객 삭제", description = "방문객 정보를 삭제 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitor/{vstApplNo}/{ioEmpId}/delete")
    public @ResponseBody ResponseModel<Boolean> deleteVisitVisitor(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo, @PathVariable String ioEmpId
    ) {
        
        paramMap.put("vstApplNo", vstApplNo);
        paramMap.put("ioEmpId", ioEmpId);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.deleteVisitVisitor(paramMap));
    }

    /**
     * 전산저장장치 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 4.
     */
    @Operation(summary = "전산저장장치 삭제", description = "전산저장장치 정보를 삭제 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/visitor/iteqmt/{vstApplNo}/{ioEmpId}/delete")
    public @ResponseBody ResponseModel<Boolean> deleteReserveVisitIteqmt(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap, @PathVariable Integer vstApplNo, @PathVariable String ioEmpId
    ) {
        
        paramMap.put("vstApplNo", vstApplNo);
        paramMap.put("ioEmpId", ioEmpId);
        return new ResponseModel<Boolean>(HttpStatus.OK, service.deleteReserveVisitIteqmt(paramMap));
    }

    /**
     * (관리자) VIP고객사 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    @Operation(summary = "(관리자) VIP고객사 목록 조회", description = "(관리자) VIP고객사 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admVipPartner")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAdmVipPartnerList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmVipPartnerList(paramMap), service.selectAdmVipPartnerListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.selectAdmVipPartnerList(paramMap));
        }
    }

    /**
     * (관리자) VIP고객사 정보 저장
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    @Operation(summary = "(관리자) VIP고객사 정보 저장", description = "(관리자) VIP고객사 정보를 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/admVipPartner")
    public @ResponseBody ResponseModel<Boolean> saveAdmVipPartner(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.saveAdmVipPartner(paramMap));
    }

    /**
     * 출입건물도면 저장
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws IOException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    @Operation(summary = "출입건물도면 정보 저장", description = "출입건물도면 정보를 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/buildEntranceInfo")
    public @ResponseBody ResponseModel<Boolean> insertBuildEntranceInfo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertBuildEntranceInfo(paramMap));
    }

    /**
     * 출입건물도면 다운로드
     *
     * @param sessionInfoVO
     * @param agent
     * @return
     *
     * @throws IOException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    @Operation(summary = "출입건물도면 다운로드", description = "캠퍼스에 맞는 출입건물도면을 다운로드 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/buildEntranceInfo/download/{entCompId}")
    public ResponseEntity<Object> download(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String entCompId, @RequestHeader("User-Agent") String agent) throws EsecurityException {
        return service.downloadBuildEntranceInfo(entCompId, agent);
    }

}
