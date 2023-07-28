package com.skshieldus.esecurity.service.entmanage;

import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Map;

public interface ReserveVisitService {

    /**
     * 방문객관리(구성원 인증) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    List<Map<String, Object>> selectVisitorCertiList(Map<String, Object> paramMap);

    /**
     * 방문객관리(구성원 인증) 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    Map<String, Object> selectVisitorCertiView(Map<String, Object> paramMap);

    /**
     * 방문객관리(구성원 인증) 승인/반려
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 26.
     */
    void updateVisitorCerti(Map<String, Object> paramMap);

    /**
     * 방문예약 신청/접수 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 28.
     */
    List<Map<String, Object>> selectVisitorProgressList(Map<String, Object> paramMap);

    /**
     * 방문예약 취소
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 28.
     */
    boolean deleteVisitorProgress(Map<String, Object> paramMap);

    /**
     * 방문예약 신청/접수 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 28.
     */
    Map<String, Object> selectReserveVisitViewReIO(Map<String, Object> paramMap);

    /**
     * 방문예약 신청/접수 방문객 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 10. 28.
     */
    List<Map<String, Object>> selectReserveVisitVstManListIO(Map<String, Object> paramMap);

    /**
     * 출입건물지정 목록 조회(상위)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 9.
     */
    List<Map<String, Object>> selectInEntrytheBuildingsTop(Map<String, Object> paramMap);

    /**
     * 출입건물지정 목록 조회(하위)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 9.
     */
    List<Map<String, Object>> selectInEntrytheBuildingsList(Map<String, Object> paramMap);

    /**
     * 전산저장장치 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 10.
     */
    List<Map<String, Object>> selectReserveVisitSelectIOList(Map<String, Object> paramMap);

    /**
     * 출입건물 저장 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    List<Map<String, Object>> selectReserveVisitBuildingIOList(Map<String, Object> paramMap);

    /**
     * 출입구역 선택 여부 수정
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    boolean updateReserveVisitBuildChkChg(Map<String, Object> paramMap);

    /**
     * 출입건물 수정(delete/insert)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    boolean updateReserveVisitBuild(Map<String, Object> paramMap);

    /**
     * 방문예약 접수 반려
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 11.
     */
    boolean rejectReserveVisit(Map<String, Object> paramMap);

    /**
     * 방문예약 상신/승인
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 15.
     */
    boolean insertReserveVisitApproval(Map<String, Object> paramMap);

    /**
     * 방문예약 후처리(승인)
     *
     * @param paramMap
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 15.
     */
    void reserveVisitApprovalPost(Map<String, Object> paramMap) throws Exception;

    /**
     * 방문예약 후처리(반려)
     *
     * @param paramMap
     * @throws Exception
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 25.
     */
    void reserveVisitApprovalRejectPost(Map<String, Object> paramMap) throws Exception;

    /**
     * 청주 게이트 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 17.
     */
    List<Map<String, Object>> selectCjGateList(Map<String, Object> paramMap);

    /**
     * 방문객 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 18.
     */
    boolean deleteRestrictVisitVisitor(Map<String, Object> paramMap);

    /**
     * 가족방문예약 상신
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 18.
     */
    boolean insertReserveVisitFamApproval(Map<String, Object> paramMap);

    /**
     * 구성원 가족 목록 조회
     *
     * @param empId
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 18.
     */
    List<Map<String, Object>> selectFamReserveVisit(String empId);

    /**
     * 가족방문예약 가족 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    List<Map<String, Object>> selectFamReserveVisitViewFamList(Map<String, Object> paramMap);

    /**
     * 가족방문예약 상세조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 19.
     */
    Map<String, Object> selectFamReserveVisitView(Map<String, Object> paramMap);

    /**
     * VIP방문예약 업체 목록 조회
     *
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 24.
     */
    List<Map<String, Object>> selectVipReserveVisitParnterList();

    /**
     * VIP방문예약 상신
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 24.
     */
    boolean insertReserveVisitVipApproval(Map<String, Object> paramMap);

    /**
     * VIP방문예약 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 25.
     */
    Map<String, Object> selectVipReserveVisitView(Map<String, Object> paramMap);

    /**
     * 단체방문 등록(상신)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 29.
     */
    boolean insertOrgVisitApproval(Map<String, Object> paramMap);

    /**
     * 단체방문 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 29.
     */
    List<Map<String, Object>> selectOrgVisitList(Map<String, Object> paramMap);

    /**
     * 단체방문 현황 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 11. 29.
     */
    Map<String, Object> selectOrgVisitView(Map<String, Object> paramMap);

    /**
     * 방문예약 후처리 => 방문예약 일별 출입정보 merge
     *
     * @param lid
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 13.
     */
    void mergeReserveIfManDt(Integer lid);

    /**
     * 단체방문예약 후처리 => 차량출입 IF
     *
     * @param lid
     * @throws Exception
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 13.
     */
    void reserveVisitOrgApprovalPost(Integer lid) throws Exception;

    /**
     * VIP 방문증 번호 저장
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    Boolean saveVipIoCardno(Map<String, Object> paramMap);

    /**
     * 방문예약 신청/접수 현황(관리자) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 18.
     */
    List<Map<String, Object>> selectVisitorProgressAdminList(Map<String, Object> paramMap);

    /**
     * 방문예약 신청/접수 현황(관리자) 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 18.
     */
    Integer selectVisitorProgressAdminCount(Map<String, Object> paramMap);

    /**
     * 방문예약ID 채번
     *
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 28.
     */
    Integer selectReserveVisitVstApplNo();

    /**
     * GlobalStaff 방문객 추가
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 1. 28.
     */
    boolean insertGlobalStaffVstMan(Map<String, Object> paramMap);

    /**
     * GlobalStaff 방문예약 상신
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 3.
     */
    boolean insertReserveVisitGsApproval(Map<String, Object> paramMap);

    /**
     * 방문예약 방문객 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 3.
     */
    boolean deleteVisitVisitor(Map<String, Object> paramMap);

    /**
     * 전산저장장치 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2022. 2. 4.
     */
    boolean deleteReserveVisitIteqmt(Map<String, Object> paramMap);

    /**
     * (관리자) VIP고객사 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    List<Map<String, Object>> selectAdmVipPartnerList(Map<String, Object> paramMap);

    /**
     * (관리자) VIP고객사 목록건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    Integer selectAdmVipPartnerListCnt(Map<String, Object> paramMap);

    /**
     * (관리자) VIP고객사 저장
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    Boolean saveAdmVipPartner(Map<String, Object> paramMap);

    /**
     * 출입건물도면 저장
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    Boolean insertBuildEntranceInfo(Map<String, Object> paramMap);

    /**
     * 출입건물도면 파일다운로드
     *
     * @param entCompId
     * @param agent
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 7.
     */
    ResponseEntity<Object> downloadBuildEntranceInfo(String entCompId, String agent);

}
