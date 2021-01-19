package com.payment.alipay.service;

import com.alipay.easysdk.payment.common.models.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.payment.alipay.bean.AliPayBillInfo;

public interface AliBillService {

    /**
     * 获取对账单
     * @param billInfo
     * @return
     */
    AlipayDataDataserviceBillDownloadurlQueryResponse getBill(AliPayBillInfo billInfo) throws Exception;
}
