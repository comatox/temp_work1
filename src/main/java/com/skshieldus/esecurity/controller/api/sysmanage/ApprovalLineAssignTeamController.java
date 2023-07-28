package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.ApprovalLineAssignTeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "결재선 팀장지정자 관리 API")
@Controller
@RequestMapping(value = "/api/sysmanage/approvalLineAssignTeam", produces = { "application/json" })
public class ApprovalLineAssignTeamController {

    @Autowired
    private ApprovalLineAssignTeamService service;

    @Operation(summary = "부서의 팀장지정자 목록 조회", description = "부서의 팀장지정자 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectApprLineAssignTeamList(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, String deptId
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectApprLineAssignTeamList(deptId));
    }

    /**
     * 부서의 사원 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param deptId 부서ID
     * @return ResponseModel<List < Map < String, Object>>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 17.
     */
    @Operation(summary = "부서의 사원 목록 조회", description = "부서의 사원 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/emp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectApprEmpListByDeptTeam(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, String deptId
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectApprEmpListByDeptTeam(deptId));
    }

    /**
     * 로그인 사용자의 부서 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param empId 로그인사용자ID
     * @return ResponseModel<List < Map < String, Object>>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 17.
     */
    @Operation(summary = "로그인 사용자의 부서 목록 조회", description = "로그인 사용자의 부서 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/dept")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectApprDeptTeamList(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, String empId
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectApprDeptTeamList(empId));
    }

    /**
     * 팀장지정자 등록 API
     *
     * @param sessionInfo
     * @param Map<String, Object>
     * @return Integer
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 17.
     */
    @Operation(summary = "팀장지정자 등록", description = "팀장지정자를 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Integer> saveApprLineAssig(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, @RequestBody Map<String, Object> data
    ) {

        return new ResponseModel<Integer>(HttpStatus.OK, service.saveApprLineAssig(data));
    }

}