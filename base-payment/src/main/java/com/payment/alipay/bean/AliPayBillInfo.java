package com.payment.alipay.bean;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/4 15:31
 */
@Data
public class AliPayBillInfo {

    @Pattern(regexp ="\\d{4}-\\d{2}-\\d{2}", message = "对账日期不合法，应为yyyy-MM-dd")
    private String billDate;
}
