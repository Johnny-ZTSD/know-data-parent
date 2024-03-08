package com.knowdata.framework.study.springboot.spring.springevent.order.phonemessage;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Johnny
 * @project know-data-parent
 * @create-time 2024/3/6  02:15:28
 * @description ...
 * @reference-doc
 * @gpt-prompt
 */
@Data
@AllArgsConstructor
public class PhoneMessageEvent {
    /** 该类型事件携带的信息 */
    public String orderId;
}
