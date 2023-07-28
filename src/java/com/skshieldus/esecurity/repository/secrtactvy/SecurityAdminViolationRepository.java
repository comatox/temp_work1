package com.skshieldus.esecurity.repository.secrtactvy;

import org.apache.ibatis.annotations.Mapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
public interface SecurityAdminViolationRepository {

    // 구성원 조회(사번검색)
    Map<String, Object> selectCoEmpInfo(HashMap<String, Object> paramMap);

    // 채번
    int getScDocNo();

    // 구성원 위규자 등록
    int coViolationInsert(HashMap<String, Object> paramMap);

    Map<String, Object> selectCoViolationExcptCnt1(HashMap<String, Object> paramMap);

    int selectCoViolationExcptCnt2(HashMap<String, Object> paramMap);

    //위규자 이메일 목록 조회
    List<Map<String, Object>> dmSecCoEmp_Violation_SendMail_List_S(HashMap<String, Object> paramMap);

    Map<String, Object> dmSecCoEmp_Violation_Info(HashMap<String, Object> paramMap);

    String selectCoEmpViolationOfendCnt(HashMap<String, Object> paramMap);

    // SC_OFEND 저장
    int dmSecCoEmp_Violation_Ofend_U(Map<String, Object> dsActDoData);

    // SC_EXP_DO 저장
    int dmSecCoEmp_Violation_Exp_I(Map<String, Object> dsActDoData);

    //채번
    String dmSecCoEmp_Violation_Corr_Plan_Seq_S(Map<String, Object> dsActDoData);

    int dmSecCoEmp_Violation_Corr_Plan_I(Map<String, Object> dsActDoData);

    // 구성원인 경우조치 사항 누적수 확인
    Map<String, Object> dmSecCoEmpViolation_Accum_S(HashMap<String, Object> dsActDoData);

    int dmSecCoEmp_Violation_Next_I(HashMap<String, Object> dsActDoData);

    // 시정계획서 누적건수 CLEAR
    int dmSecCoEmpViolation_Accum_Clear_U(HashMap<String, Object> dsActDoData);

    int dmSecCoEmpViolation_ssm_I(HashMap<String, Object> paramMap);

    // 구성원 위규자 목록 조회
    List<Map<String, Object>> selectCoViolationList(HashMap<String, Object> paramMap);

    // 구성원 위규자 목록 총갯수 조회
    int selectCoViolationListCnt(HashMap<String, Object> paramMap);

    // 구성원 위규자 상세 > 동일 위규 조회
    List<Map<String, Object>> selectCoViolationSameHistoryList(HashMap<String, Object> paramMap);

    // 구성원 위규자 상세 > 조치현황 조회
    List<Map<String, Object>> selectCoViolationActHistoryList(HashMap<String, Object> paramMap);

    // 구성원 위규자 상세조회
    Map<String, Object> selectCoViolationDetail(HashMap<String, Object> paramMap);

    int coEmpViolation_Mobile_Act(HashMap<String, Object> paramMap);

    // 외부인 위규자 입력 > 회원정보 검색
    List<Map<String, Object>> selectIoEmpInfo(HashMap<String, Object> paramMap);

    // 일일사원증 발급화면에서 도급업체 회원만 검색됨
    List<Map<String, Object>> selectIoEmpSubInfo(HashMap<String, Object> paramMap);

    // 외부인 위규자 입력 > 회원정보 총 갯수 조회
    int selectIoEmpInfoCnt(HashMap<String, Object> paramMap);

    // 일일사원증 발급화면에서 도급업체 회원만 검색됨 총갯수 조회
    int selectIoEmpSubInfoCnt(HashMap<String, Object> paramMap);

    // 접견자 목록 조회
    List<Map<String, Object>> selectIoViolationInterviewerList(HashMap<String, Object> paramMap);

    // 외부인 위규자 등록시 채번
    int getScIoDocNo();

    // 외부인 위규자 등록
    int ioViolationInsert(HashMap<String, Object> paramMap);

    // 외부인 위규자 접견자 목록 등록
    int ioViolationInterviewerInsert(Map<String, Object> paramMap);

    int dmSecIoEmpViolation_Excpt_Cnt1(HashMap<String, Object> paramMap);

    int dmSecIoEmp_Violation_Ofend_Check_Quaterly(HashMap<String, Object> paramMap);

    Map<String, Object> dmSecIoEmp_Violation_Filtered_ActionList_S(HashMap<String, Object> paramMap);

    Map<String, Object> dmSecIoEmp_Violation_ActSum_Yearly_S(HashMap<String, Object> paramMap);

    List<Map<String, Object>> dmSecIoEmp_Violation_Info(Map<String, Object> paramMap);

    List<Map<String, Object>> dmSecIoEmp_Violation_SendMail_List_S(Map<String, Object> paramMap);

    int dmSecIoEmp_Violation_Ofend_U(Map<String, Object> paramMap);

    int dmSecIoEmp_Violation_Exp_I(Map<String, Object> paramMap);

    int dmSecIoEmp_Violation_Deny_I(Map<String, Object> paramMap);

