package com.knowdata.framework.study.pay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrder {
    /** 订单编号 **/
    private String orderNo;

    /** 支付金额 **/
    private Double payAmount;

    private String operatorInfo;

    private String packageName;
}
