package com.knowdata.framework.study.pay;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "pay.alipay")
@Component
@Data
public class AliPayProperties {

    private String appId;
    private String gatewayUrl;
    private String format;
    private String charset;
    private String signType;
    //阿里同步通知接口地址
    private String returnUrl;
    //支付后阿里通知支付状态的接口地址
    private String notifyUrl;
    private String appPrivateKey;
    private String appPublicKey;
    private String publicKeyFilePath;
    //主要用于支付成功后页面跳转的网站地址（支付宝支付前端会用阿里自己的页面，所以支付成功以后要跳转到自己的页面）
    private String requestUrl;

    //支付宝支付页面超时时间
    private String overTime;
}
