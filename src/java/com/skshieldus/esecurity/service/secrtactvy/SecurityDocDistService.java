package com.skshieldus.esecurity.service.secrtactvy;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.List;
import java.util.Map;

public interface SecurityDocDistService {

    /**
     * 부서 비문분류기준표 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 24.
     */
    List<Map<String, Object>> selectSecurityDocDistItemList(Map<String, Object> paramMap);

    /**
     * 부서 비문분류기준표 상세 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 24.
     */
    Map<String, Object> selectSecurityDocDistView(Map<String, Object> paramMap);

    /**
     * 부서 비문분류기준표 상세 비문(비밀/대외비) 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 24.
     */
    List<Map<String, Object>> selectSecurityDocDistViewList(Map<String, Object> paramMap);

    /**
     * 부서 비문분류기준표 상세 비문(비밀/대외비) 엑셀 다운로드
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 24.
     */
    CommonXlsViewDTO selectSecurityDocDistViewDownload(Map<String, Object> paramMap);

    /**
     * 팀 비문분류기준표 등록 (상신)
     *
     * @param paramMap
     * @return
     *
     * @author : "X0121127<sungbum.oh@partner.sk.com>"
     * @since : 2021. 9. 27.
     */
    boolean insertSecurityDocDist(Map<String, Object> paramMap);

}
