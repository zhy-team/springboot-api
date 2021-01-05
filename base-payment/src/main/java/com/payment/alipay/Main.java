package com.payment.alipay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.factory.Factory.Payment;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.payment.alipay.config.Contants;

public class Main {

    /***
     *
     * List<Object> goodsDetailList = new ArrayList<>();
     * Map<String, Object> goodsDetail = new HashMap<>();
     * goodsDetail.put("goods_id", "apple-01");
     * goodsDetail.put("goods_name", "Apple iPhone11 128G");
     * goodsDetail.put("quantity", 1);
     * goodsDetail.put("price", "5799.00");
     * goodsDetailList.add(goodsDetail);
     *
     *Factory.Payment.FaceToFace()
     *      // 调用optional扩展方法，完成可选业务参数（biz_content下的可选字段）的设置
     *      .optional("seller_id", "2088102146225135")
     *      .optional("discountable_amount", "8.88")
     *      .optional("goods_detail", goodsDetailList)
     *      .preCreate("Apple iPhone11 128G", "2234567890", "5799.00");
     *
     *
     * Map<String, Object> optionalArgs = new HashMap<>();
     * optionalArgs.put("seller_id", "2088102146225135");
     * optionalArgs.put("discountable_amount", "8.88");
     * optionalArgs.put("goods_detail", goodsDetailList);
     *
     *Factory.Payment.FaceToFace()
     *     // 也可以调用batchOptional扩展方法，批量设置可选业务参数（biz_content下的可选字段）
     *     .batchOptional(optionalArgs)
     *     .preCreate("Apple iPhone11 128G", "2234567890", "5799.00");
     *
     *
     *Factory.Payment.FaceToFace()
     *     // 调用asyncNotify扩展方法，可以为每次API调用，设置独立的异步通知地址
     *     // 此处设置的异步通知地址的优先级高于全局Config中配置的异步通知地址
     *     .asyncNotify("https://www.test.com/callback")
     *     .preCreate("Apple iPhone11 128G", "2234567890", "5799.00");
     *
     *Factory.Payment.FaceToFace()
     *     // 调用agent扩展方法，设置app_auth_token，完成第三方代调用
     *     .agent("ca34ea491e7146cc87d25fca24c4cD11")
     *     .preCreate("Apple iPhone11 128G", "2234567890", "5799.00");
     *
     *
     *
     * 前端请求:
     * {
     * 	"amount":1000,
     * 	"subject":"cece",
     * 	"objectMap":{
     * 		"asyncNotify":"http://ceshi.com/ss",
     * 		"batchOptional":{
     * 			"body":"2088102146225135",
     * 			"goods_detail":[{
     * 				"goods_id": "apple-01",
     * 				"goods_name": "Apple iPhone11 128G"
     *              },
     *             {
     * 				"goods_id": "apple-02",
     * 				"goods_name": "Apple iPhone11 256G"
     *            }]
     *
     *     }
     *
     *    }

     * }
     *
     *
     */
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
