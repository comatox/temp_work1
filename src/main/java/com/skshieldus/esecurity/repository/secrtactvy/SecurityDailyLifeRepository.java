package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityDailyLifeRepository {

    // 인력자원 관리 목록 조회
    List<Map<String, Object>> licenseManageAdminManageList(HashMap<String, Object> paramMap);

    // 인력자원 관리 상세목록 조회
    List<Map<String, Object>> licenseManageAdminManageEmpList(HashMap<String, Object> paramMap);

    // 인력자원 관리 상세목록 삭제
    int licenseManageAdminManageEmpDelete(HashMap<String, Object> paramMap);

    // 인력자원 조회 목록
    List<Map<String, Object>> licenseManageAdminEmpList(HashMap<String, Object> paramMap);

    // 인력자원 조회 목록 엑셀다운로드
    List<Map<String, Object>> licenseManageAdminEmpListExcel(HashMap<String, Object> paramMap);

    // 자격증 관리 목록 조회
    List<Map<String, Object>> licenseManageList(HashMap<String, Object> paramMap);

    // 자격증 삭제
    int licenseManageRemove(HashMap<String, Object> paramMap);

    // 자격증 등록 채번
    int dmLicenseManage_LicenseSeq_S();

    // 자격증 등록
    int licenseManageInsert(HashMap<String, Object> paramMap);

    // 자격증 관리 목록 총갯수
    int licenseManageListCnt(HashMap<String, Object> paramMap);

    // 인력자원 조회 목록 총갯수
    int licenseManageAdminManageEmpListCnt(HashMap<String, Object> paramMap);

}
