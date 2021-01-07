package com.payment.alipay.bean;

import lombok.Data;

import java.util.Map;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/7 11:37
 */
@Data
public class AliGenericInfo {

    private String method;

    private Map<String, String> textParams;

    private Map<String, Object> bizParams;
}
