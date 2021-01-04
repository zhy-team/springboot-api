package com.payment.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zhanghuiyu
 * @Description:
 * @Date: create in 2021/1/4 15:01
 */

public class PayUtils {

    // 产生流水号
    public static String getNumberForPK() {
        String id = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String temp = sf.format(new Date());
        int random = (int) (Math.random() * 10000);
        String ran = "";
        if (("" + random).length() != 5) {
            ran = StringUtils.leftPad(random + "", 5, '0');
        }
        id = temp + ran;
        return id;
    }
}
