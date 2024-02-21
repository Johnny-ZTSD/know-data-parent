package com.knowdata.framework.siteserver.app.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author Johnny
 * @project love-connect-user-service
 * @create-time 2023/3/31  01:41:54
 * @description Spring Boot + JPA + MySQL 整合中还得配置DataSource，把它注入到Spring中接口
 * @reference-doc
 *  [1] springBoot启动提示If you want an embedded database (H2, HSQL or Derby), please put i - CSDN - https://blog.csdn.net/lvoyee/article/details/127651981
 */
@ComponentScan
@Configuration
@ConfigurationProperties(prefix="spring.datasource")
public class ApplicationDataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationDataSourceConfig.class);

    private String url;
    private String username;
    private String password;

    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}