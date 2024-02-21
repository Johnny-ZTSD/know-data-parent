package com.knowdata.framework.core.validate;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/5/20 8:15
 * @description 各类验证参数的VM变量的名称集合
 * @reference-doc
 */

public class ValidateConstants {
    /**
     * VM Options 变量名称:中国国内手机号码正则表达式
     * @description
     *  config-method: -Dcom.knowdata.framework.core.validate.chinesePhoneNumberPattern="^((13[0-9])|(14[0-9])|(15([0-9]))|(16([0-9]))|(17([0-9]))|(18[0-9])|(19[0-9]))\\\\d{8}$"
     */
    public static final String CHINA_PHONE_NUMBER_PATTERN = "com.knowdata.framework.core.validate.chinesePhoneNumberPattern";
    /**
     * 中国国内手机号码正则表达式变量的默认值
     * @samples
     *  [1] "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([0,1,6,7,]))|(18[0-2,5-9]))\\d{8}"
     *  [2] "^((13[0-9])|(14[0-9])|(15([0-9]))|(16([0-9]))|(17([0-9]))|(18[0-9])|(19[0-9]))\\d{8}"
     */
    public static final String CHINA_PHONE_NUMBER_PATTERN_DEFAULT_VALUE = "^((13[0-9])|(14[0-9])|(15([0-9]))|(16([0-9]))|(17([0-9]))|(18[0-9])|(19[0-9]))\\d{8}";
}
