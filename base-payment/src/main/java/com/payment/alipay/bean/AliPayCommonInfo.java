package com.payment.alipay.bean;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

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

    @Pattern(regexp ="^\\d+(\\.\\d{2}+)?$", message = "退款金额不合法")
    @DecimalMin(value = "0.01" , message = "退款金额最小值不能小于0.01")
    @DecimalMax(value = "100000000" , message = "退款金额最大值不能超过1亿")
    private String refundAmount;

    @Size(min = 1,max = 64,message = "退款请求号长度不合法")
    private String outRequestNo;

    private Map<String,Object> objectMap;
}
