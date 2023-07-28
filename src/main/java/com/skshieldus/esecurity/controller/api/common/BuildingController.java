package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CoBldgDTO;
import com.skshieldus.esecurity.model.common.CoBldgSearchDTO;
import com.skshieldus.esecurity.model.common.CoXempBldgOutDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.BuildingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
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

@Tag(name = "건물 API")
@RestController
@RequestMapping(value = "/api/common/building", produces = { MediaType.APPLICATION_JSON_VALUE })
public class BuildingController {

    @Autowired
    private Environment environment;

    @Autowired
    private BuildingService buildingService;

    /**
     * 건물정보 상세 조회
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 16.
     */
    @Operation(summary = "건물정보 상세 조회", description = "건물 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/{gateId}", produces = { "application/json" })
    public @ResponseBody ResponseModel<CoBldgDTO> selectCoBldg(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @Parameter(description = "건물ID") @PathVariable String gateId) {
        
        return new ResponseModel<>(HttpStatus.OK, buildingService.selectCoBldg(gateId));
    }

    /**
     * 건물정보 (key, value) 목록 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 16.
     */
    @Operation(summary = "건물정보 (key, value) 목록 조회", description = "건물 목록을 (key, value) 형식으로 조회한다. (조회API지만 조회 조건의 크기를 고려하여 Post로 제공)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/key", produces = { "application/json" })
    public @ResponseBody ResponseModel<Map<String, CoBldgDTO>> selectCoBldgList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody CoBldgSearchDTO coBldgSearchDTO
    ) {
        
        return new ResponseModel<>(HttpStatus.OK, buildingService.selectCoBldgList(coBldgSearchDTO.getGateIdList()));
    }

    /**
     * 건물보안출입문 (건물정보) 목록 조회 API
     * gateId 가 비어 있는 데이터가 포함되어 있어서 Map 아 아닌 List로 제공
     *
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 4. 22.
     */
    @Operation(summary = "건물보안출입문 (건물정보) 목록 조회", description = "건물보안출입문 (건물정보) 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/{compId}/gate")
    public @ResponseBody ResponseModel<List<CoXempBldgOutDTO>> selectCoXempBldgOut(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String compId
    ) {
        return new ResponseModel<>(HttpStatus.OK, buildingService.selectCoXempBldgOut(compId));
    }

}