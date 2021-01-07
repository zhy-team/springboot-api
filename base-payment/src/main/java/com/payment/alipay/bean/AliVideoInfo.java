package com.payment.alipay.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/7 15:40
 */
@Data
public class AliVideoInfo {

    /**
     * 视频名称
     */
    @NotBlank(message = "视频名称不能为空")
    @Size(min = 1,max = 128,message = "视频名称长度不合法")
    private String videoName;

    /**
     * 视频路径
     */
    @NotBlank(message = "视频名称不能为空")
    private String videoPath;
}
