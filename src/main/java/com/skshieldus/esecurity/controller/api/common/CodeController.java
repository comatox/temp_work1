package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CoCodeDetailDTO;
import com.skshieldus.esecurity.model.common.CoCodeMasterDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.CodeService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공통코드 조회 API")
@RestController
@RequestMapping(value = "/api/common/code", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CodeController {

    @Autowired
    private Environment environment;

    @Autowired
    private CodeService codeService;

    /**
     * 공통코드 전체(Master + DetailList) 목록 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2020. 12. 30.
     */
    @Operation(summary = "공통코드 전체 목록 조회", description = "공통코드 전체(Master + DetailList) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<CoCodeMasterDTO>> selectCodeMasterList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO) {
        return new ResponseModel<>(HttpStatus.OK, codeService.selectCodeMasterList());
    }

    /**
     * 공통코드 목록 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2020. 12. 30.
     */
    @Operation(summary = "공통코드 상세 목록 조회", description = "그룹코드별 코드 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/{grpCd}", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<CoCodeDetailDTO>> selectCodeDetailList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @Parameter(description = "그룹코드") @PathVariable String grpCd) {
        return new ResponseModel<>(HttpStatus.OK, codeService.selectCodeDetailList(grpCd));
    }

    /**
     * 공통코드 전체(Master + DetailList) (key, value) 조회 API
     *
     * @return
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 1. 28.
     */
    @Operation(summary = "공통코드 전체 (key, value) 조회", description = "공통코드 전체(Master + DetailList) (key, value)를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/key", produces = { "application/json" })
    public @ResponseBody ResponseModel<Map<String, Object>> selectCodeMasterListForKey(@Parameter(hidden = true) SessionInfoVO sessionInfoVO) {
        return new ResponseModel<>(HttpStatus.OK, codeService.selectCodeMasterListForKey());
    }

}