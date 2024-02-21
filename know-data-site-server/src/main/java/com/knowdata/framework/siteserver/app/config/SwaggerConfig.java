//package com.knowdata.framework.siteserver.app.config;
//
//import cn.loveconnect.userservice.biz.common.interceptor.SwaggerInterceptor;
//import cn.loveconnect.userservice.biz.common.model.SwaggerProperties;
//import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.web.servlet.handler.MappedInterceptor;
//import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * @author Johnny
// * @project love-connect-user-service
// * @create-time 2023/3/30  23:40:17
// * @description ...
// * @reference-doc
// */
//
//@Configuration
//@EnableSwagger2
//@EnableKnife4j
//@Import(BeanValidatorPluginsConfiguration.class)
//public class SwaggerConfig extends SuperSwaggerConfig {
//    @Value("${swagger.basic.username}")
//    private String username;
//
//    @Value("${swagger.basic.password}")
//    private String password;
//
//    @Override
//    public SwaggerProperties swaggerProperties() {
//        return SwaggerProperties.builder()
//                //设置Swagger扫描的Controller路径，只有扫描到了才会生成接口文档
//                .apiBasePackage("cn.loveconnect.userservice.biz")
//                .title("user-service")
//                .description("后台接口文档")
//                .contactName("johnny")
//                .contactEmail("johnnyztsd@gmail.com")
//                .version("1.0")
//                .enableSecurity(true)
//                .build();
//    }
//
//    /* 必须在此处配置拦截器,要不然拦不到 swagger 的静态资源 */
//    @Bean
//    @ConditionalOnProperty(name = "swagger.basic.enable", havingValue = "true")
//    public MappedInterceptor getMappedInterceptor() {
//        return new MappedInterceptor(new String[]{"/doc.html", "/swagger-ui.html", "/webjars/**"},
//                new SwaggerInterceptor(username, password));
//    }
//}