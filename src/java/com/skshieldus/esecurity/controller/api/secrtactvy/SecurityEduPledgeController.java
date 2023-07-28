package com.skshieldus.esecurity.controller.api.secrtactvy;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.secrtactvy.SecurityEduPledgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "보안교육 및 서약 API")
@Controller
@RequestMapping(value = "/api/secrtactvy/securityEduPledge", produces = { "application/json" })
public class SecurityEduPledgeController {

    @Autowired
    private Environment environment;

    @Autowired
    private SecurityEduPledgeService service;

    /**
     * 보직이동서약 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    @Operation(summary = "보직이동서약 목록 조회", description = "보직이동서약 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/positionMovePledge")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectPositionMovePledgeList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectPositionMovePledgeList(paramMap));
    }

    /**
     * 보안교육 관리(관리자) 목록 조회
     *
     * @param paramMap
     * @return
     */
    @Operation(summary = "보안교육 관리(관리자) 목록 조회", description = "보안교육 관리(관리자) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/admList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityEducationInfoAdmList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        ListDTO<Map<String, Object>> listDTO = service.selectSecurityEducationInfoAdmList(paramMap);
        return new ResponseModel<>(HttpStatus.OK, listDTO.getList(), listDTO.getTotalCount());
    }

    /**
     * 보직이동서약 상세 조회
     *
     * @param sessionInfoVO
     * @param coPositionPledgeNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    @Operation(summary = "보직이동서약 상세 조회", description = "보직이동서약 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/positionMovePledge/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectPositionMovePledge(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer coPositionPledgeNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectPositionMovePledge(coPositionPledgeNo));
    }

    /**
     * 보직이동서약 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    @Operation(summary = "보직이동서약 등록", description = "보직이동서약 정보를 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/positionMovePledge")
    public @ResponseBody ResponseModel<Boolean> insertPositionMovePledge(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertPositionMovePledge(paramMap));
    }

    /**
     * 보직이동서약 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 8.
     */
    @Operation(summary = "보직이동서약 엑셀 다운로드", description = "보직이동서약 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/positionMovePledge/download")
    public String selectPositionMovePledgeViewDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.selectPositionMovePledgeViewDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 국가핵심기술보호서약(구성원) 목록 조회
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    @Operation(summary = "국가핵심기술보호서약(구성원) 목록 조회", description = "국가핵심기술보호서약(구성원) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/protectionPledge")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectProtectionPledgeList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        

        String isAdmin = paramMap.get("isAdmin") != null
            ? paramMap.get("isAdmin").toString()
            : null;
        String type = paramMap.get("type") != null
            ? paramMap.get("type").toString()
            : null;
        List<Map<String, Object>> resultList = null;

        if ("I".equals(type)) {
            if ("Y".equals(isAdmin)) {
                // 관리자-외부인 목록 조회
                resultList = service.selectIoProtectionPledgeList(paramMap);
            }
            else {
                // 사용자-외부인 목록 조회 (화면에서 조회 하지 않고 있음으로 주석 처리 by kwg)
                // resultList = service.selectIoProtectionPledgeListByUser(paramMap);
            }
        }
        else {
            // 구성원 목록 조회
            resultList = service.selectProtectionPledgeList(paramMap);
        }

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 국가핵심기술보호서약(구성원) 상세 조회
     *
     * @param sessionInfoVO
     * @param coPositionPledgeNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    @Operation(summary = "국가핵심기술보호서약(구성원) 상세 조회", description = "국가핵심기술보호서약(구성원) 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/protectionPledge/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectProtectionPledge(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer coPositionPledgeNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectProtectionPledge(coPositionPledgeNo));
    }

