package com.knowdata.framework.study.pay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Controller("/pay")
public class PayController {

    private PayService payService;

    @RequestMapping("/ali/notify")
    public String notify(HttpServletRequest request){
        log.info("支付宝异步回调接收到请求!!!");
        Map<String,String> flag = payService.aliPayNotify(request);
        if("success".equals(flag.get("status"))){
            //这块做自己的业务逻辑
            //AsyncManager.me().execute(AsyncFactory.updateCourseSignNum(flag.get("courseId")));
            log.info("支付成功，TODO 后续业务流程");
        }
        return flag.get("status");
    }

    @RequestMapping("/ali/return")
    public void aliReturn(HttpServletRequest request, HttpServletResponse response){
        payService.aliReturn(request,response);
    }
}
