package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalLineDefDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalLineDefSearchDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalStateSearchDTO;
import java.util.List;
import java.util.Map;

public interface ApprovalLineService {

    // 결재선 미 준수 현황 조회 엑셀 다운로드
    CommonXlsViewDTO selectApprovalStateExcel(ApprovalStateSearchDTO approvalStateSearchDTO);

    // 결재선 관리 목록 조회
    List<ApprovalLineDefDTO> selectApprLineManageList(ApprovalLineDefSearchDTO searchDTO);

    // 결재선 관리 메뉴 목록 조회
    List<Map<String, Object>> selectApprMenuList(String searchMenuNm);

    // 결재선 관리 부서 목록 조회
    List<Map<String, Object>> selectApprDeptList(Map<String, Object> paramMap);

    // 결재선 관리 상세 조회
    ApprovalLineDefDTO selectApprLineManage(Integer defSeq);

    // 결재선 관리 등록
    Integer insertApprLineManage(ApprovalLineDefDTO approvalLineDefDTO);

    // 결재선 관리 수정
    Integer updateApprLineManage(ApprovalLineDefDTO approvalLineDefDTO);

}