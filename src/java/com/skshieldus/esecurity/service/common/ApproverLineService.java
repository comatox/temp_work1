package com.skshieldus.esecurity.service.common;

import com.skshieldus.esecurity.model.common.*;
import java.util.List;

public interface ApproverLineService {

    /**
     * 결재선 조회 API
     *
     * @param apprQuery 결재선 조회 조건 DTO
     * @return ApproverLineDTO
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 02. 09.
     */
    ApproverLineDTO selectApproverLine(ApproverLineQueryDTO apprQuery, SessionInfoVO sessionInfo);

    /**
     * 요청부서 부서별 결재선 지정자 목록 조회 API
     *
     * @param apprQuery 결재선 조회 조건 DTO
     * @param sessionInfo 세션정보 DTO
     * @return ResponseModel<List < SpecifiedApproverLineDTO>>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 03. 18.
     */
    List<SpecifiedApproverLineDTO> selectApprovalLineRequestLine(ApproverLineQueryDTO apprQuery, SessionInfoVO sessionInfo);

    /**
     * 결재선 사원 목록 조회 API (setApprLine)
     *
     * @param apprQuery 결재선 조회 조건 DTO
     * @return List<SpecifiedApproverLineDTO>
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 03. 25.
     */
    List<SpecifiedApproverLineDTO> selectApprEmpListByDept(ApproverLineQueryDTO apprQuery, SessionInfoVO sessionInfo);

    /**
     * 결재선 사원 목록 조회 API (setApprLine, setApprLineInOutTeam, setApprLinePL_JangOnly)
     *
     * @param apprQuery 결재선 조회 조건 DTO
     * @return List<SpecifiedApproverLineDTO>
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 03. 25.
     */
    List<SpecifiedApproverLineDTO> selectApprovalLineReqDeptTeamLeader(
        ApproverLineQueryDTO apprQuery,
        SessionInfoVO sessionInfo
    );

    /**
     * 자산반출입 허가부서결재자 현황 조회 API
     *
     * @param queryDTO
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 28.
     */
    List<PermitSpecifiedApproverDTO> selectInoutPermitSpecifiedApproverList(PermitSpecifiedApproverQueryDTO queryDTO);

    /**
     * 자산반출입 허가부서결재자 조회 API
     *
     * @param apprdefNo
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 28.
     */
    PermitSpecifiedApproverDTO selectInoutPermitSpecifiedApprover(Integer apprdefNo);

    /**
     * 자산반출입 허가부서결재자 신규 등록 API
     *
     * @param sessionInfo
     * @param permitSpecifiedApproverDTO
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 28.
     */
    int insertInoutPermitSpecifiedApprover(
        SessionInfoVO sessionInfo,
        PermitSpecifiedApproverDTO permitSpecifiedApproverDTO
    );

    /**
     * 자산반출입 허가부서결재자 수정 API
     *
     * @param sessionInfo
     * @param permitSpecifiedApproverDTO
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 28.
     */
    int updateInoutPermitSpecifiedApprover(
        SessionInfoVO sessionInfo,
        PermitSpecifiedApproverDTO permitSpecifiedApproverDTO
    );

    /**
     * 결재선-요청부서 조회
     *
     * @param paramDTO
     * @return SpecifiedApproverLineDTO
     *
     * @author : X0121126 <woogon.kim@partner.sk.com>
     * @since : 2021. 8. 25.
     */
    List<SpecifiedApproverLineDTO> selectRequestLineList(ApproverLineQueryDTO paramDTO);

    /**
     * 결재선-허가부서 조회
     *
     * @param paramDTO
     * @return
     *
     * @author : X0121126 <woogon.kim@partner.sk.com>
     * @since : 2021. 8. 25.
     */
    List<SpecifiedApproverLineDTO> selectPermitLineList(ApproverLineQueryDTO paramDTO);

    /**
     * 요청부서 결재선-사번 기준 팀장 및 상위부서 담당을 순서대로 조회 1차: 팀장/PL, 2차: 담당 인 경우에 활용
     *
     * @param empId
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    List<SpecifiedApproverLineDTO> selectReqLineDeptPLAndDamdangByEmpId(String empId);

}
