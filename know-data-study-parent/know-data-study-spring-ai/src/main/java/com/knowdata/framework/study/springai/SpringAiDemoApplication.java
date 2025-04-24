package com.knowdata.framework.study.springai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ai.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSpringAI
public class SpringAiDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringAiDemoApplication.class,  args);
    }
}