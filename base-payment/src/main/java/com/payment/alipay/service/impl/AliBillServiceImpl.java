package com.payment.alipay.service.impl;

import com.alipay.easysdk.factory.Factory;

import com.alipay.easysdk.payment.common.models.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.payment.alipay.bean.AliPayBillInfo;
import com.payment.alipay.service.AliBillService;
import org.springframework.stereotype.Service;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/6 11:09
 */

@Service
public class AliBillServiceImpl implements AliBillService {
    @Override
    public AlipayDataDataserviceBillDownloadurlQueryResponse getBill(AliPayBillInfo billInfo) throws Exception{
        return Factory.Payment.Common().downloadBill("trade", billInfo.getBillDate());
    }
}
