package com.payment.alipay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2020/12/23 14:39
 */



@Configuration
public class InitConfig {

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    public String getAppId() {
        return appId;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }
}
