package com.skshieldus.esecurity.controller.api.sample;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SampleDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "Sample API")
@Controller
@RequestMapping(value = "/api/sample", produces = { MediaType.APPLICATION_JSON_VALUE })
public class SampleController {

    @Autowired
    private Environment environment;

    @Autowired
    private SampleService service;
    /**
     * Sample API
     */
    @Operation(summary = "Sample Data 조회", description = "Sample Data 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/list")
    public @ResponseBody ResponseModel<List<SampleDTO>> sampleList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, SampleDTO sampleDTO
    ) {
        List<SampleDTO> sampleList = null;
        int resultSize = 0;
        if(sampleDTO.getPageIndex() != null && sampleDTO.getPageSize() != null) {
            sampleList = service.selectSampleListPaging(sampleDTO);
        }
        else {
            sampleList = service.selectSampleList(sampleDTO);
        }

        resultSize = service.selectSampleListCnt(sampleDTO);

        return new ResponseModel<>(HttpStatus.OK, sampleList, resultSize);
    }

    /**
     * 임직원 명단(기간별현황) 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     */
    @Operation(summary = "sample Data엑셀 다운로드", description = "임직원 명단(기간별현황) 엑셀 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/data/download")
    public String excel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap, Model model) throws EsecurityException {
        if (sessionInfoVO.getUserId() == null && environment.acceptsProfiles(Profiles.of("prd"))) {
            throw new EsecurityException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.value());
        }

        CommonXlsViewDTO commonXlsViewDTO = service.selectSampleExcelList(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }
}
