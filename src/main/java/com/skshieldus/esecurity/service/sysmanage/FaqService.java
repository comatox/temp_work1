package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.BbsDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FaqService {

    /**
     * 산업보안 FAQ 리스트
     *
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectFaqList(Map<String, Object> paramMap);

    /**
     * 산업보안 FAQ 리스트 개수
     *
     * @param paramMap
     * @return
     */
    Integer selectFaqListCnt(Map<String, Object> paramMap);

    /**
     * 산업보안 FAQ 상세정보 조회
     *
     * @param boardNo
     * @return
     */
    BbsDTO selectFaqDetail(String boardNo);

    /**
     * 산업보안 FAQ 등록
     *
     * @param paramMap
     * @return
     */
    Integer insertFaq(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException;

    /**
     * 산업보안 FAQ 수정
     *
     * @param paramMap
     * @return
     */
    Integer modifyFaq(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException;

    /**
     * 산업보안 FAQ 삭제
     *
     * @param paramMap
     * @return
     */
    Integer deleteFaq(Map<String, Object> paramMap);

    /**
     * 산업보안 FAQ 답글
     *
     * @param paramMap
     * @return
     */
    Integer insertFaqReply(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException;

    /**
     * 산업보안 FAQ 조회수 증가
     *
     * @param paramMap
     * @return
     */
    Integer modifyFaqReadUp(Map<String, Object> paramMap);

    /**
     * 파일 업데이트
     *
     * @param paramMap
     * @return
     */
    Integer updateFaqFile(Map<String, Object> paramMap);

}
