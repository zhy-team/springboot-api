package com.payment.alipay.service;


import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.payment.alipay.bean.AliPayCommonInfo;

public interface AliCommonService {
    /**
     * 交易查询
     */
    AlipayTradeQueryResponse getAlipayTradeQueryResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception;
}
