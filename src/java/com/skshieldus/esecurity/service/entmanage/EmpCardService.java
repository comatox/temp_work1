package com.skshieldus.esecurity.service.entmanage;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface EmpCardService {

    /**
     * 사원증 발급 현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    List<Map<String, Object>> selectEmpCardList(Map<String, Object> paramMap);

    /**
     * 사원증 발급 현황 조회 건수
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    Integer selectEmpCardListCnt(Map<String, Object> paramMap);

    /**
     * 사원증 발급 현황 조회
     *
     * @param empcardApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    Map<String, Object> selectEmpCard(Integer empcardApplNo);

    /**
     * 사원증 신규발급 현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    List<Map<String, Object>> selectNewEmpCardList(Map<String, Object> paramMap);

    /**
     * 사원증 신규발급 현황 조회 건수
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 28.
     */
    Integer selectNewEmpCardListCnt(Map<String, Object> paramMap);

    /**
     * 사원증 신규발급 저장
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 27.
     */
    Boolean saveNewEmpCard(MultipartFile file, Map<String, Object> paramMap);

    /**
     * 사원증 신규발급 일괄 신청
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 9. 27.
     */
    Boolean insertNewEmpCardAll(Map<String, Object> paramMap);

    /**
     * 악세서리 신청
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 12.
     */
    Boolean insertAccessory(Map<String, Object> paramMap);

    /**
     * 악세서리 신청 현황 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 12.
     */
    List<Map<String, Object>> selectAccessoryList(Map<String, Object> paramMap);

    /**
     * 악세서리 신청 현황 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 12.
     */
    Integer selectAccessoryListCnt(Map<String, Object> paramMap);

    /**
     * 사원증 (재)발급 신청
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 10. 14.
     */
    Boolean insertEmpCard(MultipartFile file, Map<String, Object> paramMap);

    /**
     * 카드번호를 이용한 통합사번 조회 I/F
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 11. 30.
     */
    Map<String, Object> selectOnedayIdCardIf(Map<String, Object> paramMap);

    /**
     * (관리자) 액세서리 발급현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    List<Map<String, Object>> selectAdmAccessoryList(Map<String, Object> paramMap);

    /**
     * (관리자) 액세서리 발급현황 목록 건수 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 15.
     */
    Integer selectAdmAccessoryListCnt(Map<String, Object> paramMap);

    /**
     * (관리자) 액세서리 수령확인/취소 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2021. 12. 17.
     */
    Boolean updateAdmAccessoryStatus(Map<String, Object> paramMap);

    /**
     * (관리자) 일일 사원증 발급 및 현황 목록
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    List<Map<String, Object>> selectAdmOnedayCardList(Map<String, Object> paramMap);

    /**
     * (관리자) 일일 사원증 발급 및 현황 목록 건수
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    Integer selectAdmOnedayCardListCnt(Map<String, Object> paramMap);

    /**
     * (관리자) 일일 사원증 반납 처리
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    Boolean returnOnedayCard(Map<String, Object> paramMap);

    /**
     * (관리자) 일일 사원증 발급 및 현황 상세 조회
     *
     * @param empcardApplNo
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 13.
     */
    Map<String, Object> selectAdmOnedayCard(Integer empcardApplNo);

    /**
     * (관리자) 일일 사원증 발급 등록
     *
     * @param paramMap
     * @return
     *
     * @author : X0121126<woogon.kim@partner.sk.com>
     * @since : 2022. 1. 14.
     */
    Map<String, Object> insertAdmOnedayCard(Map<String, Object> paramMap);

    /**
     * 일일 사원증 발급 엑셀 다운로드
     *
     * @param paramMap
     * @return
     */
    CommonXlsViewDTO admOnedayCardExcelDownload(HashMap<String, Object> paramMap);

}
