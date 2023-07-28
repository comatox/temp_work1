package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.ConstPassProvService;
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

@Tag(name = "공사출입증 지급신청/관리 API")
@Controller
@RequestMapping(value = "/api/entmanage/constPassProv", produces = { "application/json" })
public class ConstPassProvController {

    @Autowired
    private Environment environment;

    @Autowired
    private ConstPassProvService constPassProvService;

    /**
     * 공사출입증 지급신청 현황 등록 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "공사출입증 지급신청 현황 리스트 조회", description = "공사출입증 지급신청 현황 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constPassProvList", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectConstPassProvList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return constPassProvService.selectConstPassProvList(paramMap);
    }

    /**
     * 공사출입증 지급신청 현황 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "공사출입증 지급신청 현황 상세조회", description = "공사출입증 지급신청 현황을 상세조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constPassProvView", produces = { "application/json" })
    public @ResponseBody Map<String, Object> selectConstPassProvView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return constPassProvService.selectConstPassProvView(paramMap);
    }

    /**
     * 공사출입증 지급관리 현황 등록 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 22.
     */
    @Operation(summary = "공사출입증 지급관리 현황 리스트 조회", description = "공사출입증 지급관리 현황 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constPassMngList", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectConstPassMngList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return constPassProvService.selectConstPassMngList(paramMap);
    }

    /**
     * 공사출입증 지급관리 현황 사용등록 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 22.
     */
    @Operation(summary = "공사출입증 지급관리 현황 사용등록 리스트 조회", description = "공사출입증 지급관리 현황 사용등록 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constPassMngCardList", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectConstPassMngCardList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return constPassProvService.selectConstPassMngCardList(paramMap);
    }

    /**
     * 공사출입증 지급신청 신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 02. 04.
     */
    @Operation(summary = "공사출입증 지급신청", description = "공사출입증 지급신청을 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully inserted data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constPassProvRegister", produces = { "application/json" })
    public @ResponseBody boolean insertConstPassProv(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return constPassProvService.insertConstPassProv(sessionInfoVO, paramMap);
    }

    /**
     * 공사출입증 지급신청 담당자 접수반려
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 02. 04.
     */
    @Operation(summary = "공사출입증 지급신청 담당자 접수반려", description = "공사출입증 지급신청 담당자 접수반려한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully inserted data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/constPassProvReject", produces = { "application/json" })
    public @ResponseBody boolean rejectConstPassProv(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return constPassProvService.rejectConstPassProv(sessionInfoVO, paramMap);
    }

    /**
     * 공사출입증 지급신청 신청 통합결재 후처리
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 02. 04.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "공사출입증 지급신청 결재 후처리", description = "공사출입증 지급신청 결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //      @ApiResponse(responseCode = "200", description = "Successfully updated data"),
    //			@ApiResponse(responseCode = "404", description = "error") })
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processConstPassProvApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //
    //		return new ResponseModel<>(HttpStatus.OK, constPassProvApprovalPostProcess.postProcess(requestDTO));
    //	}
}
