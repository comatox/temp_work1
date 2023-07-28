package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.BuildingRegService;
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

@Tag(name = "[출입관리] 건물출입 등록 API")
@Controller
@RequestMapping(value = "/api/entmanage/buildingReg", produces = { "application/json" })
public class BuildingRegController {

    @Autowired
    private Environment environment;

    @Autowired
    private BuildingRegService service;

    /**
     * 사원 카드번호 조회 API
     *
     * @param sessionInfoVO
     * @param empId
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "사원 카드번호 조회", description = "사원 카드번호를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/cardNo/empCard/{empId}")
    public @ResponseBody ResponseModel<String> selectEmpCardNo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empId) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectEmpCardNo(empId));
    }

    /**
     * 사원 카드번호 조회 API
     *
     * @param sessionInfoVO
     * @param empId
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "사원 카드번호 조회", description = "사원 카드번호를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/cardNo/{empId}")
    public @ResponseBody ResponseModel<String> selectCardNo(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empId) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCardNo(empId));
    }

    /**
     * 이천 출입구역 건물 목록 조회 (일반구역) API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "이천 출입구역 건물 목록 조회 (일반구역)", description = "이천 출입구역 건물 목록(일반구역)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/bldgList/ic")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCardKeyBldgList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCardKeyBldgList(paramMap));
    }

    /**
     * 이천 출입구역 건물 목록 조회 - 층 단위 (일반구역) API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "이천 출입구역 건물 목록 조회 - 층 단위 (일반구역)", description = "이천 출입구역 건물 목록(일반구역)을 층 단위로 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/floorList/ic")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCardKeyFloorList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCardKeyFloorList(paramMap));
    }

    /**
     * 청주 출입구역 건물 목록 조회 (일반구역) API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 13.
     */
    @Operation(summary = "청주 출입구역 건물 목록 조회 (일반구역)", description = "청주 출입구역 건물 목록(일반구역)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/bldgList/cj")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCardKeyCjBldgList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCardKeyCjBldgList(paramMap));
    }

    /**
     * 이천 출입구역 목록 조회 (통제구역)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "이천 출입구역 목록 조회 (통제구역)", description = "이천 출입구역 목록(통제구역)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/gateSpeZone1/ic")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCardKeyGateSpeZone1List(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCardKeyGateSpeZone1List(paramMap));
    }

    /**
     * 청주 출입구역 목록 조회 (통제구역)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "청주 출입구역 목록 조회 (통제구역)", description = "청주 출입구역 목록(통제구역)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/gateSpeZone1/cj")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCardKeyCjGateSpeZone1List(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCardKeyCjGateSpeZone1List(paramMap));
    }

    /**
     * 이천 출입구역 목록 조회 (제한구역)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "이천 출입구역 목록 조회 (제한구역)", description = "이천 출입구역 목록(제한구역)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/gateSpeZone2/ic")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCardKeyGateSpeZone2List(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCardKeyGateSpeZone2List(paramMap));
    }

    /**
     * 청주 출입구역 목록 조회 (제한구역)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "청주 출입구역 목록 조회 (제한구역)", description = "청주 출입구역 목록(제한구역)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/gateSpeZone2/cj")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCardKeyCjGateSpeZone2List(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectCardKeyCjGateSpeZone2List(paramMap));
    }

    /**
     * 이천/청주 허가부서 결재자 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "이천/청주 허가부서 결재자 목록 조회", description = "이천/청주 허가부서 결재자 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/permitLine")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildPermitLine(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectBuildPermitLine(paramMap));
    }

    /**
     * 건물출입 등록(상신)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 19.
     */
    @Operation(summary = "건물출입 신청(상신)", description = "건물출입 정보를 등록(상신)한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Boolean> insertEmpcardBuildingReg(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("crtBy", sessionInfoVO.getEmpNo());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertEmpcardBuildingReg(paramMap));
    }

    /**
     * 건물출입 등록 통합결재 후처리
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 20.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "건물출입 등록 통합결재 후처리", description = "건물출입 등록 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processBuildingRegApprovalApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, buildingRegApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 건물출입 즐겨찾기 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 19.
     */
    @Operation(summary = "건물출입 즐겨찾기 등록", description = "건물출입 즐겨찾기를 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/mygate")
    public @ResponseBody ResponseModel<Boolean> insertEmpcardMyGate(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("crtBy", sessionInfoVO.getEmpNo());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertEmpcardMyGate(paramMap));
    }

    /**
     * 건물출입 신청 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "건물출입 신청 현황 목록 조회", description = "건물출입 신청 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectEmpCardBuildRegList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectEmpCardBuildRegList(paramMap));
    }

    /**
     * 건물출입 신청 현황 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "건물출입 신청 현황 상세 조회", description = "건물출입 신청 현황 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/{empcardBldgApplNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectEmpCardBuildRegInfo(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer empcardBldgApplNo
    ) throws EsecurityException {
        
        paramMap.put("empcardBldgApplNo", empcardBldgApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectEmpCardBuildRegInfo(paramMap));
    }

    /**
     * 건물출입 신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "건물출입 일괄신청", description = "건물출입 정보를 일괄 등록 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/all")
    public @ResponseBody ResponseModel<Boolean> insertBuildingRegAll(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("crtBy", sessionInfoVO.getEmpNo());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertBuildingRegAll(paramMap));
    }

    /**
     * 건물출입 일괄신청 현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "건물출입 일괄신청 현황 목록 조회", description = "건물출입 일괄신청 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/all")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectEmpCardBuildRegAllList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectEmpCardBuildRegAllList(paramMap));
    }

    /**
     * 건물출입 일괄신청 현황 상세 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "건물출입 일괄신청 현황 상세 조회", description = "건물출입 일괄신청 현황 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/all/{empcardallBldgApplNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectEmpCardBuildRegAll(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer empcardallBldgApplNo
    ) throws EsecurityException {
        
        paramMap.put("empcardallBldgApplNo", empcardallBldgApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectEmpCardBuildRegAll(paramMap));
    }

    /**
     * 건물출입 신청 현황 목록 조회(상시출입증)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "건물출입 신청 현황 목록 조회(상시출입증)", description = "건물출입 신청 현황 목록을 조회한다.(상시출입증)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectPassBuildRegList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectPassBuildRegList(paramMap));
    }

    /**
     * 건물출입 신청 현황 상세 조회(상시출입증)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "건물출입 신청 현황 상세 조회(상시출입증)", description = "건물출입 신청 현황 상세정보를 조회한다.(상시출입증)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/{passBldgApplNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectPassBuildRegView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer passBldgApplNo
    ) throws EsecurityException {
        
        paramMap.put("passBldgApplNo", passBldgApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectPassBuildRegView(paramMap));
    }

    /**
     * 건물출입 신규 출입(상시출입증) 신청 사용자 건수 조회
     * 검색된 사용자가 한명인 경우 사용자 정보까지 조회 (ioEmpInfo)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "건물출입 신청(상시출입증) 사용자 건수 조회", description = "건물출입 신청(상시출입증) 대상 사용자 건수를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/ioPassEmp/count")
    public @ResponseBody ResponseModel<Map<String, Object>> selectIoPassCountByEmpName(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectIoPassCountByEmpName(paramMap));
    }

    /**
     * 건물출입 신규 출입(상시출입증) 신청 상시출입증 사용자 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "건물출입 신청(상시출입증) 사용자 목록 조회", description = "건물출입 신청(상시출입증) 대상 사용자 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/ioPassEmp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoPassApplNoList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectIoPassApplNoList(paramMap));
    }

    /**
     * 건물출입 신규 출입(상시출입증) 신청 상시출입증 사용자 상세정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 7.
     */
    @Operation(summary = "건물출입 신청(상시출입증) 사용자 상세정보 조회", description = "건물출입 신청(상시출입증) 대상 사용자 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/ioPassEmp/{passApplNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectPassReceiptViewByPassApplNo(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable Integer passApplNo
    ) throws EsecurityException {
        
        paramMap.put("passApplNo", passApplNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectPassReceiptViewByPassApplNo(paramMap));
    }

    /**
     * 상시출입증 건물출입 등록(상신)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "상시출입증 건물출입 신청(상신)", description = "건물출입 정보를 등록(상신)한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/pass")
    public @ResponseBody ResponseModel<Boolean> insertPassBuildingReg(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("crtBy", sessionInfoVO.getEmpNo());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertPassBuildingReg(paramMap));
    }

    /**
     * 상시출입증 건물출입 등록 통합결재 후처리
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "상시출입증 건물출입 등록 통합결재 후처리", description = "건물출입 등록 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/pass/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processBuildingRegPassApprovalApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, buildingRegPassApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 통제구역 출입현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 29.
     */
    @Operation(summary = "통제구역 출입현황 목록 조회", description = "통제구역 출입현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/specialPass")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSpecialPassList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectSpecialPassList(paramMap), service.selectSpecialPassCount(paramMap));
    }

    /**
     * 통제구역 출입현황 조회조건 사업장 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 29.
     */
    @Operation(summary = "통제구역 출입현황 조회조건 사업장 목록 조회", description = "통제구역 출입현황 조회조건 사업장 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/specialPass/compList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSpecialPassCompCodeList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectSpecialPassCompCodeList(paramMap));
    }

    /**
     * 통제구역 출입현황 조회조건 건물 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 29.
     */
    @Operation(summary = "통제구역 출입현황 조회조건 건물 목록 조회", description = "통제구역 출입현황 조회조건 건물 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/specialPass/bldgList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSpecialPassBldgCodeList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectSpecialPassBldgCodeList(paramMap));
    }

    /**
     * 통제구역 출입현황 조회조건 게이트 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 29.
     */
    @Operation(summary = "통제구역 출입현황 조회조건 게이트 목록 조회", description = "통제구역 출입현황 조회조건 게이트 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/specialPass/gateList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSpecialPassGateCodeList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectSpecialPassGateCodeList(paramMap));
    }

    /**
     * 통제구역 권한 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 1.
     */
    @Operation(summary = "통제구역 권한 삭제", description = "통제구역 권한을 삭제 처리한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/pass/specialPass/delete")
    public @ResponseBody ResponseModel<Boolean> deleteSpecialPassMulti(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        paramMap.put("crtBy", sessionInfoVO.getEmpNo());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.deleteSpecialPassMulti(paramMap));
    }

    /**
     * 통제구역 출입현황 상세 변경이력 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 1.
     */
    @Operation(summary = "통제구역 출입현황 상세 변경이력 목록 조회", description = "통제구역 출입현황 상세 변경이력 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/pass/specialPass/hist/{cardNo}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectPassReceiptMngChgHistList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable String cardNo
    ) throws EsecurityException {
        
        paramMap.put("cardNo", cardNo);
        return new ResponseModel<>(HttpStatus.OK, service.selectPassReceiptMngChgHistList(paramMap));
    }

    /**
     * 기 출입구역 목록 조회 (if => _IDcard_Visit)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 6.
     */
    @Operation(summary = "기 출입구역 목록 조회 (if => _IDcard_Visit)", description = "기 출입구역 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/oldgate/{idcardId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectPassBuildingGateOldList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap, @PathVariable String idcardId
    ) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, environment.acceptsProfiles(Profiles.of("dev", "stg", "prd"))
            ? service.selectPassBuildingGateOldList(idcardId)
            : List.of());
    }

    /**
     * 사원증 건물등록현황(관리자) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 7.
     */
    @Operation(summary = "사원증 건물등록현황(관리자) 목록 조회(관리자)", description = "사원증 건물등록현황(관리자) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admin/empCard")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectEmpCardBuildRegAdmList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectEmpCardBuildRegAdmList(paramMap), service.selectEmpCardBuildRegAdmCount(paramMap));
    }

    /**
     * 출입증 건물등록현황(관리자) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 17.
     */
    @Operation(summary = "출입증 건물등록현황(관리자) 목록 조회(관리자)", description = "출입증 건물등록현황(관리자) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admin/pass")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectPassBuildRegAdmList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectPassBuildRegAdmList(paramMap), service.selectPassBuildRegAdmCount(paramMap));
    }

}

