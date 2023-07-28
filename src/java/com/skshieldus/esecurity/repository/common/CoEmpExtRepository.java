package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.CoEmpDTO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CoEmpExtRepository {

    // 협력업체 사원목록 조회
    List<CoEmpDTO> selectCoEmpExtList(Map<String, Object> paramMap);

    // 협력업체 목록건수 조회
    int selectCoEmpExtListCnt(Map<String, Object> paramMap);

    // 지번주소 목록 조회
    List<Map<String, Object>> searchZipCodeList(Map<String, Object> paramMap);

    // 지번주소 목록건수 조회
    int searchZipCodeListCnt(Map<String, Object> paramMap);

    // 도로명주소 목록 조회
    List<Map<String, Object>> searchRoadZipCodeList(Map<String, Object> paramMap);

    // 도로명주소 목록건수 조회
    int searchRoadZipCodeListCnt(Map<String, Object> paramMap);
}
