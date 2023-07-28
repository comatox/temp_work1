package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.CarPassReqstService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "방문차량출입 신청 API")
@Controller
@RequestMapping(value = "/api/entmanage/carPassReqst", produces = { "application/json" })
public class CarPassReqstController {

    @Autowired
    private Environment environment;

    @Autowired
    private CarPassReqstService carPassReqstService;

    /**
     * 방문차량출입 신청 현황 신청 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 08.
     */
    @Operation(summary = "방문차량출입 신청 현황 신청 리스트 조회", description = "방문차량출입 신청 현황 신청 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/carPassReqstList", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectCarPassReqstList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return carPassReqstService.selectCarPassReqstList(paramMap);
    }

    /**
     * 방문차량출입 신청 현황 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 08.
     */
    @Operation(summary = "방문차량출입 신청 현황 조회", description = "방문차량출입 신청 현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/view", produces = { "application/json" })
    public @ResponseBody Map<String, Object> selectCarPassReqstView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        return carPassReqstService.selectCarPassReqstView(paramMap);
    }

    /**
     * 방문차량출입 신청 현황 신청 부서별 쿼터 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 06.
     */
    @Operation(summary = "방문차량출입 신청 현황 신청 부서별 쿼터 조회", description = "방문차량출입 신청 현황 신청 부서별 쿼터를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/carPassQuotaCheck", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectCarPassQuotaCheck(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return carPassReqstService.selectCarPassQuotaCheck(paramMap);
    }

    /**
     * 방문차량출입 신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 08.
     */
    @Operation(summary = "방문차량출입 신청", description = "방문차량출입 신청을 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully inserted data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/carPassReqstRegister", produces = { "application/json" })
    public @ResponseBody boolean insertCarPassReqstRequest(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return carPassReqstService.insertCarPassReqst(sessionInfoVO, paramMap);
    }

    /**
     * 방문차량출입 신청 통합결재 후처리
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 08.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "방문차량출입 신청 결재 후처리", description = "방문차량출입 신청 결재 후처리를 수행한다.")
    //	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully updated data"),
    //			@ApiResponse(responseCode = "404", description = "error") })
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processCarPassReqstApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //
    //		return new ResponseModel<>(HttpStatus.OK, carPassReqstApprovalPostProcess.postProcess(requestDTO));
    //	}
}
