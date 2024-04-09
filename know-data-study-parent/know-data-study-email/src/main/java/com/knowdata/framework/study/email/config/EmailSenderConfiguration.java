package com.knowdata.framework.study.email.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.swing.StringUIClientPropertyKey;

import java.util.Properties;

/**
 * 邮件发送者的配置信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSenderConfiguration {
    /**
     * @example "smtp.163.com"
     **/
    public final static String MAIL_SMTP_HOST_PARAM = "mail.smtp.host";
    private String mailSmtpHost;

    /**
     * 通过ssl协议使用465端口发送、使用默认端口（25）时不需要设置 :
     * 1. mail.smtp.starttls.enable: true
     * 2. mail.smtp.starttls.required: true
     * 3. mail.smtp.auth : true
     * 4. mail.smtp.socketFactory.port : 465
     * 5. mail.smtp.socketFactory.class : javax.net.ssl.SSLSocketFactory
     * 6. mail.smtp.socketFactory.fallback : false
     * @example "25"
     **/
    public final static String MAIL_SMTP_PORT_PARAM = "mail.smtp.port";
    private Integer mailSmtpPort;

    /**
     * 链接超时
     * @example "1000"
     **/
    public final static String MAIL_SMTP_TIMEOUT_PARAM = "mail.smtp.timeout";
    public final static Integer MAIL_SMTP_TIMEOUT_DEFAULT = 1000;
    private Integer mailSmtpTimeout = MAIL_SMTP_TIMEOUT_DEFAULT;

    /**
     * @example "true"
     **/
    public final static String MAIL_SMTP_AUTH_PARAM = "mail.smtp.auth";
    private Boolean mailSmtpAuth;

    /**
     * @description 注意 : emailSession.setDebug(false/true);
     * @example "false"
     **/
    public final static String MAIL_SMTP_DEBUUG_PARAM = "mail.smtp.debug";
    public final static Boolean MAIL_SMTP_DEBUUG_DEFAULT = false;
    private Boolean mailDebug = MAIL_SMTP_DEBUUG_DEFAULT;

    /**
     * start tls
     * mail.smtp.starttls.enable
     * mail.smtp.starttls.required
     * @example "true"
     **/
    public final static String MAIL_SMTP_STARTTLS_ENABLE_PARAM = "mail.smtp.starttls.enable";
    private Boolean mailSmtpStarttlsEnable;

    public final static String MAIL_SMTP_STARTTLS_REQUIRED_PARAM = "mail.smtp.starttls.required";
    private Boolean mailSmtpStarttlsRequired;

    /**
     * @example "465"
     **/
    public final static String MAIL_SMTP_SOCKETFACTORY_PORT_PARAM = "mail.smtp.socketFactory.port";
    private Integer mailSmtpSocketFactoryPort;

    /**
     * @example "javax.net.ssl.SSLSocketFactory"
     **/
    public final static String MAIL_SMTP_SOCKETFACTORY_CLASS_PARAM = "mail.smtp.socketFactory.class";
    public final static String MAIL_SMTP_SOCKETFACTORY_CLASS_DEFAULT = "javax.net.ssl.SSLSocketFactory";
    private String mailSmtpSocketFactoryClass = MAIL_SMTP_SOCKETFACTORY_CLASS_DEFAULT;

    public final static String MAIL_SMTP_SOCKETFACTORY_FALLBACK_PARAM = "mail.smtp.socketFactory.fallback";
    public final static Boolean MAIL_SMTP_SOCKETFACTORY_FALLBACK_DEFAULT = false;
    private Boolean mailSmtpSocketFactoryFallback = MAIL_SMTP_SOCKETFACTORY_FALLBACK_DEFAULT;


    /** 发送者的邮箱账号 **/
    private String emailAccount;

    /** 发送者的邮箱授权码 **/
    private String emailAuthCode;

    public static Properties getProperties(EmailSenderConfiguration emailSenderConfiguration){
        //封装服务器连接信息
        Properties properties = new Properties();
        properties.put(EmailSenderConfiguration.MAIL_SMTP_DEBUUG_PARAM, emailSenderConfiguration.getMailDebug());
        properties.put(EmailSenderConfiguration.MAIL_SMTP_HOST_PARAM, emailSenderConfiguration.getMailSmtpHost());
        properties.put(EmailSenderConfiguration.MAIL_SMTP_PORT_PARAM, emailSenderConfiguration.getMailSmtpPort());
        properties.put(EmailSenderConfiguration.MAIL_SMTP_AUTH_PARAM, emailSenderConfiguration.getMailSmtpAuth().toString());
        if(emailSenderConfiguration.getMailSmtpStarttlsEnable() !=null){
            properties.put(EmailSenderConfiguration.MAIL_SMTP_STARTTLS_ENABLE_PARAM, emailSenderConfiguration.getMailSmtpStarttlsEnable().toString());
        }
        if(emailSenderConfiguration.getMailSmtpStarttlsRequired() !=null){
            properties.put(EmailSenderConfiguration.MAIL_SMTP_STARTTLS_REQUIRED_PARAM, emailSenderConfiguration.getMailSmtpStarttlsRequired().toString());
        }
        if(emailSenderConfiguration.getMailSmtpSocketFactoryPort() != null){
            properties.put(EmailSenderConfiguration.MAIL_SMTP_SOCKETFACTORY_PORT_PARAM, emailSenderConfiguration.getMailSmtpSocketFactoryPort());
        }
        if(emailSenderConfiguration.getMailSmtpSocketFactoryClass() != null){
            properties.put(EmailSenderConfiguration.MAIL_SMTP_SOCKETFACTORY_CLASS_PARAM, emailSenderConfiguration.getMailSmtpSocketFactoryClass());
        }
        if(emailSenderConfiguration.getMailSmtpSocketFactoryFallback() != null){
            properties.put(EmailSenderConfiguration.MAIL_SMTP_SOCKETFACTORY_FALLBACK_PARAM, emailSenderConfiguration.getMailSmtpSocketFactoryFallback().toString());
        }
        return properties;
    }

    public static EmailSenderConfiguration get163EmailConfig(String emailAccount, String emailAuthCode){
        EmailSenderConfiguration emailSenderConfiguration = new EmailSenderConfiguration();
        emailSenderConfiguration.setMailSmtpHost("smtp.163.com");
        emailSenderConfiguration.setMailSmtpPort(465);//25 / 465 / ...

        emailSenderConfiguration.setMailSmtpStarttlsEnable(true);
        emailSenderConfiguration.setMailSmtpStarttlsRequired(false);
        emailSenderConfiguration.setMailSmtpSocketFactoryPort(465);

        emailSenderConfiguration.setMailSmtpAuth(true);
        emailSenderConfiguration.setEmailAccount(emailAccount);
        emailSenderConfiguration.setEmailAuthCode(emailAuthCode);
        return emailSenderConfiguration;
    }

    /**
     * 获取QQ邮箱的配置
     * @reference-doc
     *  [1] SMTP/IMAP服务 - QQ Email - https://wx.mail.qq.com/list/readtemplate?name=app_intro.html#/agreement/authorizationCode
     *  [2] 账号与安全 - QQ Email - https://wx.mail.qq.com/account/index
     * @description
     *  接收邮件服务器： imap.qq.com，使用SSL，端口号993
     *  发送邮件服务器： smtp.qq.com，使用SSL，端口号465或587
     * @param emailAccount
     * @param emailAuthCode
     * @return
     */
    public static EmailSenderConfiguration getQqEmailConfig(String emailAccount, String emailAuthCode){
        EmailSenderConfiguration emailSenderConfiguration = new EmailSenderConfiguration();
        emailSenderConfiguration.setMailSmtpHost("smtp.qq.com");
        emailSenderConfiguration.setMailSmtpPort(465);//465 or 587

        emailSenderConfiguration.setMailSmtpStarttlsEnable(true);
        emailSenderConfiguration.setMailSmtpStarttlsRequired(false);
        emailSenderConfiguration.setMailSmtpSocketFactoryPort(465);//465 or 587

        emailSenderConfiguration.setMailSmtpAuth(true);
        emailSenderConfiguration.setEmailAccount(emailAccount);
        emailSenderConfiguration.setEmailAuthCode(emailAuthCode);
        return emailSenderConfiguration;
    }

    public static EmailSenderConfiguration getGmailEmailConfig(String emailAccount, String emailAuthCode){
        EmailSenderConfiguration emailSenderConfiguration = new EmailSenderConfiguration();
        emailSenderConfiguration.setMailSmtpHost("smtp.gmail.com");
        emailSenderConfiguration.setMailSmtpPort(465);

        emailSenderConfiguration.setMailSmtpStarttlsEnable(true);
        emailSenderConfiguration.setMailSmtpStarttlsRequired(false);
        emailSenderConfiguration.setMailSmtpSocketFactoryPort(465);

        emailSenderConfiguration.setMailSmtpAuth(true);
        emailSenderConfiguration.setEmailAccount(emailAccount);
        emailSenderConfiguration.setEmailAuthCode(emailAuthCode);
        return emailSenderConfiguration;
    }
}
