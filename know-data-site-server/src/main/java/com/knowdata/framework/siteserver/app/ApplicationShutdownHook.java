package com.knowdata.framework.siteserver.app;

import com.knowdata.framework.core.model.server.ApplicationMemoryStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/3/21 12:00
 * @description 关机钩子
 * @reference-doc
 *  [1] [SpringBoot]Spring Boot Framework @ Environment / ApplicationContext & SpringApplication - 博客园 - https://www.cnblogs.com/johnnyzen/p/17237391.html
 * @usage
 *  方法1 : @Bean or @Component ...
 *  方法2 : META-INFO/spring.factories 文件下:
 *      # org.springframework.context.ApplicationListener=cn.xxx.bd.dataservice.biz.ApplicationShutdownHook
 *      # org.springframework.context.ApplicationListener=cn.xxx.bd.dataservice.biz.ApplicationStartupHook
 */
@Component
public class ApplicationShutdownHook implements DisposableBean, ApplicationListener<ContextClosedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationShutdownHook.class);

    /** step1 JVM ShutdownHook 先于 Spring Framework 的 ApplicationListener<ContextClosedEvent> 执行 **/
    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("shutdown hook, jvm runtime hook");
            logger.info("[MEMORY-STATUS]\n{}", ApplicationMemoryStatus.collect().toStringAsMB());
        }));
    }

    /** step2 Spring Framework 的 ApplicationListener<ContextClosedEvent> 先于 javax.annotation.PreDestroy 执行 **/
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("shutdown hook, ContextClosedEvent");
    }

    /** step3 javax.annotation.PreDestroy 先于 org.springframework.beans.factory.DisposableBean#destroy 执行 **/
    @PreDestroy
    public void preDestroy() {
        logger.info("shutdown hook, pre destroy");
    }

    /** step4 **/
    @Override
    public void destroy() throws Exception {
        logger.info("shutdown hook, disposable bean");
    }
}
