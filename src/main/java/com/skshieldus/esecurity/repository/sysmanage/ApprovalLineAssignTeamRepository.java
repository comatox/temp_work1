package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ApprovalLineAssignTeamRepository {

    // 부서의 팀장지정자 목록 조회
    List<Map<String, Object>> selectApprLineAssignTeamList(String deptId);

    // 부서의 사원 목록 조회
    List<Map<String, Object>> selectApprEmpListByDeptTeam(String deptId);

    // 로그인 사용자의 부서 목록 조회
    List<Map<String, Object>> selectApprDeptTeamList(String empId);

    // 팀장지정자 등록
    Integer insertApprLineAssignTeam(Map<String, Object> data);

    // 팀장지정자 삭제
    Integer deleteApprLineAssignTeam(String deptId);

}