package com.skshieldus.esecurity.service.sysmanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GroupManageService {

    /**
     * 사업장관리 목록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    List<Map<String, Object>> selectCorpManageList(HashMap<String, Object> paramMap);

    /**
     * 사업장관리 목록 총 갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    int selectCorpManageListCnt(HashMap<String, Object> paramMap);

    /**
     * 부서관리 목록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    List<Map<String, Object>> selectDeptManageList(HashMap<String, Object> paramMap);

    /**
     * 부서관리 목록 총 갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    int selectDeptManageListCnt(HashMap<String, Object> paramMap);

    /**
     * 직위관리 목록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    List<Map<String, Object>> selectJwManageList(HashMap<String, Object> paramMap);

    /**
     * 직위관리 목록 총 갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2022. 02. 03.
     */
    int selectJwManageListCnt(HashMap<String, Object> paramMap);

    /**
     * 부서관리 등록/수정
     *
     * @param paramMap
     * @return
     */
    Boolean saveDeptManage(Map<String, Object> paramMap);

    /**
     * 사용자관리 조회
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> selectUserManageList(Map<String, Object> paramMap);

    /**
     * 사용자관리 상세조회
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> selectUserManageDetail(Map<String, Object> paramMap);

    /**
     * 사용자관리 중복아이디 체크
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> selectUserManageCheckId(Map<String, Object> paramMap);

    /**
     * 사용자관리 직책 목록 조회
     *
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectUserManageJcList(Map<String, Object> paramMap);

    /**
     * 사용자관리 직위 목록 조회
     *
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectUserManageJwList(Map<String, Object> paramMap);

    /**
     * 사용자관리 중복사번 체크
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> selectUserManageCheckEmpId(Map<String, Object> paramMap);

    /**
     * 사용자관리 저장
     *
     * @param paramMap
     * @return
     */
    int updateUserManage(Map<String, Object> paramMap);



    /**
     * 하이닉스 사용자 등록 조회
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> selectHynixUserList(Map<String, Object> paramMap);

    /**
     * 하이닉스 사용자 등록
     *
     * @param paramMap
     * @return
     */
    int insertHynixUser(Map<String, Object> paramMap);

    /**
     * 카드키 결재권한 조회
     *
     * @param paramMap
     * @return
     */
    Map<String, Object> selectCardKeyUserList(Map<String, Object> paramMap);

}