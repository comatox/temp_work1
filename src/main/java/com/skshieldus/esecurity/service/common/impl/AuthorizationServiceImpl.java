package com.skshieldus.esecurity.service.common.impl;

import com.skshieldus.esecurity.common.exception.EsecurityException;
import com.skshieldus.esecurity.model.common.MenuAuthCheckDTO;
import com.skshieldus.esecurity.model.common.SessionInfoVO;
import com.skshieldus.esecurity.repository.common.AuthorizationRepository;
import com.skshieldus.esecurity.service.common.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 인가된 메뉴 확인
 *
 * @author : X0115378 <jaehoon5.kim@partner.sk.com>
 * @since : 2021. 04. 19.
 */
@Slf4j
@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private AuthorizationRepository authorizationRepository;

    /**
     * 로그인한 사용자에게 인가된 메뉴ID 목록 조회
     *
     * @param headerMeta
     * @param sessionInfo
     * @param sessionInfo
     * @return 메뉴ID 목록
     */
    @Async
    public List<String> selectAuthorizedMenu(String headerMeta, SessionInfoVO sessionInfo) {
        List<String> result = null;
        try {
            log.info("\n   >>> selectAuthorizedMenu headerMeta: " + headerMeta);
            log.info("\n   >>> selectAuthorizedMenu sessionInfo: " + sessionInfo);
            result = authorizationRepository.selectAuthorizedMenu(sessionInfo.getEmpNo());

            //			JsonObject session = null;
            //
            //			if (strSession != null) {
            //
            //				session = new JsonObject(strSession);
            //
            //			} else if (sessionInfo != null) {
            //
            //				log.warn("\n   >>> selectAuthorizedMenu strSession is null?? " + strSession);
            //				session = new JsonObject(sessionInfo.toString());
            //				session.put("uuid", uuid);
            //
            //			}
            /**
             * ========== eSecurity 에서 필요에 의해 세션에 담을 정보 S ===============
             *
             */
            //			JsonObject eSecurity = new JsonObject();
            //			eSecurity.put("authorizedMenu", result);
            //			eSecurity.put("expireTime", System.currentTimeMillis() + (10 * 60 * 1000));	// 10 min.
            // eSecurity.put("companyno", companyno);  // TODO: 사용자의 사업장ID 값을 조회하여 담는 것도 고려해보자

            /**
             * ========== eSecurity 에서 필요에 의해 세션에 담을 정보 E ===============
             */
            //			log.info("\n   >>> selectAuthorizedMenu session: " + session);
            //			log.info("\n   >>> selectAuthorizedMenu eSecurity: " + eSecurity);
            //			RedisApiClient.addParamToSession(session, "eSecurity", eSecurity);

        } catch (Exception e) {

            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        }
        return result;
    }

    /**
     * 로그인한 사용자에게 인가된 메뉴인지 확인
     *
     * @param headerMeta
     * @param sessionInfo
     * @param menuId
     * @return
     */
    public Boolean selectMenuAuthCheck(String headerMeta, SessionInfoVO sessionInfo, String menuId) {
        Boolean result = false;
        Long startTime = System.currentTimeMillis();
        try {
            log.info("\n   >>> selectMenuAuthCheck start: " + headerMeta);
            log.info("\n   >>> selectMenuAuthCheck sessionInfo: " + sessionInfo);
            log.info("\n   >>> selectMenuAuthCheck sessionInfo.getEmpNo(): " + sessionInfo.getEmpNo());

            if (sessionInfo == null || sessionInfo.getEmpNo() == null) {
                return true;
            }

            if (menuId != null && menuId.length() <= 9) {
                MenuAuthCheckDTO menuAuthCheckDTO = new MenuAuthCheckDTO();
                menuAuthCheckDTO.setEmpId(sessionInfo.getEmpNo());
                menuAuthCheckDTO.setMenuId(menuId);
                Integer count = authorizationRepository.selectMenuAuthCheck(menuAuthCheckDTO);

                if (count > 0) {
                    result = true;
                }
            }
            else {
                result = true;
            }

            log.info("\n   >>> @Async selectAuthorizedMenu call: ");
            selectAuthorizedMenu(headerMeta, sessionInfo);
            log.info("\n   >>> selectMenuAuthCheck end: ");
        } catch (Exception e) {
            throw new EsecurityException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
                e.toString());
        } finally {
            log.info("[AUTH_CHECK] >>> selectMenuAuthCheck 걸린 시간: " + (System.currentTimeMillis() - startTime));
        }
        return result;
    }

}
