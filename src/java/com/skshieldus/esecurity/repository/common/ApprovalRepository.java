package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.*;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ApprovalRepository {

    List<ApDocDTO> selectApDocList(List<Integer> docIdList);

    List<ApDocEmpDTO> selectApDocEmpList(List<Integer> docIdList);

    String selectDocCd(ApprovalDTO approvalDTO);

    String selectDocNewCd(ApprovalDTO approvalDTO);

    int insertAPNotProcessWsdl(ApprovalEAIDocDTO approvalEAIDocDTO);

    int selectNewDocId();

    int insertApprovalDocPre(ApprovalDTO approval);

    int insertApproverPre(SavedApproverLineDTO approver);

    List<SavedApproverLineDTO> selectApprovalLineReferLine(ApprovalDTO approval);

    int insertApprovalHtml(ApprovalDTO approval);

    int insertApprovalPortalPre(ApprovalDTO approval);

    int insertApprovalDoc(Integer docId);

    int insertApprover(Integer docId);

    int insertApprovalPortal(Integer docId);

    // 결재정보 삭제
    int deleteApAppr(Integer docId);

    int deleteApApprPortal(List<Integer> docIdList);

    int deleteApDoc(List<Integer> docIdList);

    int updateApDocDelYn(Integer docId);

    int updateApApprDelYn(Integer docId);

    List<ApApprDTO> selectApApprList(Integer docId);

    ApDocDTO selectApDoc(Integer docId);

    ApDocDTO selectApDocInfo(String docId);

    int insertApDoc(ApprovalDTO approvalDTO);

    int updateApDoc(ApprovalDTO approvalDTO);

    int insertApAppr(SavedApproverLineDTO savedApproverLineDTO);

    int insertApApprPortal(ApprovalDTO approvalDTO);

    List<SpecifiedApproverLineDTO> selectRequestApprovalLine(ApproverLineQueryDTO param);

    List<SpecifiedApproverLineDTO> selectPermitApprovalLine(ApproverLineQueryDTO param);

    List<ApApprDTO> selectApprovalSaveList(Integer docId);

    List<Map<String, Object>> selectRequestDeptList(String deptId);

    List<Map<String, Object>> selectApprEmpListByDept(ApproverLineQueryDTO param);

    Map<String, Object> selectApprDocView(Integer docId);

}