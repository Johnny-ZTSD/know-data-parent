package com.knowdata.framework.core.exception;

/**
 * @author johnnyzen
 * @version v1.0
 * @project know-data-parent
 * @create-time 2023/5/24 17:46
 * @description ...
 * @refrence-doc
 * @gpt-promt
 */
public enum ApplicationError {
    /**
     * 公共异常返回值定义
     * common SYS_ERR_0
     */
    FUNCTION_IS_NOT_OPEN("FUNCTION_IS_NOT_OPEN", "功能暂未开放或开发")
    , OPERATION_FAIL("OPERATION_FAIL", "操作失败")
    , NO_OPERATION_PERMISSION("NO_OPERATION_PERMISSION","无操作权限！")

    , JOB_NAME_ERR("JOB_NAME_ERR", "作业名称错误")
    , GROOVY_TRANS_ERR("GROOVY_TRANS_ERR", "Groovy脚本反射调用失败")
    , EMBED_TRANS_ERR("EMBED_TRANS_ERR", "内置后置转换器转换失败")
    , SERVICE_NOT_FOUND("SERVICE_NOT_FOUND", "未找到指定版本的数据服务，可能系服务ID、服务版本、数据源未配置或请求信息错误")
    , SQL_IS_EMPTY("SQL_IS_EMPTY", "查询SQL为空")
    , QUERY_NOT_SUPPORT("QUERY_NOT_SUPPORT", "此查询类型暂时不支持")
    , QUERY_RESULT_IS_EMPTY("QUERY_RESULT_IS_EMPTY", "查询结果为空")
    , JINJA_TEMPLATE_TRANS_ERR("JINJA_TEMPLATE_TRANS_ERR", "渲染Jinja模板失败")
    , EXEC_SQL_ERR("EXEC_SQL_ERR", "执行SQL异常")
    , QUERY_ES_FAIL("QUERY_ES_FAIL", "执行ElasticSearch Query失败")

    , REQUEST_PARAMETER_MISSING("REQUEST_PARAMETER_MISSING", "请求的(必填)参数缺失或为空")
    , INVALID_VALUE("INVALID_VALUE", "无效值!")
    , INVALID_VALUE_CAUSE_BY_EMPTY("INVALID_VALUE_CAUSE_BY_EMPTY", "参数值为空!")
    , INVALID_VALUE_CAUSE_BY_ERROR_FORMAT("INVALID_VALUE_CAUSE_BY_ERROR_FORMAT", "参数格式错误!")

    , REQUEST_THIRD_PARTY_SERVICE_FAIL("REQUEST_THIRD_PARTY_SERVICE_FAIL", "请求第三方服务失败")
    , GET_DATASOURCE_BY_EXTERNAL_ERR("GET_DATASOURCE_BY_EXTERNAL_ERR", "通过外部数据源获取绑定的统一数据源失败")
    ;

    private String errorCode;
    private String errorMessage;

    ApplicationError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static String getName(String code) {
        for (ApplicationError c : ApplicationError.values()) {
            if (c.getErrorCode().equals(code)) {
                return c.name();
            }
        }
        return null;
    }

    public static ApplicationError getEnum(String errorCode) {
        for (ApplicationError c : ApplicationError.values()) {
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
