package com.knowdata.framework.core.application.config.cache;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent:know-data-core
 * @create-time 2023/1/12 13:54
 * @description 缓存项配置
 */
public class CacheServiceConfig {
    /**
     * 缓存名称
     * @description 暂无任何用途，仅用于语义化描述
     * @sample-value "LOCAL_CACHE#VHR#INFLUXDB#DWD_VB_CONDITION_INFO_FIELDS"
     */
    private String cacheName;

    /**
     * cache-key-format: "{serviceId}#{serviceVersion}"
     * @sample-value 100042
     */
    private String serviceId;
    /**
     * @sample-value "v1.0"
     */
    private String serviceVersion;

    /**
     * @sample-value "show field keys on bdp_dwd from dwd_vb_condition_info_ri"
     */
    private String querySql;

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    @Override
    public String toString() {
        return "CacheServiceConfig{" +
                "cacheName='" + cacheName + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", serviceVersion='" + serviceVersion + '\'' +
                ", querySql='" + querySql + '\'' +
                '}';
    }
}
