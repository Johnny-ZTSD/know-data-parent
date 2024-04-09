package com.knowdata.framework.study.email;

import com.knowdata.framework.study.email.config.EmailSenderConfiguration;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 邮件操作工具类
 * @reference-doc
 *  [0] https://mvnrepository.com/artifact/com.sun.mail/javax.mail/1.6.2
 *  [1] java实现发送邮件 - CSDN - https://blog.csdn.net/weixin_72764731/article/details/131614442
 * @description SMTP协议为邮件发送协议,在java应用程序中,可以使用SMTP协议实现发送邮件的功能。
 */
@Slf4j
public class EmailUtils {

    /**
     * 发送邮件
     * @param emailSenderConfiguration 发送者配置
     * @param receiverEmail 收件人邮箱账号
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param debug 是否调试会话
     * @return boolean : true - send success / false - send fail
     */
    public static boolean sendEmail(
        EmailSenderConfiguration emailSenderConfiguration
        , String receiverEmail
        , String subject
        , String content
        , Boolean debug
    ){
        boolean result = true;
        try {
            //1.创建Session
            Session session = createSession(emailSenderConfiguration, debug);

            //2.创建邮件对象
            MimeMessage message = new MimeMessage(session);
            //设置邮件主题
            message.setSubject(subject);
            //设置邮件内容
            message.setText(content);
            //设置发件人
            try {
                message.setFrom(new InternetAddress(emailSenderConfiguration.getEmailAccount()));
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            //设置收件人
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));

            //3.发送邮件
            Transport.send(message);
        } catch (AddressException exception) {
            result = false;
            log.error("Fail to send email and throw a `AddressException`! email-sender: {}, email-receiver: {}, exception : {}", emailSenderConfiguration.getEmailAccount(), receiverEmail, exception);
        } catch (MessagingException exception) {
            result = false;
            log.error("Fail to send email and throw a `MessagingException`! email-sender: {}, email-receiver: {}, exception : {}", emailSenderConfiguration.getEmailAccount(), receiverEmail, exception);
        }
        return result;
    }

    public static boolean sendEmail(
            EmailSenderConfiguration emailSenderConfiguration
            , String receiverEmail
            , String subject
            , String content
    ){
        return sendEmail(emailSenderConfiguration, receiverEmail, subject, content);
    }

    /**
     * 创建会话
     * @param emailSenderConfiguration 发送者配置
     * @param debug 是否调试会话
     **/
    public static Session createSession(
        EmailSenderConfiguration emailSenderConfiguration
        , Boolean debug
    ){
        Properties emailSenderProperties = EmailSenderConfiguration.getProperties(emailSenderConfiguration);

        //创建Session会话
        //参数一:服务器连接信息
        //参数二:账号密码校验对象(用户认证对象)
        Session session = Session.getInstance(emailSenderProperties, new Authenticator() {
            String emailAccount = emailSenderConfiguration.getEmailAccount() ;
            String emailAuthCode = emailSenderConfiguration.getEmailAuthCode();
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount, emailAuthCode);
            }
        });
        session.setDebug( (debug==null) || (debug==false) ? false : true);
        return session;
    }
}
