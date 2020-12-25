package com.payment.alipay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.payment.alipay.Contants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2020/12/25 16:58
 */

@Component
public class PayInit {

    @Autowired
    InitConfig initConfig;

    public PayInit(){
        Factory.setOptions(getOptions());
    }

    private Config getOptions() {
        Config config = new Config();
        config.protocol = Contants.protocol;
        config.gatewayHost = Contants.gatewayHost;
        config.signType = Contants.signType;
        config.appId = initConfig.getAppId();
        config.merchantPrivateKey = Contants.merchantPrivateKey;
        config.encryptKey = Contants.encryptKey;
        config.alipayPublicKey=Contants.alipayPublicKey;
        config.notifyUrl = initConfig.getNotifyUrl();
        return config;
    }

}
