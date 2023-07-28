package com.skshieldus.esecurity.controller.api.inoutasset;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.service.inoutasset.InoutwriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "자산반출입 작성 API")
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api/inoutasset/inoutwrite", produces = { "application/json" })
public class InoutwriteController {

    private final Environment environment;

    private final InoutwriteService service;

    /**
     * 자산반출입 구분별 그룹정보 목록 조회
     * @param paramMap
     * @return
     * @throws EsecurityException
     */
    @Operation(summary = "자산반출입 구분별 그룹정보 목록 조회", description = "자산반출입 구분별 그룹정보 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/articleGroupCode")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectArticleGroupCodeList(@RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.selectArticleGroupCodeList(paramMap));
    }

    /**
     * 자산반출입 신청 저장/상신
     *
     * @param paramMap
     * @param fileToUpload
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "자산반출입 신청 저장/상신", description = "자산반출입 신청 정보를 저장/상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/request")
    public @ResponseBody ResponseModel<Map<String, Object>> saveInoutwrite(@RequestParam HashMap<String, Object> paramMap, List<MultipartFile> fileToUpload) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, service.saveInoutwrite(paramMap, fileToUpload));
    }

}