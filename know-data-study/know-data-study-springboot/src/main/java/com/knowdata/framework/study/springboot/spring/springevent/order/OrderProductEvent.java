package com.knowdata.framework.study.springboot.spring.springevent.order;


import lombok.Data;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2024/3/5  22:32:49
 * @description step1 定义事件，继承 ApplicationEvent 的类成为一个事件类
 * @reference-doc
 *  [1] Spring Event，贼好用的业务解耦神器！ - Weixin - https://mp.weixin.qq.com/s?__biz=MzI5ODI5NDkxMw==&mid=2247605697&idx=6&sn=0f9fbf77f15983fef7a8c667bfd63b4c&chksm=ecab162fdbdc9f39ccbc2247fbc54772c5626592c2c0aec419210751aea5585d5e016bec96bf&scene=27
 * @gpt-prompt
 */
@Data
@ToString
public class OrderProductEvent extends ApplicationEvent {
    /** 该类型事件携带的信息 */
    private String orderId;

    public OrderProductEvent(Object source, String orderId) {
        super(source);
        this.orderId = orderId;
    }
}
