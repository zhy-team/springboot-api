package com.payment.alipay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.stereotype.Component;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2020/12/25 16:58
 */

@Component
public class PayInit {
    public PayInit(){
        Factory.setOptions(getOptions());
    }

    private Config getOptions() {
        Config config = new Config();
        config.protocol = Contants.protocol;
        config.gatewayHost = Contants.gatewayHost;
        config.signType = Contants.signType;
        config.appId = Contants.appId;
        config.merchantPrivateKey = Contants.merchantPrivateKey;
        config.encryptKey = Contants.encryptKey;
        config.alipayPublicKey=Contants.alipayPublicKey;
        config.notifyUrl = Contants.notifyUrl;
        return config;
    }

}
