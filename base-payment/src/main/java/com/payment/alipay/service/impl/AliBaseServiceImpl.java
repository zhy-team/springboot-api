package com.payment.alipay.service.impl;

import com.alipay.easysdk.base.image.models.AlipayOfflineMaterialImageUploadResponse;
import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.alipay.easysdk.base.qrcode.models.AlipayOpenAppQrcodeCreateResponse;
import com.payment.alipay.bean.AliAppQrcode;
import com.payment.alipay.bean.AliImagesInfo;
import com.payment.alipay.bean.AliOauthInfo;
import com.payment.alipay.bean.AliVideoInfo;
import com.payment.alipay.service.AliBaseService;
import org.springframework.stereotype.Service;
import com.alipay.easysdk.factory.Factory;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/7 9:51
 */
@Service
public class AliBaseServiceImpl implements AliBaseService {

    @Override
    public AlipaySystemOauthTokenResponse getAlipaySystemOauthTokenResponse(AliOauthInfo aliOauthInfo) throws Exception {
        return Factory.Base.OAuth().getToken(aliOauthInfo.getCode());
    }

    @Override
    public AlipaySystemOauthTokenResponse refreshTokenResponse(AliOauthInfo aliOauthInfo) throws Exception {
        return Factory.Base.OAuth().refreshToken(aliOauthInfo.getRefreshToken());
    }

    @Override
    public AlipayOpenAppQrcodeCreateResponse getAlipayOpenAppQrcodeCreateResponse(AliAppQrcode aliAppQrcode) throws Exception {
        return Factory.Base.Qrcode().create(aliAppQrcode.getUrlParam(),aliAppQrcode.getUrlParam(),aliAppQrcode.getDescribe());
    }

    @Override
    public AlipayOfflineMaterialImageUploadResponse uploadImage(AliImagesInfo aliImagesInfo) throws Exception {
        return Factory.Base.Image().upload(aliImagesInfo.getImageName(),aliImagesInfo.getImagePath());
    }

    @Override
    public com.alipay.easysdk.base.video.models.AlipayOfflineMaterialImageUploadResponse uploadVideo(AliVideoInfo aliVideoInfo) throws Exception {
        return Factory.Base.Video().upload(aliVideoInfo.getVideoName(),aliVideoInfo.getVideoPath());
    }
}
