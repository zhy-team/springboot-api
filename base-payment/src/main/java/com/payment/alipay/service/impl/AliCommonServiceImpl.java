package com.payment.alipay.service.impl;

import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.payment.alipay.bean.AliPayCommonInfo;
import com.payment.alipay.service.AliCommonService;
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
        return Factory.Payment.Common().query(aliPayCommonInfo.getOutTradeNo());
    }

}
