package com.payment.alipay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.payment.alipay.bean.AliPayInfo;
import com.payment.utils.PayUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/4 14:53
 */

@RestController
@RequestMapping("/payment/alipay")
public class AliPayController {

    private static final Logger log = LoggerFactory.getLogger("ali_pay");

    @PostMapping("/createPay")
    public AjaxResult createPay(@Valid AliPayInfo payInfo) {

        try {
            AlipayTradeCreateResponse alipayTradeCreateResponse = Factory.Payment.Common().create(payInfo.getSubject(), PayUtils.getNumberForPK(), payInfo.getAmount().toString(), payInfo.getBuyerId());
            if (ResponseChecker.success(alipayTradeCreateResponse)) {
                //支付宝交易号
                log.debug("支付宝交易号{}",alipayTradeCreateResponse.getTradeNo());
                //商户订单号
                log.debug("商户订单号{}",alipayTradeCreateResponse.getOutTradeNo());
                return AjaxResult.success("创建交易成功");
            } else {
                log.error("支付宝创建交易失败{},{},{},{}", alipayTradeCreateResponse.getCode(), alipayTradeCreateResponse.getMsg(),alipayTradeCreateResponse.getSubCode(),alipayTradeCreateResponse.getSubMsg());
                return AjaxResult.error(alipayTradeCreateResponse.getSubMsg());
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝创建交易异常", e);
            return AjaxResult.error(e.getMessage());
        }

    }

}
