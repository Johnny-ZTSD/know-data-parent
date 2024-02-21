//package com.knowdata.framework.siteserver.app.config;
//
//import com.knowdata.framework.core.util.lang.ObjectUtils;
//import com.knowdata.framework.siteserver.app.model.SwaggerProperties;
//import org.springframework.context.annotation.Bean;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.util.Collections;
//import java.util.List;
//
////import com.google.common.base.Predicates;
//
//
///**
// * @author Johnny
// * @project love-connect-user-service
// * @create-time 2023/3/30  23:41:02
// * @description Swagger 基础配置
// * @reference-doc
// *  [1] SpringBoot项目配置Swagger - CSDN - https://blog.csdn.net/adminwxc/article/details/127744816
// *  [2] Swagger 分组配置 - CSDN - https://blog.csdn.net/weixin_40516924/article/details/113059588
// *  [3] swagger登录进行用户名和密码认证 - CSDN - https://www.jianshu.com/p/0b956fac8780
// */
//public abstract class SuperSwaggerConfig {
//    /**
//     * 自定义 Swagger配置
//     */
//    public abstract SwaggerProperties swaggerProperties();
//
//    @Bean
//    public Docket docket() {
//        SwaggerProperties swaggerProperties = swaggerProperties();
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo(swaggerProperties))
//                // 选择那些路径和 api 会生成 document
//                .select()
//                // 对所有api进行监控
//                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
//                //不显示错误的接口地址,错误路径不监控
//                //.paths(Predicates.not(PathSelectors.regex("/error.*")))
//                // 对根下所有路径进行监控
//                //.paths(PathSelectors.regex("/.*"))
//                .paths(PathSelectors.any())
//                .build()
//                .groupName("default-group");
//
//        if (swaggerProperties.isEnableSecurity()) {
//            ApiKey apiKey = securitySchemes();
//            if(!ObjectUtils.isEmpty(apiKey)){
//                docket.securitySchemes( Collections.singletonList(apiKey) )
//                      .securityContexts(Collections.singletonList(securityContexts()));
//            }
//        }
//        return docket;
//    }
//
//    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
//        return new ApiInfoBuilder()
//                .title(swaggerProperties.getTitle() + " 服务API接口文档")
//                .description(swaggerProperties.getDescription())
//                .version(swaggerProperties.getVersion())
//
//                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
//                .version(swaggerProperties.getVersion())
//                .build();
//    }
//
//    private ApiKey securitySchemes() {
//        //设置请求头信息
////        return new ApiKey("token", "accessToken", "header");
//        return null;
//    }
//
//    private SecurityContext securityContexts() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                /* operationSelector : springfox 3.0.0 api */
//                .operationSelector(operationContext -> true)
//                .build();
//    }
//
//    private List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Collections.singletonList(new SecurityReference("Authorization", authorizationScopes));
//    }
//
//}
//
