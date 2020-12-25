package com.payment.alipay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.payment.alipay.Contants;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. 设置参数（全局只需设置一次）
        Factory.setOptions(getOptions());
        try {

            AlipayTradeCreateResponse response = Payment.Common().create("", "", "", "");

            AlipayDataDataserviceBillDownloadurlQueryResponse alipayDataDataserviceBillDownloadurlQueryResponse = Payment.Common().downloadBill("", "");

            alipayDataDataserviceBillDownloadurlQueryResponse.getBillDownloadUrl();
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {

                System.out.println("调用成功");
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static Config getOptions() {
        Config config = new Config();
        config.protocol = Contants.protocol;
        config.gatewayHost = Contants.gatewayHost;
        config.signType = Contants.signType;
        config.appId = Contants.appId;
        config.merchantPrivateKey = Contants.merchantPrivateKey;
        config.encryptKey = Contants.encryptKey;
        config.alipayPublicKey=Contants.alipayPublicKey;
        config.notifyUrl = "";
        return config;
    }
}
