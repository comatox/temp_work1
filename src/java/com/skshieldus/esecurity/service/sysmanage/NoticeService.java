package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.BbsDTO;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface NoticeService {

    /**
     * 산업보안 공지사항 리스트
     *
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectNoticeList(Map<String, Object> paramMap);

    /**
     * 산업보안 공지사항 리스트 개수
     *
     * @param paramMap
     * @return
     */
    Integer selectNoticeListCnt(Map<String, Object> paramMap);

    /**
     * 산업보안 공지사항 상세정보 조회
     *
     * @param boardNo
     * @return
     */
    BbsDTO selectNoticeDetail(String boardNo);

    /**
     * 산업보안 공지사항 등록
     *
     * @param paramMap
     * @return
     */
    Integer insertNotice(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException;

    /**
     * 산업보안 공지사항 수정
     *
     * @param paramMap
     * @return
     */
    Integer modifyNotice(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException;

    /**
     * 산업보안 공지사항 삭제
     *
     * @param paramMap
     * @return
     */
    Integer deleteNotice(Map<String, Object> paramMap);

    /**
     * 산업보안 공지사항 답글
     *
     * @param paramMap
     * @return
     */
    Integer insertNoticeReply(MultipartHttpServletRequest httpServletRequest, Map<String, Object> paramMap) throws IOException;

    /**
     * 산업보안 공지사항 조회수 증가
     *
     * @param paramMap
     * @return
     */
    Integer modifyNoticeReadUp(Map<String, Object> paramMap);

    /**
     * 파일 업데이트
     *
     * @param paramMap
     * @return
     */
    Integer updateNoticeFile(Map<String, Object> paramMap);

}
