package com.ruoyi.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/5 13:10
 */

@Slf4j
@Component
/**
 * 防篡改、防重放攻击过滤器
 */
public class SignAuthFilter implements Filter {
    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("初始化 SignAuthFilter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletRequest requestWrapper = new RequestWrapper(httpRequest);
        if("false".equals(securityProperties.getIsOpen())){
            filterChain.doFilter(requestWrapper, response);
            return;
        }
        Set<String> uriSet = new HashSet<>(securityProperties.getIgnoreSignUri());
        String requestUri = httpRequest.getRequestURI();
        boolean isMatch = false;
        for (String uri : uriSet) {
            isMatch = requestUri.contains(uri);
            if (isMatch) {
                break;
            }
        }
        log.info("当前请求的URI是==>{},isMatch==>{}", httpRequest.getRequestURI(), isMatch);
        if (isMatch) {
            filterChain.doFilter(requestWrapper, response);
            return;
        }

        String sign = requestWrapper.getHeader("Sign");
        Long timestamp = Convert.toLong(requestWrapper.getHeader("Timestamp"));

        if (StringUtils.isEmpty(sign)) {
            returnFail("签名不允许为空", response);
            return;
        }

        if (timestamp == null) {
            returnFail("时间戳不允许为空", response);
            return;
        }

        //重放时间限制（单位秒）
        Long difference = DateUtils.getBetweenNowTimes(timestamp);
        if (difference > securityProperties.getSignTimeout()) {
            returnFail("已过期的签名", response);
            log.info("前端时间戳：{},服务端时间戳：{}",  new Date(timestamp), new Date());
            return;
        }

        boolean accept = true;
        SortedMap<String, String> paramMap;
        switch (requestWrapper.getMethod()) {
            case "GET":
                paramMap = HttpUtil.getUrlParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, sign, timestamp);
                break;
            case "POST":
                paramMap = HttpUtil.getUrlParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, sign, timestamp);
                break;
            case "PUT":
                paramMap = HttpUtil.getUrlParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, sign, timestamp);
                break;
            case "DELETE":
                paramMap = HttpUtil.getBodyParams(requestWrapper);
                accept = SignUtil.verifySign(paramMap, sign, timestamp);
                break;
            default:
                accept = true;
                break;
        }
        if (accept) {
            filterChain.doFilter(requestWrapper, response);
        } else {
            returnFail("签名验证不通过", response);
        }
    }

    private void returnFail(String msg, ServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        String result = JSONObject.toJSONString(AjaxResult.error(msg));
        out.println(result);
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {
        log.info("销毁 SignAuthFilter");
    }
}