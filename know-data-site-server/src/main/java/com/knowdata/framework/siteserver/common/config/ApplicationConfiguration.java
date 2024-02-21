package com.knowdata.framework.siteserver.common.config;

//import cn.xxx.bd.datasource.common.serviceconfig.ServiceConfig;
import com.knowdata.framework.core.application.config.tomcat.TomcatEmbedServerProperties;
//import okhttp3.ConnectionPool;
//import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author johnny-zen
 * @version v1.0
 * @create-time 2023/5/29 18:32
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
@Configuration
public class ApplicationConfiguration {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    /**
     * 分页插件 TODO
     * //mybatis-extention-3.2.0 : com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     */
    //@Bean
    //public PaginationInterceptor paginationInterceptor() {//
    //    return new PaginationInterceptor();
    //}

    /**
     * 没有实例化 RestTemplate 时，初始化 RestTemplate
     * @return
     */
    @ConditionalOnMissingBean(RestTemplate.class)
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();//方式1 默认的http客户端
        //RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());//方式2 自定义http客户端
        return restTemplate;
    }

    /**
     * @description for elasticsearch query
     * @date 2022/11/9 下午6:00
     */
//    @Bean(name = "okHttpClient")
//    public OkHttpClient client() {
//        logger.info("okHttpClient is loaded!");
//        return new OkHttpClient().newBuilder()
//                .callTimeout(1, TimeUnit.MINUTES)
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .readTimeout(1, TimeUnit.MINUTES)
//                .connectionPool(new ConnectionPool(500, 5L, TimeUnit.MINUTES))
//                .build();
//    }

    @Bean("tomcatEmbedServerProperties")
    @ConfigurationProperties(prefix = "application-config.tomcat-server", ignoreInvalidFields = true, ignoreUnknownFields = true)
    public TomcatEmbedServerProperties tomcatEmbedServerProperties(){
        logger.info("service-config configuration bean is loading.");
        return new TomcatEmbedServerProperties();
    }

    /**
     * 本应用服务在注册中心的自定义配置
     * @return
     */
    @Bean("applicationConfigurationBean")//serviceConfig
    @ConfigurationProperties(prefix = "service-config", ignoreInvalidFields = true, ignoreUnknownFields = true)
    public com.knowdata.framework.core.application.config.ApplicationConfiguration applicationConfigurationBean(){
        logger.info("application-config configuration bean is loading.");
        return new com.knowdata.framework.core.application.config.ApplicationConfiguration();
    }
}
