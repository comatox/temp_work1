package com.skshieldus.esecurity.repository.entmanage;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface VisitEntranceRepository {

    // 정문 인원 출입 목록 조회
    List<Map<String, Object>> selectFrontDoorList(Map<String, Object> paramMap);

    // 외부업체 자산반출입 건수 조회

    // 외부업체 자산반출입 등록
    Integer insertIoInoutwriteBySelect(Map<String, Object> paramMap);

    // 외부업체 자산반출입 물품 등록
    Integer insertIoInoutarticlelistBySelect(Map<String, Object> paramMap);

    // 외부업체 자산반출입 이력 등록
    Integer insertIoInarticlehistoryBySelect(Map<String, Object> paramMap);

    // 방문객-출입건수
    Integer selectFrontDoorCardChk(Map<String, Object> paramMap);

    // 사진 3회체크
    String selectFrontDoorPhotoChk(Map<String, Object> paramMap);

    // 명함확인
    Integer mergeFrontDoorCompCardChk(Map<String, Object> paramMap);

    // 입출문 정보 업데이트
    Integer updateFrontDoorInOutprocess(Map<String, Object> paramMap);

    // Insert IO_VST_MAN_GATE_IO (Select IO_VST_MAN_GATE)
    Integer insertFrontDoorInGate(Map<String, Object> paramMap);

    // Select
    Map<String, Object> selectFrontDoorManComp(Map<String, Object> paramMap);

    // Select
    List<Map<String, Object>> selectFrontDoorUTList(Map<String, Object> paramMap);

    // Select
    List<Map<String, Object>> selectFrontDoorUpGateList(Map<String, Object> paramMap);

    // Select
    List<Map<String, Object>> selectFrontDoorGateList2(Map<String, Object> paramMap);

    // Select
    List<Map<String, Object>> selectFrontDoorGate202List(Map<String, Object> paramMap);

    // Select
    List<Map<String, Object>> selectFrontDoorGate209List(Map<String, Object> paramMap);

    // Select
    List<Map<String, Object>> selectFrontDoor3CampUT_100_08List(Map<String, Object> paramMap);

    // Select
    List<Map<String, Object>> selectFrontDoorGateListByUpGate(Map<String, Object> paramMap);

    // 이용자 정보 조회
    Map<String, Object> selectFrontDoorUserInfo(Map<String, Object> paramMap);

    // 외부인 방문예약 관리 정보 조회
    Map<String, Object> selectFrontDoorVstInfo(Map<String, Object> paramMap);

    // Select InProcess Message
    List<Map<String, Object>> selectSendFrontDoorInProcessMsgTo(Map<String, Object> paramMap);

    // 보안위규 등록
    Integer insertFrontDoorViolation(Map<String, Object> paramMap);

    // Update
    Integer updateIoVstManGate(Map<String, Object> paramMap);

    // Insert
    Integer insertIoVstManGateIoH(Map<String, Object> paramMap);

    // 건물(gate)명 조회
    String selectGateNm(String bldgId);

    // Delete IO_VST_MAN_GATE_IO
    Integer deleteIoVstManGateIo(Map<String, Object> paramMap);

    // 건물명 조회
    String selectBldgNm(Map<String, Object> paramMap);

    // Select OutProc_LprInfo_List
    List<Map<String, Object>> selectFrontDoorOutProcLprInfoList(Map<String, Object> paramMap);

    Integer selectIoInoutwriteCnt(int vstApplNo);

    // Insert
    Integer insertFrontDoorIOHIn(Map<String, Object> paramMap);

    // Update
    Integer updateFrontDoorReIn(Map<String, Object> paramMap);

    // Delete
    Integer deleteFrontDoorInGateReset(Map<String, Object> paramMap);

    // 정문 전산기기 반입 목록 조회
    List<Map<String, Object>> selectCarryInPcList(Map<String, Object> paramMap);

    // 정문 전산기기 반입 목록 건수 조회
    Integer selectCarryInPcListCnt(Map<String, Object> paramMap);

    // 정문 전산기기 반입 - 모바일 이용동의 업데이트
    Integer updateMobileUseApply(Map<String, Object> paramMap);

    // Select CO_BARCODE_PRINTER
    List<Map<String, Object>> selectBarcodePrinterList(Map<String, Object> paramMap);

    // 전산기기 반입 상세정보 조회
    Map<String, Object> selectCarryInPc(Map<String, Object> paramMap);

    // Update IO_INOUTPC_MOVE by Cancel
    Integer updateIoInoutpcMoveByCancel(Map<String, Object> paramMap);

    // Update IO_INOUTPCLIST by Cancel
    Integer updateIoInoutpclistByCancel(Map<String, Object> paramMap);

    // Insert IO_INARTICLEHISTORY
    Integer insertIoInarticlehistory(Map<String, Object> paramMap);

    // Update IO_INOUTPCLIST by Into
    Integer updateIoInoutpclistByInto(Map<String, Object> paramMap);

    // Merge IO_INOUTPC_MOVE by Into
    Integer mergeIoInoutpcMove(Map<String, Object> paramMap);

    // Select
    Map<String, Object> selectOutNetworkInfoNcaIf(Map<String, Object> paramMap);

    // 건물 출입 목록 조회
    List<Map<String, Object>> selectBuildingPassList(Map<String, Object> paramMap);

    // 건물 출입 - Gate 목록 조회
    List<Map<String, Object>> selectBuildingPassGateList(Map<String, Object> paramMap);

    // Insert Building In Process
    Integer insertBuildingPassInprocess(Map<String, Object> paramMap);

    // Select
    Map<String, Object> selectBuildingPassManComp(Map<String, Object> paramMap);

    // Update
    Integer updateIoVstManGateIoByBuilingOut(Map<String, Object> paramMap);

    // Merge
    Integer mergeBuildingPassIOHIn(Map<String, Object> paramMap);

    // Delete
    Integer deleteBuildingPassReIn(Map<String, Object> paramMap);

    // 납품 출입 목록 조회
    List<Map<String, Object>> selectDeliveryPassList(Map<String, Object> paramMap);

    // 납품 출입 목록건수 조회
    Integer selectDeliveryPassListCnt(Map<String, Object> paramMap);

    // 납품 출입 상세 조회
    Map<String, Object> selectDeliveryPass(Integer dlvAppNo);

    // 납품 출입 - 방문증번호 건수 체크
    Integer selectDeliveryPassCardChk(Map<String, Object> paramMap);

    // Select VW_IO_PASS_MST2
    Map<String, Object> selectDeliveryPassChk(Map<String, Object> paramMap);

    // Select
    Integer selectDeliveryPassDenyChk(Map<String, Object> paramMap);

    // Select
    Integer selectDeliveryPassCnt(Map<String, Object> paramMap);

    // Insert (납품출입 - 입문)
    Integer insertDeliveryPassInprocess(Map<String, Object> paramMap);

    // Update
    Integer updateDeliveryPassIoKnd(Map<String, Object> paramMap);

    // Select
    Map<String, Object> selectDeliveryPassManComp(Map<String, Object> paramMap);

    // Select
    List<Map<String, String>> selectDeliveryPassCarInfo(Map<String, Object> paramMap);

    // Insert
    Integer insertDeliveryPassIoDeliveryCarInfoForInpec(Map<String, Object> paramMap);

    // Update (납품출입 - 입문 reset)
    Integer updateDeliveryPassInreset(Map<String, Object> paramMap);

    // Update (납품출입 - 입문 reset2)
    Integer updateDeliveryPassInreset2(Map<String, Object> paramMap);

    // Update (납품출입 - 출문)
    Integer updateDeliveryPassOutprocess(Map<String, Object> paramMap);

    // Update (납품출입 - 출문 reset)
    Integer updateDeliveryPassOutreset(Map<String, Object> paramMap);

    // Select
    Map<String, Object> selectDeliveryPassUserInfo(Map<String, Object> paramMap);

    // 건물출입 현황 목록 조회
    List<Map<String, Object>> selectBuildingPassHistList(Map<String, Object> paramMap);

    // 건물출입 현황 목록 건수 조회
    Integer selectBuildingPassHistListCnt(Map<String, Object> paramMap);

}
