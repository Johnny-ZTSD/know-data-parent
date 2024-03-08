package com.knowdata.framework.study.springboot.spring.springevent.order.phonemessage;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2024/3/6  02:16:35
 * @description 定义监听器 / 推荐使用 @EventListener 注解
 * @reference-doc
 * @gpt-prompt
 */
@Slf4j
@Component
public class PhoneMessageListener {

    @Async // 对应的 SpringApplication 中 需启用 @EnableAsync 异步特性
    @SneakyThrows
    @EventListener(PhoneMessageEvent.class)
    public void sendMsg(PhoneMessageEvent event) {
        String orderId = event.getOrderId();
        long start = System.currentTimeMillis();
        log.info("开发发送短信");
        log.info("开发发送邮件");
        Thread.sleep(4000);
        long end = System.currentTimeMillis();
        log.info("{} : 发送短信、邮件耗时：({})毫秒", orderId, (end - start));
    }
}
