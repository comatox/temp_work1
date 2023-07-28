package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.IoDeliveryCarPassReqstService;
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

@Tag(name = "납품차량출입 신청 API")
@Controller
@RequestMapping(value = "/api/entmanage/ioDeliveryCarPassReqst", produces = { "application/json" })
public class IoDeliveryCarPassReqstController {

    @Autowired
    private Environment environment;

    @Autowired
    private IoDeliveryCarPassReqstService ioDeliveryCarPassReqstService;

    /**
     * 납품차량출입 신청 현황 신청 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    @Operation(summary = "납품차량출입 신청 현황 신청 리스트 조회", description = "납품차량출입 신청 현황 신청 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioDeliveryCarPassReqstList", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectIoDeliveryCarPassReqstList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return ioDeliveryCarPassReqstService.selectIoDeliveryCarPassReqstList(paramMap);
    }

    /**
     * 납품차량출입 신청 현황 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    @Operation(summary = "납품차량출입 신청 현황 조회", description = "납품차량출입 신청 현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioDeliveryCarPassReqstView", produces = { "application/json" })
    public @ResponseBody Map<String, Object> selectIoDeliveryCarPassReqstView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return ioDeliveryCarPassReqstService.selectIoDeliveryCarPassReqstView(paramMap);
    }

    /**
     * 납품차량출입 신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     */
    @Operation(summary = "납품차량출입 신청", description = "납품차량출입 신청을 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully inserted data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioDeliveryCarPassReqstRegister", produces = { "application/json" })
    public @ResponseBody boolean insertIoDeliveryCarPassReqstRequest(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return ioDeliveryCarPassReqstService.insertIoDeliveryCarPassReqst(sessionInfoVO, paramMap);
    }

    /**
     * 납품차량출입 신청 통합결재 후처리
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 14.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "납품차량출입 신청 결재 후처리", description = "납품차량출입 신청 결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //      @ApiResponse(responseCode = "200", description = "Successfully updated data"),
    //			@ApiResponse(responseCode = "404", description = "error") })
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processDeliveryCarPassReqstApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //
    //		return new ResponseModel<>(HttpStatus.OK, ioDeliveryCarPassReqstApprovalPostProcess.postProcess(requestDTO));
    //	}
}
