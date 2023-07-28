package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityRectifyPlanRepository {

    // 시정개선계획서(보안위규 이력조회) 목록 조회
    List<Map<String, Object>> selectSecurityRectifyPlanList(Map<String, Object> paramMap);

    // 시정개선계획서(보안위규 이력조회) 목록 갯수 조회
    int selectSecurityRectifyPlanListCnt(Map<String, Object> paramMap);

    // 시정개선계획서(보안위규 이력조회) 상세 조회
    Map<String, Object> selectSecurityRectifyPlanView(Map<String, Object> paramMap);

    //보안위규자 시정계획서/경고장 업데이트
    int updateCorrPlanDoc(Map<String, Object> paramMap);

    //보안위규자 시정계획서/경고장 내역 등록
    int insertCorrPlanHist(Map<String, Object> paramMap);

    //보안위규자 건물출입위반시정계획제출여부 업데이트
    int updateCorrPlanOfend(Map<String, Object> paramMap);

    int updateCorrPlanLastAppr(Map<String, String> param);

}
