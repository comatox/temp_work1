package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.*;

import java.util.List;
import java.util.Map;

public interface ApprovalService {

    /**
     * 결재문서 목록 조회
     *
     * @return
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 2.
     */
    List<ApDocDTO> selectApDocList(List<Integer> docIdList);

    /**
     * 결재문서 (key, value) 목록 조회
     *
     * @return
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 2.
     */
    Map<String, Object> selectApDocListForKey(List<Integer> docIdList);

    /**
     * 결재진행현황 사번정보 (key, value) 목록 조회
     *
     * @return
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 2. 17.
     */
    Map<String, ApDocEmpDTO> selectApDocEmpListForKey(List<Integer> docIdList);

    ApprovalDocDTO insertApproval(ApprovalDTO approval);

    /**
     * 결재 상신 저장
     *
     * @param sessionInfo
     * @param approval
     * @return
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 3. 2.
     */
    ApprovalEAIDocDTO insertApprovalWithoutEAI(String headerMeta, SessionInfoVO sessionInfo, ApprovalDTO approval);

    /**
     * EAI 통합결재 결과 수신 저장 API
     *
     * @return
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 3. 2.
     */
    boolean updateReceptionApproval(Map<String, Object> approval);

    /**
     * 결재 정보 삭제
     *
     * @param docIdList
     * @return
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 3. 17.
     */
    Boolean deleteApprovals(List<Integer> docIdList);

    /**
     * 결재문서 복원 처리
     *
     * @param docId
     * @return
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @sincce : 2021. 3. 31.
     */
    Boolean updateApDocDelYn(Integer docId);

    /**
     * 결재 복원 처리
     *
     * @param docId
     * @return
     * @author : X0121127<sungbum.oh@partner.sk.com>
     * @sincce : 2021. 3. 31.
     */
    Boolean updateApApprDelYn(Integer docId);

    /**
     * AP_APPR 목록 조회
     *
     * @param docId
     * @return
     * @author : X0121126<woogon.kim1@partner.sk.com>
     * @since : 2021. 4. 7.
     */
    List<ApApprDTO> selectApApprList(Integer docId);

    List<SpecifiedApproverLineDTO> selectRequestApprovalLine(ApproverLineQueryDTO param);

    List<SpecifiedApproverLineDTO> selectPermitApprovalLine(ApproverLineQueryDTO param);

    Map<String, Object> selectApprovalInfo(Integer docId);

    List<Map<String, Object>> selectRequestDeptList(String deptId);

    List<Map<String, Object>> selectApprEmpListByDept(ApproverLineQueryDTO param);

}