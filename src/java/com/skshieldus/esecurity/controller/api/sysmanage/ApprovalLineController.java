package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalLineDefDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalLineDefSearchDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalStateSearchDTO;
import com.skshieldus.esecurity.service.sysmanage.ApprovalLineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "결재선 관리 API")
@Controller
@RequestMapping(value = "/api/sysmanage/approvalLine", produces = { "application/json" })
public class ApprovalLineController {

    @Autowired
    private ApprovalLineService service;

    /**
     * 결재선 관리 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param apprQueryDTO 결재선 조회 조건 DTO
     * @return ResponseModel<List < ApprovalLineDefDTO>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 14.
     */
    @Operation(summary = "결재선 관리 목록 조회", description = "결재선 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<ApprovalLineDefDTO>> selectApprLineManageList(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, ApprovalLineDefSearchDTO searchDTO
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectApprLineManageList(searchDTO));
    }

    /**
     * 결재선 관리 상세 신규 등록 API
     *
     * @param sessionInfo
     * @param approvalLineDefDTO
     * @return Integer
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 14.
     */
    @Operation(summary = "결재선 관리 상세 신규 등록", description = "결재선 관리 상세를 신규 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Integer> insertApprLineManage(
        @Parameter(hidden = true) SessionInfoVO sessionInfo,
        @RequestBody ApprovalLineDefDTO approvalLineDefDTO
    ) {

        //		approvalLineDefDTO.setCrtBy(sessionInfo.getEmpNo());
        return new ResponseModel<Integer>(HttpStatus.OK, service.insertApprLineManage(approvalLineDefDTO));
    }

    /**
     * 결재선 관리 상세 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param apprQueryDTO 결재선 조회 조건 DTO
     * @return ResponseModel<List < Map < String, Object>>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 14.
     */
    @Operation(summary = "결재선 관리 상세 조회", description = "결재선 관리 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/{defSeq}")
    public @ResponseBody ResponseModel<ApprovalLineDefDTO> selectApprLineManage(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, @PathVariable Integer defSeq
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectApprLineManage(defSeq));
    }

    /**
     * 결재선 관리 상세 수정 API
     *
     * @param sessionInfo
     * @param defSeq
     * @param approvalLineDefDTO
     * @return Integer
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 14.
     */
    @Operation(summary = "결재선 관리 상세 수정", description = "결재선 관리 상세를 수정 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/{defSeq}")
    public @ResponseBody ResponseModel<Integer> updateInoutPermitSpecifiedApprover(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, @PathVariable Integer defSeq,
        @RequestBody ApprovalLineDefDTO approvalLineDefDTO
    ) {

        approvalLineDefDTO.setDefSeq(defSeq);
        //		approvalLineDefDTO.setModBy(sessionInfo.getEmpNo());
        return new ResponseModel<Integer>(HttpStatus.OK, service.updateApprLineManage(approvalLineDefDTO));
    }

    /**
     * 결재선 관리 메뉴 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param apprQueryDTO 결재선 조회 조건 DTO
     * @return ResponseModel<List < Map < String, Object>>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 14.
     */
    @Operation(summary = "결재선 관리 메뉴 목록 조회", description = "결재선 관리 메뉴 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/menu")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectApprMenuList(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, String searchMenuNm
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectApprMenuList(searchMenuNm));
    }

    /**
     * 결재선 관리 부서 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param apprQueryDTO 결재선 조회 조건 DTO
     * @return ResponseModel<List < Map < String, Object>>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 14.
     */
    @Operation(summary = "결재선 관리 부서 목록 조회", description = "결재선 관리 부서 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/dept")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectApprDeptList(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, @RequestParam Map<String, Object> paramMap
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectApprDeptList(paramMap));
    }

    /**
     * 결재선 미 준수 현황 조회 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param PortableStorageListDTO
     * @return
     *
     * @throws Exception
     * @author : X0115378<jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 05.
     */
    @Operation(summary = "결재선 미 준수 현황 조회 엑셀 다운로드", description = "결재선 미 준수 현황 조회 엑셀 다운로드 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/approvalState")
    public String selectApprovalStateExcel(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        ApprovalStateSearchDTO approvalStateSearchDTO, Model model
    ) {

        CommonXlsViewDTO commonXlsViewDTO = service.selectApprovalStateExcel(approvalStateSearchDTO);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

}