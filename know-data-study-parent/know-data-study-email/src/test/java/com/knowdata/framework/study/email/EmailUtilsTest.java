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
    //@Test
    public void sendEmailTest(){
        EmailSenderConfiguration emailSenderConfiguration = EmailSenderConfiguration.getQqEmailConfig("xxxxx@qq.com", "xxxxxxx");
        Boolean result = EmailUtils.sendEmail(emailSenderConfiguration, "yyyyy@foxmail.com", "test-subject3", "test-content", false);
        log.info("result:{}", result);
    }

}
