package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.BuildingManageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "건물관리(방문처) API")
@Controller
@RequestMapping(value = "/api/sysmanage/buildingManage", produces = { "application/json" })
public class BuildingManageController {

    @Autowired
    private BuildingManageService service;

    /**
     * 건물 목록 조회 API
     *
     * @param
     * @return ResponseModel
     */
    @Operation(summary = "건물 목록 조회", description = "건물 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectBuildingList(
        @RequestParam Map<String, Object> param
    ) {
        ListDTO<Map<String, Object>> listDTO = service.selectBuildingList(param);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 건물 상세 신규 등록 API
     *
     * @param sessionInfo
     * @param detail
     * @return Integer
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "건물 상세 신규 등록", description = "건물 상세를 신규 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Integer> insertBuilding(
        @Parameter(hidden = true) SessionInfoVO sessionInfo,
        @RequestBody Map<String, Object> detail
    ) {

        // AC IP 설정
        String acIp = "";
        if (StringUtils.isNotEmpty(sessionInfo.getIp())) {
            acIp = sessionInfo.getIp();
        }
        detail.put("acIp", acIp);

        //		approvalLineDefDTO.setCrtBy(sessionInfo.getEmpNo());
        return new ResponseModel<Integer>(HttpStatus.OK, service.insertBuilding(detail));
    }

    /**
     * 건물 상세 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param gateId
     * @return ResponseModel
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "건물 상세 조회", description = "건물 상세를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/{gateId}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectBuildingView(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, @PathVariable String gateId
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectBuildingView(gateId));
    }

    /**
     * 건물 상세 수정 API
     *
     * @param sessionInfo
     * @param gateId
     * @param detail
     * @return Integer
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "건물 상세 수정", description = "건물 상세를 수정 저장한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/{gateId}")
    public @ResponseBody ResponseModel<Integer> updateBuilding(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, @PathVariable String gateId,
        @RequestBody Map<String, Object> detail
    ) {

        detail.put("gateId", gateId);
        //		approvalLineDefDTO.setModBy(sessionInfo.getEmpNo());
        return new ResponseModel<Integer>(HttpStatus.OK, service.updateBuilding(detail));
    }

    /**
     * 최상위 건물 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param compId
     * @return ResponseModel<List < Map < String, Object>>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "최상위 건물 목록 조회", description = "최상위 건물 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/top")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectGateListTop(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, String compId
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectGateListTop(compId));
    }

    /**
     * 상위 건물 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param compId
     * @return ResponseModel<List < Map < String, Object>>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 01. 19.
     */
    @Operation(summary = "상위 건물 목록 조회", description = "상위 건물 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/sub")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectGateListSub(
        @Parameter(hidden = true) SessionInfoVO sessionInfo, @RequestParam Map<String, Object> param
    ) {

        return new ResponseModel<>(HttpStatus.OK, service.selectGateListSub(param));
    }

}