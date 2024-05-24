package com.knowdata.framework.study.flink.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Slf4j
public class SecurityUtils {
    /**
     * @refrence-doc
     *  [1] Java中哈希算法总结 - CSDN - https://blog.csdn.net/m0_73514610/article/details/131643691
     * @param originText
     * @return
     */
    @SneakyThrows
    public static String md5(String originText){
        //创建基于MD5算法的消息摘要对象
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        //更新原始数据
        md5.update(originText.getBytes());

        //获取加密后的结果
        byte[] disgetsBytes = md5.digest();
        String encryptedText = Arrays.toString(disgetsBytes);

        log.debug("encrypted result : {} | encrypted result's byte size : {}", encryptedText, disgetsBytes.length);//加密后的结果、加密结果长度

        return encryptedText;
    }
}
