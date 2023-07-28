package com.skshieldus.esecurity.repository.common;

import com.skshieldus.esecurity.model.common.*;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface IoCompRepository {

    // 외부업체 회원 목록 조회
    List<Map<String, Object>> selectIoEmpList(Map<String, Object> paramMap);

    Integer selectIoEmpListCnt(Map<String, Object> paramMap);

    // 외부업체 회원 정보 조회
    IoEmpDTO selectIoEmp(String ioEmpId);

    // 외부업체 목록 조회
    List<IoCompDTO> selectIoCompList(IoCompSearchDTO paramDTO);

    // 외부업체 목록 건수조회
    Integer selectIoCompListCnt(IoCompSearchDTO paramDTO);

    // 외부업체 정보 조회
    IoCompDTO selectIoComp(String ioCompId);

    // 외부인 차량정보 목록 조회
    List<IoEmpCarInfoViewDTO> selectIoEmpCarInfoViewList(IoEmpCarInfoViewSearchDTO ioEmpCarInfoViewSearchDTO);

    // 도급업체 회원 목록 조회
    List<Map<String, Object>> selectIoEmpSubcontList(Map<String, Object> paramMap);

    // 도급업체 회원 목 록건수 조회
    Integer selectIoEmpSubcontListCnt(Map<String, Object> paramMap);

}