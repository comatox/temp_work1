package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.CoCompService;
import com.skshieldus.esecurity.service.common.CoDeptService;
import com.skshieldus.esecurity.service.common.CoEmpExtService;
import com.skshieldus.esecurity.service.common.CoEmpService;
import com.skshieldus.esecurity.service.common.IoCompService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "사원검색 API")
@RestController
@RequestMapping(value = "/api/common/emp", produces = { MediaType.APPLICATION_JSON_VALUE })
public class EmpController {

    @Autowired
    private Environment environment;

    @Autowired
    private CoDeptService coDeptService;

    @Autowired
    private CoEmpService coEmpService;

    @Autowired
    private CoEmpExtService coEmpExtService;

    @Autowired
    private CoCompService coCompService;

    @Autowired
    private IoCompService ioCompService;

    /* NEW */
    /**
     * 사원정보검색 목록조회
     *
     * @return List<CoEmpDTO>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 03
     */
    @Operation(summary = "협력업체 사원정보 검색", description = "권한/메뉴 관리 권한목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/coEmpExt", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectCoEmpExtList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap
    ) {

        ListDTO<Map<String, Object>> listDTO = coEmpExtService.selectCoEmpExtList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 주소 검색
     *
     * @return List<CoEmpDTO>
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2023. 07. 03
     */
    @Operation(summary = "주소 검색", description = "주소를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/searchZipCode", produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectZipCodeList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap
    ) {

        ListDTO<Map<String, Object>> listDTO = coEmpExtService.selectZipCodeList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }
}
