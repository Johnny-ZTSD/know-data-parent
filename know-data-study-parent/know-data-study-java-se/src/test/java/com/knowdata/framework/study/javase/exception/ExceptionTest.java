package com.knowdata.framework.study.javase.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 异常处理机制验证
 */
@Slf4j
public class ExceptionTest {
    public static String targetMethod(String input) {
        String result = "EMPTY";
        try {
            if(input != null && input.length() <= 2){
                result = input.toLowerCase();
            } else {
                throw new RuntimeException("xxxx");//故意抛一个异常
            }
        } catch (Exception exception) {
            log.info("Fail to execute the method!input:{},exception:{}", input, exception.getMessage());
            return result;
        } finally {
            log.info("Success to execute the method finally!input:{}, result:{}", input, result);
        }
        return result;
    }

    @Test
    public void targetMethodTest(){
        //主流正常情况
        log.info("return : {}", targetMethod("Hello"));
        /** output:
         * Fail to execute the method!input:Hello,exception:xxxx
         * Success to execute the method finally!input:Hello, result:EMPTY
         * return : EMPTY
         */

        //异常情况测试
        log.info("return : {}", targetMethod("Hi"));
        /** output:
         * Success to execute the method finally!input:Hi, result:hi
         * return : hi
         */
    }
}
