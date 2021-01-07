package com.payment.alipay.controller;


import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.payment.alipay.bean.AliPayCommonInfo;
import com.payment.alipay.config.Contants;
import com.payment.alipay.service.AliCommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: zhanghuiyu
 * @Description: 支付宝异步通知
 * @Date: create in 2021/1/6 12:52
 */

@RestController
@RequestMapping("/payment/alipay/callBack")
public class AliPayCallBackController {
    private static final Logger logger = LoggerFactory.getLogger("ali_pay");

    @Autowired
    AliCommonService aliCommonService;
    @PostMapping("/callbackPay")
    public void callbackPay(HttpServletRequest req, HttpServletResponse resp) {
        // 获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = req.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        logger.info(params.toString());

        try {
            Boolean flag = Factory.Payment.Common().verifyNotify(params);

            if (flag) {
                logger.info("-----------------------------------");
                logger.info("验签成功！");
                logger.info("交易状态：" + params.get("trade_status"));
                logger.info("应用id：" + params.get("app_id"));
                logger.info("通知时间：" + params.get("notify_time"));
                logger.info("交易付款时间：" + params.get("gmt_payment"));
                logger.info("买家支付宝账号：" + params.get("buyer_logon_id"));
                logger.info("订单金额：" + params.get("total_amount") + "元");
                logger.info("用户付款金额：" + params.get("buyer_pay_amount") + "元");
                logger.info("卖家实收金额：" + params.get("receipt_amount") + "元");
                logger.info("支付金额渠道信息：" + params.get("fund_bill_list"));
                logger.info("商户系统交易流水号：" + params.get("out_trade_no"));
                logger.info("支付宝系统中的交易流水号：" + params.get("trade_no"));
                logger.info("回传参数：" + params.get("passback_params"));
                logger.info("-----------------------------------");
                if (!Contants.appId.equals(params.get("app_id"))) {
                    logger.info("应用id不匹配！" + Contants.appId + "!="
                            + params.get("app_id"));
                    return;
                }
                AliPayCommonInfo aliPayCommonInfo =new AliPayCommonInfo();
                aliPayCommonInfo.setOutTradeNo(params.get("out_trade_no"));
                AlipayTradeQueryResponse response = aliCommonService.getAlipayTradeQueryResponse(aliPayCommonInfo);

                if (ResponseChecker.success(response)) {
                    logger.info("-------------------------------");
                    logger.info("查询交易成功！");
                    logger.info("商户系统交易流水号：" + response.getOutTradeNo());
                    logger.info("支付宝系统中的交易流水号：" + response.getTradeNo());
                    logger.info("网关返回码：" + response.getCode());
                    logger.info("网关返回码描述：" + response.getMsg());
                    logger.info("业务返回码：" + response.getSubCode());
                    logger.info("业务返回码描述：" + response.getSubMsg());
                    logger.info("交易状态：" + response.getTradeStatus());
                    logger.info("交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）");
                    if (!response.getCode().equals("10000")) {
                        logger.info("网关返回码异常：" + response.getCode() + ","
                                + response.getMsg());
                    }
                    if (response.getTradeStatus().equals("TRADE_SUCCESS")
                            || response.getTradeStatus().equals(
                            "TRADE_FINISHED")) {
                        logger.info("-------------------------------");
                        logger.info("业务系统进行缴费！");
                    } else {
                        logger.info("-------------------------------");
                        logger.info("交易状态异常：" + response.getTradeStatus());
                    }

                } else {
                    logger.info("查询交易失败！");
                }
                resp.getWriter().write("success");
            } else {
                logger.info("验签失败！");
                resp.getWriter().write("failure");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
