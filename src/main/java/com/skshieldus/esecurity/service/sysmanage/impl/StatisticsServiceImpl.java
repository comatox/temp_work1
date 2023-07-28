package com.skshieldus.esecurity.service.sysmanage.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import com.skshieldus.esecurity.repository.sysmanage.StatisticsRepository;
import com.skshieldus.esecurity.service.sysmanage.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    /**
     * 통계 보안담당자 부서조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 01. 10
     */
    public List<Map<String, Object>> selectSecDeptsCombo(String secEmpId) {
        List<Map<String, Object>> resultList = null;

        try {
            resultList = statisticsRepository.selectSecDeptsCombo(secEmpId);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return resultList;
    }

    @Override
    public CommonXlsViewDTO selectStatisticsExcel(Map<String, Object> paramMap) {

        List<Map<String, Object>> resultList = null;
        CommonXlsViewDTO commonXlsViewDTO = new CommonXlsViewDTO();
        String menuId = String.valueOf(paramMap.getOrDefault("menuId", ""));
        String[] headerNameArr = null;
        String[] columnNameArr = null;
        Integer[] columnWidthArr = null;
        String fileName = "";

        try {

            switch (menuId) {

                // 통계 > 보안 위규 > 구성원 보안위규
                case "P07030101":

                    fileName = "구성원 보안위규";
                    resultList = statisticsRepository.selectCoEmpViolationExcel(paramMap);
                    headerNameArr = new String[]{ "지적일", "지적시간", "사번", "성명", "직위", "부문/총괄", "본부", "그룹/실", "팀", "독립파트",
                        "PL", "부서", "위규구분", "위규내용", "위규내용상세", "적발사업장", "적발건물", "적발건물상세", "적발GATE", "위규조치",
                        "시정계획서/경고장제출여부", "시정계획서/경고장제출일", "시정계획서/경고장경과일", "시정계획서/경고장처리상태" };
                    columnNameArr = new String[]{ "ofendDt", "ofendTm", "ofendEmpId", "ofendEmpNm", "ofendJwNm", "deptLv2",
                        "deptLv3", "deptLv4", "deptLv5", "deptLv6", "deptLv7", "ofendDeptNm", "ofendGbnNm",
                        "ofendDetailGbnNm", "ofendSubGbnNm", "actCompNm", "actBldgNm", "actLocateNm", "actGate",
                        "actDoNm", "corrPlanYn", "sendDtm", "passDate", "apprResultNm" };
                    columnWidthArr = new Integer[]{ 4000, 3000, 3000, 3000, 2500, 3000, 3000, 3000, 3000, 3000, 3000, 6000,
                        3000, 9000, 7500, 4000, 4000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };

                    break;

                // 통계 > 보안 위규 > 시정계획서/개선계획서 미제출 건수
                case "P07030102":

                    fileName = "시정계획서, 개선계획서 미제출 건수";
                    resultList = statisticsRepository.selectVioCorrNotSubmitExcel(paramMap);
                    headerNameArr = new String[]{ "지적일", "지적시간", "사번", "성명", "직위", "부문/총괄", "본부", "그룹/실", "팀", "독립파트",
                        "PL", "부서", "위규구분", "위규내용", "위규내용상세", "적발사업장", "적발건물", "적발건물상세", "적발GATE", "위규조치",
                        "시정계획서/경고장 제출여부", "시정계획서/경고장 제출일", "시정계획서/경고장 경과일", "시정계획서/경고장 처리상태" };
                    columnNameArr = new String[]{ "ofendDt", "ofendTm", "ofendEmpId", "ofendEmpNm", "ofendJwNm", "deptLv2",
                        "deptLv3", "deptLv4", "deptLv5", "deptLv6", "deptLv7", "ofendDeptNm", "ofendGbnNm",
                        "ofendDetailGbnNm", "ofendSubGbnNm", "actCompNm", "actBldgNm", "actLocateNm", "actGate",
                        "actDoNm", "corrPlanYn", "sendDtm", "passDate", "apprResultNm", };
                    columnWidthArr = new Integer[]{ 4000, 3000, 3000, 3000, 2500, 3000, 3000, 3000, 3000, 3000, 3000, 6000,
                        3000, 9000, 7500, 4000, 4000, 5000, 5000, 5000, 5000, 5000, 5000, 5000 };

                    break;

                // 통계 > 보안 점검 > 생활보안 자체점검 지적 건수 / 생활보안 자체점검 실시 횟수
                case "P07030201":
                case "P07030202":

                    fileName = "P07030201".equals(menuId)
                        ? "생활보안 자체점검 지적 건수"
                        : "생활보안 자체점검 실시 횟수";
                    resultList = statisticsRepository.selectSecCoEmpTeamViolationExcel(paramMap);
                    headerNameArr = new String[]{ "구분", "점검자", "점검일", "점검시간", "지적내용", "부문/총괄", "본부", "그룹/실", "팀", "해당자", };
                    columnNameArr = new String[]{ "ofendGbnNm", "regEmpFullNm", "ofendDt", "ofendTm", "ofendDetailGbnNm",
                        "deptLv2", "deptLv3", "deptLv4", "deptLv5", "ofendEmpFullNm", };
                    columnWidthArr = new Integer[]{ 7000, 9000, 3000, 3000, 6000, 3000, 3000, 3000, 3000, 3000 };

                    break;

                // 통계 > 전산저장장치 보유 현황 > 매체별 보유 현황 / 팀별 보유 현황
                case "P07030401":
                case "P07030402":

                    fileName = "P07030401".equals(menuId)
                        ? "매체별 보유 현황"
                        : "팀별 보유 현황";
                    resultList = statisticsRepository.selectStorageManageListExcelDeptStat(paramMap);
                    headerNameArr = new String[]{ "품목그룹", "관리번호", "모델명", "시리얼번호", "신청자부서", "신청자", "실사용자부서", "실사용자", "사용여부",
                        "보유여부" };
                    columnNameArr = new String[]{ "articlegroupname", "acserialno", "modelname", "serialno2", "deptNms",
                        "empNms", "useDeptNms", "useEmpNms", "usekndNm", "existkndNm" };
                    columnWidthArr = new Integer[]{ 7000, 4000, 5000, 10000, 7000, 3000, 5000, 3000, 3000, 3000 };

                    break;

                // 통계 > 물품 반입지연 > 사외 반출후 반입지연 건수
                case "P07030301":

                    fileName = "사외 반출후 반입지연 건수";
                    resultList = statisticsRepository.selectDeptInDelayExcelDeptStat(paramMap);
                    headerNameArr = new String[]{ "사업장", "반출입번호", "작성일자", "작성자", "반출일자", "반입예정일자", "반입구분", "상대처", "반출사유",
                        "반출입상태", "경과일" };
                    columnNameArr = new String[]{ "companyName", "inoutserialno", "writedate", "deptemplist", "outdate",
                        "indate", "inoutkndname", "partnerorcompanyname", "outreasonname", "finishstatename",
                        "delaycnt" };
                    columnWidthArr = new Integer[]{ 3000, 7000, 5000, 8000, 7000, 3000, 5000, 8000, 9000, 3000, 3000 };

                    break;

                // 통계 > 물품 반입지연 > 사내 건물간 반입지연 건수
                case "P07030302":

                    fileName = "사내 건물간 반입지연 건수";
                    resultList = statisticsRepository.selectBuildingMoveListExcelDeptStat(paramMap);
                    headerNameArr = new String[]{ "반출입번호", "반출일시", "부서명", "반출자", "지연시간", "건물이동상황", "물품명", "갯수", };
                    columnNameArr = new String[]{ "outserialno", "outDtm", "deptNm", "empJwNm", "outDelayYn",
                        "apprFullName", "outarticlename", "inoutcnt", };
                    columnWidthArr = new Integer[]{ 7000, 5000, 5000, 4000, 3000, 3000, 9000, 3000 };

                    break;

                default:
                    fileName = "ERROR";
                    break;
            }

            // set excel data
            commonXlsViewDTO.setFileName(fileName);
            commonXlsViewDTO.setHeaderNameArr(headerNameArr);
            commonXlsViewDTO.setColumnNameArr(columnNameArr);
            commonXlsViewDTO.setColumnWidthArr(columnWidthArr);
            commonXlsViewDTO.setDataList(resultList);
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }

        return commonXlsViewDTO;
    }

    ;
}