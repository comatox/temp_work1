package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface RestrictVisitRepository {

    // 업체등록 신청현황 목록 조회
    List<Map<String, Object>> selectRestrictCompList(Map<String, Object> paramMap);

    // 업체등록 신청현황 상세 조회
    Map<String, Object> selectRestrictCompView(Map<String, Object> paramMap);

    // 업체등록 신청현황 제제정보 목록 조회
    List<Map<String, Object>> selectRestrictHistCompList(Map<String, Object> paramMap);

    // 회원가입 신청현황 목록 조회
    List<Map<String, Object>> selectRestrictEmpList(Map<String, Object> paramMap);

    // 회원가입 신청현황 상세 조회
    Map<String, Object> selectRestrictEmpView(Map<String, Object> paramMap);

    // 회원가입 신청현황 제제정보 목록 조회
    List<Map<String, Object>> selectRestrictHistEmpList(Map<String, Object> paramMap);

    // 해제여부 수정(업체)
    int updateRestrictCompResolveComp(Map<String, Object> paramMap);

    // 해제여부 이력 수정(업체)
    int updateRestrictCompResolveHist(Map<String, Object> paramMap);

    // 해제여부 수정(회원)
    int updateRestrictEmpResolveEmp(Map<String, Object> paramMap);

    // 해제여부 이력 수정(회원)
    int updateRestrictEmpResolveHist(Map<String, Object> paramMap);

    // 해제여부 수정(방문예약 => IO_VST_MAN)
    int updateRestrictVisitManResolve(Map<String, Object> paramMap);

    // 해제여부 이력 수정(방문예약 => IO_VST_MAN_REST_HIST)
    int updateRestrictVisitManResolveHist(Map<String, Object> paramMap);

    // 해제여부 수정(방문예약 => IO_VST)
    int updateRestrictVisitResolve(Map<String, Object> paramMap);

    // 해제여부 이력 수정(방문예약 => IO_VST_REST_HIST)
    int updateRestrictVisitResolveHist(Map<String, Object> paramMap);

    // 해제여부 수정(상시출입증 => IO_PASS)
    int updatePassRestrictFree(Map<String, Object> paramMap);

    // 해제여부 이력 수정(상시출입증 => IO_PASS_REST_HIST)
    int updatePassRestrictHistFree(Map<String, Object> paramMap);

}
