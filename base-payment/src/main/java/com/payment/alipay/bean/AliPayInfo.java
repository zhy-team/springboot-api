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

    @Pattern(regexp ="^\\d+(\\.\\d{2}+)?$", message = "交易价格不合法")
    @DecimalMin(value = "0.01" , message = "交易价格最小值不能小于0.01")
    @DecimalMax(value = "100000000" , message = "交易价格最大值不能超过1亿")
    private String amount;

    @NotBlank(message = "交易主题不能为空")
    @Size(min = 1,max = 256,message = "交易主题 subject长度不合法")
    private String subject;

    @Size(min = 1,max = 28,message = "买家Id buyerId长度不合法")
    private String buyerId;

    @Size(min = 1,max = 64,message = "支付授权码 authCode长度不合法")
    private String authCode;

    @Size(min = 1,max = 256,message = "返回地址 returnUrl长度不合法")
    private String returnUrl;

    @Size(min = 1,max = 256,message = "退出地址 quitUrl长度不合法")
    private String quitUrl;

    private Map<String,Object> objectMap;
}
