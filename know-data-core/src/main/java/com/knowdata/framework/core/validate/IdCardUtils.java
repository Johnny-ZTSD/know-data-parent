package com.knowdata.framework.core.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/5/9  02:19:55
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

public class IdCardUtils {
    private final static Logger logger = LoggerFactory.getLogger(IdCardUtils.class);

    private static int[] w = {7,9,10,5,8,4,2,1,6, 3,7,9,10,5,8,4,2};

    /**
     * 正则：身份证号码 15 位
     */
    public static final String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";

    /**
     * 正则：身份证号码 18 位
     */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";

    /**
     * 是否为合格的身份证号码
     * @description
     *  isCard("11010119960728730X") : true
     *  isCard("11010119960728730x") : false (身份证号码中若含X，则仅支持大写X，这是国家标准；罗马数字的10,用大写X来代替10)
     * @param id
     * @return
     */
    public static boolean isCard(String id) {
        char[] c=id.toCharArray();
        int sum=0;
        for (int i = 0; i < w.length; i++) {
            sum+=(c[i]-'0')*w[i];
        }
        char[] verifyCode="10X98765432".toCharArray();
        char ch = verifyCode[sum%11];
        logger.debug("idCard: {} | sum: {} | ch:{}", sum, ch);
        return c[17]==ch;
    }
}
