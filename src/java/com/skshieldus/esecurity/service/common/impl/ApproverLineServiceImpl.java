package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.*;
import com.skshieldus.esecurity.repository.common.ApproverLineRepository;
import com.skshieldus.esecurity.service.common.ApproverLineService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ApproverLineServiceImpl implements ApproverLineService {

    @Autowired
    private ApproverLineRepository approverLineRepository;

    /**
     * 결재선 조회 API
     *
     * @param apprQuery 결재선 조회 조건 DTO
     * @return ApproverLineDTO
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 02. 09.
     */
    public ApproverLineDTO selectApproverLine(ApproverLineQueryDTO apprQuery, SessionInfoVO sessionInfo) {
        ApproverLineDTO result = null;

        try {
            result = new ApproverLineDTO();
            log.info("selectApproverLine", apprQuery.toString());
            log.info(">>>> selectApproverLine IP: " + sessionInfo.getIp());
            log.info(">>>> selectApproverLine EMP_NO: " + sessionInfo.getEmpNo());
            log.info(">>>> selectApproverLine Name: " + sessionInfo.getNameKor());
            log.info(">>>> selectApproverLine sessionInfo: " + sessionInfo);

            if (apprQuery.getDocId() != null) {
                ApprovalDocDTO approvalDoc = approverLineRepository.selectApprovalDoc(apprQuery.getDocId());
                result.setApprovalDoc(approvalDoc);

                // 저장된 결재선 목록
                SavedApproverLineDTO savedApprQuery = new SavedApproverLineDTO();
                savedApprQuery.setDocId(apprQuery.getDocId());

                // 저장된 요청부서 결재선 목록
                savedApprQuery.setApprDeptGbn("1");
                List<SavedApproverLineDTO> savedApproverLine =
                    approverLineRepository.selectSavedApproverLine(savedApprQuery);
                result.setSavedRequestApproverLine(savedApproverLine);

                // 저장된 허가부서 결재선 목록
                savedApprQuery.setApprDeptGbn("2");
                List<SavedApproverLineDTO> savedPermitApproverLine =
                    approverLineRepository.selectSavedApproverLine(savedApprQuery);
                result.setSavedPermitApproverLine(savedPermitApproverLine);
            }

            String[] menusMustApprvTeamLeader = { "P01020102", "P01010202", "P08010608", "P08011603", "P01020801",
                "P01040107", "P02010312", "P01011201", "P02021502", "P02021702", "P02021802", "P02021602",
                "P02021902", "P02022002", "P02011002", "P08010616", "P08010610", "P08010110", "P02022401",
                "P03020813", "P03010407", "P03020101", "P01060502", "P08010614", "P03010402", "P08010103",
                "P08010609", "P01010208", "P01010206", "P02021802_01", "P02011102", "P02021202", "P01040105",
                "P01040106", "P02010102", "P01040201", "P03010302", "P03010601", "P01010801", "P08010612",
                "P02010402" };
            String[] menusMustInout = { "P08011605", "P08010103", "P08010612" };    // 업체물품 반출신청, 자산반출입 신청, 반입불요 전환(해외/외부) 요구사항 REQ-MSA-F0004
            if (Arrays.asList(menusMustApprvTeamLeader).contains(apprQuery.getMenuId())) {
                /*
                 * 스키마(menuId)가 반입불요전환(외부/해외) 이고 inoutknd 가 반입불요(무상), 반입불요(유상)인경우
                 * "1", "반입필요", "2", "반입불요(무상)", "3", "반입불요(유상)"
                 * 저장된 요청부서 결재선 목록에
                 * 신청자 조직(deptId)의 팀장/PL 과 상위권자 담당/그룹장이 자동으로 선택되어지도록한다.
                 *
                 *
                 * P08010103 자산반출입 신청,
                 * */
                List<SpecifiedApproverLineDTO> requestSpecifiedApproverLine = null;
                //				if ("P08010103".equals(apprQuery.getMenuId()) && "2".equals(apprQuery.getInoutknd())) { // 자산반출입 신청 반입불요일 때 1단계-PL/팀장,2단계 담당/그룹장
                //					// 로그인 사용자로 팀장, 상위부서 담당을 순서대로 조회
                //					requestSpecifiedApproverLine =
                //							approverLineRepository.selectApprovalLineReqDeptPLAndDamdang(sessionInfo.getEmpNo());
                //					result.setRequestSpecifiedApproverLine(requestSpecifiedApproverLine);
                //				}
                log.info("담당,팀장,팀장지정자 조회: menuId: ", apprQuery.getMenuId());
                log.info("담당,팀장,팀장지정자 조회: deptId: ", apprQuery.getDeptId());
                // 담당,팀장,팀장지정자 조회
                // 초기값은 로그인 사용자 부서로 조회
                if (requestSpecifiedApproverLine == null || requestSpecifiedApproverLine.size() <= 0) {
                    requestSpecifiedApproverLine =
                        approverLineRepository.selectApprovalLineReqDeptTeamLeader(apprQuery);
                    result.setRequestSpecifiedApproverLine(requestSpecifiedApproverLine);
                }
            }
            else {
                log.info("요청부서 부서별 결재선 지정자 목록: ", apprQuery.getMenuId());
                // 요청부서 부서별 결재선 지정자 목록
                List<SpecifiedApproverLineDTO> requestSpecifiedApproverLine =
                    approverLineRepository.selectApprovalLineRequestLine(apprQuery);
                result.setRequestSpecifiedApproverLine(requestSpecifiedApproverLine);
            }

            if (Arrays.asList(menusMustInout).contains(apprQuery.getMenuId()) && apprQuery.getArticlekndno() != null) {
                /*
                 * 자산반출입 허가부서는 AP_APPR_DEF_PIOS 테이블에서 가져오는 것으로 한다.
                 */
                // 자산반출입 허가부서 부서별 결재선 지정자 목록
                List<SpecifiedApproverLineDTO> inoutPermitApprovalLine =
                    approverLineRepository.selectInoutPermitApprovalLine(apprQuery);
                result.setPermitSpecifiedApproverLine(inoutPermitApprovalLine);
            }
            else if ("P03010402".equals(apprQuery.getMenuId())) {
                /*
                 * 시정계획서 승인시 허가부서는 SC_DETL_EMP 테이블에서 가져오는 것으로 변경함: 2015-11-23 by JSH
                 */
                // 시정계획서 허가부서 부서별 결재선 지정자 목록
                List<SpecifiedApproverLineDTO> permitCorrPlanSpecifiedApproverLine =
                    approverLineRepository.selectApprovalLinePermitLineCorrPlan(apprQuery);
                result.setPermitSpecifiedApproverLine(permitCorrPlanSpecifiedApproverLine);
            }
            else {
                // 허가부서 부서별 결재선 지정자 목록
                List<SpecifiedApproverLineDTO> permitSpecifiedApproverLine =
                    approverLineRepository.selectApprovalLinePermitLine(apprQuery);
                result.setPermitSpecifiedApproverLine(permitSpecifiedApproverLine);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return result;
    }

    /**
     * 결재선 사원 목록 조회 API (setApprLine)
     *
     * @param apprQuery 결재선 조회 조건 DTO
     * @return List<SpecifiedApproverLineDTO>
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 03. 25.
     */
    @Override
    public List<SpecifiedApproverLineDTO> selectApprEmpListByDept(ApproverLineQueryDTO apprQuery, SessionInfoVO sessionInfo) {
        return approverLineRepository.selectApprEmpListByDept(apprQuery);
    }

    /**
     * 결재선 사원 목록 조회 API (setApprLine, setApprLineInOutTeam, setApprLinePL_JangOnly)
     *
     * @param apprQuery 결재선 조회 조건 DTO
     * @return List<SpecifiedApproverLineDTO>
     *
     * @author : X0121127 <sungbum.oh@partner.sk.com>
     * @since : 2021. 03. 25.
     */
    @Override
    public List<SpecifiedApproverLineDTO> selectApprovalLineReqDeptTeamLeader(ApproverLineQueryDTO apprQuery, SessionInfoVO sessionInfo) {
        return approverLineRepository.selectApprovalLineReqDeptTeamLeader(apprQuery);
    }

    /**
     * 요청부서 부서별 결재선 지정자 목록 조회 API
     *
     * @param apprQuery 결재선 조회 조건 DTO
     * @return List<SpecifiedApproverLineDTO>
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 03. 18.
     */
    @Override
    public List<SpecifiedApproverLineDTO> selectApprovalLineRequestLine(ApproverLineQueryDTO apprQuery, SessionInfoVO sessionInfo) {
        List<SpecifiedApproverLineDTO> result = null;
        try {
            if (StringUtils.isEmpty(apprQuery.getDeptId())) {
                log.info(">>>> selectApproverLine getDeptId: " + apprQuery.getDeptId());
            }
            String[] menusMustApprvTeamLeader = { "P01020102", "P01010202", "P08010608", "P08011603", "P01020801",
                "P01040107", "P02010312", "P01011201", "P02021502", "P02021702", "P02021802", "P02021602",
                "P02021902", "P02022002", "P02011002", "P08010616", "P08010610", "P08010110", "P02022401",
                "P03020813", "P03010407", "P03020101", "P01060502", "P08010614", "P03010402", "P08010103",
                "P08010609", "P01010208", "P01010206", "P02021802_01", "P02011102", "P02021202", "P01040105",
                "P01040106", "P02010102", "P01040201", "P03010302", "P03010601", "P01010801", "P08010612",
                "P02010402" };
            if (Arrays.asList(menusMustApprvTeamLeader).contains(apprQuery.getMenuId())) {

                log.info("담당,팀장,팀장지정자 조회: menuId: ", apprQuery.getMenuId());
                log.info("담당,팀장,팀장지정자 조회: deptId: ", apprQuery.getDeptId());
                // 담당,팀장,팀장지정자 조회
                // 초기값은 로그인 사용자 부서로 조회
                result = approverLineRepository.selectApprovalLineReqDeptTeamLeader(apprQuery);
            }
            else {
                log.info("요청부서 부서별 결재선 지정자 목록: ", apprQuery.getMenuId());
                // 요청부서 부서별 결재선 지정자 목록
                result = approverLineRepository.selectApprovalLineRequestLine(apprQuery);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

    /**
     * 자산반출입 허가부서결재자 현황 조회 API
     *
     * @param queryDTO
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 28.
     */
    @Override
    public List<PermitSpecifiedApproverDTO> selectInoutPermitSpecifiedApproverList(PermitSpecifiedApproverQueryDTO queryDTO) {
        List<PermitSpecifiedApproverDTO> result = null;
        try {
            result = approverLineRepository.selectInoutPermitSpecifiedApproverList(queryDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

    /**
     * 자산반출입 허가부서결재자 조회 API
     *
     * @param apprdefNo
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 04. 28.
     */
    @Override
    public PermitSpecifiedApproverDTO selectInoutPermitSpecifiedApprover(Integer apprdefNo) {
        PermitSpecifiedApproverDTO result = null;
        try {
            result = approverLineRepository.selectInoutPermitSpecifiedApprover(apprdefNo);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

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
    @Override
    public int insertInoutPermitSpecifiedApprover(SessionInfoVO sessionInfo, PermitSpecifiedApproverDTO permitSpecifiedApproverDTO) {
        int result = 0;
        try {
            permitSpecifiedApproverDTO.setCrtBy(sessionInfo.getEmpNo());
            result = approverLineRepository.insertInoutPermitSpecifiedApprover(permitSpecifiedApproverDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

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
    @Override
    public int updateInoutPermitSpecifiedApprover(SessionInfoVO sessionInfo, PermitSpecifiedApproverDTO permitSpecifiedApproverDTO) {
        int result = 0;
        try {
            permitSpecifiedApproverDTO.setModBy(sessionInfo.getEmpNo());
            result = approverLineRepository.updateInoutPermitSpecifiedApprover(permitSpecifiedApproverDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

    /* 보안양식6종은 해당 사업장은 허가라인이 없는 경우 이천의 허가라인 사용 */
    final String[] MENUIDS = { "P02021502", "P02021602", "P02021702", "P02021802", "P02021902", "P02022002" };

    @Override
    public List<SpecifiedApproverLineDTO> selectRequestLineList(ApproverLineQueryDTO paramDTO) {
        List<SpecifiedApproverLineDTO> resultList = null;

        try {
            if (paramDTO.getMenuId() != null) {
                resultList = fetchRequestLine(paramDTO);

                if (Arrays.stream(MENUIDS).anyMatch(paramDTO.getMenuId()::equals) && resultList.isEmpty()) {
                    paramDTO.setCompId("1101000001");
                }

                resultList = fetchRequestLine(paramDTO);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    private List<SpecifiedApproverLineDTO> fetchRequestLine(ApproverLineQueryDTO paramDTO) {
        List<SpecifiedApproverLineDTO> resultList = null;

        try {
            resultList = approverLineRepository.selectApprovalLineRequestLine(paramDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    @Override
    public List<SpecifiedApproverLineDTO> selectPermitLineList(ApproverLineQueryDTO paramDTO) {
        List<SpecifiedApproverLineDTO> resultList = null;

        try {
            if (paramDTO.getMenuId() != null) {
                resultList = fetchPermitLine(paramDTO);

                if (Arrays.stream(MENUIDS).anyMatch(paramDTO.getMenuId()::equals) && resultList.isEmpty()) {
                    paramDTO.setCompId("1101000001"); // 이천 사업장
                    resultList = fetchPermitLine(paramDTO);
                }
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    private List<SpecifiedApproverLineDTO> fetchPermitLine(ApproverLineQueryDTO paramDTO) {
        List<SpecifiedApproverLineDTO> resultList = null;

        try {
            if ("P03010402".equals(paramDTO.getMenuId())) {
                resultList = approverLineRepository.selectApprovalLinePermitLineCorrPlan(paramDTO);
            }
            else {
                resultList = approverLineRepository.selectApprovalLinePermitLine(paramDTO);
            }
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.toString());
        }

        return resultList;
    }

    /**
     * 요청부서 결재선-사번 기준 팀장 및 상위부서 담당을 순서대로 조회 1차: 팀장/PL, 2차: 담당 인 경우에 활용
     *
     * @param empId
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2021. 12. 09.
     */
    @Override
    public List<SpecifiedApproverLineDTO> selectReqLineDeptPLAndDamdangByEmpId(String empId) {
        List<SpecifiedApproverLineDTO> requestSpecifiedApproverLine = null;
        // 1단계-PL/팀장,2단계 담당/그룹장
        // 로그인 사용자로 팀장, 상위부서 담당을 순서대로 조회
        requestSpecifiedApproverLine = approverLineRepository.selectApprovalLineReqDeptPLAndDamdang(empId);

        return requestSpecifiedApproverLine;
    }

}
