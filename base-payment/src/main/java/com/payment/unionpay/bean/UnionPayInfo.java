package com.payment.unionpay.bean;


import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;

@Data
public class UnionPayInfo {

    private String merId;

    @DecimalMin(value = "1" , message = "交易价格最小值不能小于0.01")
    @DecimalMax(value = "100000000" , message = "交易价格最大值不能超过1亿")
    private String txnAmt;

}
