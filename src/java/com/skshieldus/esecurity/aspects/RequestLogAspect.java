package com.skshieldus.esecurity.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
@Slf4j
public class RequestLogAspect {

    @Before("within(@(@org.springframework.stereotype.Controller *) *)")
    public void beforeLog(final JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        log.info("<BeforeExecution> Date: {} || RequestURL: {} || RequestMethod: {} || Method Name: {} || Args => {} from {}, menu-id = {}",
            sdf.format(new Date()),
            request.getRequestURI(),
            request.getMethod(),
            joinPoint.getSignature().toShortString(),
            Arrays.asList(joinPoint.getArgs()),
            request.getRemoteAddr(),
            request.getHeader("menu-id")
        );
    }

    @Around("within(@(@org.springframework.stereotype.Controller *) *)")
    public Object aroundLog(final ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Object value = proceedingJoinPoint.proceed();

        long duration = System.currentTimeMillis() - start;

        log.info("<Around> {} {} from {}, menu-id = {} || took {} ms",
            request.getMethod(),
            request.getRequestURI(),
            request.getRemoteAddr(),
            request.getHeader("menu-id"),
            duration);

        return value;
    }

}
