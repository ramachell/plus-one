package com.example.plusone.discount.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import java.util.Enumeration;

@Slf4j
public class RequestUtils extends WebUtils {

    /**
     * Http 요청 객체의 값을 출력 하는 메소드
     * @param req
     */
    public static void printRequestHeader(HttpServletRequest req) {
        Enumeration<String> headerNames = req.getHeaderNames();


        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = req.getHeader(key);

            log.debug("header :: Key = " + key + " Value = " + value);

        }

        log.debug("req :: getQueryString = " + req.getQueryString());
        log.debug("req :: getServletPath = " + req.getServletPath());
        log.debug("req :: getContextPath = " + req.getContextPath());
        log.debug("req :: getRequestURL = " + req.getRequestURL());
        log.debug("req :: getRequestURI = " + req.getRequestURI());

    }

    /**
     * RequestContextHolder에 있는 HttpServletRequest를 얻는 메소드
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * RequestContextHolder의 HttpServletRequest에 있는 header 를 출력하는 메소드
     */
    public static void printRchRequestHeader() {
        printRequestHeader(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest());
    }

    /**
     * attribute 값을 가져 오기 위한 메소드
     *
     * @param String  attribute key name
     * @return Object attribute obj
     */
    public static Object getAttribute(String name) throws Exception {
        return (Object) RequestContextHolder.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * attribute 설정 메소드
     *
     * @param name attribute key name
     * @param object attribute obj
     * @return void
     */
    public static void setAttribute(String name, Object object) throws Exception {
        RequestContextHolder.getRequestAttributes().setAttribute(name, object, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 설정한 attribute 삭제 메소드
     *
     * @return void
     */
    public static void removeAttribute(String name) throws Exception {
        RequestContextHolder.getRequestAttributes().removeAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * session id얻는 메소드
     * @return String SessionId 값
     */
    public static String getSessionId() throws Exception  {
        return RequestContextHolder.getRequestAttributes().getSessionId();
    }
}
