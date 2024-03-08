package com.knowdata.framework.core.util.lang;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2023/11/9  01:46:52
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */

public class StringUtils {
    private final static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    /**
     * @description 注意区分，与 {@link ObjectUtils#isEmpty } 的不同
     * @param str
     * @return
     */
    public static boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }


    // 16进制字符
    private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 将byte数组转换成16进制字符串
     *
     * @param src
     * @return
     * @reference-doc
     * [1] java byte数组与16进制间相互转换的示例 - jb51 -  https://www.jb51.net/article/198049.htm
     * [2] Java 16进制字符串和字节数组转换的几种方法 - CSDN - https://blog.csdn.net/qq_21071977/article/details/115241001
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }

    /**
     * byte 数组 转 String
     * @reference-doc
     *  [1] Java byte[] 和 String互相转换 - CSDN - https://blog.csdn.net/qq_19734597/article/details/115865372
     * @return
     */
    public static String toString(byte [] bytes){
        return new String(bytes);
    }

    public static String toString(byte [] bytes, String charset) throws UnsupportedEncodingException {
        //方法1: by Base64.encode + Base64.decode
        //Base64 Encoded
//        String encoded = Base64.getEncoder().encodeToString(bytes);
        //Base64 Decoded
//        byte[] decoded = Base64.getDecoder().decode(encoded);
        //Verify original content
//        return new String(decoded);

        if(ObjectUtils.isEmpty(charset)){
            charset = "UTF-8";
        }

        //方法2: new String(byte [])
        return new String(bytes, charset);// or new String(bytes)
    }

    public static byte [] toBytes(String str){
        return toBytes(str, StandardCharsets.UTF_8);
    }

    public static byte [] toBytes(String str, String charsetName) throws UnsupportedEncodingException {
        return str.getBytes(charsetName);
    }

    public static byte [] toBytes(String str, Charset charset) {
        return str.getBytes(charset);
    }

    /**
     * 16进制字符串 转换为 byte []
     * @param hexStr
     * @return byte []
     * @reference-doc
     *  [1] java byte数组与16进制间相互转换的示例 - jb51 -  https://www.jb51.net/article/198049.htm
     */
    public static byte[] hexStringToBytes(String hexStr) {
        if(hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 带秘钥加密
     * @param text 明文
     * @param key 密钥
     * @return 密文
     */
    public static String md5WithKey(String text, String key) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text + key);
        return md5str;
    }

    /**
     * 不带秘钥加密
     * @param text 明文
     * @return
     * @throws Exception
     */
    public static String md5(String text) throws Exception {
        // 加密后的字符串
        String md5str = DigestUtils.md5Hex(text);
        return md5str;
    }

    /**
     * MD5验证方法
     * @description 根据传入的密钥进行验证
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     */
    public static boolean verifyMd5WithKey(String text, String key, String md5) throws Exception {
        String md5str = md5WithKey(text, key);
        if (md5str.equalsIgnoreCase(md5)) {
            logger.debug("pass to md5 verify!");
            return true;
        }
        return false;
    }
}
