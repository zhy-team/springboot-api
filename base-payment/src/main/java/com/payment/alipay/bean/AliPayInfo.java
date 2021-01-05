package com.payment.alipay.bean;

import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/4 15:15
 */

@Data
public class AliPayInfo {


    @DecimalMin(value = "0.01" , message = "交易价格最小值不能小于0.01")
    @DecimalMax(value = "100000000" , message = "交易价格最大值不能超过1亿")
    private BigDecimal amount;

    @NotBlank(message = "交易主题不能为空")
    private String subject;

    @Size(min = 1,max = 28,message = "buyerId长度不合法")
    private String buyerId;


    private Map<String,Object> objectMap;
}
