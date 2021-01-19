package com.payment.alipay.service;


import com.alipay.easysdk.payment.common.models.*;
import com.payment.alipay.bean.AliPayCommonInfo;

public interface AliCommonService {
    /**
     *
     * 交易查询
     * @param aliPayCommonInfo
     * @return AlipayTradeQueryResponse
     * @throws Exception
     */
    AlipayTradeQueryResponse getAlipayTradeQueryResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception;

    /**
     * 退款
     * @param aliPayCommonInfo
     * @return AlipayTradeRefundResponse
     * @throws Exception
     */
    AlipayTradeRefundResponse getAlipayTradeRefundResponse(AliPayCommonInfo aliPayCommonInfo) throws Exception;

    /**
     * 退款查询
     * @param aliPayCommonInfo
     * @return AlipayTradeFastpayRefundQueryResponse
     * @throws Exception
     */
    AlipayTradeFastpayRefundQueryResponse getAlipayTradeFastpayRefundQueryResponse (AliPayCommonInfo aliPayCommonInfo) throws Exception;

    /**
     * 撤销交易
     * @param aliPayCommonInfo
     * @return AlipayTradeCancelResponse
     * @throws Exception
     */
    AlipayTradeCancelResponse getAlipayTradeCancelResponse (AliPayCommonInfo aliPayCommonInfo) throws Exception;

    /**
     * 取消交易
     * @param aliPayCommonInfo
     * @return AlipayTradeCloseResponse
     * @throws Exception
     */
    AlipayTradeCloseResponse getAlipayTradeCloseResponse (AliPayCommonInfo aliPayCommonInfo) throws Exception;
}
