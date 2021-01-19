package com.payment.alipay.service;

import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.alipay.easysdk.base.qrcode.models.AlipayOpenAppQrcodeCreateResponse;
import com.payment.alipay.bean.AliAppQrcode;
import com.payment.alipay.bean.AliImagesInfo;
import com.payment.alipay.bean.AliOauthInfo;
import com.payment.alipay.bean.AliVideoInfo;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/7 9:50
 */

public interface AliBaseService {

    /**
     * 获取用户token
     * @param aliOauthInfo
     * @return
     * @throws Exception
     */
    AlipaySystemOauthTokenResponse getAlipaySystemOauthTokenResponse(AliOauthInfo aliOauthInfo)throws Exception;

    /**
     * 刷新token
     * @param aliOauthInfo
     * @return
     * @throws Exception
     */
    AlipaySystemOauthTokenResponse refreshTokenResponse(AliOauthInfo aliOauthInfo)throws Exception;

    /**
     * 生成小程序二维码
     * @return
     * @throws Exception
     */

    AlipayOpenAppQrcodeCreateResponse getAlipayOpenAppQrcodeCreateResponse(AliAppQrcode aliAppQrcode) throws Exception;

    /**
     * 上传图片
     * @param aliImagesInfo
     * @return
     * @throws Exception
     */
    com.alipay.easysdk.base.image.models.AlipayOfflineMaterialImageUploadResponse uploadImage(AliImagesInfo aliImagesInfo) throws Exception;


    com.alipay.easysdk.base.video.models.AlipayOfflineMaterialImageUploadResponse uploadVideo(AliVideoInfo aliVideoInfo) throws Exception;
}
