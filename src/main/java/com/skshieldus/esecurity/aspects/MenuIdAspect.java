package com.skshieldus.esecurity.aspects;

import com.skshieldus.esecurity.model.common.MenuDTO;
import com.skshieldus.esecurity.model.common.MenuSearchDTO;
import com.skshieldus.esecurity.repository.common.MenuRepository;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class MenuIdAspect {

    @Autowired
    private MenuRepository menuRepository;

    @After("@annotation(com.skshieldus.esecurity.aspects.MenuId)")
    public void afterMenuId(final JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MenuId menuIdAnnotation = method.getAnnotation(MenuId.class);
        String menuId = menuIdAnnotation.value();
        log.info("current menu id = {}", menuId);

        if (menuId != null) {
            MenuSearchDTO menuSearchDTO = new MenuSearchDTO();
            menuSearchDTO.setMenuId(menuId);
            MenuDTO menuDTO = menuRepository.selectMenuManageDetail(menuSearchDTO);

            String displayMenuId = menuId;
            if (menuDTO != null && !"Y".equals(menuDTO.getDisplayYn())) {
                displayMenuId = menuDTO.getUpMenuId();
            }
            request.setAttribute("menuId", menuId);
            request.setAttribute("displayMenuId", displayMenuId);
        }
    }

}
