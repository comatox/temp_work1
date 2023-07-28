package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityUSBPortLockService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "USB포트락 API")
@Controller
@RequestMapping(value = "/api/secrtactvy/securityUsbPortLock", produces = { "application/json" })
public class SecurityUSBPortLockController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityUSBPortLockService service;

    /**
     * 생산장비 마스터키 관리 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 12.
     */
    @Operation(summary = "생산장비 마스터키 관리 목록 조회", description = "생산장비 마스터키 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/productionMasterKey/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> productionMasterKeyList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String pagingYn = paramMap.get("pagingYn") != null
            ? (String) paramMap.get("pagingYn")
            : "N";

        if (pagingYn.equals("Y")) {
            return new ResponseModel<>(HttpStatus.OK, service.productionMasterKeyList(paramMap), service.productionMasterKeyListCnt(paramMap));
        }
        else {
            return new ResponseModel<>(HttpStatus.OK, service.productionMasterKeyList(paramMap));
        }
    }

    /**
     * 생산장비 마스터키 관리 권한 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "I0101124"
     * @since : 2021. 10. 12.
     */
    @Operation(summary = "생산장비 마스터키 관리 권한 조회", description = "생산장비 마스터키 관리 권한을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/productionMasterKey/adminCheck")
    public @ResponseBody ResponseModel<Map<String, Object>> productionMasterKeyAdminCheck(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {

        

        return new ResponseModel<>(HttpStatus.OK, service.productionMasterKeyAdminCheck(paramMap));
    }

    /**
     * 생산장비 마스터키 관리 상태값 변경 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 13.
     */
    @Operation(summary = "생산장비 마스터키 관리 상태값 변경", description = "생산장비 마스터키 관리 상태값을 변경 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionMasterKey/change")
    public @ResponseBody ResponseModel<Boolean> productionMasterKeyStatusChange(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionMasterKeyStatusChange(paramMap));
    }

    /**
     * 생산장비 마스터키 관리자 변경 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 13.
     */
    @Operation(summary = "생산장비 마스터키 관리자 변경", description = "생산장비 마스터키 관리자를 변경 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionMasterKey/mgmtChange")
    public @ResponseBody ResponseModel<Boolean> productionMasterKeyMgmtChange(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionMasterKeyMgmtChange(paramMap));
    }

    /**
     * 생산장비 마스터키 관리 실사결과 변경 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 13.
     */
    @Operation(summary = "생산장비 마스터키 관리 실사결과 변경", description = "생산장비 마스터키 관리 실사결과값을 변경 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionMasterKey/check")
    public @ResponseBody ResponseModel<Boolean> updateScUSBPortLockMastKeyCheck(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateScUSBPortLockMastKeyCheck(paramMap));
    }

    /**
     * 생산장비 마스터키 관리 캠퍼스/건물 변경 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "I0101124"
     * @since : 2022. 2. 23.
     */
    @Operation(summary = "생산장비 마스터키 관리 캠퍼스/건물 변경", description = "생산장비 마스터키 관리 캠퍼스/건물 변경 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionMasterKey/compBldUpdate")
    public @ResponseBody ResponseModel<Boolean> updateScUSBPortLockMastKeyCompBld(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.updateScUSBPortLockMastKeyCompBld(paramMap));
    }

    /**
     * 산업제어시스템 마스터키 관리 목록조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 15.
     */
    @Operation(summary = "산업제어시스템 마스터키 관리 목록 조회", description = "산업제어시스템 마스터키 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/industryControlMasterKey/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> industryControlMasterKeyList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.industryControlMasterKeyList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 산업제어시스템 마스터키 관리 상태값 변경 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 15.
     */
    @Operation(summary = "산업제어시스템 마스터키 관리 상태값 변경", description = "산업제어시스템 마스터키 관리 상태값을 변경 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/industryControlMasterKey/change")
    public @ResponseBody ResponseModel<Boolean> industryControlMasterKeyStatusChange(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.industryControlMasterKeyStatusChange(paramMap));
    }

    /**
     * 데이타센터 USB포트락 관리 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 18.
     */
    @Operation(summary = "데이타센터 USB포트락 관리 목록 조회", description = "데이타센터 USB포트락 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/dataCenterUSBPortLock/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> dataCenterUSBPortLockList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = service.dataCenterUSBPortLockList(paramMap);

        int totalCnt = service.dataCenterUSBPortLockListCnt(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCnt);
    }

    /**
     * 데이타센터 USB포트락 관리 Row 저장 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 18.
     */
    @Operation(summary = "데이타센터 USB포트락 관리 Row 저장", description = "데이타센터 USB포트락 관리 Row를 저장 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/dataCenterUSBPortLock/save")
    public @ResponseBody ResponseModel<Boolean> dataCenterUSBPortLockSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.dataCenterUSBPortLockSave(paramMap));
    }

    /**
     * 데이타센터 USB포트락 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 18.
     */
    @Operation(summary = "데이타센터 USB포트락 엑셀 다운로드", description = "데이타센터 USB포트락 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/dataCenterUSBPortLock/download")
    public String dataCenterUSBPortLockListDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.dataCenterUSBPortLockListDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 산업제어시스템 USB포트락 관리 목록조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    @Operation(summary = "산업제어시스템 USB포트락 관리 목록 조회", description = "산업제어시스템 USB포트락 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/industryControlUSBPortLock/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> industryControlUSBPortLockList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.industryControlUSBPortLockList(paramMap);
        int totalCnt = service.industryControlUSBPortLockListCnt(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCnt);
    }

    /**
     * 산업제어시스템 USB포트락 관리 Row 저장 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    @Operation(summary = "산업제어시스템 USB포트락 관리 Row 저장", description = "산업제어시스템 USB포트락 관리 Row를 저장 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/industryControlUSBPortLock/save")
    public @ResponseBody ResponseModel<Boolean> industryControlUSBPortLockSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody List<HashMap<String, Object>> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.industryControlUSBPortLockSave(paramMap));
    }

    /**
     * 산업제어시스템 USB포트락 관리 신규 Row 저장 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    @Operation(summary = "산업제어시스템 USB포트락 신규 Row 저장", description = "산업제어시스템 USB포트락 신규 Row를 저장 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/industryControlUSBPortLock/insert")
    public @ResponseBody ResponseModel<Boolean> industryControlUSBPortLockInsert(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.industryControlUSBPortLockInsert(paramMap));
    }

    /**
     * 산업제어시스템 USB포트락 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    @Operation(summary = "산업제어시스템 USB포트락 엑셀 다운로드", description = "산업제어시스템 USB포트락 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/industryControlUSBPortLock/download")
    public String industryControlUSBPortLockListDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.industryControlUSBPortLockListDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 생산장비 보안요원 안전재고 목록조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "생산장비 보안요원 안전재고 목록 조회", description = "생산장비 보안요원 안전재고 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/productionSafetyStock/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> productionSafetyStockList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.productionSafetyStockList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 생산장비 보안요원 안전재고엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "생산장비 보안요원 안전재고 엑셀 다운로드", description = "생산장비 보안요원 안전재고 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/productionSafetyStock/download")
    public String productionSafetyStockListDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.productionSafetyStockListDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 생산장비 보안요원 안전재고 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "생산장비 보안요원 안전재고 삭제", description = "생산장비 보안요원 안전재고를 삭제한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionSafetyStock/delete")
    public @ResponseBody ResponseModel<Boolean> productionSafetyStockDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionSafetyStockDelete(paramMap));
    }

    /**
     * 생산장비 USB포트락 점검결과 리셋
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "I0101124"
     * @since : 2022. 2. 22.
     */
    @Operation(summary = "생산장비 USB포트락 점검결과 리셋", description = "생산장비 USB포트락 점검결과 리셋")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionUSBPortLock/reset")
    public @ResponseBody ResponseModel<Boolean> productionUSBPortLockReset(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionUSBPortLockReset(paramMap));
    }

    /**
     * 생산장비 USB포트락 비대상 일괄처리
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "I0101124"
     * @since : 2022. 2. 22.
     */
    @Operation(summary = "생산장비 USB포트락 비대상 일괄처리", description = "생산장비 USB포트락 비대상 일괄처리")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionUSBPortLock/resetTarget")
    public @ResponseBody ResponseModel<Boolean> productionUSBPortLockResetTarget(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionUSBPortLockResetTarget(paramMap));
    }

    /**
     * 생산장비 보안요원 안전재고 등록/수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "생산장비 보안요원 안전재고 등록/수정", description = "생산장비 보안요원 안전재고를 등록/수정 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionSafetyStock/save")
    public @ResponseBody ResponseModel<Boolean> productionSafetyStockSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionSafetyStockSave(paramMap));
    }

    /**
     * 생산장비 보안요원 안전재고 목록조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    @Operation(summary = "생산장비 보안요원 안전재고 상세 조회", description = "생산장비 보안요원 안전재고를 상세 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/productionSafetyStock/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> productionSafetyStockDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        Map<String, Object> result = null;

        result = service.productionSafetyStockDetail(paramMap);

        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 생산장비 USB포트락 관리 목록조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "생산장비 USB포트락 관리 목록 조회", description = "생산장비 USB포트락 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/productionUSBPortLock/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> productionUSBPortLockList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.productionUSBPortLockList(paramMap);
        int totalCnt = service.productionUSBPortLockListCnt(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCnt);
    }

    /**
     * 생산장비 USB포트락 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "생산장비 USB포트락 엑셀 다운로드", description = "생산장비 USB포트락 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/productionUSBPortLock/download")
    public String productionUSBPortLockDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.productionUSBPortLockDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 생산장비 USB포트락 장착결과 저장 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "생산장비 USB포트락 장착결과 저장", description = "생산장비 USB포트락 장착결과를 저장 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionUSBPortLock/installResultSave")
    public @ResponseBody ResponseModel<Boolean> productionUSBPortLockInstallResultSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody List<HashMap<String, Object>> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionUSBPortLockInstallResultSave(paramMap));
    }

    /**
     * 생산장비 USB포트락 점검결과 저장 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    @Operation(summary = "생산장비 USB포트락 점검결과 저장", description = "생산장비 USB포트락 점검결과를 저장 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionUSBPortLock/checkResultSave")
    public @ResponseBody ResponseModel<Boolean> productionUSBPortLockCheckResultSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody List<HashMap<String, Object>> paramMap) throws EsecurityException {
        

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionUSBPortLockCheckResultSave(paramMap));
    }

    /**
     * 데이타센터 마스터키 관리 목록조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "데이타센터 마스터키 관리 목록 조회", description = "데이타센터 마스터키 관리 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/dataCenterMasterKey/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> dataCenterMasterKeyList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.dataCenterMasterKeyList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 데이타센터 마스터키 관리 상태값 변경 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 02.
     */
    @Operation(summary = "데이타센터 마스터키 관리 상태값 변경", description = "데이타센터 마스터키 관리 상태값을 변경 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/dataCenterMasterKey/change")
    public @ResponseBody ResponseModel<Boolean> dataCenterMasterKeyStatusChange(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.dataCenterMasterKeyStatusChange(paramMap));
    }

    /**
     * 생산장비 USB포트락 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 13.
     */
    @Operation(summary = "생산장비 USB포트락 삭제", description = "생산장비 USB포트락을 삭제 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionUSBPortLock/delete")
    public @ResponseBody ResponseModel<Boolean> productionUSBPortLockDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionUSBPortLockDelete(paramMap));
    }

    /**
     * 생산장비 마스터키 관리 목록 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 14.
     */
    @Operation(summary = "생산장비 마스터키 관리 엑셀 다운로드", description = "생산장비 마스터키 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/productionMasterKey/download")
    public String productionMasterKeyListDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.productionMasterKeyListDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 생산장비 마스터키 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "생산장비 마스터키 삭제", description = "생산장비 마스터키를 삭제 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionMasterKey/delete")
    public @ResponseBody ResponseModel<Boolean> productionMasterKeyDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionMasterKeyDelete(paramMap));
    }

    /**
     * 마스터키 관리부서 선택 후 보안담당자 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "마스터키 관리 보안담당자 조회", description = "관리부서 선택 후 마스터키 관리 보안담당자을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/productionMasterKey/secrtMng")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecrtEmpMng(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectSecrtEmpMng(paramMap));
    }

    /**
     * 마스터키 상세정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "마스터키 상세정보 조회", description = "마스터키 상세정보을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/productionMasterKey/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> productionMasterKeyDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.productionMasterKeyDetail(paramMap));
    }

    /**
     * 생산장비 마스터키 등록/수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    @Operation(summary = "생산장비 마스터키 등록/수정", description = "생산장비 마스터키를 등록/수정 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/productionMasterKey/save")
    public @ResponseBody ResponseModel<Boolean> productionMasterKeySave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.productionMasterKeySave(paramMap));
    }

    /**
     * 데이터센터 USB포트락 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 16.
     */
    @Operation(summary = "데이터센터 USB포트락 삭제", description = "데이터센터 USB포트락를 삭제 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/dataCenterUSBPortLock/delete")
    public @ResponseBody ResponseModel<Boolean> dataCenterUSBPortLockDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.dataCenterUSBPortLockDelete(paramMap));
    }

    /**
     * 산업제어시스템 USB포트락 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 21.
     */
    @Operation(summary = "산업제어시스템 USB포트락 삭제", description = "산업제어시스템 USB포트락를 삭제 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/industryControlUSBPortLock/delete")
    public @ResponseBody ResponseModel<Boolean> industryControlUSBPortLockDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.industryControlUSBPortLockDelete(paramMap));
    }

    /**
     * 데이터센터 마스터키 등록/수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 22.
     */
    @Operation(summary = "데이터센터 마스터키 등록/수정", description = "데이터센터 마스터키를 등록/수정 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/dataCenterMasterKey/save")
    public @ResponseBody ResponseModel<Boolean> dataCenterMasterKeySave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.dataCenterMasterKeySave(paramMap));
    }

    /**
     * 데이터센터 마스터키 관리 목록 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 22.
     */
    @Operation(summary = "데이터센터 마스터키 관리 엑셀 다운로드", description = "데이터센터 마스터키 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/dataCenterMasterKey/download")
    public String dataCenterMasterKeyExcel(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.dataCenterMasterKeyExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 데이터센터 마스터키 삭제
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 23.
     */
    @Operation(summary = "데이터센터 마스터키 삭제", description = "데이터센터 마스터키를 삭제 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/dataCenterMasterKey/delete")
    public @ResponseBody ResponseModel<Boolean> dataCenterMasterKeyDelete(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.dataCenterMasterKeyDelete(paramMap));
    }

    /**
     * 데이터센터 상세정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 23.
     */
    @Operation(summary = "데이터센터 마스터키관리 상세정보 조회", description = "데이터센터 마스터키관리 상세정보을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/dataCenterMasterKey/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> dataCenterMasterKeyDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.dataCenterMasterKeyDetail(paramMap));
    }

    /**
     * 산업제어 시스템 마스터키 목록 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 24.
     */
    @Operation(summary = "산업제어 시스템 마스터키 관리 엑셀 다운로드", description = "산업제어 시스템 마스터키 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/industryControlMasterKey/download")
    public String industryControlMasterKeyListDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.industryControlMasterKeyListDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 산업제어 시스템 마스터키관리 상세정보 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 24.
     */
    @Operation(summary = "산업제어 시스템 마스터키관리 상세정보 조회", description = "산업제어 시스템 마스터키관리 상세정보을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/industryControlMasterKey/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> industryControlMasterKeyDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.industryControlMasterKeyDetail(paramMap));
    }

    /**
     * 산업제어 시스템 마스터키 등록/수정
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 27.
     */
    @Operation(summary = "산업제어 시스템 마스터키 등록/수정", description = "산업제어 시스템 마스터키를 등록/수정 한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/industryControlMasterKey/save")
    public @ResponseBody ResponseModel<Boolean> industryControlMasterKeySave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.industryControlMasterKeySave(paramMap));
    }

}

