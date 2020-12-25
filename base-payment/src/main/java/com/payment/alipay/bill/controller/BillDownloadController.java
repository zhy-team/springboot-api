package com.payment.alipay.bill.controller;

import com.payment.alipay.bill.BillUtils;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.common.models.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.payment.alipay.config.InitConfig;
import com.ruoyi.common.core.domain.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public AjaxResult downloadBill(HttpServletRequest request, HttpServletResponse response) {
        //账单日期格式：yyyy-MM-dd
        String BillDate = request.getParameter("billDate");

        if (StringUtils.isNotEmpty(BillDate) && BillDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            try {
                //billType: trade、signcustomer；trade指商户基于支付宝交易收单的业务账单；signcustomer是指基于商户支付宝余额收入及支出等资金变动的帐务账单。
                AlipayDataDataserviceBillDownloadurlQueryResponse billResponse = Factory.Payment.Common().downloadBill("trade", BillDate);
                if(ResponseChecker.success(billResponse)){
                    BillUtils.BillDeal(billResponse.getBillDownloadUrl(), "zhifubaoBill.zip");
                    String billTxt = BillUtils.readZipFile("zhifubaoBill.zip", "gbk");
                    BillUtils.createFile(billTxt,"d:/",BillDate+".txt");
                    return AjaxResult.success();
                }else{
                    log.error("支付宝下载失败{},{}", billResponse.getCode(),billResponse.getMsg());
                    return AjaxResult.error("支付宝下载失败");
                }
            } catch (Exception e) {
                log.error("支付宝下载对账单异常", e);
                return AjaxResult.error(e.getMessage());
            }

        } else {
            return AjaxResult.error("支付宝对账日期格式错误");
        }

    }

}
