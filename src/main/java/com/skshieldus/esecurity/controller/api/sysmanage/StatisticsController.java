package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.sysmanage.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Tag(name = "통계 조회 API")
@Controller
@RequestMapping(value = "/api/sysmanage/statistics", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class StatisticsController {

    @Autowired
    private Environment environment;

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 통계 보안담당자 부서조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 01. 10
     */
    @Operation(summary = "보안담당자 부서 조회", description = "보안담당자 부서를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/secDeptsCombo/{secEmpId}", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectMyRecentStatistics(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable String secEmpId
    ) {
        

        return new ResponseModel<>(HttpStatus.OK, statisticsService.selectSecDeptsCombo(secEmpId));
    }

    /**
     * 통계 엑셀다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2022. 01. 10
     */
    @Operation(summary = "통계 엑셀다운로드", description = "통계 현황을 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/download")
    public String selectStatisticsExcel(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = statisticsService.selectStatisticsExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

}