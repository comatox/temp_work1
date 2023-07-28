package com.skshieldus.esecurity.service.secrtactvy;

import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.util.List;
import java.util.Map;

public interface SecretBoxCheckService {

    /**
     * 비밀문서함 점검결과 목록 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    List<Map<String, Object>> selectSecretBoxCheckList(Map<String, Object> paramMap);

    /**
     * 비밀문서함 점검결과 상세 조회
     *
     * @param scboxChkApplNo
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    Map<String, Object> selectSecretBoxCheck(Integer scboxChkApplNo);

    /**
     * 비밀문서함 점검결과 EGSS 기본정보 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    Map<String, Object> selectEGSSSecretBoxCheck(Map<String, Object> paramMap);

    /**
     * 비밀문서함 점검결과 점수 목록 EGSS 조회
     *
     * @param paramMap
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    List<Map<String, Object>> selectEGSSSecretBoxCheckSummary(Map<String, Object> paramMap);

    /**
     * 비밀문서함 점검결과 목록 조회 엑셀 저장
     *
     * @param paramMap
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    CommonXlsViewDTO selectSecretBoxCheckExcelList(Map<String, Object> paramMap);

    /**
     * 비밀문서함 점검결과 상신
     *
     * @param paramMap
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    Boolean insertSecretBoxCheck(Map<String, Object> paramMap);

    /**
     * 비밀문서함 점검결과 상신 문서 번호 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    Boolean updateSecretBoxCheckDocId(Map<String, Object> paramMap);

    /**
     * 비밀문서함 점검결과 상신 승인 결과 업데이트
     *
     * @param paramMap
     * @return
     *
     * @author : X0115378 <jaehoon5.kim@partner.sk.com>
     * @since : 2022. 02. 09.
     */
    Boolean updateSecretBoxCheckApplStat(Map<String, Object> paramMap);

}
