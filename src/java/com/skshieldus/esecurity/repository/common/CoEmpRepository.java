package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface CoEmpRepository {

    // 구성원 목록 조회
    List<CoEmpDTO> selectCoEmpList(CoEmpDTO coEmpDTO);

    // 구성원 목록건수 조회
    Integer selectCoEmpListCnt(CoEmpDTO coEmpDTO);

    // 구성원 상세 조회
    CoEmpDTO selectCoEmp(String empId);

    // 상신문서 docId 로 상신자의 휴대전화번호를 찾아온다.
    String selectTelByDocId(Integer docId);

    // 메인 팀장,담당자 정보 조회
    List<CoEmpDTO> selectMainScMgrList(String empId);

    // 메인 보안활동 정보 조회(mgr)
    CoEmpStatDTO selectMainMgrStatList(String empId);

    // 메인 보안활동 정보 조회(sc)
    CoEmpStatDTO selectMainScStatList(String empId);

    // 구성원+협력사(CO_DRM_EMP) 목록 조회
    List<CoEmpDTO> selectCoXEmpList(CoEmpDTO coEmpDTO);

    // 현채인 (우시/충칭) 사원 목록 조회
    List<GlobalStaffDTO> selectGlobalStaffProductEmpList(GlobalStaffSearchDTO globalStaffSearchDTO);

    // 구성원 차량정보 목록 조회
    List<CoEmpCarInfoViewDTO> selectCoEmpCarInfoViewList(CoEmpCarInfoViewSearchDTO coEmpCarInfoViewSearchDTO);

    // 복수 구성원 목록 조회
    List<Map<String, Object>> selectMultiCoEmpInfoList(Map<String, Object> paramMap);

}
