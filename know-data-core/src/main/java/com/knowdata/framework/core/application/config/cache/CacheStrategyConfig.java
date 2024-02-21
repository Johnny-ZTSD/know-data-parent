package com.knowdata.framework.core.application.config.cache;

/**
 * @author johnny-zen
 * @version v1.0
 * @project know-data-parent:know-data-core
 * @create-time 2023/1/12 14:35
 * @description 缓存策略
 * @referece-doc
 *  [1] CacheBuilder - demo2s - https://www.demo2s.com/java/java-com-google-common-cache-cachebuilder.html
 *  [1] Guava Cache - jianshu - https://www.jianshu.com/p/d0d27cf44162
 */
public class CacheStrategyConfig {
    /**
     * 设置写入缓存后过期时间(xx秒后过期) | TimeUnit.SECONDS
     */
    private Integer refreshAfterWrite;
    private static Integer DEFAULT_REFRESH_FATER_WRITE = 600;

    /**
     * 缓存的并发级别
     * Guava提供了设置并发级别的api，使得缓存支持并发的写入和读取。
     * 同ConcurrentHashMap类似, Guava cache的并发也是通过分离锁实现。
     * 在一般情况下，将并发级别设置为服务器cpu核心数是一个比较不错的选择。
     */
    private Integer concurrencyLevel;
    private static Integer DEFAULT_CONCURRENCY_LEVEL = Runtime.getRuntime().availableProcessors();


    /**
     * 缓存初始化容量
     * 指定用于缓存的hash table最低总规模;合理的初始容量能够减少缓存容器的扩容次数。
     * 例如:
     *  设置了 initialCapacity 为 60，还设置了 concurrencyLevel 为8。
     *  将会把存储的空间分为8块，每块都有一个 hash table 结构，每个 hash table 的初始规模为8。
     *  如果缓存空间有限，需要预估足够大的初始化空间来缓，避免在数据增长时昂贵的扩展操作（扩展空间会导致深度COPY）。
     */
    private Integer initialCapacity;
    private static Integer DEFAULT_INITIAL_CAPACITY = 100;

    private static CacheStrategyConfig DEFAULT_CONFIG;

    static {
        DEFAULT_CONFIG = new CacheStrategyConfig();
        DEFAULT_CONFIG.setRefreshAfterWrite(DEFAULT_REFRESH_FATER_WRITE);
        DEFAULT_CONFIG.setConcurrencyLevel(DEFAULT_CONCURRENCY_LEVEL);
        DEFAULT_CONFIG.setInitialCapacity(DEFAULT_INITIAL_CAPACITY);
    }


    public Integer getRefreshAfterWrite() {
        return refreshAfterWrite==null?DEFAULT_REFRESH_FATER_WRITE:refreshAfterWrite;
    }

    public void setRefreshAfterWrite(Integer refreshAfterWrite) {
        this.refreshAfterWrite = refreshAfterWrite;
    }

    public Integer getConcurrencyLevel() {
        return concurrencyLevel==null?DEFAULT_CONCURRENCY_LEVEL:concurrencyLevel;
    }

    public void setConcurrencyLevel(Integer concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    public Integer getInitialCapacity() {
        return initialCapacity==null?DEFAULT_INITIAL_CAPACITY:initialCapacity;
    }

    public void setInitialCapacity(Integer initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public static CacheStrategyConfig getDefaultConfig(){
        return DEFAULT_CONFIG;
    }
}
