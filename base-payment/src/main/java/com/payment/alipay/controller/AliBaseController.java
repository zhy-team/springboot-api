package com.payment.alipay.controller;

import com.alipay.easysdk.base.image.models.AlipayOfflineMaterialImageUploadResponse;
import com.alipay.easysdk.base.oauth.Client;
import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.alipay.easysdk.base.qrcode.models.AlipayOpenAppQrcodeCreateResponse;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.member.identification.models.AlipayUserCertifyOpenInitializeResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCloseResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.payment.alipay.bean.*;
import com.payment.alipay.service.AliBaseService;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
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
 * @Description:
 * @Date: create in 2021/1/7 9:55
 */

@RestController
@RequestMapping("/payment/alipay/base")
public class AliBaseController {

    private static final Logger log = LoggerFactory.getLogger("ali_pay");

    @Autowired
    AliBaseService aliBaseService;

    /**
     * 通用接口
     *
     * //设置系统参数（OpenAPI中非biz_content里的参数）
     * Map<String, String> textParams = new HashMap<>();
     * textParams.put("app_auth_token", "201712BB_D0804adb2e743078d1822d536956X34");
     *
     * //设置业务参数（OpenAPI中biz_content里的参数）
     * Map<String, Object> bizParams = new HashMap<>();
     * bizParams.put("subject", "Iphone6 16G");
     * bizParams.put("out_trade_no", UUID.randomUUID().toString());
     * bizParams.put("total_amount", "0.10");
     * bizParams.put("buyer_id", "2088002656718920");
     * Map<String, String> extendParams = new HashMap<>();
     * extendParams.put("hb_fq_num", "3");
     * extendParams.put("hb_fq_seller_percent", "3");
     * bizParams.put("extend_params", extendParams);
     *
     * AlipayOpenApiGenericResponse response = Factory.Util.Generic().execute(
     *         "alipay.trade.create", textParams, bizParams);
     */
    @PostMapping("/Generic/execute")
    @RepeatSubmit
    public AjaxResult execute(@RequestBody @Valid AliGenericInfo aliGenericInfo) {
        try {
            AlipayOpenApiGenericResponse execute = Factory.Util.Generic().execute(aliGenericInfo.getMethod(), aliGenericInfo.getTextParams(), aliGenericInfo.getBizParams());
            if (ResponseChecker.success(execute)) {
                return AjaxResult.success("支付宝通用接口成功", execute.toMap().toString());
            } else {
                log.error("支付宝通用接口失败{},{},{},{}", execute.getCode(), execute.getMsg(), execute.getSubCode(), execute.getSubMsg());
                return AjaxResult.error(execute.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝通用接口异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }


    @PostMapping("/OAuth/getToken")
    @RepeatSubmit
    public AjaxResult getToken(@RequestBody @Valid AliOauthInfo aliOauthInfo) {
        try {
            AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = aliBaseService.getAlipaySystemOauthTokenResponse(aliOauthInfo);

            if (ResponseChecker.success(alipaySystemOauthTokenResponse)) {
                return AjaxResult.success("支付宝获取授权访问令牌成功", alipaySystemOauthTokenResponse.toMap().toString());
            } else {
                log.error("支付宝获取授权访问令牌失败{},{},{},{}", alipaySystemOauthTokenResponse.getCode(), alipaySystemOauthTokenResponse.getMsg(), alipaySystemOauthTokenResponse.getSubCode(), alipaySystemOauthTokenResponse.getSubMsg());
                return AjaxResult.error(alipaySystemOauthTokenResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝获取授权访问令牌异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }


    @PostMapping("/OAuth/refreshToken")
    @RepeatSubmit
    public AjaxResult refreshToken(@RequestBody @Valid AliOauthInfo aliOauthInfo) {
        try {
            AlipaySystemOauthTokenResponse alipaySystemOauthTokenResponse = aliBaseService.refreshTokenResponse(aliOauthInfo);
             if (ResponseChecker.success(alipaySystemOauthTokenResponse)) {
                return AjaxResult.success("支付宝刷新令牌成功", alipaySystemOauthTokenResponse.toMap().toString());
            } else {
                log.error("支付宝刷新令牌失败{},{},{},{}", alipaySystemOauthTokenResponse.getCode(), alipaySystemOauthTokenResponse.getMsg(), alipaySystemOauthTokenResponse.getSubCode(), alipaySystemOauthTokenResponse.getSubMsg());
                return AjaxResult.error(alipaySystemOauthTokenResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝刷新令牌异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }


    @PostMapping("/Qrcode/create")
    @RepeatSubmit
    public AjaxResult QrcodeCreate(@RequestBody @Valid AliAppQrcode aliAppQrcode) {
        try {
            AlipayOpenAppQrcodeCreateResponse alipayOpenAppQrcodeCreateResponse = aliBaseService.getAlipayOpenAppQrcodeCreateResponse(aliAppQrcode);
            if (ResponseChecker.success(alipayOpenAppQrcodeCreateResponse)) {
                return AjaxResult.success("支付宝生成小程序二维码成功", alipayOpenAppQrcodeCreateResponse.toMap().toString());
            } else {
                log.error("支付宝生成小程序二维码失败{},{},{},{}", alipayOpenAppQrcodeCreateResponse.getCode(), alipayOpenAppQrcodeCreateResponse.getMsg(), alipayOpenAppQrcodeCreateResponse.getSubCode(), alipayOpenAppQrcodeCreateResponse.getSubMsg());
                return AjaxResult.error(alipayOpenAppQrcodeCreateResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝生成小程序二维码异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/Image/upload")
    @RepeatSubmit
    public AjaxResult ImageUpload(@RequestBody @Valid AliImagesInfo aliImagesInfo) {
        try {
            com.alipay.easysdk.base.image.models.AlipayOfflineMaterialImageUploadResponse alipayOfflineMaterialImageUploadResponse=aliBaseService.uploadImage(aliImagesInfo);
            if (ResponseChecker.success(alipayOfflineMaterialImageUploadResponse)) {
                return AjaxResult.success("支付宝上传图片成功", alipayOfflineMaterialImageUploadResponse.toMap().toString());
            } else {
                log.error("支付宝上传图片失败{},{},{},{}", alipayOfflineMaterialImageUploadResponse.getCode(), alipayOfflineMaterialImageUploadResponse.getMsg(), alipayOfflineMaterialImageUploadResponse.getSubCode(), alipayOfflineMaterialImageUploadResponse.getSubMsg());
                return AjaxResult.error(alipayOfflineMaterialImageUploadResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝上传图片异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

    @PostMapping("/Video/upload")
    @RepeatSubmit
    public AjaxResult VideoUpload(@RequestBody @Valid AliVideoInfo aliVideoInfo) {
        try {
            com.alipay.easysdk.base.video.models.AlipayOfflineMaterialImageUploadResponse alipayOfflineMaterialImageUploadResponse = aliBaseService.uploadVideo(aliVideoInfo);
            if (ResponseChecker.success(alipayOfflineMaterialImageUploadResponse)) {
                return AjaxResult.success("支付宝上传视频成功", alipayOfflineMaterialImageUploadResponse.toMap().toString());
            } else {
                log.error("支付宝上传视频失败{},{},{},{}", alipayOfflineMaterialImageUploadResponse.getCode(), alipayOfflineMaterialImageUploadResponse.getMsg(), alipayOfflineMaterialImageUploadResponse.getSubCode(), alipayOfflineMaterialImageUploadResponse.getSubMsg());
                return AjaxResult.error(alipayOfflineMaterialImageUploadResponse.getSubMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("支付宝上传视频异常", e);
            return AjaxResult.error(e.getMessage());
        }
    }

}
