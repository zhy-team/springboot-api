package com.payment.alipay.controller;

import com.payment.alipay.bean.AliPayBillInfo;
import com.payment.alipay.bean.AliPayInfo;
import com.payment.utils.BillUtils;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.ruoyi.common.core.domain.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author: zhanghuiyu
 * @Description:支付宝对账
 * @Date: create in 2020/12/23 13:24
 */

@RestController
@RequestMapping("/payment/alipay")
public class BillDownloadController {
    private static final Logger log = LoggerFactory.getLogger("ali_pay");

    @GetMapping("/downloadBill")
    public AjaxResult downloadBill(@Valid AliPayBillInfo billInfo) {
        //账单日期格式：yyyy-MM-dd
        try {
            //billType: trade、signcustomer；trade指商户基于支付宝交易收单的业务账单；signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单。
            AlipayDataDataserviceBillDownloadurlQueryResponse billResponse = Factory.Payment.Common().downloadBill("trade", billInfo.getBillDate());
            if (ResponseChecker.success(billResponse)) {
                log.info("支付宝账单地址：" + billResponse.getBillDownloadUrl());
                BillUtils.BillDeal(billResponse.getBillDownloadUrl(), "zhifubaoBill.zip");
                String billTxt = BillUtils.readZipFile("zhifubaoBill.zip", "gbk");
                BillUtils.createFile(billTxt, "d:/", billInfo.getBillDate() + ".txt");
                return AjaxResult.success("支付宝获取账单成功");
            } else {
                log.error("支付宝账单下载失败{},{},{},{}", billResponse.getCode(), billResponse.getMsg(),billResponse.getSubCode(),billResponse.getSubMsg());
                return AjaxResult.error("支付宝账单下载失败");
            }
        } catch (Exception e) {
            log.error("支付宝下载对账单异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

}
