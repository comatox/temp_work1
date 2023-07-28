package com.skshieldus.esecurity.service.entmanage;

import java.util.List;
import java.util.Map;

public interface RestrictVisitService {

    /**
     * 업체등록 신청현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    List<Map<String, Object>> selectRestrictCompList(Map<String, Object> paramMap);

    /**
     * 업체등록 신청현황 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    Map<String, Object> selectRestrictCompView(Map<String, Object> paramMap);

    /**
     * 업체등록 신청현황 제제대상 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    List<Map<String, Object>> selectRestrictHistCompList(Map<String, Object> paramMap);

    /**
     * 회원가입 신청현황 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    List<Map<String, Object>> selectRestrictEmpList(Map<String, Object> paramMap);

    /**
     * 회원가입 신청현황 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    Map<String, Object> selectRestrictEmpView(Map<String, Object> paramMap);

    /**
     * 회원가입 신청현황 제제대상 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    List<Map<String, Object>> selectRestrictHistEmpList(Map<String, Object> paramMap);

    /**
     * 업체 제제대상 해제 처리
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    boolean updateRestrictCompResolveComp(Map<String, Object> paramMap);

    /**
     * 회원 제제대상 해제 처리
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 12. 1.
     */
    boolean updateRestrictEmpResolveEmp(Map<String, Object> paramMap);

}
