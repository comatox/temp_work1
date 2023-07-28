package com.skshieldus.esecurity.repository.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.ApprovalLineDefDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalLineDefSearchDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalStateDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalStateSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ApprovalLineRepository {

    // 결재선 미 준수 현황 조회 엑셀 다운로드
    List<ApprovalStateDTO> selectApprovalStateExcel(ApprovalStateSearchDTO approvalStateSearchDTO);

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