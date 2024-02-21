package com.knowdata.framework.core.application.config;

import com.knowdata.framework.core.application.config.logger.LoggersConfig;
import com.knowdata.framework.core.application.config.cache.CacheConfig;
import com.knowdata.framework.core.application.config.datasource.DataSourcesPoolConfig;
import com.knowdata.framework.core.application.config.tomcat.TomcatEmbedServerProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author johnny-zen
 * @version v1.0
 * @project bdp-data-service-parent
 * @create-time 2022/10/6 16:16
 * @description 当前应用工程的自定义配置项
 * @reference-doc
 *  [1] SpringBoot写配置文件报错“The elements [xxx,xxx] were left unbound.“ - CSDN - https://blog.csdn.net/cnds123321/article/details/118117356
 *      1. 需要手动添加【无参构造器】，或者使用lombok的 @NoArgsConstructor 注解
 *      2. {@link com.knowdata.framework.core.application.config.ApplicationConfiguration }
 */

@Data
@NoArgsConstructor
public class ApplicationConfiguration { /** "application-config" **/
    /** "application-config.log" **/
    private List<LoggersConfig> log;

    /** "application-config.cache" **/
    private CacheConfig cache;

    // tomcat-server
    private TomcatEmbedServerProperties tomcatServer;

    // datasources-pool-config:
    private DataSourcesPoolConfig datasourcesPoolConfig = DataSourcesPoolConfig.getDefaultDatasourcesPoolConfig();

    /** ------------------------------ **/

    public List<LoggersConfig> getLog() {
        return log;
    }

    public void setLog(List<LoggersConfig> log) {
        this.log = log;
    }

    public CacheConfig getCache() {
        return cache;
    }

    public void setCache(CacheConfig cache) {
        this.cache = cache;
    }

    public TomcatEmbedServerProperties getTomcatServer() {
        return tomcatServer;
    }

    public void setTomcatServer(TomcatEmbedServerProperties tomcatServer) {
        this.tomcatServer = tomcatServer;
    }

    public DataSourcesPoolConfig getDatasourcesPoolConfig() {
        return datasourcesPoolConfig;
    }

    public void setDatasourcesPoolConfig(DataSourcesPoolConfig datasourcesPoolConfig) {
        this.datasourcesPoolConfig = datasourcesPoolConfig;
    }

}
