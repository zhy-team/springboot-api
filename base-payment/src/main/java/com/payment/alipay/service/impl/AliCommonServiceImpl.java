package com.payment.alipay.service.impl;

import com.alipay.easysdk.payment.common.models.*;
import com.payment.alipay.bean.AliPayCommonInfo;
import com.payment.alipay.service.AliCommonService;
import com.payment.utils.PayUtils;
import org.springframework.stereotype.Service;
import com.alipay.easysdk.factory.Factory;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/6 13:01
 */
@Service
public class AliCommonServiceImpl implements AliCommonService {

    @Override
    public AlipayTradeQueryResponse getAlipayTradeQueryResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception {
        com.alipay.easysdk.payment.common.Client client = Factory.Payment.Common();
        client = (com.alipay.easysdk.payment.common.Client)PayUtils.getClient(client,aliPayCommonInfo.getObjectMap());
        return client.query(aliPayCommonInfo.getOutTradeNo());
    }

    @Override
    public AlipayTradeRefundResponse getAlipayTradeRefundResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception {
        com.alipay.easysdk.payment.common.Client client = Factory.Payment.Common();
        client = (com.alipay.easysdk.payment.common.Client)PayUtils.getClient(client,aliPayCommonInfo.getObjectMap());
        return client.refund(aliPayCommonInfo.getOutTradeNo(),aliPayCommonInfo.getRefundAmount());
    }

    @Override
    public AlipayTradeFastpayRefundQueryResponse getAlipayTradeFastpayRefundQueryResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception {
        com.alipay.easysdk.payment.common.Client client = Factory.Payment.Common();
        client = (com.alipay.easysdk.payment.common.Client)PayUtils.getClient(client,aliPayCommonInfo.getObjectMap());
        return client.queryRefund(aliPayCommonInfo.getOutTradeNo(),aliPayCommonInfo.getOutRequestNo());
    }

    @Override
    public AlipayTradeCancelResponse getAlipayTradeCancelResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception {
        com.alipay.easysdk.payment.common.Client client = Factory.Payment.Common();
        client = (com.alipay.easysdk.payment.common.Client)PayUtils.getClient(client,aliPayCommonInfo.getObjectMap());
        return client.cancel(aliPayCommonInfo.getOutTradeNo());
    }

    @Override
    public AlipayTradeCloseResponse getAlipayTradeCloseResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception {
        com.alipay.easysdk.payment.common.Client client = Factory.Payment.Common();
        client = (com.alipay.easysdk.payment.common.Client)PayUtils.getClient(client,aliPayCommonInfo.getObjectMap());
        return client.close(aliPayCommonInfo.getOutTradeNo());
    }

}
