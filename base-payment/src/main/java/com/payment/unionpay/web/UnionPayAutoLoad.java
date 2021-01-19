package com.payment.unionpay.web;


import com.payment.unionpay.acp.sdk.SDKConfig;
import org.springframework.stereotype.Component;

@Component
public class UnionPayAutoLoad{
    private boolean flag;
	/**
	 * 银联加载配置文件
	 */
	public UnionPayAutoLoad(){
		flag = false;
		if(flag){
			SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		}
	}
}
