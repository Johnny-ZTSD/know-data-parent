package com.knowdata.framework.siteserver.app;

import cn.hutool.core.date.DateUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Johnny
 * @project love-connect-user-service
 * @create-time 2023/5/15  00:27:04
 * @description
 *  @InitBinder方法只对当前Controller生效，要想全局生效，可以使用@ControllerAdvice
 *  过@ControllerAdvice可以将对于控制器的全局配置放置在同一个位置，注解了@ControllerAdvice的类的方法可以使用@ExceptionHandler，@InitBinder，@ModelAttribute注解到方法上，这对所有注解了@RequestMapping的控制器内的方法有效。
 * @reference-doc
 *  [1] https://gitee.com/love-connect-team/love-connect-user-service/blob/master/user-service-biz/src/main/java/cn/loveconnect/userservice/biz/GlobalControllerAdvice.java
 */
@ControllerAdvice
public class GlobalControllerAdvice {
    /**
     * @description
     * @reference-doc
     *  [1] springMVC中Date类型入参 - 博客园-  https://www.cnblogs.com/guoyafenghome/p/8678730.html
     *  [2] springMVC之@InitBinder的用法 - CSDN - https://blog.csdn.net/weixin_43888891/article/details/127348918 [推荐]
     * @param request
     * @param binder
     */
    @InitBinder
    public void dateFormat(HttpServletRequest request, ServletRequestDataBinder binder){
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"), true));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class,
                new StringTrimmerEditor(true));
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                // DateUtil.parse是 hutool 当中的方法
                setValue(DateUtil.parse(text));
            }
        });
    }
}