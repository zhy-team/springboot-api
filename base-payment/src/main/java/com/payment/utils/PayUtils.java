package com.payment.utils;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.facetoface.Client;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

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

    public static Object getClient(Object client,Map<String,Object> map) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<?> clazz = Class.forName(client.getClass().getName());
        if(map!=null){
            if(map.get("agent")!=null){
                Method agent = clazz.getMethod("agent",String.class);
                client = agent.invoke(client,(String)map.get("agent"));
            }
            if(map.get("asyncNotify")!=null){
                Method agent = clazz.getMethod("asyncNotify",String.class);
                client = agent.invoke(client,(String)map.get("asyncNotify"));
            }
            if(map.get("batchOptional")!=null){
                Method agent = clazz.getMethod("batchOptional",Map.class);
                client = agent.invoke(client,(Map<String, Object>)map.get("batchOptional"));
            }
        }
        return client;
    }

}
