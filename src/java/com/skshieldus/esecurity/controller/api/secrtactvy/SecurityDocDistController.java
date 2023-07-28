package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityDocDistService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "비문분류기준표 API")
@Controller
@RequestMapping(value = "/api/secrtactvy/securityDocDist", produces = { "application/json" })
public class SecurityDocDistController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityDocDistService service;

    /**
     * 부서 비문분류기준표 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 24.
     */
    @Operation(summary = "부서 비문분류기준표 목록 조회", description = "부서 비문분류기준표 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityDocDistItemList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        // 관리자여부 고정 (사용자)
        paramMap.put("adminYn", "N");
        resultList = service.selectSecurityDocDistItemList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 부서 비문분류기준표 상세  조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scDocDistId
     * @return
     *
     * @throws EsecurityException
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 24.
     */
    @Operation(summary = "부서 비문분류기준표 상세  조회", description = "부서 비문분류기준표 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/{scDocDistId}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecurityDocDistView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable String scDocDistId
    ) throws EsecurityException {
        
        paramMap.put("scDocDistId", scDocDistId);
        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityDocDistView(paramMap));
    }

    /**
     * 부서 비문분류기준표 상세 비문(비밀/대외비) 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scDocDistId
     * @return
     *
     * @throws EsecurityException
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 24.
     */
    @Operation(summary = "부서 비문분류기준표 상세 비문(비밀/대외비) 목록 조회", description = "부서 비문분류기준표 상세 비문(비밀/대외비) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/{scDocDistId}/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityDocDistViewList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable String scDocDistId
    ) throws EsecurityException {
        
        paramMap.put("scDocDistId", scDocDistId);
        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityDocDistViewList(paramMap));
    }

    /**
     * 팀 비문분류기준표 등록(상신)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 27.
     */
    @Operation(summary = "팀 비문분류기준표 등록(상신)", description = "팀 비문분류기준표를 등록(상신)한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Boolean> insertRepairGateInChange(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());
        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertSecurityDocDist(paramMap));
    }

    /**
     * 비문분류기준표 등록 통합결재 후처리
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 28.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "비문분류기준표 등록 통합결재 후처리", description = "비문분류기준표 등록 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processSecurityDocDistApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, securityDocDistApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 부서 비문분류기준표 상세 비문(비밀/대외비) 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param scDocDistId
     * @param model
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 24.
     */
    @Operation(summary = "부서 비문분류기준표 상세 비문(비밀/대외비) 엑셀 다운로드", description = "부서 비문분류기준표 상세 비문(비밀/대외비) 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/{scDocDistId}/download")
    public String selectSecurityDocDistViewDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, @PathVariable String scDocDistId, Model model
    ) {
        
        paramMap.put("scDocDistId", scDocDistId);
        CommonXlsViewDTO commonXlsViewDTO = service.selectSecurityDocDistViewDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

}

