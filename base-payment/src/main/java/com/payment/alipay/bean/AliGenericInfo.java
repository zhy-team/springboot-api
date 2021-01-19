package com.payment.alipay.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/7 11:37
 */
@Data
public class AliGenericInfo {


    /**
     * 方法名
     */
    @NotBlank(message = "方法名不能为空")
    private String method;

    /**
     * 设置系统参数（OpenAPI中非biz_content里的参数）
     */
    private Map<String, String> textParams;
    /**
     * 设置业务参数（OpenAPI中biz_content里的参数）
     */
    private Map<String, Object> bizParams;
}
