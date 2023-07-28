package com.skshieldus.esecurity.service.entmanage;

import java.util.List;
import java.util.Map;

public interface VisitEntranceIfService {

    /**
     * 출입증에 게이트 권한 부여
     * 1. 분당  S/W센터
     * 2. 청주 1,2,3 캠퍼스 UT 목적지.
     * 3. 온라인 인솔 미대상 + 안내데스크 없는 건물 (P&T, 통합자재창고, 인력개발원, SCM, 경영지원본관, M15, M14, 청주 원자재 창고 1,2)
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 18.
     */
    Boolean executeGateEnterPermissionIf(Map<String, Object> paramMap);

    /**
     * 방문객 입문시, 구성원에게 알릴 메일과 kakao 컨텐츠 데이터 생성, 반환
     *
     * @param isOfficeRoom
     * @param visitReserveDataMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 18.
     */
    List<Map<String, String>> executeGetVisitorGateInNotiData(boolean isOfficeRoom, Map<String, Object> visitReserveDataMap);

    /**
     * 정문 인원 입/출문 메일 발송
     *
     * @param paramMap
     * @param empId
     * @param acIp
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 18.
     */
    Boolean executeGateInOutVstMail(Map<String, String> paramMap, String empId, String acIp);

    /**
     * 등록할 위규 정보 데이터 생성
     *
     * @param paramMap
     * @param visitReserveDataMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 18.
     */
    Map<String, Object> getViolationData(Map<String, Object> paramMap, Map<String, Object> visitReserveDataMap);

    /**
     * 외곽 입문시 온라인 인솔 시스템에 전송할 데이터 생성
     *
     * @param reserveGateInfoList
     * @param visitReserveDataMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 18.
     */
    Map<String, Object> getSmartTagIfData(Map<String, Object> visitReserveDataMap, String[] reserveGateInfoList);

    /**
     * get Cube IF Data
     *
     * @param paramMap
     * @param visitReserveDataMap
     * @param receiverList
     * @param bldgIdList
     * @param bldgNmList
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 18.
     */
    String[] getCubeIfData(Map<String, Object> paramMap, Map<String, Object> visitReserveDataMap, String receiverList, List<String> bldgIdList, List<String> bldgNmList);

    /**
     * Send cube message
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 18.
     */
    Boolean executeFrontDoorCubeIF(Map<String, Object> paramMap);

    /**
     * SSM System 연동
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 18.
     */
    Boolean executeFrontDoorSsmIF(Map<String, Object> paramMap);

    /**
     * 정문출문_고담주차장 연동
     *
     * @param vstApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 19.
     */
    Boolean executeFrontDoorOutProcLprInfoIF(Integer vstApplNo);

    /**
     * FrontDoor Card IF
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 19.
     */
    Boolean executeFrontDoorCardIF(Map<String, Object> paramMap);

}