    /**
     * 국가핵심기술보호서약(외부인) 상세 조회
     *
     * @param sessionInfoVO
     * @param coPositionPledgeNo
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    @Operation(summary = "국가핵심기술보호서약(외부인) 상세 조회", description = "국가핵심기술보호서약(외부인) 상세 정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/protectionPledge/io/{no}")
    public @ResponseBody ResponseModel<Map<String, Object>> selectIoProtectionPledge(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @PathVariable("no") Integer coPositionPledgeNo) throws EsecurityException {
        

        return new ResponseModel<>(HttpStatus.OK, service.selectIoProtectionPledge(coPositionPledgeNo));
    }

    /**
     * 국가핵심기술보호서약 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 13.
     */
    @Operation(summary = "국가핵심기술보호서약(구성원) 등록", description = "국가핵심기술보호서약(구성원)정보를 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully insert data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/protectionPledge")
    public @ResponseBody ResponseModel<Boolean> insertProtectionPledge(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        

        // AC IP 설정
        String remoteIp = "";
        if (StringUtils.isNotEmpty(sessionInfoVO.getIp()))
            remoteIp = sessionInfoVO.getIp();
        paramMap.put("acIp", remoteIp);

        return new ResponseModel<>(HttpStatus.OK, service.insertProtectionPledge(paramMap));
    }

