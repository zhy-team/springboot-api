package com.payment.alipay.service;


import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.payment.alipay.bean.AliPayCommonInfo;

public interface AliCommonService {
    /**
     * 交易查询
     */
    AlipayTradeQueryResponse getAlipayTradeQueryResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception;

    /**
     * 退款
     * @param aliPayCommonInfo
     * @return
     * @throws Exception
     */
    AlipayTradeRefundResponse getAlipayTradeRefundResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception;

    /**
     * 退款查询
     * @param aliPayCommonInfo
     * @return
     * @throws Exception
     */
    AlipayTradeFastpayRefundQueryResponse getAlipayTradeFastpayRefundQueryResponse (AliPayCommonInfo aliPayCommonInfo) throws Exception;
}
