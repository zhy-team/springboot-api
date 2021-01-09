package com.payment.unionpay.web;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/5 13:14
 */

@Component
@Data
public class UnionPayProperties {
    /**
     * 是否开启
     */
    @Value("${unionpay.isUsed}")
    String isUsed;

}