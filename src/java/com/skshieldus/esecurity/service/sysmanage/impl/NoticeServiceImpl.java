package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.component.FileUpload;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.sysmanage.BbsDTO;
import com.skshieldus.esecurity.repository.sysmanage.NoticeRepository;
import com.skshieldus.esecurity.service.sysmanage.NoticeService;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    private final FileUpload fileUpload;

    @Override public List<Map<String, Object>> selectNoticeList(Map<String, Object> paramMap) {
        return noticeRepository.selectNoticeList(paramMap);
    }

    @Override public Integer selectNoticeListCnt(Map<String, Object> paramMap) {
        return noticeRepository.selectNoticeListCnt(paramMap);
    }

    @Override public BbsDTO selectNoticeDetail(String boardNo) {
        BbsDTO result;

        try {
            result = noticeRepository.selectNoticeDetail(boardNo);
            //  as-is에 있던 소스, 주석 해제 시 가운데 정렬 등 ckeditor 기능이 제한 됨 by 230725_이정엽
            //            String content = result.getContent();
            //            if (!StringUtils.isEmpty(content)) {
            //                content = MimeUtil.replace(content, "&", "&amp;");
            //                content = MimeUtil.replace(content, "\"", "&#34;");
            //                result.setContent(content);
            //            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override public Integer insertNotice(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException {
        Map<String, Object> fileResult = fileUpload.uploadFile(httpServletRequest.getFile("fileToUpload"), "bbs");
        paramMap.put("files", fileResult.get("fullPath"));
        return noticeRepository.insertNotice(paramMap);
    }

    @Override public Integer modifyNotice(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException {
        Map<String, Object> fileResult = fileUpload.uploadFile(httpServletRequest.getFile("fileToUpload"), "bbs");
        paramMap.put("files", fileResult.get("fullPath"));
        return noticeRepository.modifyNotice(paramMap);
    }

    @Override public Integer deleteNotice(Map<String, Object> paramMap) {
        return noticeRepository.deleteNotice(paramMap);
    }

    @Override public Integer insertNoticeReply(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException {
        Map<String, Object> fileResult = fileUpload.uploadFile(httpServletRequest.getFile("fileToUpload"), "bbs");
        paramMap.put("files", fileResult.get("fullPath"));
        return noticeRepository.insertNoticeReply(paramMap);
    }

    @Override public Integer modifyNoticeReadUp(Map<String, Object> paramMap) {
        return noticeRepository.modifyNoticeReadUp(paramMap);
    }

    @Override public Integer updateNoticeFile(Map<String, Object> paramMap) {
        return noticeRepository.updateNoticeFile(paramMap);
    }

}
