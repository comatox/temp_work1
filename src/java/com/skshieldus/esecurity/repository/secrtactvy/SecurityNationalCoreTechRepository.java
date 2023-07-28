package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityNationalCoreTechRepository {

    // 국가핵심기술 보안진단 체크리스트 목록 조회
    List<Map<String, Object>> selectSecurityNationalCoreTechChecklist(Map<String, Object> paramMap);

    // 국가핵심기술 보안진단  체크리스트 상세 조회
    Map<String, Object> selectSecurityNationalCoreTechChecklistView(Map<String, Object> paramMap);

    // 국가핵심기술 보안진단 체크리스트 등록
    int insertNationalCoreTechChecklist(Map<String, Object> paramMap);

    // 국가핵심기술 보안진단  목록 조회
    List<Map<String, Object>> selectSecurityNationalCoreTechList(Map<String, Object> paramMap);

    // 국가핵심기술 보안진단  상세 조회
    Map<String, Object> selectSecurityNationalCoreTechView(Map<String, Object> paramMap);

    // 국가핵심기술 보안진단  등록
    int insertNationalCoreTech(Map<String, Object> paramMap);

    //국가핵심기술 보안진단 최종 체크리스트  조회
    List<Map<String, Object>> selectLastChecklist(Map<String, Object> paramMap);

    //doc id 변경
    int updateNationalCoreTechDocId(Map<String, Object> paramMap);

    //국가핵심기술 보안진단  결재 후처리
    int updateNationalCoreTechApplWork(Map<String, Object> paramMap);

    int updateCodeInfo(Map<String, Object> paramMap);

}

