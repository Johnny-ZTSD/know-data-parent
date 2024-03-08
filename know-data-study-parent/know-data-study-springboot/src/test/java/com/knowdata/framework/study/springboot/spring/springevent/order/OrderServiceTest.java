package com.knowdata.framework.study.springboot.spring.springevent.order;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2024/3/5  22:32:49
 * @description 测试类
 * @reference-doc
 *  [1] Spring Event，贼好用的业务解耦神器！ - Weixin - https://mp.weixin.qq.com/s?__biz=MzI5ODI5NDkxMw==&mid=2247605697&idx=6&sn=0f9fbf77f15983fef7a8c667bfd63b4c&chksm=ecab162fdbdc9f39ccbc2247fbc54772c5626592c2c0aec419210751aea5585d5e016bec96bf&scene=27
 * @gpt-prompt
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    public void buyOrderTest() {
        log.info("下单 - start");
        orderService.buyOrder("732171109");
        log.info("下单 - end");
    }

}
