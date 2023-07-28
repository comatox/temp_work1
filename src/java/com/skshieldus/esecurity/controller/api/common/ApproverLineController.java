package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.ApproverLineDTO;
import com.skshieldus.esecurity.model.common.ApproverLineQueryDTO;
import com.skshieldus.esecurity.model.common.PermitSpecifiedApproverDTO;
import com.skshieldus.esecurity.model.common.PermitSpecifiedApproverQueryDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.model.common.SpecifiedApproverLineDTO;
import com.skshieldus.esecurity.service.common.ApproverLineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결재선 API")
@RestController
@RequestMapping(value = "/api/common/approverLine", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class ApproverLineController {

    @Autowired
    private Environment environment;

    @Autowired
    private ApproverLineService approverLineService;

    /**
     * 결재선 목록 조회 API
     *
     * @param sessionInfo 세션정보 DTO
     * @param apprQuery 결재선 조회 조건 DTO
     * @return ResponseModel<ApproverLineDTO>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 02. 09.
     */
    @Operation(summary = "결재선 조회", description = "결재선을 조회한다. 결재선은 허가부서 결재선, 요청부서 결재선, 참조부서 결재선이 있다. DOC_ID, COMP_ID ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<ApproverLineDTO> selectApproverLine(@Parameter(hidden = true) SessionInfoVO sessionInfo, ApproverLineQueryDTO apprQuery) {

        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectApproverLine(apprQuery, sessionInfo));
    }

    /**
     * 요청부서 부서별 결재선 지정자 목록 조회 API
     *
     * @param sessionInfo 세션정보 DTO
     * @param apprQuery 결재선 조회 조건 DTO
     * @return ResponseModel<List < SpecifiedApproverLineDTO>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 03. 18.
     */
    @Operation(summary = "요청부서 부서별 결재선 지정자 목록 조회", description = "요청부서 부서별 결재선 지정자 목록 조회 API deptId 값 필수 ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/reqDeptSpecifiedApproverList")
    public @ResponseBody ResponseModel<List<SpecifiedApproverLineDTO>> selectApprovalLineRequestLine(@Parameter(hidden = true) SessionInfoVO sessionInfo, ApproverLineQueryDTO apprQuery) {
        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectApprovalLineRequestLine(apprQuery, sessionInfo));
    }

    /**
     * 결재선 사원 목록 조회 API (setApprLine)
     *
     * @param sessionInfo 세션정보 DTO
     * @param apprQuery 결재선 조회 조건 DTO
     * @return ResponseModel<List < SpecifiedApproverLineDTO>>
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 03. 25.
     */
    @Operation(summary = "결재선 사원 목록 조회 API (setApprLine)", description = "결재선 사원 목록을 조회한다. (setApprLine) ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/apprEmpListByDept")
    public @ResponseBody ResponseModel<List<SpecifiedApproverLineDTO>> selectApprEmpListByDept(@Parameter(hidden = true) SessionInfoVO sessionInfo, ApproverLineQueryDTO apprQuery) {
        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectApprEmpListByDept(apprQuery, sessionInfo));
    }

    /**
     * 결재선 사원 목록 조회 API (setApprLine, setApprLineInOutTeam, setApprLinePL_JangOnly)
     *
     * @param sessionInfo 세션정보 DTO
     * @param apprQuery 결재선 조회 조건 DTO
     * @return ResponseModel<List < SpecifiedApproverLineDTO>>
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 03. 25.
     */
    @Operation(summary = "결재선 사원 목록 조회 API (setApprLine, setApprLineInOutTeam, setApprLinePL_JangOnly)", description = "결재선 사원 목록을 조회한다. (setApprLine, setApprLineInOutTeam, setApprLinePL_JangOnly) ")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/approvalLineReqDeptTeamLeader")
    public @ResponseBody ResponseModel<List<SpecifiedApproverLineDTO>> selectApprovalLineReqDeptTeamLeader(@Parameter(hidden = true) SessionInfoVO sessionInfo, ApproverLineQueryDTO apprQuery) {
        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectApprovalLineReqDeptTeamLeader(apprQuery, sessionInfo));
    }

    /**
     * 자산반출입 허가부서결재자 현황 조회 API
     *
     * @param sessionInfo
     * @return List<InoutPermitSpecifiedApproverDTO>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 07.
     */
    @Operation(summary = "자산반출입 허가부서결재자 현황 조회", description = "자산반출입 허가부서결재자 현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/inoutPermitSpecifiedApprover")
    public @ResponseBody ResponseModel<List<PermitSpecifiedApproverDTO>> selectInoutPermitSpecifiedApproverList(@Parameter(hidden = true) SessionInfoVO sessionInfo, PermitSpecifiedApproverQueryDTO queryDTO) {
        // TODO: 조회조건 DTO 정의해야 함.
        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectInoutPermitSpecifiedApproverList(queryDTO));
    }

    /**
     * 자산반출입 허가부서결재자 신규 등록 API
     *
     * @param sessionInfo
     * @param permitSpecifiedApproverDTO
     * @return InoutPermitSpecifiedApproverDTO
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 07.
     */
    @Operation(summary = "자산반출입 허가부서결재자 신규 등록", description = "자산반출입 허가부서결재자를 신규 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/inoutPermitSpecifiedApprover")
    public @ResponseBody ResponseModel<Integer> insertInoutPermitSpecifiedApprover(
        @Parameter(hidden = true) SessionInfoVO sessionInfo,
        @RequestBody PermitSpecifiedApproverDTO permitSpecifiedApproverDTO
    ) {
        return new ResponseModel<>(HttpStatus.OK, approverLineService.insertInoutPermitSpecifiedApprover(sessionInfo, permitSpecifiedApproverDTO));
    }

    /**
     * 자산반출입 허가부서결재자 조회 API
     *
     * @param sessionInfo
     * @param apprdefNo
     * @return InoutPermitSpecifiedApproverDTO
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 07.
     */
    @Operation(summary = "자산반출입 허가부서결재자 조회", description = "자산반출입 허가부서결재자를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/inoutPermitSpecifiedApprover/{apprdefNo}")
    public @ResponseBody ResponseModel<PermitSpecifiedApproverDTO> selectInoutPermitSpecifiedApprover(
        @Parameter(hidden = true) SessionInfoVO sessionInfo,
        @PathVariable Integer apprdefNo
    ) {
        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectInoutPermitSpecifiedApprover(apprdefNo));
    }

    /**
     * 자산반출입 허가부서결재자 수정 API
     *
     * @param sessionInfo
     * @param apprdefNo
     * @param permitSpecifiedApproverDTO
     * @return InoutPermitSpecifiedApproverDTO
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 07.
     */
    @Operation(summary = "자산반출입 허가부서결재자 수정", description = "자산반출입 허가부서결재자를 수정 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/inoutPermitSpecifiedApprover/{apprdefNo}")
    public @ResponseBody ResponseModel<Integer> updateInoutPermitSpecifiedApprover(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, @PathVariable Integer apprdefNo,
        @RequestBody PermitSpecifiedApproverDTO permitSpecifiedApproverDTO
    ) {
        return new ResponseModel<>(HttpStatus.OK, approverLineService.updateInoutPermitSpecifiedApprover(sessionInfo, permitSpecifiedApproverDTO));
    }

    @Operation(summary = "요청부서 결재선 조회", description = "요청부서 결재선을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/request", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<SpecifiedApproverLineDTO>> selectRequestLineList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, ApproverLineQueryDTO paramDTO) {
        

        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectRequestLineList(paramDTO));
    }

    @Operation(summary = "허가부서 결재선 조회", description = "허가부서 결재선을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/permit", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<SpecifiedApproverLineDTO>> selectPermitLineList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, ApproverLineQueryDTO paramDTO) {
        

        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectPermitLineList(paramDTO));
    }

    /**
     * 요청부서 결재선-사번 기준 팀장 및 상위부서 담당을 순서대로 조회 1차: 팀장/PL, 2차: 담당 인 경우에 활용
     *
     * @param sessionInfo 세션정보 DTO
     * @param empId 사번
     * @return ResponseModel<List < SpecifiedApproverLineDTO>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    @Operation(summary = "요청부서 결재선-사번 기준 팀장 및 상위부서 담당", description = "요청부서 결재선-사번 기준 팀장 및 상위부서 담당을 순서대로 조회 1차: 팀장/PL, 2차: 담당 인 경우에 활용")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/specifiedPLAndDamdang/{empId}")
    public @ResponseBody ResponseModel<List<SpecifiedApproverLineDTO>> selectReqLineDeptPLAndDamdangByEmpId(@Parameter(hidden = true) SessionInfoVO sessionInfo, @PathVariable("empId") String empId) {
        if (StringUtils.isEmpty(empId) && sessionInfo != null) {
            empId = sessionInfo.getEmpNo();
        }
        return new ResponseModel<>(HttpStatus.OK, approverLineService.selectReqLineDeptPLAndDamdangByEmpId(empId));
    }

}
