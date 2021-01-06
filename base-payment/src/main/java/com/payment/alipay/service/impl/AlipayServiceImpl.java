package com.payment.alipay.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.Client;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.payment.alipay.bean.AliPayInfo;
import com.payment.alipay.service.AlipayService;
import com.payment.utils.PayUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/5 15:20
 */
@Service
public class AlipayServiceImpl implements AlipayService {

    @Override
    public AlipayTradePrecreateResponse getAlipayTradePrecreateResponse(AliPayInfo aliPayInfo) throws Exception {
        com.alipay.easysdk.payment.facetoface.Client client = Factory.Payment.FaceToFace();
        client = (com.alipay.easysdk.payment.facetoface.Client)PayUtils.getClient(client,aliPayInfo.getObjectMap());
        return client.preCreate(aliPayInfo.getSubject(), PayUtils.getNumberForPK(), aliPayInfo.getAmount());
    }

    @Override
    public AlipayTradeCreateResponse getAlipayTradeCreateResponse(AliPayInfo aliPayInfo) throws Exception{
        com.alipay.easysdk.payment.common.Client client = Factory.Payment.Common();
        client = (com.alipay.easysdk.payment.common.Client)PayUtils.getClient(client,aliPayInfo.getObjectMap());
        return client.create(aliPayInfo.getSubject(), PayUtils.getNumberForPK(), aliPayInfo.getAmount(), aliPayInfo.getBuyerId());
    }

    @Override
    public AlipayTradePayResponse getAlipayTradePayResponse(AliPayInfo aliPayInfo) throws Exception {
        com.alipay.easysdk.payment.facetoface.Client client = Factory.Payment.FaceToFace();
        client = (com.alipay.easysdk.payment.facetoface.Client)PayUtils.getClient(client,aliPayInfo.getObjectMap());
        return client.pay(aliPayInfo.getSubject(),PayUtils.getNumberForPK(),aliPayInfo.getAmount(),aliPayInfo.getAuthCode());
    }

    @Override
    public AlipayTradeAppPayResponse getAlipayTradeAppPayResponse(AliPayInfo aliPayInfo) throws Exception {
        com.alipay.easysdk.payment.app.Client client = Factory.Payment.App();
        client = (com.alipay.easysdk.payment.app.Client)PayUtils.getClient(client,aliPayInfo.getObjectMap());
        return client.pay(aliPayInfo.getSubject(),PayUtils.getNumberForPK(),aliPayInfo.getAmount());
    }

    @Override
    public AlipayTradePagePayResponse getAlipayTradePagePayResponse(AliPayInfo aliPayInfo) throws Exception {
        com.alipay.easysdk.payment.page.Client client = Factory.Payment.Page();
        client = (com.alipay.easysdk.payment.page.Client)PayUtils.getClient(client,aliPayInfo.getObjectMap());
        return client.pay(aliPayInfo.getSubject(),PayUtils.getNumberForPK(),aliPayInfo.getAmount(),aliPayInfo.getReturnUrl());
    }

    @Override
    public AlipayTradeWapPayResponse getAlipayTradeWapPayResponse(AliPayInfo aliPayInfo) throws Exception {
        com.alipay.easysdk.payment.wap.Client client = Factory.Payment.Wap();
        client = (com.alipay.easysdk.payment.wap.Client)PayUtils.getClient(client,aliPayInfo.getObjectMap());
        return client.pay(aliPayInfo.getSubject(),PayUtils.getNumberForPK(),aliPayInfo.getAmount(),aliPayInfo.getQuitUrl(),aliPayInfo.getReturnUrl());

    }


}
