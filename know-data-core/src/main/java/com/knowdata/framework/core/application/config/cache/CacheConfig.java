package com.knowdata.framework.core.application.config.cache;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent:know-data-core
 * @create-time 2023/1/12 14:32
 * @description 缓存配置
 */
//@Component("com.knowdata.bd.dataservice.common.dto.serviceconfig.CacheConfg")
//@RefreshScope
//@ConfigurationProperties(prefix = "service-config.cache")

@Configuration("com.knowdata.bd.dataservice.common.dto.serviceconfig.CacheConfg")
/**
 * 从 nacos 获取 List/数组类型、复杂类对象的配置，可以使用@NacosConfigurationProperties
 */
public class CacheConfig {
    /**
     * 缓存策略
     */
    private CacheStrategyConfig strategy;

    private List<CacheServiceConfig> cacheServices;

    /**
     * 默认配置 + 防止空指针(调用 strategy 时)
     */
    private static CacheStrategyConfig DEFAULT_STRATEGY_CONFIG;

    /**
     * 默认配置 + 防止空指针(调用 items 时)
     */
    private static List<CacheServiceConfig> DEFAULT_SERVICES_CONFIG;

    static {
        DEFAULT_STRATEGY_CONFIG = CacheStrategyConfig.getDefaultConfig();
        DEFAULT_SERVICES_CONFIG = new ArrayList<>();
    }

    public CacheConfig() {
    }

    public CacheConfig(CacheStrategyConfig strategy, List<CacheServiceConfig> cacheServices) {
        this.strategy = strategy;
        this.cacheServices = cacheServices;
    }

    public CacheStrategyConfig getStrategy() {
        return strategy!=null?strategy:DEFAULT_STRATEGY_CONFIG;
    }

    public void setStrategy(CacheStrategyConfig strategy) {
        this.strategy = strategy;
    }

    public List<CacheServiceConfig> getCacheServices() {
        return cacheServices!=null?cacheServices:DEFAULT_SERVICES_CONFIG;
    }

    public void setCacheServices(List<CacheServiceConfig> cacheServices) {
        this.cacheServices = cacheServices;
    }

    @Override
    public String toString() {
        return "CacheConfg{" +
                "strategy='" + strategy + '\'' +
                ", cacheServices=" + cacheServices +
                '}';
    }
}
