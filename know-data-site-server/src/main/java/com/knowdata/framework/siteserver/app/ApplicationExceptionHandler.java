package com.knowdata.framework.siteserver.app;

import com.knowdata.framework.core.exception.ApplicationException;
import com.knowdata.framework.core.model.dto.XResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/5/29 17:40
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
@ControllerAdvice // controller 切面注解
public class ApplicationExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(value = ApplicationException.class)   //表示当 ApplicationException 的异常被抛出时，执行此方法
    public XResponse handleApplicationException(ApplicationException exception){
        logger.error("Operation Fail! errorCode： {}, errorMessage: {}, cause : {}", exception.getErrorCode(), exception.getErrorMessage(),  exception.getCause());
        return XResponse.failure(exception.getErrorCode(), exception.getErrorMessage());
    }

    //未知异常
    @ExceptionHandler
    public XResponse handleOtherException(Exception e){
        //ModelAndView mv=new ModelAndView(); mv.addObject("tips","请稍后测试"); mv.setViewName("defaultError");
        logger.error("Operation Fail! errorMessage: {}, cause : {}", e.getMessage(),  e.getCause());
        XResponse response = XResponse.failure(null, e.getMessage());
        return response;
    }
}
