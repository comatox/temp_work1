package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.*;
import com.skshieldus.esecurity.service.common.CoCompService;
import com.skshieldus.esecurity.service.common.CoDeptService;
import com.skshieldus.esecurity.service.common.CoEmpService;
import com.skshieldus.esecurity.service.common.IoCompService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "인사정보 API")
@RestController
@RequestMapping(value = "/api/common/organization", produces = { MediaType.APPLICATION_JSON_VALUE })
public class OrganizationController {

    @Autowired
    private Environment environment;

    @Autowired
    private CoDeptService coDeptService;

    @Autowired
    private CoEmpService coEmpService;

    @Autowired
    private CoCompService coCompService;

    @Autowired
    private IoCompService ioCompService;

    /**
     * 부서 목록 조회 API
     *
     * @param sessionInfoVO
     * @param coDeptDTO
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "부서 목록 조회", description = "부서 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/dept")
    public @ResponseBody ResponseModel<List<CoDeptDTO>> selectDeptList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, CoDeptDTO coDeptDTO
    ) {
        

        // 목록 조회
        List<CoDeptDTO> resultList = coDeptService.selectCoDeptList(coDeptDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 부서 목록(Key, Value) 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param coDeptDTO
     * @return ResponseModel<List < CoEmpDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "부서 목록(Key, Value) 조회", description = "인사 목록(Key, Value)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/deptKey")
    public @ResponseBody ResponseModel<Map<String, Object>> selectDeptListForKey(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, CoDeptDTO coDeptDTO
    ) {
        

        // 목록 조회
        Map<String, Object> resultList = coDeptService.selectCoDeptListForKey(coDeptDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 부서 상세 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param deptId 부서ID
     * @return ResponseModel<CoDeptDTO>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "부서 상세 조회", description = "부서 상세정보를 조회한다.")
    @GetMapping(value = "/dept/{id}")
    public @ResponseBody ResponseModel<CoDeptDTO> selectDept(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String deptId
    ) {
        

        // 상세 조회
        CoDeptDTO reuslt = coDeptService.selectCoDept(deptId);

        return new ResponseModel<>(HttpStatus.OK, reuslt);
    }

    /**
     * 인사 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param coEmpDTO 인사정보 DTO
     * @return ResponseModel<List < CoEmpDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "인사 목록 조회", description = "인사 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/emp")
    public @ResponseBody ResponseModel<List<CoEmpDTO>> selectEmpList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, CoEmpDTO coEmpDTO
    ) {
        

        // 목록 조회
        List<CoEmpDTO> resultList = coEmpService.selectCoEmpList(coEmpDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 인사 목록(Key, Value) 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param coEmpDTO
     * @return ResponseModel<List < CoEmpDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "인사 목록(Key, Value) 조회", description = "인사 목록(Key, Value)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/empKey")
    public @ResponseBody ResponseModel<Map<String, Object>> selectEmpListForKey(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, CoEmpDTO coEmpDTO
    ) {
        

        // 목록 조회
        Map<String, Object> resultList = coEmpService.selectCoEmpListForKey(coEmpDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 인사 상세 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param empId 인사ID
     * @return ResponseModel<CoEmpDTO>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "인사 상세 조회", description = "인사 상세정보를 조회한다.")
    @GetMapping(value = "/emp/{id}")
    public @ResponseBody ResponseModel<CoEmpDTO> selectEmp(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String empId
    ) {
        

        // 상세 조회
        CoEmpDTO result = coEmpService.selectCoEmp(empId);

        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 권한 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param empId 인사ID
     * @return ResponseModel<CoEmpDTO>
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 19.
     */
    @Operation(summary = "권한 목록 조회", description = "권한 목록을 조회한다.")
    @GetMapping(value = "/auth/{id}")
    public @ResponseBody ResponseModel<List<CoEmpAuthDTO>> selectCoEmpAuthList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String empId
    ) {
        

        // 상세 조회
        List<CoEmpAuthDTO> resultList = coEmpService.selectCoEmpAuthList(empId);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 인사(구성원+협력사(CO_DRM_EMP)) 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param coEmpDTO 인사정보 DTO
     * @return ResponseModel<List < CoEmpDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "인사(구성원+협력사(CO_DRM_EMP)) 목록 조회", description = "인사(구성원+협력사(CO_DRM_EMP)) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/xemp")
    public @ResponseBody ResponseModel<List<CoEmpDTO>> selectXEmpList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, CoEmpDTO coEmpDTO
    ) {
        

        // 목록 조회
        List<CoEmpDTO> resultList = coEmpService.selectCoXEmpList(coEmpDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 업체 목록 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param coCompDTO 업체정보 DTO
     * @return ResponseModel<List < CoCompDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "업체 목록 조회", description = "업체 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/comp")
    public @ResponseBody ResponseModel<List<CoCompDTO>> selectCompList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, CoCompDTO coCompDTO
    ) {
        

        // 목록 조회
        List<CoCompDTO> resultList = coCompService.selectCoCompList(coCompDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 업체 목록(key, value) 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param coCompDTO 업체정보 DTO
     * @return ResponseModel<List < CoCompDTO>>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "업체 목록(Key, Value) 조회", description = "업체 목록(Key, Value)을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/compKey")
    public @ResponseBody ResponseModel<Map<String, Object>> selectCompListForKey(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, CoCompDTO coCompDTO
    ) {
        

        // 목록 조회
        Map<String, Object> resultList = coCompService.selectCoCompListForKey(coCompDTO);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 업체 상세 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @param compId 업체ID
     * @return ResponseModel<CoCompDTO>
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "업체 상세 조회", description = "업체 상세정보를 조회한다.")
    @GetMapping(value = "/comp/{id}")
    public @ResponseBody ResponseModel<CoCompDTO> selectComp(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String compId
    ) {
        

        // 상세 조회
        CoCompDTO result = coCompService.selectCoComp(compId);

        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 부서 목록 조회(트리구조) API
     *
     * @param sessionInfoVO
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 1. 21.
     */
    @Operation(summary = "부서 목록 조회(트리구조)", description = "부서 목록을 트리구조로 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/deptTree")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectDeptTreeList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO) {
        

        // 목록 조회
        List<Map<String, Object>> resultList = coDeptService.selectDeptTreeList();

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 메인 팀장,담당자 정보 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @return ResponseModel<List < CoEmpDTO>>
     *
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 25.
     */
    @Operation(summary = "메인 팀장,담당자 정보 조회", description = "메인의 팀장,담당자 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/emp/mainscmgr/{empId}")
    public @ResponseBody ResponseModel<List<CoEmpDTO>> selectMainScMgrList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empId
    ) {
        
        // 목록 조회
        List<CoEmpDTO> resultList = coEmpService.selectMainScMgrList(empId);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 메인 보안활동 정보 조회 API
     *
     * @param sessionInfoVO 세션정보 DTO
     * @return ResponseModel<CoEmpStatDTO>
     *
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @since : 2021. 8. 25.
     */
    @Operation(summary = "메인 보안활동 정보 조회", description = "메인 보안활동 정보를 조회 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/emp/mainscmgr/stat/{empId}/{type}")
    public @ResponseBody ResponseModel<CoEmpStatDTO> selectMainMgrStatList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String empId,
        @PathVariable String type
    ) {
        
        CoEmpStatDTO result = null;
        // 조회
        if ("p".equals(type)) {
            result = coEmpService.selectMainScStatList(empId);
        }
        else {
            result = coEmpService.selectMainMgrStatList(empId);
        }

        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 구성원 차량정보 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws Exception
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "구성원 차량정보  목록 불러오기", description = "구성원 차량정보  목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/emp/carInfoList")
    public @ResponseBody ResponseModel<List<CoEmpCarInfoViewDTO>> selectCoEmpCarInfoViewList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, CoEmpCarInfoViewSearchDTO paramMap
    )
        throws Exception {
        

        // 목록 조회
        List<CoEmpCarInfoViewDTO> resultList = coEmpService.selectCoEmpCarInfoViewList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 외부인 차량정보 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws Exception
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 12.
     */
    @Operation(summary = "외부인 차량정보  목록 불러오기", description = "외부인 차량정보  목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/ioEmp/carInfoList")
    public @ResponseBody ResponseModel<List<IoEmpCarInfoViewDTO>> selectIoEmpCarInfoViewList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, IoEmpCarInfoViewSearchDTO paramMap
    )
        throws Exception {
        

        // 목록 조회
        List<IoEmpCarInfoViewDTO> resultList = ioCompService.selectIoEmpCarInfoViewList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 복수 구성원 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws Exception
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    @Operation(summary = "복수 구성원  목록 불러오기", description = "복수의 구성원 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/emp/multi")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectMultiCoEmpInfoList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) throws Exception {
        

        // 목록 조회
        List<Map<String, Object>> resultList = coEmpService.selectMultiCoEmpInfoList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 사용자 관리 부서조회
     *
     * @return List<Map<String, Object>>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 19
     */
    @Operation(summary = "사용자 관리 부서조회", description = "사용자 관리 부서를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/dept/compDeptList", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCompDeptList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap
    ) {
        return new ResponseModel<>(HttpStatus.OK, coDeptService.selectCompDeptList(paramMap));
    }
}
