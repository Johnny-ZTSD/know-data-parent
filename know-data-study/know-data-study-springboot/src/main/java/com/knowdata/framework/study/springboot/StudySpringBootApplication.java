package com.knowdata.framework.study.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
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
