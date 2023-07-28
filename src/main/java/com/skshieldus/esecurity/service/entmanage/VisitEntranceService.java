package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.List;
import java.util.Map;

public interface VisitEntranceService {

    /**
     * 정문 인원 출입 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 25.
     */
    List<Map<String, Object>> selectFrontDoorList(Map<String, Object> paramMap);

    /**
     * 정문 인원 출입 - 인쇄 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    Boolean executeFrontDoorPrint(Map<String, Object> paramMap);

    /**
     * 정문 인원 출입 - 입문 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    Map<String, Object> executeFrontDoorInprocess(Map<String, Object> paramMap);

    /**
     * 정문 인원 출입 - 입문 Reset
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    Boolean executeFrontDoorInReset(Map<String, Object> paramMap);

    /**
     * 정문 인원 출입 - 출문 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 1.
     */
    Map<String, Object> executeFrontDoorOutprocess(Map<String, Object> paramMap);

    /**
     * 정문 인원 출입 - 출문 Reset
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    Boolean executeFrontDoorOutReset(Map<String, Object> paramMap);

    /**
     * 정문 인원 출입 - 재출입 Reset
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 7.
     */
    Boolean executeFrontDoorReIn(Map<String, Object> paramMap);

    /**
     * 정문 인원 출입 - Cube 메세지 응답 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 6.
     */
    Boolean receieveCubeByFrontDoor(Map<String, Object> paramMap);

    /**
     * 정문 전산기기 반입 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 10.
     */
    List<Map<String, Object>> selectCarryInPcList(Map<String, Object> paramMap);

    /**
     * 정문 전산기기 반입 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 10.
     */
    Integer selectCarryInPcListCnt(Map<String, Object> paramMap);

    /**
     * 정문 전산기기 반입 - 모바일 이용동의 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 10.
     */
    Boolean updateMobileUseApply(Map<String, Object> paramMap);

    /**
     * 정문 전산기기 반입 - 바코드 인쇄
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 10.
     */
    Map<String, Object> executePrintBarcode(Map<String, Object> paramMap);

    /**
     * 정문 전산기기 반입 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 13.
     */
    Map<String, Object> selectCarryInPc(Map<String, Object> paramMap);

    /**
     * 정문 전산기기 반입 취소
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 13.
     */
    Boolean cancelCarryInPc(Map<String, Object> paramMap);

    /**
     * 정문 전산기기 반입
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 13.
     */
    Boolean intoCarryInPc(Map<String, Object> paramMap);

    /**
     * 건물 출입 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    List<Map<String, Object>> selectBuildingPassList(Map<String, Object> paramMap);

    /**
     * 건물 출입 - Gate 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    List<Map<String, Object>> selectBuildingPassGateList(Map<String, Object> paramMap);

    /**
     * 건물 출입 - 입문 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    Boolean executeBuildingInprocess(Map<String, Object> paramMap);

    /**
     * 건물 출입 - 출문 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    Boolean executeBuildingOutprocess(Map<String, Object> paramMap);

    /**
     * 건물 출입 - 재출입 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 21.
     */
    Boolean executeBuildingPassReInOut(Map<String, Object> paramMap);

    /**
     * 납품출입 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 27.
     */
    List<Map<String, Object>> selectDeliveryPassList(Map<String, Object> paramMap);

    /**
     * 납품출입 목록건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 27.
     */
    Integer selectDeliveryPassListCnt(Map<String, Object> paramMap);

    /**
     * 납품출입 상세 조회
     *
     * @param dlvAppNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 27.
     */
    Map<String, Object> selectDeliveryPass(Integer dlvAppNo);

    /**
     * 납품 출입 - 입문 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    Map<String, Object> executeDeliveryPassInprocess(Map<String, Object> paramMap);

    /**
     * 납품 출입 - 입문 Reset
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    Boolean executeDeliveryPassInReset(Map<String, Object> paramMap);

    /**
     * 납품 출입 - 출문 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    Map<String, Object> executeDeliveryPassOutprocess(Map<String, Object> paramMap);

    /**
     * 납품 출입 - 출문 Reset
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 28.
     */
    Boolean executeDeliveryPassOutReset(Map<String, Object> paramMap);

    /**
     * 건물출입 현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 8.
     */
    List<Map<String, Object>> selectBuildingPassHistList(Map<String, Object> paramMap);

    /**
     * 건물출입 현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 8.
     */
    Integer selectBuildingPassHistListCnt(Map<String, Object> paramMap);

    /**
     * 건물출입 현황 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 2. 8.
     */
    CommonXlsViewDTO selectBuildingPassHistListExcel(Map<String, Object> paramMap);

}
