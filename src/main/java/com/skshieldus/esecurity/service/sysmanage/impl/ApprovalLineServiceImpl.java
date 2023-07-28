package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalLineDefDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalLineDefSearchDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalStateDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalStateSearchDTO;
import com.skshieldus.esecurity.repository.sysmanage.ApprovalLineRepository;
import com.skshieldus.esecurity.service.sysmanage.ApprovalLineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApprovalLineServiceImpl implements ApprovalLineService {

    @Autowired
    private ApprovalLineRepository approvalLineRepository;

    //결재선 미 준수 현황 조회 엑셀 다운로드
    @Override
    public CommonXlsViewDTO selectApprovalStateExcel(ApprovalStateSearchDTO approvalStateSearchDTO) {
        List<ApprovalStateDTO> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        commonXlsViewDTO.setFileName("결재선 미 준수 현황 ");
        try {
            approvalStateSearchDTO.setSchemaNms(Arrays.asList(approvalStateSearchDTO.getSchemaNm().split(",")));
            resultList = approvalLineRepository.selectApprovalStateExcel(approvalStateSearchDTO);
            for (int i = 0; i < resultList.size(); i++) {
                ApprovalStateDTO detail = resultList.get(i);
                Integer gradeOrg = detail.getGradeOrg();
                Integer gradeReal = detail.getGradeReal();
                String schemaNm = detail.getSchemaNm();
                String docNm = detail.getDocNm();
                String apprAllEmp = detail.getApprAllEmp();
                if (gradeOrg == 3) {
                    detail.setGradeOrgNm("담당");
                }
                else {
                    detail.setGradeOrgNm("팀장/PL");
                }
                switch (schemaNm) {
                    case "ORG_VISIT":
                    case "BUILD_EMPCARD_APPL":
                    case "BUILD_PASS_APPL":
                    case "CAR_PASS_TEMP":
                    case "PHOTO_ING_APPL":
                        detail.setPermitGbnNm("하이스텍");
                        break;
                    case "INOUT":
                        detail.setPermitGbnNm("허가부서");
                        break;
                    case "ITEQMT_RGAD_APPL":
                    case "ITEQMT_REISSUE_APPL":
                        detail.setPermitGbnNm("SK 쉴더스");
                        break;
                    default:
                        if (gradeOrg < gradeReal) {
                            detail.setPermitGbnNm("산업보안");
                        }
                        else {
                            detail.setPermitGbnNm("없음");
                        }
                        break;
                }
                String remark = "";

                if (gradeOrg < gradeReal) {
                    remark = "[결재선 미 준수]";
                }

                if ("CORR_PLAN".equals(schemaNm) && "경고장 개선계획서 작성".equals(docNm)) {
                    remark += " " + apprAllEmp;
                }
                detail.setRemark(remark);
            }
            // set header names
            String[] headerNameArr = { "번호", "항목", "최종 승인권자 직책", "허가부서", "신청일", "최종 승인자", "최종 승인자 직책", "최종 승인일",
                "허가부서 승인자", "허가부서 승인자 직책", "허가부서 승인일", "팀장 지정자 여부", "기타" };

            commonXlsViewDTO.setHeaderNameArr(headerNameArr);
            // set column names (data field name)
            String[] columnNameArr = { "rowNum", "docNm", "gradeOrgNm", "permitGbnNm", "crtDtm", "apprEmpNm",
                "apprJcNm", "apprDtm", "permitEmpNm", "permitJcNm", "permitDtm", "appointYn", "remark" };
            commonXlsViewDTO.setColumnNameArr(columnNameArr);

            // set column width
            Integer[] columnWidthArr = { 1600, 9600, 4500, 3000, 3000, 3000, 4000, 5000, 4000, 5000, 5000, 4000, 8000 };

            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);

            // set excel data
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return commonXlsViewDTO;
    }

    // 결재선 관리 목록 조회
    @Override
    public List<ApprovalLineDefDTO> selectApprLineManageList(ApprovalLineDefSearchDTO searchDTO) {
        List<ApprovalLineDefDTO> resultList = null;
        try {
            resultList = approvalLineRepository.selectApprLineManageList(searchDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return resultList;
    }

    // 결재선 관리 메뉴 목록 조회
    @Override
    public List<Map<String, Object>> selectApprMenuList(String searchMenuNm) {
        List<Map<String, Object>> resultList = null;
        try {
            resultList = approvalLineRepository.selectApprMenuList(searchMenuNm);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return resultList;
    }

    // 결재선 관리 부서 목록 조회
    @Override
    public List<Map<String, Object>> selectApprDeptList(Map<String, Object> paramMap) {
        List<Map<String, Object>> resultList = null;
        try {
            resultList = approvalLineRepository.selectApprDeptList(paramMap);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return resultList;
    }

    // 결재선 관리 상세 조회
    @Override
    public ApprovalLineDefDTO selectApprLineManage(Integer defSeq) {
        ApprovalLineDefDTO resultList = null;
        try {
            resultList = approvalLineRepository.selectApprLineManage(defSeq);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return resultList;
    }

    // 결재선 관리 등록
    @Override
    public Integer insertApprLineManage(ApprovalLineDefDTO approvalLineDefDTO) {
        Integer result = null;
        try {
            result = approvalLineRepository.insertApprLineManage(approvalLineDefDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

    // 결재선 관리 수정
    @Override
    public Integer updateApprLineManage(ApprovalLineDefDTO approvalLineDefDTO) {
        Integer result = null;
        try {
            result = approvalLineRepository.updateApprLineManage(approvalLineDefDTO);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

}