package com.skshieldus.esecurity.controller.api.inoutasset;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.service.inoutasset.InoutChangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Tag(name = "자산반출입 변경요청 API")
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/api/inoutasset/inoutchange", produces = { "application/json" })
public class InoutChangeController {

    private final InoutChangeService service;

    /**
     * 반입일자 변경 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "반입일자 변경 목록 조회", description = "반입일자 변경 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/indatechange")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectInDateChangeList(@RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.selectInDateChangeList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 반입일자 변경 신청 상신
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "반입일자 변경 신청 상신", description = "반입일자 변경 신청을 상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/indatechange")
    public @ResponseBody ResponseModel<Boolean> insertInDateChange(@RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        service.insertInDateChange(paramMap);
        return new ResponseModel<>(HttpStatus.OK, true);
    }

    /**
     * 담당자 변경 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "담당자 변경 목록 조회", description = "담당자 변경 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/empchange")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectInOutEmpChangeList(@RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.selectInOutEmpChangeList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 담당자 변경 신청 상신
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "담당자 변경 신청 상신", description = "담당자 변경 신청을 상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/empchange")
    public @ResponseBody ResponseModel<Boolean> insertEmpChange(@RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        service.insertEmpChange(paramMap);
        return new ResponseModel<>(HttpStatus.OK, true);
    }

    /**
     * 반입불요전환 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "반입불요전환 목록 조회", description = "반입불요전환 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/inoutkndchange")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectInOutKndChangeList(@RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.selectInOutKndChangeList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 반입불요 전환 신청 상신
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "반입불요 전환 신청 상신", description = "반입불요 전환 신청을 상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/inoutkndchange")
    public @ResponseBody ResponseModel<Boolean> insertInOutKndChange(@RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        service.insertInOutKndChange(paramMap);
        return new ResponseModel<>(HttpStatus.OK, true);
    }

    /**
     * 물품반입확인서 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "물품반입확인서 목록 조회", description = "물품반입확인서 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/finishchange")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectFinishChangeList(@RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.selectFinishChangeList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 물품반입확인서 신청 저장/상신
     *
     * @param paramMap
     * @param file1
     * @param file2
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "물품반입확인서 신청 저장/상신", description = "물품반입확인서 신청 정보를 저장/상신한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/finishchange")
    public @ResponseBody ResponseModel<Boolean> saveInoutwrite(@RequestParam HashMap<String, Object> paramMap, @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2) throws EsecurityException {
        service.insertFinishChange(paramMap, file1, file2);
        return new ResponseModel<>(HttpStatus.OK, true);
    }

}