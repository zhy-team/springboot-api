package com.payment.alipay.bean;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/6 13:09
 */
@Data
public class AliPayCommonInfo {

    @Size(min = 1,max = 64,message = "商家订单号长度不合法")
    private String outTradeNo;

    @Size(min = 1,max = 64,message = "支付宝订单号长度不合法")
    private String tradeNo;
}
