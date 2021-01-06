package com.payment.alipay.service;

import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.payment.alipay.bean.AliPayInfo;

import java.lang.reflect.InvocationTargetException;

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

    /**
     * 通用创建交易
     */
    AlipayTradeCreateResponse getAlipayTradeCreateResponse(AliPayInfo payInfo) throws Exception;

    /**
     * 面对面支付
     * <p>
     * 扫用户出示的付款码，完成付款
     */
    AlipayTradePayResponse getAlipayTradePayResponse(AliPayInfo payInfo) throws Exception;

    /**
     * app生成订单串
     *
     * 再使用客户端 SDK 凭此串唤起支付宝收银台
     */
    AlipayTradeAppPayResponse getAlipayTradeAppPayResponse(AliPayInfo payInfo) throws Exception;

    /**
     * 电脑网站
     *
     * 生成交易表单，渲染后自动跳转支付宝网站引导用户完成支付
     */
    AlipayTradePagePayResponse getAlipayTradePagePayResponse(AliPayInfo payInfo) throws Exception;

    /**
     * 手机网站
     *
     * 生成交易表单，渲染后自动跳转支付宝网站引导用户完成支付
     */
    AlipayTradeWapPayResponse getAlipayTradeWapPayResponse(AliPayInfo payInfo) throws Exception;
}
