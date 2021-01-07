package com.payment.alipay.bean;

import lombok.Data;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/7 14:05
 */

@Data
public class AliOauthInfo {

    private String code;

    private String refreshToken;
}
