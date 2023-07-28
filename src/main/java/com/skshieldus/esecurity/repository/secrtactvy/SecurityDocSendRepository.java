package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityDocSendRepository {

    // 외부공개 자료 신청현황 목록 조회
    List<Map<String, Object>> selectSecurityDocSendList(Map<String, Object> paramMap);

    // 외부공개 자료 신청현황 목록건수 조회
    Integer selectSecurityDocSendListCnt(Map<String, Object> paramMap);

    // 외부공개 자료 신청현황 상세 조회
    Map<String, Object> selectSecurityDocSend(Map<String, Object> paramMap);

    // 외부공개자료 신청 (상신)시 전송할 메일주소 조회
    List<Map<String, Object>> selectDocSendMailList(HashMap<String, Object> paramMap);

    // 외부공개자료 사전승인(상신)
    int insertDocSend(HashMap<String, Object> paramMap);

    //외부공개자료 사전승인 논문정보 조회
    Map<String, Object> selectDocSendApplView(Map<String, String> param);

    // 자격증ID 신규채번
    int selectDocSendSeq();

    // 외부공개 자료 신청현황 목록 엑셀다운로드
    List<Map<String, Object>> selectSecurityDocSendListExcel(HashMap<String, Object> paramMap);

}
