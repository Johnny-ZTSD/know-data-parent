package com.knowdata.framework.core.exception;

/**
 * 系统级错误码
 */
public enum SystemError {
    UNAUTHORIZED("401", "未经授权便访问目标资源")
    /* 403 , 没有权限访问此站 , 该状态表示服务器理解了本次请求但是拒绝执行该任务，该请求不该重发给服务器 */
    , FORBIDDEN("403", "禁止访问")
    , FORBIDDEN_EXECUTABLE_ACCESS("403.1", "禁止访问：禁止可执行访问")
    , FORBIDDEN_READABLE_ACCESS("403.2", "禁止访问：禁止读访问")
    , FORBIDDEN_WRITEABLE_ACCESS("403.3", "禁止访问：禁止写访问")
    , FORBIDDEN_NOT_SSL("403.4", "禁止访问：要求SSL")
    , FORBIDDEN_NOT_SSL128("403.5", "禁止访问：要求SSL128")
    , FORBIDDEN_IP_ACCESS("403.6", "禁止访问：IP地址被拒绝")
    , FORBIDDEN_NOT_EXISTS_CLIENT_CERTIFICATE("403.7", "禁止访问：要求客户证书")
    , FORBIDDEN_INVALID_PASSWORD("403.11", "禁止访问：密码已更改")
    /* 在服务器不想提供任何反馈信息的情况下，服务器可以用 404 Not Found 代替 403 Forbidden */
    , NOT_FOUND("404", "未找到目标资源")

    , SERVER_INTERNAL_ERROR("500", "系统内部错误")
    , GATEWAY_TIMEOUT("504", "网关超时");

    private String errorCode;
    private String errorMessage;

    SystemError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static String getName(String code) {
        for (SystemError c : SystemError.values()) {
            if (c.getErrorCode().equals(code)) {
                return c.name();
            }
        }
        return null;
    }

    public static SystemError getEnum(String errorCode) {
        for (SystemError c : SystemError.values()) {
            if (c.getErrorCode().equals(errorCode)) {
                return c;
            }
        }
        return null;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorCode;
    }
}
