package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.IoTmpCarReqstService;
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

import java.util.List;
import java.util.Map;

@Tag(name = "임시차량출입 신청 API")
@Controller
@RequestMapping(value = "/api/entmanage/ioTmpCarReqst", produces = { "application/json" })
public class IoTmpCarReqstController {

    @Autowired
    private Environment environment;

    @Autowired
    private IoTmpCarReqstService ioTmpCarReqstService;

    /**
     * 임시차량출입 신청 현황 신청 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 08.
     */
    @Operation(summary = "임시차량출입 신청 현황 신청 리스트 조회", description = "임시차량출입 신청 현황 신청 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioTmpCarReqstList", produces = { "application/json" })
    public @ResponseBody List<Map<String, Object>> selectIoTmpCarReqstList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return ioTmpCarReqstService.selectIoTmpCarReqstList(paramMap);
    }

    /**
     * 임시차량출입 신청 현황 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 08.
     */
    @Operation(summary = "임시차량출입 신청 현황 조회", description = "임시차량출입 신청 현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioTmpCarReqstView", produces = { "application/json" })
    public @ResponseBody Map<String, Object> selectIoTmpCarReqstView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return ioTmpCarReqstService.selectIoTmpCarReqstView(paramMap);
    }

    /**
     * 임시차량출입 신청
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 08.
     */
    @Operation(summary = "임시차량출입 신청", description = "임시차량출입 신청을 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully inserted data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioTmpCarReqstRegister", produces = { "application/json" })
    public @ResponseBody boolean insertIoTmpCarReqstRequest(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        

        return ioTmpCarReqstService.insertIoTmpCarReqst(sessionInfoVO, paramMap);
    }

    /**
     * 임시차량출입 신청 통합결재 후처리
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 08.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "임시차량출입 신청 결재 후처리", description = "임시차량출입 신청 결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //  @ApiResponse(responseCode = "200", description = "Successfully updated data"),
    //			@ApiResponse(responseCode = "404", description = "error") })
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processIoTmpCarReqstApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //
    //		return new ResponseModel<>(HttpStatus.OK, ioTmpCarReqstApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 임시차량출입 신청 엑셀다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 11. 09.
     */
    @Operation(summary = "임시차량출입 신청 현황 엑셀다운로드", description = "임시차량출입 신청 현황을 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/download")
    public String selectIoTmpCarReqstListExcel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap, Model model) {
        

        CommonXlsViewDTO commonXlsViewDTO = ioTmpCarReqstService.selectIoTmpCarReqstListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

}
