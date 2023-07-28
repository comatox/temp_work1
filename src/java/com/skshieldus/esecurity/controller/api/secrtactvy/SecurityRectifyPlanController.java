package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityRectifyPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "보안위규 및 시정계획서 API")
@Controller
@RequestMapping(value = "/api/secrtactvy/securityRectifyPlan", produces = { "application/json" })
public class SecurityRectifyPlanController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityRectifyPlanService service;

    /**
     * 시정개선계획서(보안위규 이력조회) 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 07.
     */
    @Operation(summary = "시정개선계획서(보안위규 이력조회) 목록 조회", description = "시정개선계획서(보안위규 이력조회) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityRectifyPlanList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityRectifyPlanList(paramMap), service.selectSecurityRectifyPlanListCnt(paramMap));
    }

    /**
     * 시정개선계획서(보안위규 이력조회) 상세 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 07.
     */
    @Operation(summary = "시정개선계획서(보안위규 이력조회) 상세 조회", description = "시정개선계획서(보안위규 이력조회) 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/view")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecurityRectifyPlanView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap
    ) throws EsecurityException {
        Map<String, Object> result = service.selectSecurityRectifyPlanView(paramMap);
        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 시정개선계획서(보안위규 이력조회) > 시정계획서 제출(상신)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 08.
     */
    @Operation(summary = "시정계획서 제출(상신)", description = "시정계획서를 제출(상신)한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "")
    public @ResponseBody ResponseModel<Boolean> insertRectifyPlan(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {

        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertRectifyPlan(paramMap));
    }

    /**
     * 시정계획서 제출 통합결재 후처리
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 08.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "시정계획서 제출 통합결재 후처리", description = "시정계획서 제출 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processSecurityRectifyPlanApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, securityRectifyPlanPostProcess.postProcess(requestDTO));
    //	}
}

