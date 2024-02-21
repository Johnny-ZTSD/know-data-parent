package com.knowdata.framework.core.application.config.tomcat;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent:know-data-core
 * @create-time 2023/4/11 15:56
 * @description ...
 */
//@ComponentScan
//@Configuration
//@ConfigurationProperties(
//    prefix="service-config.tomcat-server"
//    , ignoreUnknownFields = true
//)
public class TomcatEmbedServerProperties {
    private Integer port;

    private Integer minSpareThreads;

    private Integer maxThreads;

    private Integer acceptCount;

    private Integer maxConnections;

    private Integer maxKeepAliveRequests;

    private Integer keepAliveTimeout;

    private Integer connectionTimeout;

    public TomcatEmbedServerProperties(Integer port, Integer minSpareThreads, Integer maxThreads, Integer acceptCount, Integer maxConnections, Integer maxKeepAliveRequests, Integer keepAliveTimeout, Integer connectionTimeout) {
        this.port = port;
        this.minSpareThreads = minSpareThreads;
        this.maxThreads = maxThreads;
        this.acceptCount = acceptCount;
        this.maxConnections = maxConnections;
        this.maxKeepAliveRequests = maxKeepAliveRequests;
        this.keepAliveTimeout = keepAliveTimeout;
        this.connectionTimeout = connectionTimeout;
    }

    public TomcatEmbedServerProperties() {
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getMinSpareThreads() {
        return minSpareThreads;
    }

    public void setMinSpareThreads(Integer minSpareThreads) {
        this.minSpareThreads = minSpareThreads;
    }

    public Integer getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(Integer maxThreads) {
        this.maxThreads = maxThreads;
    }

    public Integer getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(Integer acceptCount) {
        this.acceptCount = acceptCount;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }

    public Integer getMaxKeepAliveRequests() {
        return maxKeepAliveRequests;
    }

    public void setMaxKeepAliveRequests(Integer maxKeepAliveRequests) {
        this.maxKeepAliveRequests = maxKeepAliveRequests;
    }

    public Integer getKeepAliveTimeout() {
        return keepAliveTimeout;
    }

    public void setKeepAliveTimeout(Integer keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    private static boolean isPositive(int value) {
        return value > 0;
    }

    @Override
    public String toString() {
        return "TomcatEmbedServerProperties{" +
                "port=" + port +
                ", minSpareThreads=" + minSpareThreads +
                ", maxThreads=" + maxThreads +
                ", acceptCount=" + acceptCount +
                ", maxConnections=" + maxConnections +
                ", maxKeepAliveRequests=" + maxKeepAliveRequests +
                ", keepAliveTimeout=" + keepAliveTimeout +
                ", connectionTimeout=" + connectionTimeout +
                '}';
    }
}
