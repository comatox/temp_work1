package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityDeputyRepository {

    // 보안담당자 신규/변경 조회
    List<Map<String, Object>> securityDeputyList(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 상세정보 - securityDeputyView
    Map<String, Object> securityDeputyView(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 상세정보 > 관리부서 Tree - securityDeputyDeptTreeList
    List<Map<String, Object>> securityDeputyDeptTreeList(Map<String, Object> paramMap);

    // 팀내생활보안점검 > 점검부서 Tree - securityDeputyDeptTreeList3
    List<Map<String, Object>> securityDeputyDeptTreeList3(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > scChangeNo 시퀀스 채번 - selectScChangeNoSeq
    int selectScChangeNoSeq();

    // 보안담당자 신규/변경 신청 > 결재선 추가 - insertSecrtChangeApAppr
    int insertSecrtChangeApAppr(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > AP_DOC Update - updateApDocScrtChg
    int updateApDocScrtChg(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 신청정보 Insert - insertSecrtChange
    int insertSecrtChange(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 결재선 AP_SEQ 증가처리 - updateSecrtChangeApAppr
    int updateSecrtChangeApAppr(String docId);

    // 보안담당자 신규/변경 신청 > 이전 신청정보 Insert - insertScDeptSecEmpPre
    int insertScDeptSecEmpPre(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 신청정보 Select - selectSecrtChange
    Map<String, Object> selectSecrtChange(Map<String, Object> paramMap);

    /* 보안담당자 신규/변경 신청 > 결제 후처리 승인 Start */
    // 보안담당자 신규/변경 신청 > 결제 후처리 승인 > 1) 구 보안 담당자 정보  UPDATE (삭제 플래그 처리) -  deleteSecrtChangeDeptSec (ASIS : dmSecrtChange_DeptSec_D)
    int deleteSecrtChangeDeptSec(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 결제 후처리 승인 > 2-1) 신 보안담당자 정보 INSERT - insertSecrtChangeDeptSec (ASIS : dmSecrtChange_DeptSec_I)
    int insertSecrtChangeDeptSec(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 결제 후처리 승인 > 2-2) 신 보안담당자 정보 INSERT - deleteSecrtChangeScDeptSecEmp (ASIS : dmSecrtChange_ScDeptSecEmp_D)
    int deleteSecrtChangeScDeptSecEmp(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 결제 후처리 승인 > 2-3) 신 보안담당자 정보 INSERT - insertSecrtChangeScDeptSecEmp (ASIS : dmSecrtChange_ScDeptSecEmp_I)
    int insertSecrtChangeScDeptSecEmp(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 결제 후처리 승인 > 3) 구 보안 담당자 권한 삭제 - deleteSecrtChangeOldSecrtEmpAuth (ASIS : dmSecrtChange_Old_Secrt_Emp_Auth_D_U)
    int deleteSecrtChangeOldSecrtEmpAuth(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 결제 후처리 승인 > 4) 신 보안담당자권한 부여 - updateSecrtChangeNewSecrtEmpAuth(ASIS : dmSecrtChange_New_Secrt_Emp_Auth_U)
    int updateSecrtChangeNewSecrtEmpAuth(Map<String, Object> paramMap);
    /* 보안담당자 신규/변경 신청 > 결제 후처리 승인 End */

    // 보안담당자 신규/변경 신청 > 보안담당자 List - secrtDeptDuptyCheckDuptyRenew
    List<Map<String, Object>> secrtDeptDuptyCheckDuptyRenew(Map<String, Object> paramMap);

    // 보안담당자 신규/변경 신청 > 보안담당자 정보 - secrtDeptDuptyDuptyInfo
    Map<String, Object> secrtDeptDuptyDuptyInfo(Map<String, Object> paramMap);

}
