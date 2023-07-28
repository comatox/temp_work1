package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.common.model.ListDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.Map;

public interface UserAuthManageService {

    /**
     * 환경설정 사용자/권한관리 목록조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 25
     */
    ListDTO<Map<String, Object>> selectUserAuthList(Map<String, Object> paramMap);

    /**
     * 환경설정 사용자/권한관리 목록 전체건수 조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 25
     */
    Integer selectUserAuthListCnt(Map<String, Object> paramMap);

    /**
     * 환경설정 사용자/권한관리 권한목록조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 25
     */
    ListDTO<Map<String, Object>> selectUserAuthManageEmpAuthList(Map<String, Object> paramMap);

    /**
     * 환경설정 사용자/권한관리 저장
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 25
     */
    boolean saveUserAuthManage(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

}