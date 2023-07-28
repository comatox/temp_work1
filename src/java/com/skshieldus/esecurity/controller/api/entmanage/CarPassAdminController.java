package com.skshieldus.esecurity.controller.api.entmanage;

import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.service.entmanage.CarPassAdminService;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "차량출입 관리 API")
@Controller
@RequestMapping(value = "/api/entmanage/carPassAdmin", produces = { "application/json" })
public class CarPassAdminController {

    @Autowired
    private Environment environment;

    @Autowired
    private CarPassAdminService carPassAdminService;

    /**
     * 방문차량 쿼터현황 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    @Operation(summary = "방문차량 쿼터현황 리스트 조회", description = "방문차량 쿼터현황 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioCarPassInOutStts/list", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoCarPassInOutSttsList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        Map<String, Object> resultMap = carPassAdminService.selectIoCarPassInOutSttsList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 방문차량 쿼터관리 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    @Operation(summary = "방문차량 쿼터관리 리스트 조회", description = "방문차량 쿼터관리 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioIcCarQuota/list", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoIcCarQuotaList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        Map<String, Object> resultMap = carPassAdminService.selectIoIcCarQuotaList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 방문차량 부서 쿼터관리 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    @Operation(summary = "방문차량 부서 쿼터관리 조회", description = "방문차량 부서 쿼터관리를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/ioIcCarQuota/view/{deptDivId}", produces = { "application/json" })
    public @ResponseBody ResponseModel<Map<String, Object>> selectIoCarQuota(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap,
        @PathVariable String deptDivId
    ) {
        paramMap.put("deptDivId", deptDivId);
        return new ResponseModel<>(HttpStatus.OK, carPassAdminService.selectIoCarQuota(paramMap));
    }

    /**
     * 방문차량 부서 쿼터관리 업데이트 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    @Operation(summary = "방문차량 부서 쿼터관리 업데이트", description = "방문차량 부서 쿼터관리를 업데이트한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioIcCarQuota/update", produces = { "application/json" })
    public @ResponseBody ResponseModel<Boolean> updateIoCarQuota(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        return new ResponseModel<>(HttpStatus.OK, carPassAdminService.updateIoCarQuota(sessionInfoVO, paramMap));
    }

    /**
     * 방문차량 부서 쿼터관리 삭제컬럼 업데이트 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    @Operation(summary = "방문차량 부서 쿼터관리 삭제컬럼 업데이트", description = "방문차량 부서 쿼터관리 삭제컬럼을 업데이트한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioIcCarQuota/delete", produces = { "application/json" })
    public @ResponseBody ResponseModel<Boolean> updateIoCarQuotaDelYn(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        return new ResponseModel<>(HttpStatus.OK, carPassAdminService.updateIoCarQuotaDelYn(sessionInfoVO, paramMap));
    }

    /**
     * 차량출입관리 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 08.
     */
    @Operation(summary = "차량출입관리 리스트 조회", description = "차량출입관리 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/ioCarPassProgress/list", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectIoCarPassProgressList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        Map<String, Object> resultMap = carPassAdminService.selectIoCarPassProgressList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 차량출입관리 엑셀 다운로드 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 08.
     */
    @Operation(summary = "차량출입관리 엑셀 다운로드", description = "차량출입관리 엑셀파일을 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @GetMapping(value = "/ioCarPassProgress/excel", produces = { "application/json" })
    public String selectIoCarPassProgressListExcel(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap, Model model
    ) {

        CommonXlsViewDTO commonXlsViewDTO = carPassAdminService.selectIoCarPassProgressListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

    /**
     * 차량출입관리 방문객 입/출문 업데이트 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 01.
     */
    @Operation(summary = "차량출입관리 방문객 입/출문 업데이트", description = "차량출입관리 방문객 입/출문을 업데이트한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/updateInOut", produces = { "application/json" })
    public @ResponseBody ResponseModel<Boolean> updateInOut(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        return new ResponseModel<>(HttpStatus.OK, carPassAdminService.updateInOut(sessionInfoVO, paramMap));
    }

    /**
     * 차량출입신청 관리자 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    @Operation(summary = "차량출입신청 관리자 리스트 조회", description = "차량출입신청 관리자 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/carPassAdminList/list", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectAllCarPassList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {
        Map<String, Object> resultMap = carPassAdminService.selectAllCarPassList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 차량출입신청 관리자 엑셀다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    @Operation(summary = "차량출입신청 관리자 현황 엑셀다운로드", description = "차량출입신청 관리자 현황을 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/carPassAdminList/excel")
    public String selectCarPassAdminListExcel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap, Model model) {

        CommonXlsViewDTO commonXlsViewDTO = carPassAdminService.selectCarPassAdminListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

    /**
     * 안전작업차량 출입관리 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    @Operation(summary = "안전작업차량 출입관리 리스트 조회", description = "안전작업차량 출입관리 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/safetyCarPassProgress/list", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectSafetyCarPassProgressList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        Map<String, Object> resultMap = carPassAdminService.selectSafetyCarPassProgressList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 안전작업차량 출입관리 관리자 엑셀다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27.
     */
    @Operation(summary = "안전작업차량 출입관리 현황 엑셀다운로드", description = "안전작업차량 출입관리 현황을 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/safetyCarPassProgress/excel")
    public String selectSafetyCarPassProgressListExcel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap, Model model) {

        CommonXlsViewDTO commonXlsViewDTO = carPassAdminService.selectSafetyCarPassProgressListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }

    /**
     * 임시차량 출입관리 리스트 조회 API
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27
     */
    @Operation(summary = "임시차량 출입관리 리스트 조회", description = "임시차량 출입관리 리스트를 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found") })
    @PostMapping(value = "/tmpcarPassProgress/list", produces = { "application/json" })
    public @ResponseBody ResponseModel<List<Map<String, Object>>> selectTmpcarPassProgressList(
        @Parameter(hidden = true) SessionInfoVO sessionInfoVO,
        @RequestBody Map<String, Object> paramMap
    ) {

        Map<String, Object> resultMap = carPassAdminService.selectTmpcarPassProgressList(paramMap);
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) resultMap.get("list");
        int totalCount = (int) resultMap.get("totalCount");

        return new ResponseModel<>(HttpStatus.OK, resultList, totalCount);
    }

    /**
     * 임시차량 출입관리 관리자 엑셀다운로드
     *
     * @param sessionInfoVO
     * @param paramMap
     * @return
     *
     * @author : X0125104<won.shin@partner.sk.com>
     * @since : 2023. 07. 27
     */
    @Operation(summary = "임시차량 출입관리 현황 엑셀다운로드", description = "임시차량 출입관리 현황을 엑셀로 다운로드한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully searched data contents"),
        @ApiResponse(responseCode = "404", description = "Not found")
    })
    @GetMapping(value = "/tmpcarPassProgress/excel")
    public String selectTmpcarPassProgressListExcel(@Parameter(hidden = true) SessionInfoVO sessionInfoVO, @RequestParam Map<String, Object> paramMap, Model model) {

        CommonXlsViewDTO commonXlsViewDTO = carPassAdminService.selectTmpcarPassProgressListExcel(paramMap);
        model.addAttribute("data", commonXlsViewDTO);

        return "xlsView";
    }
}
