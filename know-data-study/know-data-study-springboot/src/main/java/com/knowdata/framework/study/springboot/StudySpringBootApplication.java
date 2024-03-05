package com.knowdata.framework.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
@EnableAsync // 启用异步特性 ，对应的 ApplicationEvent 机制中 Listener 类需要开启异步的方法增加 @Async 注解
@RestController
@SpringBootApplication
public class StudySpringBootApplication {
    public static void main( String[] args ) {
        SpringApplication.run(StudySpringBootApplication.class, args);
    }

    @RequestMapping("/hello")
    String home() {
        return "Hello World!This is a spring boot application!";
    }
}
