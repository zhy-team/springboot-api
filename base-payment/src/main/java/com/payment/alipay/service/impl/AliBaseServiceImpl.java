package com.payment.alipay.service.impl;

import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.payment.alipay.bean.AliOauthInfo;
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
}
