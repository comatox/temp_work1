package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.common.SessionInfoVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AuthMenuManageService {

    /**
     * 환경설정 권한/메뉴관리 권한목록조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 16
     */
    List<Map<String, Object>> selectAuthList(HashMap<String, Object> paramMap);

    /**
     * 환경설정 권한/메뉴관리 권한/메뉴목록조회
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 16
     */
    Map<String, Object> selectAuthMenuList(Map<String, Object> paramMap);

    /**
     * 환경설정 권한/메뉴관리 저장
     *
     * @return
     *
     * @author : X0125104 <won.shin@partner.sk.com>
     * @since : 2022. 02. 16
     */
    boolean saveAuthMenuManage(SessionInfoVO sessionInfoVO, Map<String, Object> paramMap);

}