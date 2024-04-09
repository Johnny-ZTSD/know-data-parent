package com.knowdata.framework.study.email;

import com.knowdata.framework.study.email.config.EmailSenderConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import static org.junit.Assert.*;

@Slf4j
public class EmailUtilsTest {
    /**
     * 发送邮件测试
     */
    @Test
    public void sendEmailTest(){
        EmailSenderConfiguration emailSenderConfiguration = EmailSenderConfiguration.getQqEmailConfig("xxxx@qq.com", "xxxx");
        Boolean result = EmailUtils.sendEmail(
            emailSenderConfiguration
            , "xxxx@foxmail.com"
            , "test-subject3"
            , "test-content"
            , true
        );
        log.info("result:{}", result);
    }

}
