package com.knowdata.framework.siteserver.app;

import com.knowdata.framework.core.util.spring.ApplicationContextUtils;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-date 2022/7/12 19:51
 * @description 本工程的启动类
 * @reference-doc
 */
//@EnableFeignClients // [feign] Feign 客户端调用第三方接口，本工程暂不涉及
//@EnableDiscoveryClient(autoRegister = true) // [nacos] 注册中心 | feign 整合 nacos 需要启用此注解(服务提供端 + Feign 客户端) | true: 开启服务自动注册功能,项目启动后能在 nacos 的web端界面看到服务的相关信息,且具备拉取服务信息的功能(前提是 nacos.discovery.enabled 不为 false)

@EnableSwagger2 // [swagger] 开启swagger2
@Api(tags = "data-datasource-app 启动模块") // [swagger] 作用范围: 模块类 | tags = "模块说明"

@MapperScan("com.knowdata.framework.siteserver.app") // [mybatis]

@ServletComponentScan(basePackages = "com.knowdata.framework.siteserver.app")// 扫描 Servlet 包，例如: ApplicationRequestListener

@SpringBootApplication // [spring-boot]
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController // // [spring-web(mvc / webflux)]
//@RequestMapping("/") // [spring-web(mvc / webflux)]
//@ComponentScan(basePackages = {"com.knowdata.framework.siteserver.app"}) // [spring-context]
public class KnowDataSiteApplication {
    private static final Logger logger = LoggerFactory.getLogger(KnowDataSiteApplication.class);

    @Autowired
    private ApplicationContext applicationContext;

    public static void main( String[] args ) {
        logger.info("server startup - start");
        //ApplicationContext applicationContext = SpringApplication.run(DataServiceBizApplication.class, args);

        SpringApplication springApplication = new SpringApplication(KnowDataSiteApplication.class);
        ConfigurableApplicationContext applicationContext = springApplication.run(args);

        ApplicationContextUtils.setApplicationContext(applicationContext);
        logger.info("server startup - end");
    }
}

