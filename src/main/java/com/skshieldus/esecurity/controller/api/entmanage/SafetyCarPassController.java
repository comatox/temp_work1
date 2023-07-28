package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.SafetyCarPassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "차량출입 관리 API")
@Controller
@RequestMapping(value = "/api/entmanage/safetyCarPass", produces = { "application/json" })
public class SafetyCarPassController {

    @Autowired
    private Environment environment;

    @Autowired
    private SafetyCarPassService safetyCarPassService;

    /**
     * 안전작업차량 출입신청 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 08.
     */
    @Operation(summary = "안전작업차량 출입신청 리스트 조회", description = "안전작업차량 출입신청 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/list", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSafetyCarPassList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        Map<String, Object> resultMap = safetyCarPassService.selectSafetyCarPassList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 안전작업차량 출입관리 상세현황 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    @Operation(summary = "안전작업차량 출입관리 상세현황 조회", description = "안전작업차량 출입관리 상세현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/view/{tmpcarAppNo}", produces = { "application/json" })
    public @ResponseBody ResponseModel<Map<String, Object>> selectSafetyCarPassView(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap,
        @PathVariable String tmpcarAppNo
    ) {
        paramMap.put("tmpcarAppNo", tmpcarAppNo);
        return new ResponseModel<>(HttpStatus.OK, safetyCarPassService.selectSafetyCarPassView(paramMap));
    }

    @Operation(summary = "안전작업차량 출입신청 등록", description = "안전작업차량 출입신청을 등록한다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/save")
    public @ResponseBody ResponseModel<Boolean> insertSafetyCarPassRequest(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody Map<String, Object> paramMap) throws EsecurityException {

        return new ResponseModel<>(HttpStatus.OK, safetyCarPassService.insertSafetyCarPassRequest(sessionInfoVO, paramMap));
    }

}
