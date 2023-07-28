package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface EntManageCommonRepository {

    // 신청대상자 상세 조회(출입증신청NO[PASS_APPL_NO] 이용)
    Map<String, Object> selectPassReceipt(Integer passApplNo);

    // 신청대상자 상세 조회(출입건물등록신청번호[PASS_BLDG_APPL_NO] 이용)
    Map<String, Object> selectPassReceiptByBldg(Integer passBldgApplNo);

    // 출입증-카드키_통합아이디(SHIXXXXXXX) 조회
    String selectPassIDcardId(Map<String, Object> paramMap);

    // 출입건물 목록 조회(Old)
    List<Map<String, Object>> selectOldPassBuildingList(Map<String, Object> paramMap);

    // 출입건물 목록 조회(New)
    List<Map<String, Object>> selectNewPassBuildingList(Map<String, Object> paramMap);

    // 출입증 신청 시 건물정보 목록 조회
    List<Map<String, Object>> selectPassRequestCoBldgList(String[] params);

    // Hystec Procedure 호출

    // 상시출입증 정지및 제한 여부 조회
    Map<String, Object> selectPassInsStopDenyInfo(Map<String, Object> paramMap);

    // 상시출입증 보안교육 현황 상세 조회
    Map<String, Object> selectPassSecEdu(Integer passApplNo);

    // IF_IDCARD 등록
    int insertIfIdcard(Map<String, Object> paramMap);

    // SMS 정보 조회
    Map<String, Object> selectSmsInfo(Map<String, Object> paramMap);

    // 메일 결재정보 조회
    Map<String, String> selectMailApprCoEmp(String docId);

    // 메일 결재문서 명 조회
    String selectMailDocNm(Map<String, Object> paramMap);

    // 메일 외부사용자 정보 조회
    Map<String, String> selectMailIoEmp(String ioEmpId);

    // 메일 구성원 정보 조회
    Map<String, String> selectMailApplCoEmp(String empId);

    // 캠퍼스별 반입건물 목록 조회
    List<Map<String, Object>> selectCompGateList(String compId);

    // 외부인 목록 조회
    List<Map<String, Object>> selectIoEmpExtList(Map<String, Object> paramMap);

    // 외부인 체크 - 사진 등록
    String selectReserveVisitPhotoChk(String ioEmpId);

    // 외부인 체크 - 시정공문/시정계획서 승인 여부
    Map<String, String> selectReserveVisitCorrPlan(String ioEmpId);

    // 외부인 체크 - 출입제한 여부
    Map<String, String> selectIoEmpDenyYn(String ioEmpId);

    // 외부인 체크 - 재직정보
    Map<String, String> selectReserveVisitNameChk(String ioEmpId);

    // 카카오메세지 템플릿 조회
    Map<String, String> selectKakaoMsgForm(String templateCode);

    // 빌딩 목록 조회
    List<Map<String, Object>> selectBuildingList(Map<String, Object> paramMap);

    // 외부인 목록 (dmIoEmpEnterAllCompList)
    List<Map<String, Object>> selectIoEmpEnterAllCompList(Map<String, Object> paramMap);

    // 외부인 목록 건수 (dmIoEmpEnterAllCompList)
    Integer selectIoEmpEnterAllCompListCnt(Map<String, Object> paramMap);

    // Send SMS
    Integer insertSendSMSNew(Map<String, Object> paramMap);

    // Insert send sms log
    Integer insertSendSMSLog(Map<String, Object> paramMap);

}
