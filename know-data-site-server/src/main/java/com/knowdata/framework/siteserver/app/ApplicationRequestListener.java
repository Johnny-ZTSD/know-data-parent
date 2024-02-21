package com.knowdata.framework.siteserver.app;

//import cn.xxx.bd.datasource.app.common.util.LogExcludedPathsUtil;
import com.google.gson.Gson;
import com.knowdata.framework.siteserver.common.util.LogExcludedPathsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/4/3 15:50
 * @description ...
 * @reference-doc
 *  [1] springboot监听http请求 - CSDN - https://blog.csdn.net/m0_67402096/article/details/124421427
 */
@WebListener
public class ApplicationRequestListener implements ServletRequestListener {
    private Logger logger = LoggerFactory.getLogger(ApplicationRequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        //servletPath
        String servletPath = request.getServletPath();
        if(!LogExcludedPathsUtil.isExcludedPath(servletPath)){
            logger.debug("request destroyed ... ");
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ServletRequest servletRequest = servletRequestEvent.getServletRequest();
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        //servletPath
        String servletPath = request.getServletPath();
        // query params
        Map<String, String[]> paramtersMap = servletRequest.getParameterMap();
        // protocol
        String protocol = servletRequest.getProtocol();
        // headers
        Map<String, String> headers = getHeaders(request);
        // method
        String method = request.getMethod();
        if(!LogExcludedPathsUtil.isExcludedPath(servletPath)){
            logger.info("servletPath:{} | protocol : {}, method: {} , headers: {}, paramters: {} | request initialized ...", servletPath, protocol, method, new Gson().toJson(headers), new Gson().toJson( paramtersMap) );
        }
    }

    private static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name	= enumeration.nextElement();
            String value = request.getHeader(name);
            headerMap.put(name, value);
        }
        return headerMap;
    }
    private static Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> parameterMap = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name	= enumeration.nextElement();
            String value = request.getParameter(name);
            parameterMap.put(name, value);
        }
        return parameterMap;
    }
}