package com.knowdata.framework.study.email.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
     * @example "25"
     **/
    public final static String MAIL_SMTP_PORT_PARAM = "mail.smtp.port";
    private Integer mailSmtpPort;

    /**
     * @example "true"
     **/
    public final static String MAIL_SMTP_AUTH_PARAM = "mail.smtp.auth";
    private Boolean mailSmtpAuth;

    /**
     * @example "true"
     **/
    public final static String MAIL_SMTP_STARTTLS_ENABLE_PARAM = "mail.smtp.starttls.enable";
    private Boolean mailSmtpStarttlsEnable;

    /** 发送者的邮箱账号 **/
    private String emailAccount;

    /** 发送者的邮箱授权码 **/
    private String emailAuthCode;

    public static Properties getProperties(EmailSenderConfiguration emailSenderConfiguration){
        //封装服务器连接信息
        Properties properties = new Properties();
        properties.put(EmailSenderConfiguration.MAIL_SMTP_HOST_PARAM, emailSenderConfiguration.getMailSmtpHost());
        properties.put(EmailSenderConfiguration.MAIL_SMTP_PORT_PARAM, emailSenderConfiguration.getMailSmtpPort());
        properties.put(EmailSenderConfiguration.MAIL_SMTP_AUTH_PARAM, emailSenderConfiguration.getMailSmtpAuth().toString());
        properties.put(EmailSenderConfiguration.MAIL_SMTP_STARTTLS_ENABLE_PARAM,emailSenderConfiguration.getMailSmtpStarttlsEnable().toString());
        return properties;
    }

    public static EmailSenderConfiguration get163EmailConfig(String emailAccount, String emailAuthCode){
        EmailSenderConfiguration emailSenderConfiguration = new EmailSenderConfiguration();
        emailSenderConfiguration.setMailSmtpHost("smtp.163.com");
        emailSenderConfiguration.setMailSmtpPort(25);
        emailSenderConfiguration.setMailSmtpAuth(true);
        emailSenderConfiguration.setMailSmtpStarttlsEnable(true);

        emailSenderConfiguration.setEmailAccount(emailAccount);
        emailSenderConfiguration.setEmailAuthCode(emailAuthCode);
        return emailSenderConfiguration;
    }
}
