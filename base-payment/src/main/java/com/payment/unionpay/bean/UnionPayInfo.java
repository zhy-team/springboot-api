package com.payment.unionpay.bean;


import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UnionPayInfo {
    @DecimalMin(value = "1" , message = "交易价格最小值不能小于0.01")
    @DecimalMax(value = "100000000" , message = "交易价格最大值不能超过1亿")
    private String txnAmt;

    @Size(min = 1,max = 1024,message = "银联自定义字段长度不合法")
    private String reqReserved;
}
