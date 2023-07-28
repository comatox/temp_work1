package com.skshieldus.esecurity.controller.api.inoutasset;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.service.inoutasset.InoutCommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Tag(name = "자산반출입 공통 API")
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api/inoutasset/inoutcommon", produces = { "application/json" })
public class InoutCommonController {

    private final InoutCommonService service;

    /**
     * 반출입 및 물품내역 정보 조회
     *
     * @param inoutApplNo
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "반출입 및 물품내역 정보 조회", description = "반출입 및 물품내역 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/view/{inoutApplNo}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectInOutView(@PathVariable("inoutApplNo") Integer inoutApplNo) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectInOutView(inoutApplNo));
    }

}