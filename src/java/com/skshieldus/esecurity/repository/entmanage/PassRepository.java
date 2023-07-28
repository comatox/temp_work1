package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface PassRepository {

    // 상시출입증 접수현황 목록 조회
    List<Map<String, Object>> selectRegularPassList(Map<String, Object> paramMap);

    // 상시출입증 접수현황 목록 건수 조회
    Integer selectRegularPassListCnt(Map<String, Object> paramMap);

    // 상시출입증 제재여부 조회
    Map<String, Object> selectRegularPassChkRestrict(Integer passApplNo);

    // Update IO_PASS.REST_YN
    Integer updateRegularPassRestrictRestYn(Integer passApplNo);

    // INSERT 제재이력정보
    Integer insertRegularPassRestrictHist(Integer passApplNo);

    // Update IO_PASS.REST_CHK
    Integer updateRegularPassRestrictRestChk(Integer passApplNo);

    // 상시출입증 접수현황 정보 수정
    Integer updateRegularPass(Map<String, Object> paramMap);

    // 출입증 정보 조회
    Map<String, Object> selectPassInfoViewForIDCardIF(Integer passApplNo);

    // 출입증 기존 출입일자 정보 조회
    Map<String, Object> selectPassOldIODate(Integer passApplNo);

    // 상시출입증 관리 목록 조회
    List<Map<String, Object>> selectRegularPassMngList(Map<String, Object> paramMap);

    // 상시출입증 관리 목록 건수 조회
    Integer selectRegularPassMngListCnt(Map<String, Object> paramMap);

    // 상시출입증 관리 상세 조회
    Map<String, Object> selectRegularPassMng(Integer passApplNo);

    // 상시출입증 관리 변경 이력 목록 조회
    List<Map<String, Object>> selectRegularPassMngChangeHistory(String cardNo);

    // 상시출입증 강제 만료 정보 조회
    Map<String, String> selectRegularPassMngExpireInfo(Map<String, Object> paramMap);

    // IO_PASS_MAIL_HIST 테이블 삭제
    Integer deletePassRemoveMailHist(String ioEmpId);

    // IO_PASS 테이블 강제만료 필드 업데이트
    Integer updatePassReceiptMngExpireInfo(Map<String, Object> paramMap);

    // IO_PASS_EXPR_HIST 테이블 등록
    Integer insertPassInsPassExprHist(Map<String, Object> paramMap);

    // 상시출입증 1달 연장 기간 조회
    Map<String, Object> selectRegularPassMngExtInfo(Integer passApplNo);

    // 상시출입증 연장정보 등록 - IO_PASS
    Integer insertRegularPassMngExtension(Map<String, Object> paramMap);

    // 상시출입증 연장정보 등록 - IO_PASS_EXTN
    Integer insertRegularPassMngExtn(Map<String, Object> paramMap);

    // 상시출입증 연장정보 등록 - IF_IDCARD
    Integer insertRegularPassMngExtnIdcard(Map<String, Object> paramMap);

    // 사전 정지예외 신청현황 목록 조회
    List<Map<String, Object>> selectRegularPassExcptList(Map<String, Object> paramMap);

    // 사전 정지예외 신청현황 목록 건수 조회
    Integer selectRegularPassExcptListCnt(Map<String, Object> paramMap);

    // 사전 정지예외 신청현황 상세 조회
    Map<String, Object> selectRegularPassExcptDetail(Integer excptApplNo);

    // 사전 정지예외 신청현황 정보 조회 (결재 상신 시)
    Map<String, Object> selectRegularPassExcpt(Integer excptApplNo);

    // 사전 정지예외 신청현황 정보 수정
    Integer updateRegularPassExcpt(Map<String, Object> paramMap);

    // IF_IO_PASS_EXCPT 건수
    Integer selectIfIoPassExcptCnt(Integer excptApplNo);

    // IF_IO_PASS_EXCPT 등록
    Integer insertIfIoPassExcpt(Integer excptApplNo);

    // 사전 정지예외 신청 기본정보 조회
    Map<String, Object> selectRegularPassExcptBasicInfo(Integer excptApplNo);

    // 사후 정지예외 신청현황 목록 조회
    List<Map<String, Object>> selectRegularPassCancelList(Map<String, Object> paramMap);

    // 사후 정지예외 신청현황 목록 건수 조회
    Integer selectRegularPassCancelListCnt(Map<String, Object> paramMap);

    // 사후 정지예외 신청현황 정보 조회
    Map<String, Object> selectRegularPassCancel(Integer cancelApplNo);

    // 사후 정지예외 신청 IDCARD ID 조회
    Map<String, Object> selectPassCancelBasicInfo(Integer cancelApplNo);

    // 사후 정지예외 신청현황 정보 수정
    Integer updateRegularPassCancel(Map<String, Object> paramMap);

    // IO_PASS_EXPR_HIST 등록
    Integer insertIoPassExprHist(Map<String, Object> paramMap);

    // IO_PASS_EXPR_CANCEL 수정
    Integer insertIoPassExprCancel(Map<String, Object> paramMap);

    // 도급업체 인력변경 신청현황 목록 조회
    List<Map<String, Object>> selectInsSubcontMoveList(Map<String, Object> paramMap);

    // 도급업체 인력변경 신청현황 목록 건수 조회
    Integer selectInsSubcontMoveListCnt(Map<String, Object> paramMap);

    // 도급업체 인력변경 신청현황 상세 정보 조회
    Map<String, Object> selectInsSubcontMove(Integer moveApplNo);

    // 도급업체 인력변경 신청 정보(변경할 인력) 목록 조회
    List<Map<String, Object>> selectInsSubcontMoveManList(Integer moveApplNo);

    // 도급업체 인력변경 신청현황 정보 수정
    Integer updateInsSubcontMove(Map<String, Object> paramMap);

    // Update IO_EMP_COM_MOVE_MAN
    Integer updateInsSubcontMoveMan(Map<String, Object> paramMap);

    // Update IO_EMP
    Integer updateIoEmpCompId(Map<String, Object> paramMap);

    // Update IO_INOUTWRITE (업체물품)
    Integer updateIoInoutCompId(Map<String, Object> paramMap);

    // Select NEW_PASS_APPL_NO AND OLD_PASS_APPL_NO
    Map<String, Object> selectInsSubcontMovePassApplNo(Map<String, Object> paramMap);

    // Select IO_PASS Info
    Map<String, Object> selectIoPassInfo(Map<String, Object> paramMap);

    // Insert IO_PASS (for InsSubcontMove)
    Integer insertSubcontMoveIoPass(Map<String, Object> paramMap);

    // Insert IF_IDCARD (for InsSubcontMove)
    Integer insertSubcontMoveIfIdcard(Map<String, Object> paramMap);

    // Select InsSubcontMoveIfEntrance
    Map<String, Object> selectSubcontMoveIfEntrance(Map<String, Object> paramMap);

    // Insert HEIF_ENTRANCE_INFO
    Integer insertSubcontMoveIfEntranceHn(Map<String, Object> paramMap);

    // 대표관리자 접수상신 목록 조회
    List<Map<String, Object>> selectIoCompCoorpVendorList(Map<String, Object> paramMap);

    // 대표관리자 접수상신 목록 건수 조회
    Integer selectIoCompCoorpVendorListCnt(Map<String, Object> paramMap);

    // 대표관리자 접수상신 상세 정보 조회
    Map<String, Object> selectIoCompCoorpVendor(Map<String, Object> paramMap);

    // Select IO_ADMIN_APPL
    Map<String, Object> selectIoAdminAppl(Integer adminAppNo);

    // 외부업체 정보 수정
    Integer updateIoComp(Map<String, Object> paramMap);

    // 대표관리자 접수상신 반려 시 수정(외부업체 테이블)
    Integer updateCoorpVendorAdminApprRecevieReject(Map<String, Object> paramMap);

    // 대표관리자 접수상신 반려 시 수정
    Integer updateCoorpVendorAdminApprRecevieReject2(Map<String, Object> paramMap);

    int updateCoorpVendorInfoSubcontYn(Map<String, Object> paramMap);

    // Update IO_ADMIN_APPL
    Integer updateIoAdminAppl(Map<String, Object> paramMap);

    // 대표관리자 접수상신 승인 SMS Info
    Map<String, Object> selectIoAdminApplSmsInfo(Integer adminAppNo);

    // 대표관리자 접수상신 반려 SMS Info
    Map<String, Object> selectCoorpVendorAdminReceiveRejectGetSMSInfo(String ioCompId);

    // Select VW_IO_PASS_MST2
    List<Map<String, Object>> selectVwIoPassMst2List(Map<String, Object> paramMap);

    // (관리자) 출입증 발급현황 목록 조회
    List<Map<String, Object>> selectAdmPassList(Map<String, Object> paramMap);

    // (관리자) 출입증 발급현황 목록 건수 조회
    Integer selectAdmPassListCnt(Map<String, Object> paramMap);

    // (관리자) 협력업체 현황 목록 조회
    List<Map<String, Object>> selectAdmIoCompCoorpList(Map<String, Object> paramMap);

    // (관리자) 협력업체 현황 목록 건수 조회
    Integer selectAdmIoCompCoorpListCnt(Map<String, Object> paramMap);

    // (관리자) 협력업체 현황 상세 조회
    Map<String, Object> selectAdmIoCompCoorp(String ioCompId);

    // (관리자) 출입증 강제만료 현황 목록 조회
    List<Map<String, Object>> selectAdmPassExpireList(Map<String, Object> paramMap);

    // (관리자) 출입증 강제만료 현황 목록 건수 조회
    Integer selectAdmPassExpireListCnt(Map<String, Object> paramMap);

    // (관리자) 구성원 기간연장 현황 목록 조회
    List<Map<String, Object>> selectAdmPassExtendList(Map<String, Object> paramMap);

    // (관리자) 구성원 기간연장 현황 목록 건수 조회
    Integer selectAdmPassExtendListCnt(Map<String, Object> paramMap);

    // 장기예외신청현황(산업보안) 목록 조회
    List<Map<String, Object>> selectRegularPassExcptJangList(Map<String, Object> paramMap);

    // 장기예외신청현황(산업보안) 목록 건수 조회
    Integer selectRegularPassExcptJangListCnt(Map<String, Object> paramMap);

    // 장기예외신청현황(산업보안) 상세 조회
    Map<String, Object> selectRegularPassExcptJang(Integer excptApplNo);

    // Delete IO_PASS_EXCPT
    Integer deleteIoPassExcpt(Integer excptApplNo);

    // Delete IF_IO_PASS_EXCPT
    Integer deleteIfIoPassExcpt(Integer excptApplNo);

    // (관리자) 장기예외신청현황(산업보안) - Insert IO_PASS_EXCPT
    Integer insertIoPassExcpt(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 현황 목록 조회
    List<Map<String, Object>> selectAdmPassSecEduList(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 현황 목록 건수 조회
    Integer selectAdmPassSecEduListCnt(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 상세 조회
    Map<String, Object> selectAdmPassSecEdu(Integer passApplNo);

    // (관리자) 상시출입증 보안교육 - 교육이수 업데이트
    Integer updateAdmPassSecEduIsuProc(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 - 위규내용 조회
    String selectIoOfendRsn(Map<String, String> paramMap);

    // (관리자) 상시출입증 보안교육 - Insert SC_IO_OFEND (보안위규내역 등록)
    Integer insertIoOfendInfo(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 - Update IO_PASS_EDU (교육대상자 정보 update)
    Integer updateAdmPassSecEdu(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 - Insert SC_IO_CORR_PLAN (보안위규조치내역 시정계획서 등록)
    Integer insertIoCorrPlan‌Info(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 - Insert SC_IO_OFEND_MEET (보안위규자NO 등록)
    Integer insertIoOfendMeet(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 - Update SC_IO_OFEND (보안위규내역 수정)
    Integer updateIoOfendInfo(Map<String, Object> paramMap);

    // (관리자) 상시출입증 보안교육 - Update SC_IO_CORR_PLAN (보안위규조치내역 시정계획서 수정)
    Integer updateIoCorrPlan‌Info(Map<String, Object> paramMap);

    // 출입증 정지/해지 현황 목록 조회
    List<Map<String, Object>> selectRegularPassExprHistList(Map<String, Object> paramMap);

    // 출입증 정지/해지 현황 목록 건수 조회
    Integer selectRegularPassExprHistListCnt(Map<String, Object> paramMap);

    // 출입증 정지/해지 현황 상세 조회
    Map<String, Object> selectRegularPassExprHist(Integer exprApplNo);

    // 통제구역 출입권한 삭제 현황 목록 조회
    List<Map<String, Object>> selectSpecialPassCancList(Map<String, Object> paramMap);

    // 통제구역 출입권한 삭제 현황 목록 건수 조회
    Integer selectSpecialPassCancListCnt(Map<String, Object> paramMap);

    // 통제구역 출입권한 자동 삭제 현황 목록 조회
    List<Map<String, Object>> selectSpecialPassAutoCancList(Map<String, Object> paramMap);

    // 통제구역 출입권한 자동 삭제 현황 목록 건수 조회
    Integer selectSpecialPassAutoCancListCnt(Map<String, Object> paramMap);

}
