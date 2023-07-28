package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.component.FileUpload;
import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.sysmanage.BbsDTO;
import com.skshieldus.esecurity.repository.sysmanage.FaqRepository;
import com.skshieldus.esecurity.service.sysmanage.FaqService;
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
public class FaqServiceImpl implements FaqService {

    private final FileUpload fileUpload;

    private final FaqRepository faqRepository;

    @Override public List<Map<String, Object>> selectFaqList(Map<String, Object> paramMap) {
        List<Map<String, Object>> result = faqRepository.selectFaqList(paramMap);
        System.out.println("result = " + result);
        return faqRepository.selectFaqList(paramMap);
    }

    @Override public Integer selectFaqListCnt(Map<String, Object> paramMap) {
        return faqRepository.selectFaqListCnt(paramMap);
    }

    @Override public BbsDTO selectFaqDetail(String boardNo) {
        BbsDTO result;

        try {
            result = faqRepository.selectFaqDetail(boardNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }
        return result;
    }

    @Override public Integer insertFaq(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException {
        Map<String, Object> fileResult = fileUpload.uploadFile(httpServletRequest.getFile("fileToUpload"), "bbs");
        paramMap.put("files", fileResult.get("fullPath"));
        return faqRepository.insertFaq(paramMap);
    }

    @Override public Integer modifyFaq(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException {
        Map<String, Object> fileResult = fileUpload.uploadFile(httpServletRequest.getFile("fileToUpload"), "bbs");
        paramMap.put("files", fileResult.get("fullPath"));
        return faqRepository.modifyFaq(paramMap);
    }

    @Override public Integer deleteFaq(Map<String, Object> paramMap) {
        return faqRepository.deleteFaq(paramMap);
    }

    @Override public Integer insertFaqReply(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException {
        Map<String, Object> fileResult = fileUpload.uploadFile(httpServletRequest.getFile("fileToUpload"), "bbs");
        paramMap.put("files", fileResult.get("fullPath"));
        return faqRepository.insertFaqReply(paramMap);
    }

    @Override public Integer modifyFaqReadUp(Map<String, Object> paramMap) {
        return faqRepository.modifyFaqReadUp(paramMap);
    }

    @Override public Integer updateFaqFile(Map<String, Object> paramMap) {
        return faqRepository.updateFaqFile(paramMap);
    }

}
