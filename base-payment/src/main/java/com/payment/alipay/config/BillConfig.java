package com.payment.alipay.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2020/12/23 14:29
 */

@Configuration
public class BillConfig {

    @Value("${alipay.bill.path}")
    private String billPath;

    public String getBillPath() {
        return billPath;
    }
}
