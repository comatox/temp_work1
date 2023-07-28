package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdmittedRepository {

    // 통제구역 입실 정보 등록
    int insertAdmittedReg(Map<String, Object> paramMap);

    // 통제구역 입실 정보 수정
    int updateVisitorAdmittedReg(Map<String, Object> paramMap);

    // 입실 정보 조회(체크) - 임직원
    String selectStaffAdmittedChk(Map<String, Object> paramMap);

    // 상위 건물 목록 조회
    List<Map<String, Object>> selectBuildingControlUp(Map<String, Object> paramMap);

    // 하위 건물(Bay) 목록 조회
    List<Map<String, Object>> selectBuildingControlDownList(Map<String, Object> paramMap);

    // 통제구역 퇴실 정보 수정(임직원)
    int updateStaffCheckout(Map<String, Object> paramMap);

    // 퇴실 정보 조회(건수) - 임직원
    Integer selectStaffCheckout(Map<String, Object> paramMap);

    // 상시출입객 입실 정보 조회
    String selectAlwaysAdmittedChk(Map<String, Object> paramMap);

    // 상시출입객 등록정보 조회
    Map<String, Object> selectAlwaysAdmittedRegChk(Map<String, Object> paramMap);

    // 상시출입객퇴실 목록 조회
    List<Map<String, Object>> selectAlwaysCurrentLineList(Map<String, Object> paramMap);

    // 방문객퇴실 목록 조회
    List<Map<String, Object>> selectVisitorCurrentLineList(Map<String, Object> paramMap);

    // 통제구역 퇴실 정보 수정(임직원 제외)
    int updateCheckout(Map<String, Object> paramMap);

    // 통제구역 퇴실 정보 수정(방문객 추가 수정 - IO_VST_MAN_GATE_IO)
    int updateVisitorCheckout(Map<String, Object> paramMap);

    // 방문객입실 목록 조회
    List<Map<String, Object>> selectVisitorAdmittedList(Map<String, Object> paramMap);

    // 물품반입 등록
    int insertItemsImport(Map<String, Object> paramMap);

    // 물품반출 목록 조회
    List<Map<String, Object>> selectItemsExportList(Map<String, Object> paramMap);

    // 물품반출 수정
    int updateItemsExport(Map<String, Object> paramMap);

}
