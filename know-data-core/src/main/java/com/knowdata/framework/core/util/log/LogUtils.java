package com.knowdata.framework.core.util.log;

import org.slf4j.Logger;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  01:42:32
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

public class LogUtils {
    public static void log(Logger logger, String messageFormat, Object ...args) {
        if(logger.isTraceEnabled()){
            logger.trace(messageFormat, args);
        } else if(logger.isDebugEnabled()) {
            logger.debug(messageFormat, args);
        } else if(logger.isInfoEnabled()) {
            logger.info(messageFormat, args);
        } else if(logger.isWarnEnabled()) {
            logger.warn(messageFormat, args);
        } else if(logger.isErrorEnabled()) {
            logger.error(messageFormat, args);
        } else {
            logger.info(messageFormat, args);
        }
    }
}
