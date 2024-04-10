package com.knowdata.framework.study.pay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class PayService {
    private AliPayProperties aliPayProperties;

    // 处理支付宝支付的异步通知
    public Map<String,String> aliPayNotify(HttpServletRequest request) {
        Map<String,String> result = new HashMap<>();
        // 获取支付宝通知内容
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }

        // 验证签名
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayProperties.getAppPublicKey(), aliPayProperties.getCharset(), aliPayProperties.getSignType());
            if(signVerified){
                // 验证成功
                // 处理你的业务逻辑，例如更新订单状态等
                // ...

                // 返回操作成功的结果到支付宝服务器
                // 注意：仅在验证成功时返回success，否则需要返回fail
                // 此处简化处理，实际应用中应该返回对应的操作结果
                result.put("status", "success");
                //response.getWriter().write("success");
            } else {
                // 验证失败
                // 返回fail
                result.put("status", "fail");
                //response.getWriter().write("fail");
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            // 验证异常，通常是网络问题或支付宝内部错误
            // 返回fail
            //response.getWriter().write("fail");
            result.put("status", "fail");
        }
        return result;
    }
    //支付宝拉起统一下单
    public Map<String, String> prePay(UserOrder order, String clientIP) {
        log.info("支付宝统一下单接收到请求,下单用户ip ：{}", clientIP);
        Map<String, String> map = new HashMap<>();
        AlipayClient alipayClient=new DefaultAlipayClient(aliPayProperties.getGatewayUrl(),
                aliPayProperties.getAppId(),aliPayProperties.getAppPrivateKey(),aliPayProperties.getFormat(),
                aliPayProperties.getCharset(),aliPayProperties.getAppPublicKey(),aliPayProperties.getSignType()
        );
        try {
            //商户订单号，商户网站订单系统中唯一订单号，必填
            String out_trade_no = new String(order.getOrderNo().getBytes("UTF-8"),"UTF-8");
            //付款金额，必填
            Double payPrice = order.getPayAmount();
            String total_amount = new String(payPrice.toString().getBytes("UTF-8"),"UTF-8");
            //订单名称，必填
            String subject = new String(order.getPackageName().getBytes("UTF-8"),"UTF-8");
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            String encode = URLEncoder.encode(order.getOperatorInfo(), "UTF-8");
            request.setNotifyUrl(aliPayProperties.getNotifyUrl());
            request.setReturnUrl(aliPayProperties.getReturnUrl());
            request.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                    + "\"total_amount\":\""+ total_amount +"\","
                    + "\"subject\":\""+ subject +"\","
                    + "\"passback_params\":\""+ encode +"\","
                    + "\"timeout_express\":\""+ aliPayProperties.getOverTime()+"\","
                    + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
            AlipayTradePagePayResponse response = null;
            response = alipayClient.pageExecute(request);


            map.put("success", "true");
            map.put("body", response.getBody());
            map.put("msg", response.getMsg());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return map;
    }

    public void aliReturn(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("支付宝同步回调接收到请求");
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //调用SDK验证签名
            boolean flag = AlipaySignature.rsaCertCheckV1(params, aliPayProperties.getPublicKeyFilePath(), aliPayProperties.getCharset(), aliPayProperties.getSignType());
            if (flag) {
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

                //付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

                log.info("支付宝同步回调参数 out_trade_no： {} ，trade_no： {}， total_amount ： {}", out_trade_no, trade_no, total_amount);
                response.sendRedirect(aliPayProperties.getRequestUrl());
            } else {
                System.out.println("验签失败");
                response.sendRedirect(aliPayProperties.getRequestUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendRedirect(aliPayProperties.getRequestUrl());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
