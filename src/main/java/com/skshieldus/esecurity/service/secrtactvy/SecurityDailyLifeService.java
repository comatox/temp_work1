package com.skshieldus.esecurity.service.secrtactvy;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SecurityDailyLifeService {

    /**
     * 인력자원 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 07.
     */
    List<Map<String, Object>> licenseManageAdminManageList(HashMap<String, Object> paramMap);

    /**
     * 인력자원 관리 상세목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 07.
     */
    List<Map<String, Object>> licenseManageAdminManageEmpList(HashMap<String, Object> paramMap);

    /**
     * 인력자원 관리 상세목록 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 07.
     */
    Boolean licenseManageAdminManageEmpDelete(HashMap<String, Object> paramMap);

    /**
     * 인력자원 조회 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 08.
     */
    List<Map<String, Object>> licenseManageAdminEmpList(HashMap<String, Object> paramMap);

    /**
     * 인력자원 조회 목록 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 08.
     */
    CommonXlsViewDTO licenseManageAdminEmpListExcel(HashMap<String, Object> paramMap);

    /**
     * 자격증 관리 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 09.
     */
    List<Map<String, Object>> licenseManageList(HashMap<String, Object> paramMap);

    /**
     * 자격증 삭제
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 09.
     */
    Boolean licenseManageRemove(HashMap<String, Object> paramMap);

    /**
     * 자격증 등록
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 09.
     */
    Boolean licenseManageInsert(HashMap<String, Object> paramMap);

    /**
     * 자격증 관리 목록 총갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 21.
     */
    int licenseManageListCnt(HashMap<String, Object> paramMap);

    /**
     * 인력자원 관리 상세목록 총갯수
     *
     * @param paramMap
     * @return
     *
     * @author : "X0122721<jeyeon.kim@partner.sk.com>"
     * @since : 2021. 12. 21.
     */
    int licenseManageAdminManageEmpListCnt(HashMap<String, Object> paramMap);

}
