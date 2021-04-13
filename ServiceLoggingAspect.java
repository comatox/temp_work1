package com.icomsys.cinnamon.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.icomsys.cinnamon.share.types.common.ServiceStatusType;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @Name ServiceAspect
 * @Description 서비스 공통 Logging AOP
 * @Author sungbumoh
 * @CreateDate 2020. 8. 13.
 */
@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    public static final String SERVICE_TIMER_TASK_NAME = "SERVICE_TIMER";
    public static final int SERVICE_LOGPRINT_MAXLINES = 10;
    public static final int SERVICE_LOGPRINT_MAXLENGTH = 150;

    /**
     * @Name processServiceLogging
     * @Description 서비스 요청 처리 시 요청 및 처리정보 출력
     * @Author sungbumoh
     * @CreateDate 2020. 8. 13.
     * 
     * @param joinPoint
     * @return
     * @throws Throwable
     *
     * @ChangeDescription
     */
    @SuppressWarnings("unchecked")
    @Around("execution(* com.icomsys.cinnamon.core..*Controller.*(..))")
    public Object loggingServiceRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // stop watch 설정
        StopWatch stopWatch = new StopWatch();
        // 시간체크 시작 지점
        stopWatch.start(SERVICE_TIMER_TASK_NAME);
        Date inPointTime = new Date();
        // 서비스 상태 유형 (CLEAN ~ CRITICAL)
        ServiceStatusType serviceStatus;
        // 응답 상태코드
        HttpStatus responseStatus = null;
        // JSON 문자열 처리 OM
        ObjectMapper objectMapper = new ObjectMapper();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // Request Parameter(body) 정보
        String requestBody = "";
        // Response Parameter(body) 정보
        String responseBody = "";

        ContentCachingRequestWrapper wrappingRequest = (ContentCachingRequestWrapper) request;
        if (wrappingRequest.getContentLength() > 0) {
            requestBody = new String(wrappingRequest.getContentAsByteArray());
            Object requestBodyObject = objectMapper.readValue(requestBody, Object.class);
            requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestBodyObject);
        }

        // Target 엔드포인트 실행
        Object result = joinPoint.proceed();

        // 결과 타입이 정상적으로 ResponseEntity 일 경우
        if (result instanceof ResponseEntity) {
            ResponseEntity<Object> response = (ResponseEntity<Object>) result;
            // 응답 상태코드 설정
            responseStatus = response.getStatusCode();
            // 응답 데이터 (to json string)
            if (response.getBody() != null)
                responseBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody());
        }

        // 시간체크 종료 지점
        stopWatch.stop();
        Date outPointTime = new Date();

        // 실행 시간 (inPointTime ~ outPointTime)
        double resultTime = stopWatch.getTotalTimeSeconds();

        // 실행 시간 별 서비스 상태 설정 (시간 수정은 ServiceStatusType enum type 수정)
        if (resultTime <= ServiceStatusType.CLEAN.getServiceTime()) {
            serviceStatus = ServiceStatusType.CLEAN;
        } else if (resultTime <= ServiceStatusType.WARNING.getServiceTime()) {
            serviceStatus = ServiceStatusType.WARNING;
        } else if (resultTime <= ServiceStatusType.BAD.getServiceTime()) {
            serviceStatus = ServiceStatusType.BAD;
        } else {
            serviceStatus = ServiceStatusType.CRITICAL;
        }

        // 요청 URL
        String servletPath = request.getServletPath();
        // 요청 METHOD (GET/POST/PUT/DELETE)
        String headerMethod = request.getMethod().toUpperCase();

        // 요청 Host
        String remoteHost = request.getRemoteHost();

        // 실행 Class, Method 정보
        String excuteClass = joinPoint.getTarget().getClass().getName();
        String excuteMethod = joinPoint.getSignature().getName();

        // 요청 IP
        String remoteIp = this.getIpFromRequest(request);

        // Console logging 처리
        log.info(" ");
        log.info("+============================================================================================");
        log.info("|------------------------------ CINNAMON API LOGGING PRINT IN -------------------------------");
        log.info("|   ");
        log.info("|   +-REQUEST INFO-+");
        log.info("|   {} {}", headerMethod, servletPath);
        log.info("|   query string: {}", request.getQueryString());
        log.info("|   ");
        log.info("|   +-REMOTE INFO-+");
        log.info("|   remote host: {}", remoteHost);
        log.info("|   remote addr: {}", remoteIp);
        log.info("|   ");
        log.info("|   +-PROCESS INFO-+");
        log.info("|   service class name: {}\t", excuteClass);
        log.info("|   service method name: {}\t", excuteMethod);
        log.info("|   service in time: {}\t", inPointTime);
        log.info("|   service out time: {}\t", outPointTime);
        log.info("|   service excute time: {}\t", resultTime + "s");
        log.info("|   service pass status [ {} ]", serviceStatus.getValue());
        if (responseStatus != null) {
            log.info("|   http status  [ {} ]", responseStatus);
        }
        log.info("|   ");
        log.info("|   +-DATA INFO-+");
        log.info("|   request data:");
        if (!StringUtils.isEmpty(requestBody)) {
            Arrays.asList(requestBody.lines().toArray(String[]::new)).stream().forEach(line -> {
                log.info("|      {}", line);
            });
        }
        log.info("|   response data:");
        if (!StringUtils.isEmpty(responseBody)) {
            Arrays.asList(responseBody.lines().toArray(String[]::new)).stream().forEach(line -> {
                log.info("|      {}", line);
            });
        }
        log.info("|   ");
        log.info("|------------------------------ CINNAMON API LOGGING PRINT OUT ------------------------------");
        log.info("+============================================================================================");
        log.info(" ");
        
        return result;
    }

    /**
     * @Name processExceptionLogging
     * @Description 에러 발생 시 에러정보 출력
     *              (예외처리 되지않은 Runtime Exception의 경우는 이곳을 타지 않음..)
     * @Author sungbumoh
     * @CreateDate 2020. 8. 13.
     * 
     * @param joinPoint
     * @param exception
     *
     * @ChangeDescription
     */
    @AfterThrowing(pointcut = "execution(* com.icomsys.cinnamon.core..*Controller.*(..))", throwing = "exception")
    public void loggingServiceException(JoinPoint joinPoint, Throwable exception) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // 요청 Host
        String remoteHost = request.getRemoteHost();
        // 요청 IP
        String remoteIp = this.getIpFromRequest(request);

        // 요청 URL
        String servletPath = request.getServletPath();
        // 요청 METHOD (GET/POST/PUT/DELETE)
        String headerMethod = request.getMethod().toUpperCase();

        // 실행 Class, Method 정보
        String excuteClass = joinPoint.getTarget().getClass().getName();
        String excuteMethod = joinPoint.getSignature().getName();

        log.error(" ");
        log.error("+============================================================================================");
        log.error("|----------------------------- CINNAMON ERROR LOGGING PRINT IN ------------------------------");
        log.error("|   ");
        log.error("|   +-LOGGING SERVLET-+");
        log.error("|   {} {}", headerMethod, servletPath);
        log.error("|   query string: {}", request.getQueryString());
        log.error("|   ");
        log.error("|   +-REMOTE INFO-+");
        log.error("|   remote host: {}", remoteHost);
        log.error("|   remote addr: {}", remoteIp);
        log.error("|   ");
        log.error("|   service class name: {}\t", excuteClass);
        log.error("|   service method name: {}\t", excuteMethod);
        log.error("|   ");
        log.error("|   +-ERROR INFO-+");
        if (exception.getCause() != null) {
            // Root Exception 출력
            log.error("|   error type: {}", exception.getCause().getClass().getSimpleName());
            // Exception 상세 (최대 라인까지만 출력)
            log.error("|   error detail: ");
            int[] exceptionPrintIndex = {0};
            Arrays.asList(ExceptionUtils.getStackFrames(exception)).stream().anyMatch(printLine -> {
                printLine = printLine.trim();
                exceptionPrintIndex[0]++;
                if (printLine.length() > SERVICE_LOGPRINT_MAXLENGTH) {
                    Splitter.fixedLength(SERVICE_LOGPRINT_MAXLENGTH).splitToList(printLine).stream().forEach(fixedPrintLine -> {
                        log.error("|      " + fixedPrintLine);
                    });
                } else {
                    log.error("|      " + printLine);
                }
                // 최대 라인까지만 출력
                return exceptionPrintIndex[0] > SERVICE_LOGPRINT_MAXLINES;
            });
        } else {
            log.error("|   unknown exception cause info");
        }
        log.error("|   ");
        log.error("|----------------------------- CINNAMON ERROR LOGGING PRINT OUT -----------------------------");
        log.error("+============================================================================================");
        log.error(" ");
    }

    /**
     * @Name getIpFormRequest
     * @Description 요청 정보에서 IP 추출
     * @Author sungbumoh
     * @CreateDate 2020. 8. 14.
     * 
     * @param request
     * @return
     *
     * @ChangeDescription
     */
    private String getIpFromRequest(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
