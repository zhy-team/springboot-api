package com.payment.alipay.service;

import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.payment.alipay.bean.AliPayInfo;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/5 15:15
 */


public interface AlipayService {
    /**
     * 面对面支付
     * <p>
     * 生成交易付款码，待用户扫码付款
     */
    AlipayTradePrecreateResponse getAlipayTradePrecreateResponse(AliPayInfo aliPayInfo) throws Exception;
}
