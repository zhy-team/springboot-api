package com.payment.alipay.bean;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/7 14:05
 */

@Data
public class AliOauthInfo {

    @Size(min = 1,max = 40,message = "code长度不合法")
    private String code;

    @Size(min = 1,max = 40,message = "refreshToken长度不合法")
    private String refreshToken;
}
