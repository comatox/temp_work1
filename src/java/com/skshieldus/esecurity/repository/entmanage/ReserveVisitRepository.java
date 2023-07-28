package com.skshieldus.esecurity.repository.entmanage;

import com.skshieldus.esecurity.model.entmanage.SendSpmsDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReserveVisitRepository {

    // 방문객관리(구성원 인증) 목록 조회
    List<Map<String, Object>> selectVisitorCertiList(Map<String, Object> paramMap);

    // 방문객관리(구성원 인증) 상세 조회
    Map<String, Object> selectVisitorCertiView(Map<String, Object> paramMap);

    // 방문객관리(구성원 인증) 승인 처리(IO_EMP_CO_CERTI)
    int updateVisitorCerti(Map<String, Object> paramMap);

    // 방문객관리(구성원 인증) 승인 처리(IO_EMP)
    int updateVisitorNameCheck(Map<String, Object> paramMap);

    // 방문객관리(구성원 인증) 반려 처리
    int deleteVisitorCerti(Map<String, Object> paramMap);

    // 방문예약 신청/접수 현황 목록 조회
    List<Map<String, Object>> selectVisitorProgressList(Map<String, Object> paramMap);

    // 방문예약 신청/접수 현황(관리자) 목록 조회
    List<Map<String, Object>> selectVisitorProgressAdminList(Map<String, Object> paramMap);

    // 방문예약 신청/접수 현황(관리자) 목록 건수 조회
    Integer selectVisitorProgressAdminCount(Map<String, Object> paramMap);

    // 방문예약 취소 처리
    int deleteVisitorProgress(Map<String, Object> paramMap);

    // 방문예약 신청/접수 상세정보 조회
    Map<String, Object> selectReserveVisitViewReIO(Map<String, Object> paramMap);

    // 방문예약 신청/접수 방문객 목록 조회
    List<Map<String, Object>> selectReserveVisitVstManListIO(Map<String, Object> paramMap);

    // 출입건물지정 목록 조회(상위)
    List<Map<String, Object>> selectInEntrytheBuildingsTop(Map<String, Object> paramMap);

    // 출입건물지정 목록 조회(하위)
    List<Map<String, Object>> selectInEntrytheBuildingsList(Map<String, Object> paramMap);

    // 전산저장장치 목록 조회
    List<Map<String, Object>> selectReserveVisitSelectIOList(Map<String, Object> paramMap);

    // 출입건물 저장 목록 조회
    List<Map<String, Object>> selectReserveVisitBuildingIOList(Map<String, Object> paramMap);

    // 출입구역 선택 여부 수정
    int updateReserveVisitBuildChkChg(Map<String, Object> paramMap);

    // 출입건물 삭제
    int deleteReserveVisitBuild(Map<String, Object> paramMap);

    // 출입건물 등록
    int insertReserveVisitBuild(Map<String, Object> paramMap);

    // 방문에약 반려
    int updateReserveVisitReject(Map<String, Object> paramMap);

    // 방문예약 접견자 수정
    int updateReserveVisitMeetEmpId(Map<String, Object> paramMap);

    // 미인솔 여부 및 사유 수정
    int updateReserveVisitVstStat(Map<String, Object> paramMap);

    // 방문예약 SMS발송대상 목록
    List<Map<String, String>> selectVisitListForSMS(Map<String, Object> paramMap);

    // vip 네트워크 정보
    List<Map<String, String>> selectVipNetworkInfo(Integer vstApplNo);

    // 청주 게이트 목록
    List<Map<String, Object>> selectCjGateList();

    // 방문객 삭제
    int deleteRestrictVisitVisitor(Map<String, Object> paramMap);

    // 방문객 삭제 => REST_YN 처리
    int updateRestrictVisitResolve(Map<String, Object> paramMap);

    // 방문객 삭제 => REST_YN 처리
    int updateRestrictVisitResolveHist(Map<String, Object> paramMap);

    // 방문객 삭제 => REST_YN 처리
    int deleteRestrictVisitManResolveHist(Map<String, Object> paramMap);

    // 구성원 가족 목록 조회
    List<Map<String, Object>> selectFamReserveVisit(String empId);

    // 가족방문예약 가족 목록 조회
    List<Map<String, Object>> selectFamReserveVisitViewFamList(Map<String, Object> paramMap);

    // 가족방문예약 신청 => IO_EMP 등록
    int insertFamReserveVisitIoemp(Map<String, Object> paramMap);

    // 가족방문예약 신청 => IO_VST_MAN 등록
    int insertReserveVisitMan(Map<String, Object> paramMap);

    // 가족방문예약 신청 => IO_VST_MAN_GATE_IO 등록
    int insertVipReserveVisitManGate(Map<String, Object> paramMap);

    // 가족방문예약 신청 => IO_VST 등록
    int insertFamReserveVisitVst(Map<String, Object> paramMap);

    // 가족방문예약 신청 => 결재문서ID 수정
    int updateVipReserveVisitVstDocId(Map<String, Object> paramMap);

    // 방문예약ID 조회(생성)
    Integer selectVstApplNo();

    // 가족방문예약 상세조회 => Re
    Map<String, Object> selectFamReserveVisitViewRe(Map<String, Object> paramMap);

    // 가족방문예약 상세조회 => Info
    Map<String, Object> selectFamReserveVisitViewInfo(Map<String, Object> paramMap);

    // 방문예약 후처리 결재상태 수정
    int updateReserveVisitApplStat(Map<String, Object> paramMap);

    // VIP방문예약 업체 목록 조회
    List<Map<String, Object>> selectVipReserveVisitParnterList();

    // VIP방문예약 신청 => IO_EMP 등록
    int insertVipReserveVisitIoemp(Map<String, Object> paramMap);

    // VIP방문예약 신청 => IO_VST 등록
    int insertVipReserveVisitVst(Map<String, Object> paramMap);

    // VIP방문예약 신청 => IO_VIP_NETWORK 수정
    int updateVipNetwork(Map<String, Object> paramMap);

    // VIP방문예약 신청 => IO_VST_MAN_VIP 등록
    int insertVipReserveVisitManVst(Map<String, Object> paramMap);

    // VIP방문예약 신청 => IO_VIP_CAR_INFO 등록
    int insertReserveVisitCar(Map<String, Object> paramMap);

    // VIP방문예약 상세조회 => 기본정보
    Map<String, Object> selectVipReserveVisitViewRE(Map<String, Object> paramMap);

    // VIP방문예약 상세조회 => 기본정보
    Map<String, Object> selectVipReserveVisitViewInfo(Map<String, Object> paramMap);

    // VIP방문예약 상세조회 => 차량목록
    List<Map<String, Object>> selectVipReserveVisitCarList(Map<String, Object> paramMap);

    // VIP방문예약 상세조회 => 방문자목록
    List<Map<String, Object>> selectVipReserveVisitManList(Map<String, Object> paramMap);

    // VIP방문예약 상세조회 => 출입건물목록
    List<Map<String, Object>> selectVipReserveVisitBuildingList(Map<String, Object> paramMap);

    // 단체방문 등록 => IO_GROUP 등록
    int insertIoGroup(Map<String, Object> paramMap);

    // 단체방문 등록 => IO_GROUP_CAR_INFO 등록
    int insertIoGroupCar(Map<String, Object> paramMap);

    // 단체방문 등록 => IO_GROUP 수정(결재문서ID)
    int updateOrgVisitDocId(Map<String, Object> paramMap);

    // 단체방문 후처리 => 결재상태 수정
    int updateOrgVisitApprResult(Map<String, Object> paramMap);

    // 단체방문 현황 목록 조회
    List<Map<String, Object>> selectOrgVisitList(Map<String, Object> paramMap);

    // 단체방문 현황 상세 조회
    Map<String, Object> selectOrgVisitView(Map<String, Object> paramMap);

    // 단체방문 현황 상세 차량목록 조회
    List<Map<String, Object>> selectOrgVisitCarList(Map<String, Object> paramMap);

    // 방문예약 후처리 => 이천 차량목록 조회(VIP)
    List<SendSpmsDTO> selectVipIcSpmsIfInfo(Integer vstApplNo);

    // 방문예약 후처리 => 고담 차량목록 조회
    List<SendSpmsDTO> selectSendICSpmsIoVstInfoForGotham(Integer vstApplNo);

    // 방문예약 후처리 => 청주 차량목록 조회(VIP)
    List<SendSpmsDTO> selectSendCJSpmsIoVipCarInfoForSpms(Integer vstApplNo);

    // 방문예약 후처리 => 전산저장장치 NAC 연동 건수(확인)
    Integer selectVisitNacIfCount(Integer vstApplNo);

    // 방문예약 후처리 => 전산저장장치 NAC 연동 목록
    List<Map<String, String>> selectVisitNacIfInfoList(Integer vstApplNo);

    // 방문예약 후처리 => 전산저장장치 NAC 연동 로그 등록
    int insertNacLogReg(Map<String, String> paramMap);

    // 방문예약 후처리 => 방문예약 일별 출입정보 merge 목록 조회
    List<Map<String, Object>> selectReserveIfManGateIoList(Integer vstApplNo);

    // 방문예약 후처리 => 방문예약 일별 출입정보 merge
    int insertReserveIfManGateIoMerge(Map<String, Object> paramMap);

    // 방문예약 후처리 => 사업장ID 조회(차량IF 처리 시)
    String selectIoVstCarVstCompId(Integer vstApplNo);

    // 단체방문예약 후처리 => 이천 차량목록 조회
    List<SendSpmsDTO> selectGroupIcSpmsIfInfoList(Integer vstApplNo);

    // 단체방문예약 후처리 => 청주 차량목록 조회
    List<SendSpmsDTO> selectSendCjSpmsGroupCarInfoList(Integer vstApplNo);

    // VIP 방문증 번호 업데이트
    Integer updateVipIoCardno(Map<String, Object> paramMap);

    // 방문예약ID 조회(채번)
    Integer selectReserveVisitVstApplNo();

    // GlobalStaff 사번 존재여부 조회
    String selectReserveVisitIoEmpGsCnt(String ioEmpId);

    // GlobalStaff 방문예약 방문객 정보 등록
    int insertReserveVisitVstMan(Map<String, Object> paramMap);

    // GlobalStaff 방문객 정보 등록
    int insertReserveVisitIoEmp(Map<String, Object> paramMap);

    // GlobalStaff 방문객 정보 수정
    int updateReserveVisitIoEmp(Map<String, Object> paramMap);

    // GlobalStaff 방문예약 방문객 건수 조회
    Integer selectReserveVisitVstManCnt(Integer vstApplNo);

    // GlobalStaff 방문예약 등록
    int insertReserveVisit(Map<String, Object> paramMap);

    // GlobalStaff 방문예약 전산저장장치 등록
    int insertReserveVisitVstManIteqmt(Map<String, Object> paramMap);

    // GlobalStaff 방문예약 상신 메일 정보 조회
    List<Map<String, Object>> selectGlobalStaffMailSend(Integer vstApplNo);

    // 방문예약 전산저장장치 삭제
    int deleteReserveVisitIteqmt(Map<String, Object> paramMap);

    // 방문예약 방문객 삭제
    int deleteReserveVisitMan(Map<String, Object> paramMap);

    // 방문예약 방문객 건수 수정
    int updateReserveVisitVst(Map<String, Object> paramMap);

    // (관리자) VIP고객사 목록 조회
    List<Map<String, Object>> selectAdmVipPartnerList(Map<String, Object> paramMap);

    // (관리자) VIP고객사 목록건수 조회
    Integer selectAdmVipPartnerListCnt(Map<String, Object> paramMap);

    // VIP고객사 저장
    Integer saveVipPartner(Map<String, Object> paramMap);

    // 출입건물도면 캠퍼스별 사용안함 처리
    Integer updateBuildingEntranceInfoUseYn(String entCompId);

    // 출입건물도면 캠퍼스별 등록
    Integer insertBuildingEntranceInfo(Map<String, Object> paramMap);

    // 출입건물도면 조회
    Map<String, Object> selectBuildingEntranceInfo(String entCompId);

}
