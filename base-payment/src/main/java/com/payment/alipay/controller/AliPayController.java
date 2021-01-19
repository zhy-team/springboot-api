package com.payment.alipay.controller;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.app.Client;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import com.payment.alipay.bean.AliPayInfo;
import com.payment.alipay.service.AlipayService;
import com.payment.utils.PayUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @Author: zhanghuiyu
 * @Description: 支付宝交易类
 * @Date: create in 2021/1/4 14:53
 */

@RestController
@RequestMapping("/payment/alipay/pay")
public class AliPayController {

    @Autowired
    AlipayService alipayService;

    private static final Logger log = LoggerFactory.getLogger("ali_pay");

    /**
     * 通用创建交易
     */
    @PostMapping("/createPay")
    @RepeatSubmit
    @Log(title = "支付宝创建交易", businessType = BusinessType.OTHER)
    public AjaxResult createPay(@RequestBody @Valid AliPayInfo payInfo) {

        try {
            AlipayTradeCreateResponse alipayTradeCreateResponse = alipayService.getAlipayTradeCreateResponse(payInfo);
            if (ResponseChecker.success(alipayTradeCreateResponse)) {
                //支付宝交易号
                log.debug("支付宝交易号{}", alipayTradeCreateResponse.getTradeNo());
                //商户订单号
                log.debug("商户订单号{}", alipayTradeCreateResponse.getOutTradeNo());
                return AjaxResult.success("支付宝创建交易成功", alipayTradeCreateResponse.toMap().toString());
            } else {
                log.error("支付宝创建交易失败{},{},{},{}", alipayTradeCreateResponse.getCode(), alipayTradeCreateResponse.getMsg(), alipayTradeCreateResponse.getSubCode(), alipayTradeCreateResponse.getSubMsg());
                return AjaxResult.error(alipayTradeCreateResponse.getSubMsg());
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝创建交易异常", e);
            return AjaxResult.error(e.getMessage());
        }

    }


    /**
     * 面对面支付
     * <p>
     * 生成交易付款码，待用户扫码付款
     */
    @PostMapping("/faceToFacePrecreate")
    @RepeatSubmit
    @Log(title = "支付宝当面付生成交易付款码", businessType = BusinessType.OTHER)
    public AjaxResult faceToFacePrecreate(@RequestBody @Valid AliPayInfo payInfo) {

        try {
            AlipayTradePrecreateResponse alipayTradePrecreateResponse = alipayService.getAlipayTradePrecreateResponse(payInfo);
            if (ResponseChecker.success(alipayTradePrecreateResponse)) {
                log.debug("支付宝二维码地址{}", alipayTradePrecreateResponse.getQrCode());
                return AjaxResult.success("当面付生成交易付款码成功", alipayTradePrecreateResponse.toMap().toString());
            } else {
                log.error("当面付生成交易付款码失败{},{},{},{}", alipayTradePrecreateResponse.getCode(), alipayTradePrecreateResponse.getMsg(), alipayTradePrecreateResponse.getSubCode(), alipayTradePrecreateResponse.getSubMsg());
                return AjaxResult.error(alipayTradePrecreateResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("当面付生成交易付款码异常", e);
            return AjaxResult.error(e.getMessage());
        }

    }

    /**
     * 面对面支付
     * <p>
     * 扫用户出示的付款码，完成付款
     */
    @PostMapping("/faceToFacePay")
    @RepeatSubmit
    @Log(title = "支付宝付款码交易", businessType = BusinessType.OTHER)
    public AjaxResult faceToFacePay(@RequestBody @Valid AliPayInfo payInfo) {
        try {
            AlipayTradePayResponse alipayTradePayResponse = alipayService.getAlipayTradePayResponse(payInfo);
            if (ResponseChecker.success(alipayTradePayResponse)) {
                return AjaxResult.success("支付宝付款码交易成功", alipayTradePayResponse.toMap().toString());
            } else {
                log.error("付款码交易失败{},{},{},{}", alipayTradePayResponse.getCode(), alipayTradePayResponse.getMsg(), alipayTradePayResponse.getSubCode(), alipayTradePayResponse.getSubMsg());
                return AjaxResult.error(alipayTradePayResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("付款码交易异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * app生成订单串
     *
     * 再使用客户端 SDK 凭此串唤起支付宝收银台
     */
    @PostMapping("/appPay")
    @RepeatSubmit
    @Log(title = "支付宝app生成订单串", businessType = BusinessType.OTHER)
    public AjaxResult appPay(@RequestBody @Valid AliPayInfo payInfo) {
        try {
            AlipayTradeAppPayResponse alipayTradeAppPayResponse = alipayService.getAlipayTradeAppPayResponse(payInfo);
            if (ResponseChecker.success(alipayTradeAppPayResponse)) {
                return AjaxResult.success("支付宝app生成订单串成功", alipayTradeAppPayResponse.getBody());
            } else {
                log.error("app生成订单串失败{}",alipayTradeAppPayResponse.getBody());
                return AjaxResult.error(alipayTradeAppPayResponse.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("app生成订单串异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 电脑网站
     *
     * 生成交易表单，渲染后自动跳转支付宝网站引导用户完成支付
     */
    @PostMapping("/webPay")
    @RepeatSubmit
    @Log(title = "支付宝电脑网站生成订单串", businessType = BusinessType.OTHER)
    public AjaxResult webPay(@RequestBody @Valid AliPayInfo payInfo) {
        try {
            AlipayTradePagePayResponse alipayTradePagePayResponse = alipayService.getAlipayTradePagePayResponse(payInfo);
            if (ResponseChecker.success(alipayTradePagePayResponse)) {
                return AjaxResult.success("支付宝电脑网站生成订单串成功", alipayTradePagePayResponse.getBody());
            } else {
                log.error("电脑网站生成订单串失败{}",alipayTradePagePayResponse.getBody());
                return AjaxResult.error(alipayTradePagePayResponse.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("电脑网站生成订单串异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 手机网站
     *
     * 生成交易表单，渲染后自动跳转支付宝网站引导用户完成支付
     */
    @PostMapping("/wapPay")
    @RepeatSubmit
    @Log(title = "支付宝手机网站生成订单串", businessType = BusinessType.OTHER)
    public AjaxResult wapPay(@RequestBody @Valid AliPayInfo payInfo) {
        try {
            AlipayTradeWapPayResponse alipayTradeWapPayResponse = alipayService.getAlipayTradeWapPayResponse(payInfo);
            if (ResponseChecker.success(alipayTradeWapPayResponse)) {
                return AjaxResult.success("支付宝手机网站生成订单串成功", alipayTradeWapPayResponse.getBody());
            } else {
                log.error("手机网站生成订单串失败{}",alipayTradeWapPayResponse.getBody());
                return AjaxResult.error(alipayTradeWapPayResponse.getBody());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("手机网站生成订单串异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * 创建花呗分期交易
     */
    @PostMapping("/huaBei")
    @RepeatSubmit
    @Log(title = "支付宝创建花呗分期交易", businessType = BusinessType.OTHER)
    public AjaxResult huaBei(@RequestBody @Valid AliPayInfo payInfo) {
        try {

            com.alipay.easysdk.payment.huabei.models.AlipayTradeCreateResponse huaBeiTrade = alipayService.getHuaBeiTrade(payInfo);
            if (ResponseChecker.success(huaBeiTrade)) {
                return AjaxResult.success("支付宝创建花呗分期交易成功", huaBeiTrade.toMap().toString());
            } else {
                log.error("支付宝创建花呗分期交易失败{},{},{},{}", huaBeiTrade.getCode(), huaBeiTrade.getMsg(), huaBeiTrade.getSubCode(), huaBeiTrade.getSubMsg());
                return AjaxResult.error(huaBeiTrade.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("手机创建花呗分期交易异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

}
