package com.skshieldus.esecurity.service.sysmanage;

import java.util.List;
import java.util.Map;

public interface ApprovalLineAssignTeamService {

    // 부서의 팀장지정자 목록 조회
    List<Map<String, Object>> selectApprLineAssignTeamList(String deptId);

    // 부서의 사원 목록 조회
    List<Map<String, Object>> selectApprEmpListByDeptTeam(String deptId);

    // 로그인 사용자의 부서 목록 조회
    List<Map<String, Object>> selectApprDeptTeamList(String empId);

    // 팀장지정자 등록
    Integer saveApprLineAssig(Map<String, Object> data);

}