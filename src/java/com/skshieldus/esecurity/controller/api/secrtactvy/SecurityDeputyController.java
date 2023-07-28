package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityDeputyService;
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
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "보안담당자 신규/변경 API")
//@RestController
@Controller
@RequestMapping(value = "/api/secrtactvy/securityDeputy", produces = { "application/json" })
public class SecurityDeputyController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityDeputyService service;

    /**
     * 보안담당자 신규/변경 조회 - securityDeputyList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 01
     */
    @Operation(summary = "보안담당자 신규/변경 조회", description = "보안담당자 신규/변경 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityDeputyList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityDeputyList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityDeputyList(paramMap));
    }

    /**
     * 보안담당자 신규/변경 상세정보 - securityDeputyView
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 01
     */
    @Operation(summary = "보안담당자 신규/변경 상세정보", description = "보안담당자 신규/변경 상세정보")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityDeputyView")
    public @ResponseBody ResponseModel<Map<String, Object>> securityDeputyView(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityDeputyView(paramMap));
    }

    /**
     * 보안담당자 신규/변경 상세정보 > 관리부서 Tree - securityDeputyDeptTreeList
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 01
     */
    @Operation(summary = "보안담당자 신규/변경 상세정보 > 관리부서 Tree", description = "보안담당자 신규/변경 상세정보 > 관리부서 Tree")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityDeputyDeptTreeList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityDeputyDeptTreeList(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityDeputyDeptTreeList(paramMap));
    }

    /**
     * 팀내생활보안점검 > 점검부서 Tree - securityDeputyDeptTreeList3
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 23
     */
    @Operation(summary = "팀내생활보안점검 > 점검부서 Tree", description = "팀내생활보안점검 > 점검부서 Tree")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/securityDeputyDeptTreeList3")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> securityDeputyDeptTreeList3(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.securityDeputyDeptTreeList3(paramMap));
    }

    /**
     * 보안담당자 신규/변경  > 보안담당자 신규/변경 (상신)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 26
     */
    @Operation(summary = "보안담당자 신규/변경 (상신)", description = "보안담당자 신규/변경를 (상신)한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/insertSecurityDeputy")
    public @ResponseBody ResponseModel<Boolean> insertSecurityDeputy(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestBody HashMap<String, Object> paramMap
    ) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertSecurityDeputy(paramMap));
    }

    /**
     * 보안담당자 신규/변경  > 보안담당자 신규/변경 통합결재 후처리
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 26
     * @param sessionInfoVO
     * @param paramMap
     * @return
     * @throws EsecurityException
     */
    //	@Operation(summary = "보안담당자 신규/변경 통합결재 후처리", description = "보안담당자 신규/변경 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processSecurityRectifyPlanApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //
    //		return new ResponseModel<>(HttpStatus.OK, securityDeputyApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 보안담당자 신규/변경 신청 > 보안담당자 List - secrtDeptDuptyCheckDuptyRenew
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 29
     */
    @Operation(summary = "보안담당자 신규/변경 신청 > 보안담당자 List", description = "보안담당자 신규/변경 신청 > 보안담당자 List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDeptDuptyCheckDuptyRenew")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> secrtDeptDuptyCheckDuptyRenew(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());
        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secrtDeptDuptyCheckDuptyRenew(paramMap));
    }

    /**
     * 보안담당자 신규/변경 신청 > 보안담당자 정보 - secrtDeptDuptyDuptyInfo
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0123882<skhy.X0123882@partner.sk.com>
     * @since : 2021. 11. 01
     */
    @Operation(summary = "보안담당자 신규/변경 신청 > 보안담당자 정보", description = "보안담당자 신규/변경 신청 > 보안담당자 정보")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/secrtDeptDuptyDuptyInfo")
    public @ResponseBody ResponseModel<Map<String, Object>> secrtDeptDuptyDuptyInfo(
        @Parameter(hidden = true)
            SessionInfoVO sessionInfoVO,
        @RequestParam HashMap<String, Object> paramMap
    ) throws EsecurityException {

        //System.out.println("# sessionInfoVO:" + sessionInfoVO.toString());

        

        //System.out.println("# paramMap:" + paramMap.toString());
        return new ResponseModel<>(HttpStatus.OK, service.secrtDeptDuptyDuptyInfo(paramMap));
    }

}
