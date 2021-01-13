package com.payment.unionpay.acp.demo.daishou;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.payment.unionpay.acp.demo.DemoBase;
import com.payment.unionpay.acp.sdk.AcpService;
import com.payment.unionpay.acp.sdk.LogUtil;
import com.payment.unionpay.acp.sdk.SDKConfig;

/**
 * 重要：联调测试时请仔细阅读注释！
 * 
 * 产品：代收产品<br>
 * 交易：实名认证(带绑定标识码)：前台交易<br>
 * 日期： 2015-09<br>

 * 版权： 中国银联<br>
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 * 该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《代收产品接口规范》<br>
 *              《平台接入接口规范-第5部分-附录》（内包含应答码接口规范）<br>
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                             测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                          2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 * 交易说明: 1)前台交易，只有前台通知。
 * 		  2）后续代收可以使用绑定标识码支付（参见Form09_6_2_DaiShou_BindId.java)
 */

public class Form09_6_9_RealAuth_Front_BindId  extends HttpServlet  {

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html; charset="+ DemoBase.encoding);
		
		String merId = req.getParameter("merId");
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");
		String bindId = req.getParameter("bindId");
		
		Map<String, String> contentData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);                  //版本号
		contentData.put("encoding", DemoBase.encoding);                //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod());                           //签名方法 目前只支持01-RSA方式证书加密
		contentData.put("txnType", "72");                              //交易类型 11-代收
		contentData.put("txnSubType", "01");                           //交易子类型  01-实名认证
		contentData.put("bizType", "000501");                          //业务类型 代收产品
		contentData.put("channelType", "07");                          //渠道类型07-PC
		
		/***商户接入参数***/
		contentData.put("merId", merId);                   			   //商户号码（本商户号码仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
		contentData.put("orderId", orderId);             	           //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("txnTime", txnTime);                           //订单发送时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效
		contentData.put("accType", "01");                              //账号类型
		
		//【前台实名认证可以在打开的银联认证页面输入accNo和对应的customerInfo中的验证要素，如果在请求报文中上送accNo,那么必须按照商户号在银联后台配置的实名认证交易必送验证元素上送】
		//实名认证的customerInfo送什么验证要素是配置到银联后台到商户号上的，这些验证要素可以在商户的《全渠道入网申请表》中找到，也可以请咨询您的业务人员或者银联业务运营接口人
		//【商户号777290058110097验证要素上送参考《测试商户号777290058110097代收、实名认证交易必送验证要素配置说明.txt》】
		//如果商户获取不到卡号等信息那么可以注释掉accNo和customerInfo的上送，前台银联实名认证页面不回显卡号等验证要素
		//Map<String,String> customerInfoMap = new HashMap<String,String>();
		//customerInfoMap.put("certifTp", "01");						//证件类型
		//customerInfoMap.put("certifId", "341126197709218366");		//证件号码

		////////////如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
		//String accNo = AcpService.encryptData("6216261000000000018", "UTF-8");  //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		//contentData.put("accNo", accNo);
		//contentData.put("encryptCertId",AcpService.getEncryptCertId());         //加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
		//String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap,null,DemoBase.encoding_UTF8);
		//////////
	
		/////////如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
		//contentData.put("accNo", "6216261000000000018");
		//String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,null,DemoBase.encoding_UTF8);   //前台实名认证送支付验证要素 customerInfo中要素不要加密  
		////////
		
		//实名认证交易配置了必送【绑定标识码】那么bindId必送【一般如果勾选了必送绑定标识号码那么代收交易中的EBPP认证元素控制一般只勾选了 必送绑定标识码，所以此时一般代收使用Form09_6_2_DaiShou_BindId.java即可】
		contentData.put("bindId", bindId);       //可以自行定义 1-128位字母、数字和/或特殊符号字符
	    
		//前台通知地址 （需设置为外网能访问 http https均可），支付成功后的页面 点击“返回商户”的时候将异步通知报文post到该地址
		//如果想要实现过几秒中自动跳转回商户页面权限，需联系银联业务申请开通自动返回商户权限
		//通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  代收产品接口规范  实名认证 应答报文
		contentData.put("frontUrl", DemoBase.frontUrl);  
		
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);  			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();  							//获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, reqData,DemoBase.encoding);  	//生成自动跳转的Html表单
		
		LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
		resp.getWriter().write(html);																	//将生成的html写到浏览器中完成自动跳转打开银联支付页面；这里调用signData之后，将html写到浏览器跳转到银联页面之前均不能对html中的表单项的名称和值进行修改，如果修改会导致验签不通过
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
