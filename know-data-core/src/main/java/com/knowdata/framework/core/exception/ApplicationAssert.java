package com.knowdata.framework.core.exception;

import org.slf4j.Logger;
import org.springframework.util.ObjectUtils;

import javax.annotation.Nullable;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2023/6/2 11:37
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
public class ApplicationAssert {

    public static void isTrue(boolean expression, String errorCode, String errorMessage) {
        if (!expression) {
            throw new ApplicationException(errorCode, errorMessage);
        }
    }

    public static void isTrue(boolean expression, ApplicationError errorCode, String errorMessage) {
        if (!expression) {
            throw new ApplicationException(errorCode.getErrorCode(), errorMessage);
        }
    }

    public static void isTrue(boolean expression, ApplicationError errorCode, Logger logger) {
        if (!expression) {
            logger.error(errorCode + " | " + errorCode.getErrorMessage());
            //LoggerUtil.log(logger, errorCode + " | " + errorCode.getErrorMessage());
            throw new ApplicationException(errorCode.getErrorCode(), errorCode.getErrorMessage());
        }
    }

    public static void isTrue(boolean expression, ApplicationError errorCode, String errorMessage, Logger logger) {
        if (!expression) {
            logger.error(errorCode + " | " + errorCode.getErrorMessage());
            //LoggerUtil.log(logger, errorCode + " | " + errorMessage);
            throw new ApplicationException(errorCode.getErrorCode(), errorMessage);
        }
    }

    public static void notEmpty(@Nullable Object object, ApplicationError errorCode, String errorMessage, Logger logger) {
        if (ObjectUtils.isEmpty(object)) {
            logger.error(errorCode + " | " + errorCode.getErrorMessage());
            throw new ApplicationException(errorCode.getErrorCode(), errorMessage);
        }
    }

    public static void notEmpty(@Nullable Object object, ApplicationError errorCode, String errorMessage) {
        if (ObjectUtils.isEmpty(object)) {
            throw new ApplicationException(errorCode.getErrorCode(), errorMessage);
        }
    }

    public static void isEmpty(@Nullable Object object, ApplicationError errorCode, String errorMessage) {
        if (!ObjectUtils.isEmpty(object)) {
            throw new ApplicationException(errorCode.getErrorCode(), errorMessage);
        }
    }

    public static void notNull(@Nullable Object object, ApplicationError errorCode, String errorMessage) {
        if (object == null) {
            throw new ApplicationException(errorCode.getErrorCode(), errorMessage);
        }
    }

    public static void isNull(@Nullable Object object, ApplicationError errorCode, String errorMessage) {
        if (object != null) {
            throw new ApplicationException(errorCode.getErrorCode(), errorMessage);
        }
    }
}