package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.*;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ApproverLineRepository {

    ApprovalDocDTO selectApprovalDoc(Integer docId);

    List<SpecifiedApproverLineDTO> selectApprovalLineRequestLine(ApproverLineQueryDTO apprQueryDTO);

    List<SpecifiedApproverLineDTO> selectApprovalLineReqDeptTeamLeader(ApproverLineQueryDTO apprQueryDTO);

    /* 로그인한 EMP_ID의 속한 부서 PL(팀장)과 상위 부서의 담당(부서장) 조회 */
    List<SpecifiedApproverLineDTO> selectApprovalLineReqDeptPLAndDamdang(String empId);

    List<SpecifiedApproverLineDTO> selectApprovalLinePermitLine(ApproverLineQueryDTO apprQueryDTO);

    List<SpecifiedApproverLineDTO> selectApprovalLinePermitLineCorrPlan(ApproverLineQueryDTO apprQueryDTO);

    List<SavedApproverLineDTO> selectSavedApproverLine(SavedApproverLineDTO apprQuery);

    List<SpecifiedApproverLineDTO> selectApprEmpListByDept(ApproverLineQueryDTO apprQueryDTO);

    /* 관리자 기초코드 > 자산반출입 허가부서결재자 */
    List<PermitSpecifiedApproverDTO> selectInoutPermitSpecifiedApproverList(PermitSpecifiedApproverQueryDTO queryDTO);

    /* 관리자 기초코드 > 자산반출입 허가부서결재자 */
    PermitSpecifiedApproverDTO selectInoutPermitSpecifiedApprover(Integer apprdefNo);

    /* 관리자 기초코드 > 자산반출입 허가부서결재자 */
    int insertInoutPermitSpecifiedApprover(PermitSpecifiedApproverDTO permitSpecifiedApproverDTO);

    /* 관리자 기초코드 > 자산반출입 허가부서결재자 */
    int updateInoutPermitSpecifiedApprover(PermitSpecifiedApproverDTO permitSpecifiedApproverDTO);

    List<SpecifiedApproverLineDTO> selectInoutPermitApprovalLine(ApproverLineQueryDTO apprQueryDTO);

}
