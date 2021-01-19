package com.ruoyi.framework.interceptor;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/5 13:19
 */

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * http 工具类 获取请求中的参数
 */
@Slf4j
public class HttpUtil {
    /**
     * 获取 Body 参数
     *
     * @param request
     */
    public static SortedMap<String, String> getBodyParams(final HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str;
        StringBuilder wholeStr = new StringBuilder();
        //一行一行的读取body体里面的内容；
        while ((str = reader.readLine()) != null) {
            wholeStr.append(str);
        }

        if (StringUtils.isEmpty(wholeStr)) {
            wholeStr.append("{}");
        }

        //转化成json对象
        return JSONObject.parseObject(wholeStr.toString(), SortedMap.class);
    }

    /**
     * 将URL请求参数转换成Map
     *
     * @param request
     */
    public static SortedMap<String, String> getUrlParams(HttpServletRequest request) {
        String param = "";
        SortedMap<String, String> result = new TreeMap<>();
        if (StringUtils.isEmpty(request.getQueryString())) {
            return result;
        }

        try {
            param = URLDecoder.decode(request.getQueryString(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String[] params = param.split("&");
        for (String s : params) {
            int index = s.indexOf("=");
            result.put(s.substring(0, index), s.substring(index + 1));
        }
        return result;
    }
}