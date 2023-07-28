package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.ApproverLineQueryDTO;
import com.skshieldus.esecurity.model.common.SpecifiedApproverLineDTO;
import com.skshieldus.esecurity.service.common.ApprovalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "결재선 조회 API")
@RestController
@RequestMapping(value = "/api/common/approval", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @Operation(summary = "요청부서 결재선 조회", description = "요청부서 결재선 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/request")
    public @ResponseBody ResponseModel<List<SpecifiedApproverLineDTO>> selectRequestApprovalLine(ApproverLineQueryDTO apprQuery) {

        return new ResponseModel<>(HttpStatus.OK, approvalService.selectRequestApprovalLine(apprQuery));
    }

    @Operation(summary = "허가부서 결재선 조회", description = "허가부서 결재선 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/permit")
    public @ResponseBody ResponseModel<List<SpecifiedApproverLineDTO>> selectPermitApprovalLine(ApproverLineQueryDTO apprQuery) {

        return new ResponseModel<>(HttpStatus.OK, approvalService.selectPermitApprovalLine(apprQuery));
    }

    @Operation(summary = "결재정보 조회", description = "결재정보 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/saved/{docId}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectApprovalSaveList(@PathVariable("docId") Integer docId) {

        return new ResponseModel<>(HttpStatus.OK, approvalService.selectApprovalInfo(docId));
    }

    @Operation(summary = "요청부서 결재자 부서 목록 조회", description = "요청부서 결재자 부서 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/request/dept/{deptId}")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectRequestDeptList(@PathVariable("deptId") String deptId) {
        return new ResponseModel<>(HttpStatus.OK, approvalService.selectRequestDeptList(deptId));
    }

    @Operation(summary = "요청부서 결재자 부서별 사원 목록 조회", description = "요청부서 결재자 부서별 사원 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/request/emp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectApprEmpListByDept(ApproverLineQueryDTO param) {
        return new ResponseModel<>(HttpStatus.OK, approvalService.selectApprEmpListByDept(param));
    }

}
