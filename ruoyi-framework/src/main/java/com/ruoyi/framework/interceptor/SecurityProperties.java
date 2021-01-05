package com.ruoyi.framework.interceptor;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/5 13:14
 */

@Component
@ConfigurationProperties(prefix = "spring.security")
@Data
public class SecurityProperties {
    /**
     * 是否开启
     */
    String isOpen;

    /**
     * 允许忽略签名地址
     */
    List<String> ignoreSignUri;

    /**
     * 签名超时时间(秒)
     */
    Integer signTimeout;
}