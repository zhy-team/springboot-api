package com.ruoyi.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.SortedMap;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/5 13:13
 */

@Slf4j
public class SignUtil {


    private static final String key="88weSQdasdwe124584sw5s1da78WQ";

    /**
     * 验证签名
     *
     * @param params
     * @param sign
     * @return
     */
    public static boolean verifySign(SortedMap<String, String> params, String sign, Long timestamp) {
        String paramsJsonStr = "Timestamp" + timestamp + JSONObject.toJSONString(params)+"key="+key;
        return verifySign(paramsJsonStr, sign);
    }

    /**
     * 验证签名
     *
     * @param params
     * @param sign
     * @return
     */
    public static boolean verifySign(String params, String sign) {
        log.info("Header Sign : {}", sign);
        if (StringUtils.isEmpty(params)) {
            return false;
        }
        log.info("Param : {}", params);
        String paramsSign = getParamsSign(params);
        log.info("Param Sign : {}", paramsSign);
        return sign.equals(paramsSign);
    }

    /**
     * @return 得到签名
     */
    public static String getParamsSign(String params) {
        return DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
    }
}