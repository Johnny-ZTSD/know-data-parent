package com.knowdata.framework.core.application.config.datasource;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent:know-data-core
 * @create-time 2023/5/6 17:20
 * @description ...
 */
//@ConfigurationProperties(prefix = "some.properties")
@Data
@NoArgsConstructor
public class DataSourcesPoolConfig {
    /**
     * 从连接池获取连接对象的最大等待时长
     */
    private Long maxBorrowWaitingTimeMillis = 60000L;

    /**
     * 对象池每个 Key(数据源)最大实例化对象数
     */
    //@Value("${service-config.datasources.dbpool-config.default-config.max-total-per-key:100}")
    private Integer maxTotalPerKey;

    /**
     * 对象池每个key最大的闲置对象数
     */
    //@Value("${service-config.datasources.dbpool-config.default-config.max-idle-per-key:60}")
    private Integer maxIdlePerKey;

    /**
     * 对象池每个key最大的闲置对象数
     */
    //@Value("${service-config.datasources.dbpool-config.default-config.max-total:1000}")
    private Integer maxTotal;

    //@Value("${service-config.datasources.dbpool-config.default-config.test-on-create:true}")
    private Boolean testOnCreate;

    //@Value("${service-config.datasources.dbpool-config.default-config.test-on-borrow:false}")
    private Boolean testOnBorrow;

    //@Value("${service-config.datasources.dbpool-config.default-config.test-on-return:true}")
    private Boolean testOnReturn;

    public static DataSourcesPoolConfig getDefaultDatasourcesPoolConfig(){
        DataSourcesPoolConfig dataSourcesPoolConfig = new DataSourcesPoolConfig();
        dataSourcesPoolConfig.setMaxTotal(100);
        dataSourcesPoolConfig.setMaxIdlePerKey(3);
        dataSourcesPoolConfig.setMaxTotalPerKey(10);
        dataSourcesPoolConfig.setTestOnCreate(true);
        dataSourcesPoolConfig.setTestOnBorrow(true);
        dataSourcesPoolConfig.setTestOnReturn(true);
        return dataSourcesPoolConfig;
    }
}