    /**
     * 국가핵심기술보호서약 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 8.
     */
    @Operation(summary = "국가핵심기술보호서약 엑셀 다운로드", description = "국가핵심기술보호서약 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/protectionPledge/download")
    public String selectIoProtectionPledgeViewDowload(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap, Model model) {
        

        String type = paramMap.get("type") != null
            ? paramMap.get("type").toString()
            : null;
        CommonXlsViewDTO commonXlsViewDTO = null;

        if ("I".equals(type)) {
            // 관리자-외부인 목록 조회
            commonXlsViewDTO = service.selectIoProtectionPledgeViewDownload(paramMap);
        }
        else {
            // commonXlsViewDTO 목록 조회
            commonXlsViewDTO = service.selectProtectionPledgeViewDownload(paramMap);
        }

        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 보안교육 신청 현황 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 03.
     */
    @Operation(summary = "보안교육 신청 현황 목록 조회", description = "보안교육 신청 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/eduReq/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSecurityEducationRequestList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.selectSecurityEducationRequestList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 보안교육 신청 현황 상세 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 04.
     */
    @Operation(summary = "보안교육 신청 현황 상세 조회", description = "보안교육 신청 현황 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/eduReq/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSecurityEducationRequestView(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectSecurityEducationRequestView(paramMap));
    }

    /**
     * 보안교육 신청 현황 상세 > 교육 대상 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 04.
     */
    @Operation(summary = "보안교육 신청 현황 상세 > 교육 대상 목록 조회", description = "보안교육 신청 현황 상세 > 교육 대상 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/eduReq/targetList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectEducationTargetList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.selectEducationTargetList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 보안교육 예약현황 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 04.
     */
    @Operation(summary = "보안교육 예약현황 조회", description = "보안교육 예약현황을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/eduReq/reservationStatus")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectEducationReservationStatus(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.selectEducationReservationStatus(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 특수업무수행서약 현황(구성원) 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param type [ C=구성원, I=외부인 ]
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 05.
     */
    @Operation(summary = "특수업무수행서약 현황(구성원/외부인) 목록 조회", description = "특수업무수행서약 현황(구성원/외부인) 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/specialTask/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSpecialTaskList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.selectSpecialTaskList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 특수업무수행서약 현황(구성원) 상세조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param type [ C=구성원, I=외부인 ]
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 08.
     */
    @Operation(summary = "특수업무수행서약 현황(구성원/외부인) 상세조회", description = "특수업무수행서약 현황(구성원/외부인) 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/specialTask/detail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectSpecialTaskDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectSpecialTaskDetail(paramMap));
    }

    /**
     * 자료 제공 요청 및 파기 확인 현황 목록 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 09.
     */
    @Operation(summary = "자료 제공 요청 및 파기 확인 현황 목록 조회", description = "자료 제공 요청 및 파기 확인 현황 목록을 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/dataProvide/list")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectDataProvideList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.selectDataProvideList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList);
    }

    /**
     * 자료 제공 요청 및 파기 확인 현황 목록 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 09.
     */
    @Operation(summary = "자료 제공 요청 및 파기 확인 현황 목록 엑셀 다운로드", description = "자료 제공 요청 및 파기 확인 현황 목록을 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/dataProvide/download")
    public String dataProvideListDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.dataProvideListDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 자료 제공 요청 및 파기 확인 현황 > 자료 제공 요청서 상세조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 10.
     */
    @Operation(summary = "자료 제공 요청 및 파기 확인 현황 > 자료 제공 요청서 상세조회", description = "자료 제공 요청 및 파기 확인 현황 > 자료 제공 요청서 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/dataProvide/provideDetail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectProvideDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectProvideDetail(paramMap));
    }

    /**
     * 자료 제공 요청 및 파기 확인 현황 > 자료 파기 확인서 상세조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 10.
     */
    @Operation(summary = "자료 제공 요청 및 파기 확인 현황 > 자료 파기 확인서 상세조회", description = "자료 제공 요청 및 파기 확인 현황 > 자료 파기 확인서 상세정보를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/dataProvide/destroyDetail")
    public @ResponseBody ResponseModel<Map<String, Object>> selectDestroyDetail(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        return new ResponseModel<>(HttpStatus.OK, service.selectDestroyDetail(paramMap));
    }

    /**
     * 협력업체 목록 검색
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "협력업체 목록 검색", description = "협력업체 목록을 검색한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/eduReq/ioCompList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> searchIoCompList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.searchIoCompList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, service.searchIoCompListCnt(paramMap));
    }

    /**
     * 본사조직 목록 검색
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    @Operation(summary = "본사조직 부서검색", description = "본사조직 부서를 검색한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/eduReq/deptList")
    public @ResponseBody ResponseModel<List<Map<String, Object>>> searchDeptList(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        
        List<Map<String, Object>> resultList = null;

        resultList = service.searchDeptList(paramMap);

        return new ResponseModel<>(HttpStatus.OK, resultList, service.searchDeptListCnt(paramMap));
    }

    /**
     * 보안교육 신청(상신)
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 12.
     */
    @Operation(summary = "보안교육 신청(상신)", description = "보안교육 신청(상신)한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/eduReq/save")
    public @ResponseBody ResponseModel<Boolean> insertSecurityEdu(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.insertSecurityEdu(paramMap));
    }

    /**
     * 보안교육 신청 통합결재 후처리
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 12.
     * @param sessionInfoVO
     * @param requestDTO
     * @return
     * @throws Exception
     */
    //	@Operation(summary = "보안교육 신청 통합결재 후처리", description = "보안교육 신청 통합결재 후처리를 수행한다.")
    //	@ApiResponses(value = {
    //			@ApiResponse(responseCode = "200", description="Successfully search data contents"),
    //			@ApiResponse(responseCode = "404", description="Not found")
    //	})
    //	@PostMapping(value = "/approval/postprocess")
    //	public @ResponseBody ResponseModel<EaiResponseDTO> processSecurityEduPledgeApproval(
    //			@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody EaiRequestDTO requestDTO)
    //			throws Exception {
    //		return new ResponseModel<>(HttpStatus.OK, securityEduPledgeApprovalPostProcess.postProcess(requestDTO));
    //	}

    /**
     * 특수업무수행서약 등록
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 15.
     */
    @Operation(summary = "특수업무수행서약 등록", description = "특수업무수행서약을 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully save data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping(value = "/specialTask/save")
    public @ResponseBody ResponseModel<Boolean> specialTaskPledgeSave(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestBody HashMap<String, Object> paramMap) throws EsecurityException {
        
        paramMap.put("acIp", sessionInfoVO.getIp());

        return new ResponseModel<Boolean>(HttpStatus.OK, service.specialTaskPledgeSave(paramMap));
    }

    /**
     * 관리자 > 보안생활화 > 보안교육 신청현황 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 06.
     */
    @Operation(summary = "보안교육 신청현황 엑셀다운", description = "보안교육 신청현황 엑셀다운")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"),
        @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/eduReq/download")
    public String securityEducationRequestListDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.securityEducationRequestListDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

    /**
     * 특수업무수행서약 현황 목록 엑셀 다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @param model
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 29.
     */
    @Operation(summary = "특수업무수행서약 현황 목록 엑셀 다운로드", description = "특수업무수행서약 현황 정보를 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/specialTask/download")
    public String specialTaskPledgeListDownload(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO
        , @RequestParam HashMap<String, Object> paramMap, Model model
    ) {
        

        CommonXlsViewDTO commonXlsViewDTO = service.specialTaskPledgeListDownload(paramMap);
        model.addAttribute("data", commonXlsViewDTO);
        return "xlsView";
    }

}

