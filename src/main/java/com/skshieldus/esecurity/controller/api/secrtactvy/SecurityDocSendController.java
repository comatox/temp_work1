package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityDocSendService;
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

@Tag(name = "외부공개 자료 사전증인 API")
@Controller
@RequestMapping(value = "/api/secrtactvy/securityDocSend", produces = { "application/json" })
public class SecurityDocSendController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityDocSendService service;

    /**
     * 외부공개 자료 신청현황 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 13.
     */
    @Operation(summary = "외부공개 자료 신청현황 목록 조회", description = "외부공개 자료 신청현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityDocSendList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityDocSendList(paramMap));
    }

    /**
     * 외부공개 자료 신청현황 상세 조회
     *
     * @param sessionInfoVO
     * @param coPositionPledgeNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    @Operation(summary = "외부공개 자료 신청현황 상세 조회", description = "외부공개 자료 신청현황 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecurityDocSend(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer docSendapplNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityDocSend(docSendapplNo));
    }

    /**
     * 외부공개자료 사전승인 > 외부공개자료 승인신청(상신)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "외부공개자료 승인신청(상신)", description = "외부공개자료 승인신청(상신)한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Boolean> insertSecurityDocSend(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertDocSend(paramMap));
    }

    /**
     * 외부공개자료 승인신청 통합결재 후처리
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 02.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "외부공개자료 승인신청 통합결재 후처리", description = "외부공개자료 승인신청 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processSecurityDocSendApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, securityDocSendApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 발표논문리스트 조회 SOAP 연동
     *
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     *
     * @throws Exception
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "발표논문리스트 조회 SOAP 연동", description = "발표논문리스트 조회 SOAP 연동")
    @GetMapping(value = "/thesisList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> getThesisList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) {
        
        List<Map<String, Object>> resultList = service.getThesisList(paramMap);
        int totCnt = 0;
        if (resultList.size() > 0) {
            totCnt = Integer.parseInt((String) resultList.get(0).get("totCnt"));
        }

        return new ResponseModel<>(HttpStatus.OK, resultList, totCnt);
    }

    /**
     * 외부공개 자료 신청현황 목록 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 10.
     */
    @Operation(summary = "외부공개 자료 신청현황 목록 엑셀 다운로드", description = "외부공개 자료 신청현황 목록 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/download")
    public String selectSecurityDocSendListExcel(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.selectSecurityDocSendListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

}

