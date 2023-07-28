package com.skshieldus.esecurity.controller.api.sysmanage;

import com.skshieldus.esecurity.common.component.FileUpload;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.common.model.ResponseModel;
import com.skshieldus.esecurity.model.sysmanage.BbsDTO;
import com.skshieldus.esecurity.service.sysmanage.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Tag(name = "산업보안 공지사항 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sysmanage/notice", produces = { MediaType.APPLICATION_JSON_VALUE })
public class NoticeController {

    private final FileUpload fileUpload;

    private final NoticeService noticeService;

    /**
     * 산업보안 공지사항 리스트 조회
     *
     * @param paramMap
     * @return
     */
    @Operation(summary = "산업보안 공지사항 리스트 조회", description = "산업보안 공지사항 리스트 조회한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping(value = "/list")
    public ResponseModel<List<Map<String, Object>>> selectNoticeList(@RequestParam HashMap<String, Object> paramMap) throws EsecurityException {
        return new ResponseModel<>(HttpStatus.OK, noticeService.selectNoticeList(paramMap), noticeService.selectNoticeListCnt(paramMap));
    }

    /**
     * 산업보안 공지사항 등록
     *
     * @param httpServletRequest
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "산업보안 공지사항 등록", description = "산업보안 공지사항을 등록한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/request")
    public ResponseModel<Map<String, Object>> insertNotice(
        MultipartHttpServletRequest httpServletRequest,
        @RequestParam Map<String, Object> paramMap
    ) throws EsecurityException, IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("resultList", noticeService.insertNotice(httpServletRequest, paramMap));
        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 산업보안 공지사항 수정
     *
     * @param paramMap
     * @return
     *
     * @throws EsecurityException
     */
    @Operation(summary = "산업보안 공지사항 수정", description = "산업보안 공지사항을 수정한다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/modify")
    public ResponseModel<Map<String, Object>> modifyNotice(
        MultipartHttpServletRequest httpServletRequest,
        @RequestParam Map<String, Object> paramMap
    ) throws EsecurityException, IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("resultList", noticeService.modifyNotice(httpServletRequest, paramMap));
        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 산업보안 공지사항 상세보기
     *
     * @param model
     * @return
     */
    @Operation(summary = "산업보안 공지사항 상세", description = "산업보안 공지사항 상세보기")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @GetMapping("/detail/{boardNo}")
    public ResponseModel<BbsDTO> noticeDetail(Model model, @PathVariable(value = "boardNo") String boardNo) throws EsecurityException {
        BbsDTO result = noticeService.selectNoticeDetail(boardNo);
        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 산업보안 공지사항 삭제
     *
     * @param paramMap
     * @return
     */
    @Operation(summary = "산업보안 공지사항 삭제", description = "산업보안 공지사항 삭제하기")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping("/delete")
    public ResponseModel<Map<String, Object>> noticeDelete(@RequestBody Map<String, Object> paramMap) throws EsecurityException {
        Map<String, Object> result = new HashMap<>();
        result.put("result", noticeService.deleteNotice(paramMap));
        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 산업보안 공지사항 답글
     *
     * @param paramMap
     * @return
     */
    @Operation(summary = "산업보안 공지사항 답글", description = "산업보안 공지사항 답글달기")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping("/reply")
    public ResponseModel<Map<String, Object>> noticeReply(
        MultipartHttpServletRequest httpServletRequest,
        @RequestParam Map<String, Object> paramMap
    ) throws EsecurityException, IOException {
        Map<String, Object> result = new HashMap<>();
        result.put("result", noticeService.insertNoticeReply(httpServletRequest, paramMap));
        return new ResponseModel<>(HttpStatus.OK, result);
    }

    /**
     * 산업보안 파일 삭제 ajax
     *
     * @param paramMap
     * @return
     *
     * @throws IOException
     */
    @PostMapping(value = "/deleteAjax")
    public ResponseModel<Map<String, Object>> deleteFile(@RequestBody Map<String, Object> paramMap) throws IOException {
        Map<String, Object> resultMap;
        resultMap = fileUpload.deleteFile((String) paramMap.get("filePath"));

        Integer deleteFile = noticeService.updateNoticeFile(paramMap);
        resultMap.put("deleteFile", deleteFile);

        return new ResponseModel<>(HttpStatus.OK, resultMap);
    }

    /**
     * 산업보안 공지사항 조회수 증가
     *
     * @param paramMap
     * @return
     *
     * @throws IOException
     */
    @Operation(summary = "산업보안 공지사항 조회수 증가", description = "산업보안 공지사항 조회수 증가")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully search data"), @ApiResponse(responseCode = "404", description = "error") })
    @PostMapping(value = "/readUp")
    public ResponseModel<Integer> noticeReadUp(@RequestBody Map<String, Object> paramMap) throws EsecurityException {
        Integer result = noticeService.modifyNoticeReadUp(paramMap);
        return new ResponseModel<>(HttpStatus.OK, result);
    }

}
