package com.skshieldus.esecurity.controller.api.common;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.IoCompDTO;
import com.skshieldus.esecurity.model.common.IoCompSearchDTO;
import com.skshieldus.esecurity.model.common.IoEmpDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.common.IoCompService;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "외부업체 정보 API")
@RestController
@RequestMapping(value = "/api/common/ioComp", produces = { MediaType.APPLICATION_JSON_VALUE })
public class IoCompController {

    @Autowired
    private Environment environment;

    @Autowired
    private IoCompService service;

    /**
     * 외부업체 회원 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramDTO
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 4. 26.
     */
    @Operation(summary = "외부업체 회원 목록 조회", description = "외부업체 회원 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/ioEmp")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoEmpList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap) {

        ListDTO<Map<String, Object>> listDTO = service.selectIoEmpList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 외부업체 회원 정보 조회 API
     *
     * @param sessionInfoVO
     * @param ioEmpId
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 4. 26.
     */
    @Operation(summary = "외부업체 회원 정보 조회", description = "외부업체 회원 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/ioEmp/{id}")
    public @ResponseBody ResponseModel<IoEmpDTO> selectIoEmp(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String ioEmpId) {
        

        // 상세 조회
        IoEmpDTO result = service.selectIoEmp(ioEmpId);
        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 외부업체 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramDTO
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "외부업체 목록 조회", description = "외부업체 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "")
    public @ResponseBody ResponseModel<List<IoCompDTO>> selectIoCompList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, IoCompSearchDTO paramDTO) {
        

        // 목록 조회
        List<IoCompDTO> resultList = service.selectIoCompList(paramDTO);

        if ("Y".equals(paramDTO.getPagingYn())) {
            return new ResponseModel<>(HttpStatus.OK, resultList, service.selectIoCompListCnt(paramDTO));
        }

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 외부업체 상세 조회 API
     *
     * @param sessionInfoVO
     * @param ioCompId
     * @return
     *
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "외부업체 상세 조회", description = "외부업체 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"), @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/{id}")
    public @ResponseBody ResponseModel<IoCompDTO> selectIoComp(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("id") String ioCompId) {
        

        // 상세 조회
        IoCompDTO result = service.selectIoComp(ioCompId);

        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 도급업체 회원정보 목록 조회
     *
     * @throws Exception
     * @author : HoonLee
     */
    @Operation(summary = "도급업체 회원정보 목록 조회", description = "도급업체 회원정보 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/ioEmp/subcont")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoEmpSubcontList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestParam Map<String, Object> paramMap ) throws Exception {
        // 목록 조회
        ListDTO<Map<String, Object>> listDTO = service.selectIoEmpSubcontList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());

    }

}
