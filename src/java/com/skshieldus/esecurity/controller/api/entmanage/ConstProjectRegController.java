package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.ConstProjectRegService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;

@Tag(name = "공사프로젝트 등록 API")
@Controller
@RequestMapping(value = "/api/entmanage/constProjectReg", produces = { "application/json" })
public class ConstProjectRegController {

    @Autowired
    private Environment environment;

    @Autowired
    private ConstProjectRegService constProjectRegService;

    /**
     * 공사프로젝트 등록 현황 등록 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "공사프로젝트 등록 현황 등록 리스트 조회", description = "공사프로젝트 등록 현황 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constProjectRegList", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectConstProjectRegList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        paramMap.put("authYn", "N");
        return constProjectRegService.selectConstProjectRegList(paramMap);
    }

    /**
     * 공사프로젝트 등록 현황 등록 리스트 조회 API (관리자)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 03. 08.
     */
    @Operation(summary = "공사프로젝트 등록 현황 등록 리스트 조회 (관리자)", description = "공사프로젝트 등록 현황 리스트를 조회한다. (관리자)")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constProjectRegListAdm", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectConstProjectRegListAdm(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        paramMap.put("authYn", "Y");
        return constProjectRegService.selectConstProjectRegList(paramMap);
    }

    /**
     * 공사프로젝트 등록 현황 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "공사프로젝트 등록 현황 조회", description = "공사프로젝트 등록 현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constProjectRegView", produces = { "application/json" })
    public @ResponseBody Map<String, Object> selectConstProjectRegView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return constProjectRegService.selectConstProjectRegView(paramMap);
    }

    /**
     * 공사프로젝트 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "공사프로젝트 등록", description = "공사프로젝트를 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully inserted data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constProjectRegister", produces = { "application/json" })
    public @ResponseBody boolean insertConstProjectRegRequest(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return constProjectRegService.insertConstProjectReg(sessionInfoVO, paramMap);
    }

    /**
     * 공사프로젝트 등록 통합결재 후처리
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 16.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //    @Operation(summary = "공사프로젝트 등록 결재 후처리", description = "공사프로젝트 등록 결재 후처리를 수행한다.")
    //    @ApiResponses(value = {
    //            @ApiResponse(responseCode = "200", description = "Successfully updated data"),
    //            @ApiResponse(responseCode = "404", description = "error") })
    //    @PostMapping(value = "/approval/postprocess")
    //    public @ResponseBody ResponseModel<EaiResponseDTO> processConstProjectRegApproval(
    //            @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //            throws Exception {
    //
    //        return new ResponseModel<>(HttpStatus.OK, constProjectRegApprovalPostProcess.postProcess(requestDTO));
    //    }
}
