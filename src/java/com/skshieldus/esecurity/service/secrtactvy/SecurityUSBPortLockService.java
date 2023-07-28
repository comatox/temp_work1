package com.skshieldus.esecurity.service.secrtactvy;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SecurityUSBPortLockService {

    /**
     * 생산장비 마스터키 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 12.
     */
    List<Map<String, Object>> productionMasterKeyList(HashMap<String, Object> paramMap);

    /**
     * 생산장비 마스터키 관리 권한 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "I0101124"
     * @since : 2022. 2. 24.
     */
    Map<String, Object> productionMasterKeyAdminCheck(HashMap<String, Object> paramMap);

    /**
     * 생산장비 마스터키 관리 목록건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 8.
     */
    int productionMasterKeyListCnt(HashMap<String, Object> paramMap);

    /**
     * 생산장비 마스터키 관리 상태값 변경
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 13.
     */
    Boolean productionMasterKeyStatusChange(Map<String, Object> paramMap);

    /**
     * 생산장비 마스터키 관리자 변경
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 13.
     */
    Boolean productionMasterKeyMgmtChange(HashMap<String, Object> paramMap);

    /**
     * 생산장비 마스터키 실사결과 변경
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 8.
     */
    Boolean updateScUSBPortLockMastKeyCheck(HashMap<String, Object> paramMap);

    /**
     * 생산장비 마스터키 관리 캠퍼스/건물 변경
     *
     * @param paramMap
     * @return
     *
     * @author : I0101124
     * @since : 2022. 2. 25.
     */
    Boolean updateScUSBPortLockMastKeyCompBld(HashMap<String, Object> paramMap);

    /**
     * 산업제어시스템 마스터키 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 14.
     */
    List<Map<String, Object>> industryControlMasterKeyList(HashMap<String, Object> paramMap);

    /**
     * 산업제어시스템 마스터키 관리 상태값 변경
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 14.
     */
    Boolean industryControlMasterKeyStatusChange(HashMap<String, Object> paramMap);

    /**
     * 데이타센터 USB포트락 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 18.
     */
    List<Map<String, Object>> dataCenterUSBPortLockList(HashMap<String, Object> paramMap);

    /**
     * 데이타센터 USB포트락 관리 목록 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 18.
     */
    Integer dataCenterUSBPortLockListCnt(HashMap<String, Object> paramMap);

    /**
     * 데이타센터 USB포트락 관리 Row저장
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 18.
     */
    Boolean dataCenterUSBPortLockSave(HashMap<String, Object> paramMap);

    /**
     * 데이타센터 USB포트락 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 18.
     */
    CommonXlsViewDTO dataCenterUSBPortLockListDownload(HashMap<String, Object> paramMap);

    /**
     * 산업제어시스템 USB포트락 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    List<Map<String, Object>> industryControlUSBPortLockList(HashMap<String, Object> paramMap);

    /**
     * 산업제어시스템 USB포트락 관리 목록 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    Integer industryControlUSBPortLockListCnt(HashMap<String, Object> paramMap);

    /**
     * 산업제어시스템 USB포트락 관리 관리 Row 저장
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    Boolean industryControlUSBPortLockSave(List<HashMap<String, Object>> paramMap);

    /**
     * 산업제어시스템 USB포트락 신규 Row 저장
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    Boolean industryControlUSBPortLockInsert(HashMap<String, Object> paramMap);

    /**
     * 산업제어시스템 USB포트락 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 21.
     */
    CommonXlsViewDTO industryControlUSBPortLockListDownload(HashMap<String, Object> paramMap);

    /**
     * 생산장비 보안요원 안전재고 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021.10.22
     */
    List<Map<String, Object>> productionSafetyStockList(HashMap<String, Object> paramMap);

    /**
     * 생산장비 보안요원 안전재고 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    CommonXlsViewDTO productionSafetyStockListDownload(HashMap<String, Object> paramMap);

    /**
     * 생산장비 보안요원 안전재고 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    Boolean productionSafetyStockDelete(HashMap<String, Object> paramMap);

    /**
     * 생산장비 USB포트락 점검결과 리셋
     *
     * @param paramMap
     * @return
     *
     * @author : "I0101124"
     * @since : 2022. 2. 22.
     */
    Boolean productionUSBPortLockReset(HashMap<String, Object> paramMap);

    /**
     * 생산장비 USB포트락비대상 일괄처리
     *
     * @param paramMap
     * @return
     *
     * @author : "I0101124"
     * @since : 2022. 2. 22.
     */
    Boolean productionUSBPortLockResetTarget(HashMap<String, Object> paramMap);

    /**
     * 생산장비 보안요원 안전재고 등록/수정
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 22.
     */
    Boolean productionSafetyStockSave(HashMap<String, Object> paramMap);

    /**
     * 생산장비 보안요원 안전재고 상세조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021.10.22
     */
    Map<String, Object> productionSafetyStockDetail(HashMap<String, Object> paramMap);

    /**
     * 생산장비 USB포트락 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    List<Map<String, Object>> productionUSBPortLockList(HashMap<String, Object> paramMap);

    /**
     * 생산장비 USB포트락 관리 목록 총갯수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    int productionUSBPortLockListCnt(HashMap<String, Object> paramMap);

    /**
     * 생산장비 USB포트락 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    CommonXlsViewDTO productionUSBPortLockDownload(HashMap<String, Object> paramMap);

    /**
     * 생산장비 USB포트락 장착결과 저장
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    Boolean productionUSBPortLockInstallResultSave(List<HashMap<String, Object>> paramMap);

    /**
     * 생산장비 USB포트락 점검결과 저장
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    Boolean productionUSBPortLockCheckResultSave(List<HashMap<String, Object>> paramMap);

    /**
     * 데이타센터 마스터키 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 02.
     */
    List<Map<String, Object>> dataCenterMasterKeyList(HashMap<String, Object> paramMap);

    /**
     * 데이타센터 마스터키 관리 상태값 변경
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 11. 02.
     */
    Boolean dataCenterMasterKeyStatusChange(HashMap<String, Object> paramMap);

    /**
     * 생산장비 USB포트락 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 13.
     */
    Boolean productionUSBPortLockDelete(HashMap<String, Object> paramMap);

    /**
     * 생산장비 마스터키 목록 엑셀다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 14.
     */
    CommonXlsViewDTO productionMasterKeyListDownload(HashMap<String, Object> paramMap);

    /**
     * 생산장비 마스터키 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    Boolean productionMasterKeyDelete(HashMap<String, Object> paramMap);

    /**
     * 마스터키 보안담당자 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    Map<String, Object> selectSecrtEmpMng(HashMap<String, Object> paramMap);

    /**
     * 마스터키 상세정보 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    Map<String, Object> productionMasterKeyDetail(HashMap<String, Object> paramMap);

    /**
     * 마스터키 등록/수정
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 15.
     */
    Boolean productionMasterKeySave(HashMap<String, Object> paramMap);

    /**
     * 데이터센터 USB포트락 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 16.
     */
    Boolean dataCenterUSBPortLockDelete(HashMap<String, Object> paramMap);

    /**
     * 산업제어시스템 USB포트락 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 21.
     */
    Boolean industryControlUSBPortLockDelete(HashMap<String, Object> paramMap);

    /**
     * 데이터센터 마스터키 등록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 22.
     */
    Boolean dataCenterMasterKeySave(HashMap<String, Object> paramMap);

    /**
     * 데이터센터 마스터키 목록 엑셀다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 22.
     */
    CommonXlsViewDTO dataCenterMasterKeyExcel(HashMap<String, Object> paramMap);

    /**
     * 데이터센터 마스터키 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 23.
     */
    Boolean dataCenterMasterKeyDelete(HashMap<String, Object> paramMap);

    /**
     * 데이터센터 마스터키 상세보기
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 23.
     */
    Map<String, Object> dataCenterMasterKeyDetail(HashMap<String, Object> paramMap);

    /**
     * 산업제어 시스템 마스터키 목록 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 24.
     */
    CommonXlsViewDTO industryControlMasterKeyListDownload(HashMap<String, Object> paramMap);

    /**
     * 산업제어 시스템 마스터키 상세보기
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 24.
     */
    Map<String, Object> industryControlMasterKeyDetail(HashMap<String, Object> paramMap);

    /**
     * 산업제어 시스템 마스터키 저장
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 27.
     */
    Boolean industryControlMasterKeySave(HashMap<String, Object> paramMap);

}
