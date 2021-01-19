package com.payment.unionpay.acp.demo.token;

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
 * token支付缴税
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class Form_6_3_Token_TaxPay extends HttpServlet{
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
		String txnAmt = req.getParameter("txnAmt");
		String origQryId = req.getParameter("origQryId");
		String token = req.getParameter("token");
		String smsCode = req.getParameter("smsCode");
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);                  //版本号
		data.put("encoding", DemoBase.encoding);           //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "13");                              //交易类型 13 账单支付
		data.put("txnSubType", "02");                           //交易子类型 02 网上缴税
		data.put("bizType", "000902");                          //业务类型 
		data.put("channelType", "07");                          //渠道类型
		data.put("accessType", "0"); 
		data.put("currencyCode", "156"); 
		data.put("bussCode", "S0_9800_0000");                   //
		
		/***商户接入参数***/
		data.put("merId", merId);   
		data.put("orderId", orderId);             			   //商户订单号，如上送短信验证码，请填写获取验证码时一样的orderId，此处默认取demo演示页面传递的参数	
		data.put("txnTime", txnTime);         				   //订单发送时间，如上送短信验证码，请填写获取验证码时一样的txnTime，此处默认取demo演示页面传递的参数
		data.put("txnAmt", txnAmt);							   //交易金额，单位分，如上送短信验证码，请填写获取验证码时一样的txnAmt，此处默认取demo演示页面传递的参数
		data.put("origQryId", origQryId);// 账单要素，根据前文显示要素列表由用户填写值，此处默认取demo演示页面传递的参数
		
		//token号（从前台开通的后台通知中获取或者后台开通的返回报文中获取）
		data.put("tokenPayData", "{token="+token+"&trId=99988877766}");
		
		//验证码看业务配置.
		if(null != smsCode && !"".equals(smsCode)){
			Map<String,String> customerInfoMap = new HashMap<String,String>();
			customerInfoMap.put("smsCode", smsCode);
			String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap,null,DemoBase.encoding);
			data.put("customerInfo", customerInfoStr);
		}
		
		data.put("backUrl", DemoBase.backUrl);//后台通知地址

		// 请求方保留域，
        // 透传字段，查询、通知、对账文件中均会原样出现，如有需要请启用并修改自己希望透传的数据。
        // 出现部分特殊字符时可能影响解析，请按下面建议的方式填写：
        // 1. 如果能确定内容不会出现&={}[]"'等符号时，可以直接填写数据，建议的方法如下。
//		data.put("reqReserved", "透传信息1|透传信息2|透传信息3");
        // 2. 内容可能出现&={}[]"'符号时：
        // 1) 如果需要对账文件里能显示，可将字符替换成全角＆＝｛｝【】“‘字符（自己写代码，此处不演示）；
        // 2) 如果对账文件没有显示要求，可做一下base64（如下）。
        //    注意控制数据长度，实际传输的数据长度不能超过1024位。
        //    查询、通知等接口解析时使用new String(Base64.decodeBase64(reqReserved), DemoBase.encoding);解base64后再对数据做后续解析。
//		data.put("reqReserved", Base64.encodeBase64String("任意格式的信息都可以".toString().getBytes(DemoBase.encoding)));

		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		Map<String, String> reqData = AcpService.sign(data,DemoBase.encoding);			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String requestBackUrl = SDKConfig.getConfig().getJfBackRequestUrl();  			//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.backTransUrl
		Map<String, String> rspData = AcpService.post(reqData,requestBackUrl,DemoBase.encoding); //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode") ;
				if(("00").equals(respCode)){
					//成功
					//TODO
					
				}else{
					//其他应答码为失败请排查原因或做失败处理
					//TODO
				}
			}else{
				LogUtil.writeErrorLog("验证签名失败");
				//TODO 检查验证签名失败的原因
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
		}
		String reqMessage = DemoBase.genHtmlResult(reqData);
		String rspMessage = DemoBase.genHtmlResult(rspData);
		resp.getWriter().write("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
