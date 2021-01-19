package com.payment.unionpay.controller;

import com.payment.unionpay.acp.demo.DemoBase;
import com.payment.unionpay.acp.sdk.AcpService;
import com.payment.unionpay.acp.sdk.LogUtil;
import com.payment.unionpay.acp.sdk.SDKConfig;
import com.payment.unionpay.bean.UnionPayInfo;
import com.payment.unionpay.config.Contants;
import com.payment.utils.PayUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment/unionpay")
public class UnionPayController {

    private static final Logger log = LoggerFactory.getLogger("union_pay");


    @PostMapping("/Form_6_2_FrontConsume")
    @RepeatSubmit
    @Log(title = "银联支付请求", businessType = BusinessType.OTHER)
    public AjaxResult form_6_2_FrontConsume(@RequestBody @Valid UnionPayInfo unionPayInfo) {
        Map<String, String> requestData = Contants.getData("01","01","000201",unionPayInfo.getTxnAmt(),unionPayInfo.getReqReserved());
        requestData.put("channelType", "07");
        Map<String, String> submitFromData = AcpService.sign(requestData,DemoBase.encoding);
        String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();
        String html = AcpService.createAutoFormHtml(requestFrontUrl, submitFromData,DemoBase.encoding);
        return AjaxResult.success("银联支付用接口成功", html);
    }
}
