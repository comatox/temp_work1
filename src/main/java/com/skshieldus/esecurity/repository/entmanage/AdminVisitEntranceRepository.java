package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminVisitEntranceRepository {

    // 업체정보 정정 신청 현황 - 목록 조회
    List<Map<String, Object>> selectCompInfoChgReqList(HashMap<String, Object> paramMap);

    // 업체정보 정정 신청 현황 - 목록 총갯수 조회
    int selectCompInfoChgReqListCnt(HashMap<String, Object> paramMap);

    // 업체정보 정정 신청 현황 - 상세조회
    Map<String, Object> selectCompInfoChgReqView(Integer ioCompApplNo);

    // 업체정보 정정 신청 현황 - 승인/반려
    int executeCompInfoChgReqUpdate(HashMap<String, Object> paramMap);

    Map<String, Object> coorpVendroView(HashMap<String, Object> paramMap);

    int coorpVendroCompUpdate(Map<String, Object> dataMap);

    int dmCoorpVendroComp_NEW_ES_SN();

    // 여권변경 신청 현황 - 목록 조회
    List<Map<String, Object>> selectPassportChgReqList(HashMap<String, Object> paramMap);

    // 여권변경 신청 현황 - 목록 총갯수 조회
    int selectPassportChgReqListCnt(HashMap<String, Object> paramMap);

    // 여권변경 신청 현황 - 상세 조회
    Map<String, Object> selectPassportChgReqView(Integer passportApplNo);

    // 여권변경 신청 현황 - 승인/반려
    int executePassportChgReqUpdate(HashMap<String, Object> paramMap);

    int dmIoEmpPassportNoUpdate(HashMap<String, Object> paramMap);

    int dmOffLimitsIoEmpChgHistInsert(HashMap<String, Object> paramMap);

    // 소속업체 이직 신청현황 - 목록조회
    List<Map<String, Object>> selectCompChgReqList(HashMap<String, Object> paramMap);

    // 소속업체 이직 신청현황 - 목록 총갯수 조회
    int selectCompChgReqListCnt(HashMap<String, Object> paramMap);

    // 소속업체 이직 신청현황 - 상세조회
    Map<String, Object> selectCompChgReqView(Integer compApplNo);

    // 소속업체 이직 신청현황 - 승인/반려
    int executeCompChgReqUpdate(HashMap<String, Object> paramMap);

    int insertOffLimitsIoEmpChgHist(Map<String, Object> paramMap);

    int updateVisitIoEmpCompId(Map<String, Object> paramMap);

    int updateVisitIoInoutCompId(Map<String, Object> paramMap);

    int insertOffLimitsCompChgExprHist(Map<String, Object> paramMap);

    Map<String, Object> selectOffLimitsExprHistSeq(Map<String, Object> paramMap);

}
