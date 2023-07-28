package com.skshieldus.esecurity.repository.sysmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface StatisticsRepository {

    List<Map<String, Object>> selectSecDeptsCombo(String secEmpId);

    List<Map<String, Object>> selectCoEmpViolationExcel(Map<String, Object> paramMap);

    List<Map<String, Object>> selectVioCorrNotSubmitExcel(Map<String, Object> paramMap);

    List<Map<String, Object>> selectSecCoEmpTeamViolationExcel(Map<String, Object> paramMap);

    List<Map<String, Object>> selectStorageManageListExcelDeptStat(Map<String, Object> paramMap);

    List<Map<String, Object>> selectDeptInDelayExcelDeptStat(Map<String, Object> paramMap);

    List<Map<String, Object>> selectBuildingMoveListExcelDeptStat(Map<String, Object> paramMap);

}