package com.payment.unionpay.acp.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.payment.unionpay.acp.sdk.AcpService;
import com.payment.unionpay.acp.sdk.LogUtil;
import com.payment.unionpay.acp.sdk.SDKConfig;

/**
 *重要：联调测试时请仔细阅读注释！
 *
 *产品：代收产品<br>
 *交易：签约：前台交易<br>
 *日期：2019-03<br>
 *版权：中国银联<br>
 *说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码性能规范性等方面的保障<br>
 *该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《代收产品接口规范》<br>
 *             《平台接入接口规范-第5部分-附录》（内包含应答码接口规范）<br>
 *测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 *							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                            测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                         2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 *交易说明:有前台通知和后台通知，后台通知判断交易是否成功。
 */


public class Form09_6_6_QianYue_Front extends HttpServlet {
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		/**
		 * 请求银联接入地址，获取证书文件，证书路径等相关参数初始化到SDKConfig类中
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方法可使用监听的方式写入缓存,无须在这出现
		 */
		//这里已经将加载属性文件的方法挪到了web/AutoLoadServlet.java中
		//SDKConfig.getConfig().loadPropertiesFromSrc(); //从classpath加载acp_sdk.properties文件
		super.init();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String merId = req.getParameter("merId");
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");
		
		String accNo = req.getParameter("accNo");
		String certifTp = req.getParameter("certifTp");
		String certifId = req.getParameter("certifId");
		String customerNm = req.getParameter("customerNm");
		String phoneNo = req.getParameter("phoneNo");
		
		String billType= req.getParameter("billType");
		String billNo= req.getParameter("billNo");
		
		Map<String, String> contentData = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);                  //版本号
		contentData.put("encoding", DemoBase.encoding);                //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod());                           //签名方法 目前只支持01-RSA方式证书加密
		contentData.put("txnType", "72");                              //交易类型 
		contentData.put("txnSubType", "16");                           //交易子类型 
		contentData.put("bizType", "000000");                          //业务类型 代收产品
		contentData.put("channelType", "07");                          //渠道类型07-PC

		/***商户接入参数***/
		contentData.put("merId", merId);                  			   //商户号码（商户号码777290058110097仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		contentData.put("accessType", "0");                            //接入类型，商户接入固定填0，不需修改	
		contentData.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		contentData.put("txnTime", txnTime);         				   //订单发送时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效
	//	contentData.put("accType", "01");                              //账号类型
		contentData.put("billType",billType);                           //代收款项 
		contentData.put("billNo",billNo);                           //用户号码
		contentData.put("reserved", "{validMonth=" + req.getParameter("validMonth") + "&frequency=" + req.getParameter("frequency") + "}"); //委托关系期限和代收频率必填，其他选填字段请参考规范reserved字段定义
		
		Map<String,String> customerInfoMap = new HashMap<String,String>();
		customerInfoMap.put("certifTp", certifTp);						//证件类型
		customerInfoMap.put("certifId", certifId);						//证件号码
		customerInfoMap.put("customerNm", customerNm);					//姓名
		customerInfoMap.put("phoneNo", phoneNo);			    		//手机号
		
		////////////如果商户号开通了【商户对敏感信息加密】的权限那么需要对 accNo，pin和phoneNo，cvn2，expired加密（如果这些上送的话），对敏感信息加密使用：
		String accNoEnc = AcpService.encryptData(accNo, "UTF-8");  	//这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
		contentData.put("accNo", accNoEnc);
		contentData.put("encryptCertId",AcpService.getEncryptCertId());//加密证书的certId，配置在acp_sdk.properties文件 acpsdk.encryptCert.path属性下
		String customerInfoStr = AcpService.getCustomerInfoWithEncrypt(customerInfoMap,null,DemoBase.encoding);
		contentData.put("customerInfo",customerInfoStr);                           
		//////////
		
		/////////如果商户号未开通【商户对敏感信息加密】权限那么不需对敏感信息加密使用：
//		contentData.put("accNo", accNo);            					 //这里测试的时候使用的是测试卡号，正式环境请使用真实卡号
//		String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,null,DemoBase.encoding);
		contentData.put("customerInfo",customerInfoStr);                         
		////////
		
		contentData.put("frontUrl", DemoBase.frontUrl);
		contentData.put("backUrl", DemoBase.backUrl);
		
		/**请求参数设置完毕，以下对请求参数进行签名并生成html表单，将表单写入浏览器跳转打开银联页面**/
		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);  			  //报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestFrontUrl = SDKConfig.getConfig().getFrontRequestUrl();    						  //获取请求银联的前台地址：对应属性文件acp_sdk.properties文件中的acpsdk.frontTransUrl
		String html = AcpService.createAutoFormHtml(requestFrontUrl, reqData,DemoBase.encoding);     //生成自动跳转的Html表单
		
		LogUtil.writeLog("打印请求HTML，此为请求报文，为联调排查问题的依据："+html);
		resp.getWriter().write(html);		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
