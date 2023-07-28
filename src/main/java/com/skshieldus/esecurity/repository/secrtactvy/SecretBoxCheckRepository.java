package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecretBoxCheckRepository {

    // 비밀문서함 점검결과 목록 조회
    List<Map<String, Object>> selectSecretBoxCheckList(Map<String, Object> paramMap);

    // 비밀문서함 점검결과 상세 조회
    Map<String, Object> selectSecretBoxCheck(Integer scboxChkApplNo);

    // 비밀문서함 점검결과 EGSS 기본정보 조회
    Map<String, Object> selectEGSSSecretBoxCheckMaster(Map<String, Object> paramMap);

    // 비밀문서함 점검결과 점수 목록 EGSS 조회
    List<Map<String, Object>> selectEGSSSecretBoxCheckSummary(Map<String, Object> paramMap);

    // 비밀문서함 점검결과 목록 조회 엑셀 저장
    List<Map<String, Object>> selectSecretBoxCheckExcelList(Map<String, Object> paramMap);

    // 비밀문서함 점검결과 EGSS 점검문항 목록 조회
    List<Map<String, Object>> selectEGSSSecretBoxCheckQstList(Map<String, Object> paramMap);

    // 비밀문서함 점검결과 상신 점검번호 채번
    Integer selectSecretBoxCheckSeq();

    // 비밀문서함 점검결과 상신
    int insertSecretBoxCheck(Map<String, Object> paramMap);

    // 비밀문서함 점검결과 상신 문서 번호 업데이트
    int updateSecretBoxCheckDocId(Map<String, Object> paramMap);

    // 비밀문서함 점검결과 상신 승인 결과 업데이트
    int updateSecretBoxCheckApplStat(Map<String, Object> paramMap);

}
