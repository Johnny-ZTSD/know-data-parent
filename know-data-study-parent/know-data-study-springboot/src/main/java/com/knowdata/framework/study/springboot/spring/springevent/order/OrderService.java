package com.knowdata.framework.study.springboot.spring.springevent.order;

import com.knowdata.framework.study.springboot.spring.springevent.order.phonemessage.PhoneMessageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2024/3/5  22:39:55
 * @description step3 发布事件，通过 ApplicationEventPublisher 发布事件
 * @reference-doc
 * @gpt-prompt
 */
@Slf4j
@Service
//@RequiredArgsConstructor
public class OrderService {

    /** 注入ApplicationContext用来发布事件 */
    private final ApplicationContext applicationContext;

    /**
     * 下单
     *
     * @param orderId 订单ID
     */
    public String buyOrder(String orderId) {
        long start = System.currentTimeMillis();
        // 1.查询订单详情
        //todo

        // 2.检验订单价格 （同步处理）
        applicationContext.publishEvent(new OrderProductEvent(this, orderId));

        // 3.短信通知（异步处理）
        applicationContext.publishEvent(new PhoneMessageEvent(orderId));

        // 4. 计算耗时
        long end = System.currentTimeMillis();
        log.info("任务全部完成，总耗时：({})毫秒", end - start);
        return "{\"result\" : \"购买成功\"}";
    }

    //@RequiredArgsConstructor 注解等效于 :
    public OrderService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

}