    int dmSecIoEmp_Violation_PassExprHist_I(Map<String, Object> paramMap);

    int dmSecIoEmp_Violation_PassExprHist_Seq_S();

    Map<String, Object> dmSecIoEmp_Violation_Idcard_Info(Map<String, Object> paramMap);

    int dmSecIoEmp_Violation_Corr_Plan_Seq_S(Map<String, Object> paramMap);

    int dmSecIoEmp_Violation_CorrPlan_I(Map<String, Object> paramMap);

    int dmSecIoEmp_Violation_ssm_I(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectIoViolationList(HashMap<String, Object> paramMap);

    int selectIoViolationListCnt(HashMap<String, Object> paramMap);

    Map<String, Object> selectIoViolationDetail(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectIoViolationDetailInterviewerList(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectIoViolationSameHistoryList(HashMap<String, Object> paramMap);

    List<Map<String, Object>> selectIoViolationActHistoryList(HashMap<String, Object> paramMap);

    int dmSecIoEmp_Violation_Mobile_Ofend_U(HashMap<String, Object> paramMap);

    //구성원위규자(보안요원) 목록 조회
    List<Map<String, Object>> dmSecCoEmp_Violation_List_Sec_S(HashMap<String, Object> paramMap);

    //구성원위규자(보안요원) 목록 총갯수 조회
    int dmSecCoEmp_Violation_List_Sec_Count_S(HashMap<String, Object> paramMap);

    //구성원위규자(보안요원) 삭제
    int dmSecrtCorrPan_OFEND_D(HashMap<String, Object> paramMap);

    //외부인위규자(보안요원) 목록 조회
    List<Map<String, Object>> dmSecIoEmp_Violation_List_Sec_S(HashMap<String, Object> paramMap);

    //외부인위규자(보안요원) 목록 총 갯수 조회
    int dmSecIoEmp_Violation_List_Sec_Count_S(HashMap<String, Object> paramMap);

    //외부인위규자(보안요원) 삭제
    int dmSecrtIoCorrPlan_OFEND_D(HashMap<String, Object> paramMap);

    //외부인위규자 자동징구 가능여부 조회 (Y/N)
    String selectAutoActYn(HashMap<String, Object> paramMap);

    int dmPassExcptGetExprApplNo();

    int dmSecIoEmp_Violation_Deny_Seq_S();

    int ioEmpViolationOfendUpdate(HashMap<String, Object> paramMap);

    HashMap<String, Object> getIoEmpViolationInfo(Map<String, Object> param);

    int resetEmpViolationInfo(Map<String, Object> param);

    // 외부인 위규자 결재 등록
    int insertScIoOfendDoc(Map<String, Object> paramMap);

    // 외부인 위규자 결재 수정
    int updateScIoOfendDoc(Map<String, Object> paramMap);

    // 외부인 위규자 결재 목록 조회
    List<Map<String, Object>> selectIoViolationApprList(Integer scIoDocNo);

    // 외부인 위규자 결재 상세 조회
    Map<String, Object> selectIoViolationApprByDocId(Integer docId);

    // 외부인 위규자 조치 정보 업데이트
    int updateScIoOfendActInfo(Map<String, Object> paramMap);

    int coEmpViolationOfendUpdate(HashMap<String, Object> paramMap);

    HashMap<String, Object> getCoEmpViolationInfo(Map<String, Object> param);

    int resetCoEmpViolationInfo(Map<String, Object> param);

    // 구성원 위규자 결재 등록
    int insertScOfendDoc(Map<String, Object> paramMap);

    // 구성원 위규자 결재 수정
    int updateScOfendDoc(Map<String, Object> paramMap);

    // 구성원 위규자 결재 목록 조회
    List<Map<String, Object>> selectCoViolationApprList(Integer scIoDocNo);

    // 구성원 위규자 결재 상세 조회
    Map<String, Object> selectCoViolationApprByDocId(Integer docId);

    // 구성원 위규자 조치 정보 업데이트
    int updateScOfendActInfo(Map<String, Object> paramMap);

    // 메인 > 보안활동 > 구성원 위규자 목록 조회
    List<Map<String, Object>> selectMainScCoOfendList(Map<String, Object> paramMap);

    // 메인 > 보안활동 > 구성원 위규자 목록 건수 조회
    Integer selectMainScCoOfendListCnt(Map<String, Object> paramMap);

    // 메인 > 보안활동 > 외부인 위규자 목록 조회
    List<Map<String, Object>> selectMainScIoOfendList(Map<String, Object> paramMap);

    // 메인 > 보안활동 > 외부인 위규자 목록 건수 조회
    Integer selectMainScIoOfendListCnt(Map<String, Object> paramMap);

    // 위규자 목록 이메일 조회
    List<Map<String, Object>> dmSecCoEmp_Discipline_SendMail_List_S(HashMap<String, Object> paramMap);

    // 2차 메일 발송 정보 업데이트
    int dmSecCoEmp_Violation_ActDo2ChaMail_U(HashMap<String, Object> paramMap);

}
