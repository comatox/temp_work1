package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityUSBPortLockRepository {

    // 생산장비 마스터키 목록 조회
    List<Map<String, Object>> productionMasterKeyList(HashMap<String, Object> paramMap);

    // 생산장비 마스터키 목록건수 조회
    int productionMasterKeyListCnt(HashMap<String, Object> paramMap);

    // 생산장비 마스터키 상태값 변경
    int productionMasterKeyStatusChange(Map<String, Object> param);

    // 생산장비 마스터키 상태변경시 메일 수신자 이메일주소 조회
    List<HashMap<String, Object>> selectProductionMasterKeyEmailAddr(Map<String, Object> paramMap);

    // 생산장비 마스터키 관리자 변경
    int productionMasterKeyMgmtChange(HashMap<String, Object> paramMap);

    // 산업제어시스템 마스터키 목록 조회
    List<Map<String, Object>> industryControlMasterKeyList(HashMap<String, Object> paramMap);

    // 산업제어시스템 마스터키 상태값 변경
    int industryControlMasterKeyStatusChange(HashMap<String, Object> paramMap);

    // 산업제어시스템 마스터키 상태변경시 메일 수신자 이메일주소 조회
    List<HashMap<String, Object>> selectIncMasterKeyEmailAddr(HashMap<String, Object> paramMap);

    // 데이타센터 USB포트락 관리 목록 조회
    List<Map<String, Object>> dataCenterUSBPortLockList(HashMap<String, Object> paramMap);

    // 데이타센터 USB포트락 관리 목록 총갯수 조회
    int dataCenterUSBPortLockListCnt(HashMap<String, Object> paramMap);

    // 데이타센터 USB포트락 Row저장
    int dataCenterUSBPortLockSave(HashMap<String, Object> paramMap);

    // 데이타센터 USB포트락 관리 목록 조회 (Excel용)
    List<Map<String, Object>> dataCenterUSBPortLockExcel(HashMap<String, Object> paramMap);

    // 산업제어시스템 USB포트락 관리 목록 조회
    List<Map<String, Object>> industryControlUSBPortLockList(HashMap<String, Object> paramMap);

    // 산업제어시스템 USB포트락 관리 목록 총갯수 조회
    int industryControlUSBPortLockListCnt(HashMap<String, Object> paramMap);

    // 산업제어시스템 USB포트락 관리 row저장
    int industryControlUSBPortLockSave(HashMap<String, Object> paramMap);

    // 산업제어시스템 USB포트락 신규 Row저장
    int industryControlUSBPortLockInsert(HashMap<String, Object> paramMap);

    // 생산장비 보안요원 안전재고 목록 조회
    List<Map<String, Object>> productionSafetyStockList(HashMap<String, Object> paramMap);

    // 생산장비 보안요원 안전재고 삭제
    int productionSafetyStockDelete(HashMap<String, Object> paramMap);

    // 생산장비 USB포트락 점검결과 리셋
    int productionUSBPortLockReset(HashMap<String, Object> paramMap);

    // 생산장비 USB포트락비대상 일괄처리
    int productionUSBPortLockResetTarget(HashMap<String, Object> paramMap);

    // 생산장비 보안요원 안전재고 pkSeq값 조회
    Object getProductionSafetyStockPkSeq(HashMap<String, Object> paramMap);

    // 생산장비 보안요원 안전재고 등록/수정
    int productionSafetyStockSave(HashMap<String, Object> paramMap);

    // master key 보유여부, 파손으로 변경
    int updateUSBPortLockMasterKeyStatus(HashMap<String, Object> paramMap);

    int updateUSBPortLockMasterKeyMerge(HashMap<String, Object> paramMap);

    // 생산장비 보안요원 안전재고 상세조회
    Map<String, Object> productionSafetyStockDetail(HashMap<String, Object> paramMap);

    // 생산장비 USB포트락 목록 조회
    List<Map<String, Object>> productionUSBPortLockList(HashMap<String, Object> paramMap);

    // 생산장비 USB포트락 목록 총 갯수 조회
    int productionUSBPortLockListCnt(HashMap<String, Object> paramMap);

    //생산장비 USB포트락 목록 조회 (Excel용)
    List<Map<String, Object>> productionUSBPortLockExcel(HashMap<String, Object> paramMap);

    //생산장비 USB포트락 장착결과 저장
    int productionUSBPortLockInstallResultSave(HashMap<String, Object> param);

    //생산장비 USB포트락 점검결과 저장
    int productionUSBPortLockCheckResultSave(HashMap<String, Object> param);

    //데이타센터 마스터키 관리 목록 조회
    List<Map<String, Object>> dataCenterMasterKeyList(HashMap<String, Object> paramMap);

    //데이타센터 마스터키 상태값 변경
    int dataCenterMasterKeyStatusChange(HashMap<String, Object> paramMap);

    //데이타센터 마스터키 상태변경시 메일 수신자 이메일주소 조회
    List<HashMap<String, Object>> selectDataCenterEmailAddr(HashMap<String, Object> paramMap);

    // 생산장비 마스터키 관리 실사결과 변경
    int updateScUSBPortLockMastKeyCheck(Map<String, Object> paramMap);

    // 생산장비 마스터키 관리 건물 변경
    int updateScUSBPortLockMastKeyCompBld(Map<String, Object> paramMap);

    // 생산장비 USB포트락 삭제
    int productionUSBPortLockDelete(HashMap<String, Object> paramMap);

    // 생산장비 마스터키 엑셀 다운로드
    List<Map<String, Object>> productionMasterKeyListDownload(HashMap<String, Object> paramMap);

    // 생산장비 마스터키 삭제
    int productionMasterKeyDelete(HashMap<String, Object> paramMap);

    // 보안담당자 조회
    List<Map<String, Object>> selectSecrtEmpMng(HashMap<String, Object> paramMap);

    // 마스터키 상세정보 조회
    Map<String, Object> productionMasterKeyDetail(HashMap<String, Object> paramMap);

    // 생산장비 마스터키 채번
    String selectProductionMastKeyPkSeq();

    // 마스터키 등록/수정
    int productionMasterKeySave(HashMap<String, Object> paramMap);

    // 데이터센터 USB포트락 삭제
    int dataCenterUSBPortLockDelete(HashMap<String, Object> paramMap);

    // 산업제어시스템 USB포트락 삭제
    int industryControlUSBPortLockDelete(HashMap<String, Object> paramMap);

    // 데이터센터 마스터키 채번
    String selectDataCenterMastKeyPkSeq();

    // 데이터센터 마스터키 등록/수정
    int dataCenterMasterKeySave(HashMap<String, Object> paramMap);

    // 데이터센터 마스터키 엑셀 다운로드
    List<Map<String, Object>> dataCenterMasterKeyExcel(HashMap<String, Object> paramMap);

    // 데이터센터 마스터키 삭제
    int dataCenterMasterKeyDelete(HashMap<String, Object> paramMap);

    // 데이터센터 마스터키 상세조회
    Map<String, Object> dataCenterMasterKeyDetail(HashMap<String, Object> paramMap);

    // 산업제어 시스템 마스터키 엑셀 다운로드
    List<Map<String, Object>> industryControlMasterKeyListExcel(HashMap<String, Object> paramMap);

    // 산업제어 시스템 마스터키 상세조회
    Map<String, Object> industryControlMasterKeyDetail(HashMap<String, Object> paramMap);

    // 산업제어 시스템 마스터키 채번
    String selectindustryControlMastKeyPkSeq();

    // 산업제어 시스템 마스터키 등록/수정
    int industryControlMasterKeySave(HashMap<String, Object> paramMap);

    // 생산장비 마스터키 관리 권한 조회
    Map<String, Object> productionMasterKeyAdminCheck(HashMap<String, Object> paramMap);

}
