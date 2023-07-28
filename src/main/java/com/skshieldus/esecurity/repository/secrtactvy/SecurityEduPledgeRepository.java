package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityEduPledgeRepository {

    // 보직이동서약 목록 조회
    List<Map<String, Object>> selectPositionMovePledgeList(Map<String, Object> paramMap);

    // 보직이동서약 목록건수 조회
    Integer selectPositionMovePledgeListCnt(Map<String, Object> paramMap);

    // 보직이동서약 상세 조회
    Map<String, Object> selectPositionMovePledge(Integer coPositionPledgeNo);

    // 보직이동서약 등록
    int insertPositionMovePledge(Map<String, Object> paramMap);

    // 국가핵심기술보호서약-구성원 목록 조회
    List<Map<String, Object>> selectProtectionPledgeList(Map<String, Object> paramMap);

    // 국가핵심기술보호서약-구성원 목록건수 조회
    Integer selectProtectionPledgeListCnt(Map<String, Object> paramMap);

    // 국가핵심기술보호서약-구성원 상세 조회
    Map<String, Object> selectProtectionPledge(Integer coPositionPledgeNo);

    // 국가핵심기술보호서약-외부인 목록 조회
    List<Map<String, Object>> selectIoProtectionPledgeList(Map<String, Object> paramMap);

    // 국가핵심기술보호서약-외부인 목록건수 조회
    Integer selectIoProtectionPledgeListCnt(Map<String, Object> paramMap);

    // 국가핵심기술보호서약-외부인(사용자화면) 목록 조회
    List<Map<String, Object>> selectIoProtectionPledgeListByUser(Map<String, Object> paramMap);

    // 국가핵심기술보호서약-외부인(사용자화면) 목록건수 조회
    Integer selectIoProtectionPledgeListCntByUser(Map<String, Object> paramMap);

    // 국가핵심기술보호서약-구성원 상세 조회
    Map<String, Object> selectIoProtectionPledge(Integer specialPledgeNo);

    // 국가핵심기술보호서약-구성원 등록
    int insertProtectionPledge(Map<String, Object> paramMap);

    // 보안교육 신청 현황 목록 조회
    List<Map<String, Object>> selectSecurityEducationRequestList(HashMap<String, Object> paramMap);

    // 보안교육 신청 현황 상세 조회
    Map<String, Object> selectSecurityEducationRequestView(HashMap<String, Object> paramMap);

    // 보안교육 대상자 목록 조회
    List<Map<String, Object>> selectEducationTargetList(HashMap<String, Object> paramMap);

    // 보안교육 예약현황
    List<Map<String, Object>> selectEducationReservationStatus(HashMap<String, Object> paramMap);

    // 특수업무수행서약 현황(구성원) 목록 조회
    List<Map<String, Object>> selectSpecialTaskCoList(HashMap<String, Object> paramMap);

    // 특수업무수행서약 현황(외부인) 목록 조회
    List<Map<String, Object>> selectSpecialTaskIoList(HashMap<String, Object> paramMap);

    // 특수업무수행서약 현황(구성원) 상세 조회
    Map<String, Object> selectSpecialTaskCoDetail(HashMap<String, Object> paramMap);

    // 특수업무수행서약 현황(외부인) 상세 조회
    Map<String, Object> selectSpecialTaskIoDetail(HashMap<String, Object> paramMap);

    //자료 제공 요청 및 파기 확인 현황 목록 조회
    List<Map<String, Object>> selectDataProvideList(HashMap<String, Object> paramMap);

    //자료 제공 요청 및 파기 확인 현황 목록 엑셀다운로드 데이터 조회
    List<Map<String, Object>> dataProvideListExcel(HashMap<String, Object> paramMap);

    //자료 제공 요청서 상세 조회
    Map<String, Object> selectProvideDetail(HashMap<String, Object> paramMap);

    //자료 파기 확인서 상세 조회
    Map<String, Object> selectDestroyDetail(HashMap<String, Object> paramMap);

    // 협력업체 목록 조회
    List<Map<String, Object>> searchIoCompList(HashMap<String, Object> paramMap);

    // 협력업체 목록갯수조회
    int searchIoCompListCnt(HashMap<String, Object> paramMap);

    // 본사조직 부서 목록 조회
    List<Map<String, Object>> searchDeptList(HashMap<String, Object> paramMap);

    // 본사조직 부서 목록갯수 조회
    int searchDeptListCnt(HashMap<String, Object> paramMap);

    // 보안교육 신청 > 신규 채번
    String getSecurityEduCationSeq(HashMap<String, Object> paramMap);

    // 보안교육 신청 > 마스터 정보 등록
    int insertSecurityEduReq(HashMap<String, Object> paramMap);

    // 보안교육 신청 > 서브정보(교육대상) 등록
    void insertSecurityEduReqSub(HashMap<String, Object> paramMap);

    // 보안교육신청 > 결재문서번호 UPDATE
    int updateSecurityEduDocId(HashMap<String, Object> paramMap);

    // 특수업무수행서약 등록
    int specialTaskPledgeSave(HashMap<String, Object> paramMap);

    // 보안교육신청 현황 엑셀다운로드
    List<Map<String, Object>> securityEducationRequestExcel(HashMap<String, Object> paramMap);

    // 특수업무수행서약 현황(외부인) 목록 조회[관리자화면]
    List<Map<String, Object>> selectSpecialTaskIoListAdm(HashMap<String, Object> paramMap);

    // 특수업무서행서약 현황(구성원) 엑셀 다운로드
    List<Map<String, Object>> selectSpecialTaskCoListExcel(HashMap<String, Object> paramMap);

    // 특수업무서행서약 현황(외부인) 엑셀 다운로드
    List<Map<String, Object>> selectSpecialTaskIoListExcel(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectSecurityEducationInfoAdmList(HashMap<String, Object> paramMap);

    Integer selectSecurityEducationInfoAdmCount(HashMap<String, Object> paramMap);

}
