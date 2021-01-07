package com.payment.alipay.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/7 15:17
 */

@Data
public class AliAppQrcode {

    /**
     * 必选	256	page/component/component-pages/view/view为小程序中能访问到的页面路径	page/component/component-pages/view/view
     */
    @NotBlank(message = "urlParam不能为空")
    @Size(min = 1,max = 256,message = "urlParam长度不合法")
    private String urlParam;
    /**
     * 必选	256	小程序的启动参数，打开小程序的query ，在小程序 onLaunch的方法中获取	x=1
     */
    @NotBlank(message = "queryParam不能为空")
    @Size(min = 1,max = 256,message = "queryParam长度不合法")
    private String queryParam;

    /**
     * 必选	32	对应的二维码描述	二维码描述
     */
    @NotBlank(message = "describe不能为空")
    @Size(min = 1,max = 32,message = "describe长度不合法")
    private String describe;

}
