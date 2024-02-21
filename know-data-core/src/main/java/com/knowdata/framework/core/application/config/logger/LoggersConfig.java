package com.knowdata.framework.core.application.config.logger;

import java.util.List;

/**
 * @version v1.0
 * @project know-data-parent:know-data-core
 * @create-time 2022/10/6 16:17
 * @description
 */
public class LoggersConfig {
    /**
     * "service-config.log[index].logLevel"
     */
    private String logLevel;

    /**
     * "service-config.log[index].logLevel"
     */
    private List<String> loggersNames;

    public LoggersConfig() {

    }

    public LoggersConfig(String logLevel, List<String> loggersNames) {
        this.logLevel = logLevel;
        this.loggersNames = loggersNames;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public List<String> getLoggersNames() {
        return loggersNames;
    }

    public void setLoggersNames(List<String> loggersNames) {
        this.loggersNames = loggersNames;
    }
}
