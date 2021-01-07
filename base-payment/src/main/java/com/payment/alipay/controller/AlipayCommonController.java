package com.payment.alipay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.*;
import com.payment.alipay.bean.AliPayCommonInfo;
import com.payment.alipay.bean.AliPayInfo;
import com.payment.alipay.service.AliCommonService;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/6 13:25
 */
@RestController
@RequestMapping("/payment/alipay/common")
public class AlipayCommonController {

    @Autowired
    AliCommonService aliCommonService;

    private static final Logger log = LoggerFactory.getLogger("ali_pay");

    /**
     * 查询交易
     */
    @PostMapping("/query")
    @RepeatSubmit
    public AjaxResult query(@Valid AliPayCommonInfo aliPayCommonInfo) {
        try {
            AlipayTradeQueryResponse alipayTradeQueryResponse = aliCommonService.getAlipayTradeQueryResponse(aliPayCommonInfo);
            if (ResponseChecker.success(alipayTradeQueryResponse)) {
                return AjaxResult.success("支付宝查询交易成功", alipayTradeQueryResponse.toMap().toString());
            } else {
                log.error("支付宝查询交易失败{},{},{},{}", alipayTradeQueryResponse.getCode(), alipayTradeQueryResponse.getMsg(), alipayTradeQueryResponse.getSubCode(), alipayTradeQueryResponse.getSubMsg());
                return AjaxResult.error(alipayTradeQueryResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝查询交易异常", e);
            return AjaxResult.error(e.getMessage());
        }

    }


    /**
     * 退款
     */
    @PostMapping("/refund")
    @RepeatSubmit
    public AjaxResult refund(@Valid AliPayCommonInfo aliPayCommonInfo) {
        try {
            AlipayTradeRefundResponse alipayTradeRefundResponse = aliCommonService.getAlipayTradeRefundResponse(aliPayCommonInfo);
            if (ResponseChecker.success(alipayTradeRefundResponse)) {
                return AjaxResult.success("支付宝退款成功", alipayTradeRefundResponse.toMap().toString());
            } else {
                log.error("支付宝退款失败{},{},{},{}", alipayTradeRefundResponse.getCode(), alipayTradeRefundResponse.getMsg(), alipayTradeRefundResponse.getSubCode(), alipayTradeRefundResponse.getSubMsg());
                return AjaxResult.error(alipayTradeRefundResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝退款异常", e);
            return AjaxResult.error(e.getMessage());
        }

    }

    /**
     * 退款查询
     */
    @PostMapping("/queryRefund")
    @RepeatSubmit
    public AjaxResult queryRefund(@Valid AliPayCommonInfo aliPayCommonInfo) {
        try {
            AlipayTradeFastpayRefundQueryResponse alipayTradeFastpayRefundQueryResponse = aliCommonService.getAlipayTradeFastpayRefundQueryResponse(aliPayCommonInfo);
            if (ResponseChecker.success(alipayTradeFastpayRefundQueryResponse)) {
                return AjaxResult.success("支付宝退款查询成功", alipayTradeFastpayRefundQueryResponse.toMap().toString());
            } else {
                log.error("支付宝退款查询失败{},{},{},{}", alipayTradeFastpayRefundQueryResponse.getCode(), alipayTradeFastpayRefundQueryResponse.getMsg(), alipayTradeFastpayRefundQueryResponse.getSubCode(), alipayTradeFastpayRefundQueryResponse.getSubMsg());
                return AjaxResult.error(alipayTradeFastpayRefundQueryResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝退款查询异常", e);
            return AjaxResult.error(e.getMessage());
        }

    }


    /**
     * 撤销交易
     */
    @PostMapping("/cancel")
    @RepeatSubmit
    public AjaxResult cancel(@Valid AliPayCommonInfo aliPayCommonInfo) {
        try {
            AlipayTradeCancelResponse alipayTradeCancelResponse = aliCommonService.getAlipayTradeCancelResponse(aliPayCommonInfo);
            if (ResponseChecker.success(alipayTradeCancelResponse)) {
                return AjaxResult.success("支付宝撤销交易成功", alipayTradeCancelResponse.toMap().toString());
            } else {
                log.error("支付宝撤销交易失败{},{},{},{}", alipayTradeCancelResponse.getCode(), alipayTradeCancelResponse.getMsg(), alipayTradeCancelResponse.getSubCode(), alipayTradeCancelResponse.getSubMsg());
                return AjaxResult.error(alipayTradeCancelResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝撤销交易异常", e);
            return AjaxResult.error(e.getMessage());
        }

    }


    /**
     * 关闭交易
     */
    @PostMapping("/close")
    @RepeatSubmit
    public AjaxResult close(@Valid AliPayCommonInfo aliPayCommonInfo) {
        try {
            AlipayTradeCloseResponse alipayTradeCloseResponse = aliCommonService.getAlipayTradeCloseResponse(aliPayCommonInfo);
            if (ResponseChecker.success(alipayTradeCloseResponse)) {
                return AjaxResult.success("支付宝关闭交易成功", alipayTradeCloseResponse.toMap().toString());
            } else {
                log.error("支付宝关闭交易失败{},{},{},{}", alipayTradeCloseResponse.getCode(), alipayTradeCloseResponse.getMsg(), alipayTradeCloseResponse.getSubCode(), alipayTradeCloseResponse.getSubMsg());
                return AjaxResult.error(alipayTradeCloseResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝关闭交易异常", e);
            return AjaxResult.error(e.getMessage());
        }

    }

}
