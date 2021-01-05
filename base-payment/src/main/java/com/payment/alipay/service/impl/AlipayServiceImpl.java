package com.payment.alipay.service.impl;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.facetoface.Client;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
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
        Client client = Factory.Payment.FaceToFace();
        client = (Client)PayUtils.getClient(client,aliPayInfo.getObjectMap());
        return client.preCreate(aliPayInfo.getSubject(), PayUtils.getNumberForPK(), aliPayInfo.getAmount().toString());
    }




}
