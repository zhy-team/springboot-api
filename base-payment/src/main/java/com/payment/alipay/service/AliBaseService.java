package com.payment.alipay.service;

import com.alipay.easysdk.base.oauth.models.AlipaySystemOauthTokenResponse;
import com.payment.alipay.bean.AliOauthInfo;

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

}
