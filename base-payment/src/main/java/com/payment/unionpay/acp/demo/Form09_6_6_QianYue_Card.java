package com.payment.unionpay.acp.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import com.payment.unionpay.acp.sdk.AcpService;
import com.payment.unionpay.acp.sdk.CertUtil;
import com.payment.unionpay.acp.sdk.LogUtil;
import com.payment.unionpay.acp.sdk.SDKConfig;

/**
 * 重要：联调测试时请仔细阅读注释！
 *
 * 产品：代收产品<br>
 * 交易：有卡签约：后台交易，只有同步应答<br>
 * 日期：2019-03<br>
 * 版权：中国银联<br>
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码性能规范性等方面的保障<br>
 * 该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《代收产品接口规范》<br>
 *             《平台接入接口规范-第5部分-附录》（内包含应答码接口规范）<br>
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 *							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                            测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                         2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 */


@SuppressWarnings("serial")
public class Form09_6_6_QianYue_Card extends HttpServlet {
	
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

		String acqInsCode = req.getParameter("acqInsCode");
		String merId = req.getParameter("merId");
		String merCatCode = req.getParameter("merCatCode");
		String merName = req.getParameter("merName");
		String merAbbr = req.getParameter("merAbbr");
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");
		String txnAmt = req.getParameter("txnAmt");
		String billType= req.getParameter("billType");
		String billNo= req.getParameter("billNo");
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统产品参数，非特殊情况请勿修改***/
		data.put("version", DemoBase.version);   //版本号
		data.put("encoding", DemoBase.encoding); //字符集编码
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法            //签名方法 目前只支持01-RSA方式证书加密
		data.put("txnType", "72");                              //交易类型 
		data.put("txnSubType", "16");                           //交易子类型 
		data.put("bizType", "000000");                          //业务类型 代收产品
		data.put("channelType", "08");           //渠道类型
		data.put("accessType", "0");             //接入类型，机构接入固定填1，不需修改
		data.put("encryptCertId", CertUtil.getEncryptCertId()); //加密证书序列号，从配置文件配的证书读取
		data.put("billType",billType);                           //代收款项 
		data.put("billNo",billNo);                           //用户号码
		data.put("reserved", "{validMonth=" + req.getParameter("validMonth") + "&frequency=" + req.getParameter("frequency") + "}"); //委托关系期限和代收频率必填，其他选填字段请参考规范reserved字段定义
		
		/***请安实际情况修改***/
		data.put("merId", merId);                  			   //商户号码（测试环境符合格式随便送，生产由收单自己的业务人员定）
		data.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		data.put("txnTime", txnTime);         				   //订单发送时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效
		data.put("currencyCode", "156");         //币种，境内商户一定156
		data.put("txnAmt", txnAmt);               //交易金额，单位分
		data.put("backUrl", DemoBase.backUrl);   //后台通知地址，后台通知参数详见open.unionpay.com帮助中心 下载  产品接口规范  网关支付产品接口规范 退货交易 商户通知,其他说明同消费交易的后台通知

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
					
		
		/**
		 * 测试卡信息设置
		 * 
		 * 测试卡说明：
		 * 请真实刷卡测试，
		 * 磁条卡请报给测试支持人员配置，
		 * IC卡请使用检测中心的测试卡，如之前接过线下pos，可用之前的测试IC卡，如果没有可以问银联分公司借，借不到请联系测试支持人员，信总也有少量的检查中心测试卡可外借。如用真实卡仿真会校验arqc失败导致反34.
		 */
		//卡号，请改为从pos后读取填写，
		data.put("accNo", "6225000000000253");
		Map<String, String> cardTransDataMap = new HashMap<String, String>();
		//55域IC卡数据，请改为从pos后读取填写，注意如果pos读取的数据是二进制格式的话，iCCardData这个参数请直接设置为二进制的数据。此处为了方便演示用的16进制数据，所以有个转2进制的过程。。
		//IC卡必送，磁条卡不送
		byte[] iCCardData;
		try {
			iCCardData = Hex.decodeHex("9F2608CE72DF690FBD47FA9F2701809F101307000103A02800010A01000000100066C817619F3704771B7AF79F360200179505088004E0009A031505259C01009F02060000000001005F2A02015682027C009F1A0201569F03060000000000009F3303E0F9C89F34034203009F3501229F1E0838323033363639388408A0000003330101019F090201409F4104000000069F63103030303130303030FF00000000000000".toCharArray());
			cardTransDataMap.put("ICCardData", Base64.encodeBase64String(iCCardData));  
		} catch (DecoderException e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		}  
//		//23域卡序列号，请改为从pos后读取填写。
//		//IC卡必送，磁条卡不送。
		cardTransDataMap.put("ICCardSeqNumber", "002"); 
//		//35域第二磁道数据，请改为从pos后读取填写，注意别带长度。
		//选送。
		cardTransDataMap.put("track2Data", "6225000000000253=301022000000");
		//36域第三磁道数据，请改为从pos后读取填写，注意别带长度。
		//选送。
		cardTransDataMap.put("track3Data", "6225000000000253=301022000000");

		//磁条卡填02，ic卡接触填05，非接填07
		cardTransDataMap.put("POSentryModeCode", "05");  
		cardTransDataMap.put("carrierAppTp", "3"); //勿改，mpos接入固定用法
		cardTransDataMap.put("carrierTp", "5");    //勿改，mpos接入固定用法

		//以下3个字段如果送了会透传至发卡行，如果有需求要送，请在生产环境对各个发卡行的卡进行充分测再送。
//		cardTransDataMap.put("transSendMode", "1");  //透传到CUPS的 F60.3.5：交易发起方式，具体看全渠道有卡产品规范或者cups规范。
//		cardTransDataMap.put("termEntryCap", "6"); //透传到CUPS的 F60.2.2：终端读取能力，具体看全渠道有卡产品规范或者cups规范。
//		cardTransDataMap.put("chipCondCode", "0"); //透传到CUPS的 F 60.2.3：IC卡条件代码，具体看全渠道有卡产品规范或者cups规范。
		
		//按规范加密和组装然后设置到map里
		String cardTransDataStr = AcpService.getCardTransData(cardTransDataMap, data, DemoBase.encoding);
		data.put("cardTransData", cardTransDataStr); //请参考getCardTransData方法内部，相关要素从读卡器采集。

		Map<String,String> customerInfoMap = new HashMap<String,String>();
		//密码。请改为从pos后读取填写。
		//有密信用卡必送，无密信用卡不送，借记卡必送。
		customerInfoMap.put("pin", "123456");	
		//有效期。请改为从pos后读取填写。
		//选送，生产多数发卡行送或者不送都会成功，部分发卡行送了会失败，极少数发卡行IC卡不送会失败，建议先不送，确定哪家发卡行必送再改。
		customerInfoMap.put("expired", "3010");	
		
		//按规范加密和组装然后设置到map里。
		//注意此域为非常规用法，请勿调用AcpService.getCustomerInfo方法组装，
		//调之前请确定卡号已经设置完成。
	    String customerInfoStr = AcpService.getCustomerInfo(customerInfoMap, (String)data.get("accNo"), DemoBase.encoding);
		if(customerInfoMap != null & customerInfoMap.size()!=0) //没子域别出现这个key
			data.put("customerInfo", customerInfoStr);
	
		/**请求参数设置完毕，以下对请求参数进行签名并发送http post请求，接收同步应答报文------------->**/
		Map<String, String> reqData  = AcpService.sign(data, DemoBase.encoding);//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可。
		String url = SDKConfig.getConfig().getCardRequestUrl();//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的 acpsdk.cardTransUrl
		Map<String, String> rspData = AcpService.post(reqData, url, DemoBase.encoding);//这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData,  DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				String respCode = rspData.get("respCode");
				if("00".equals(respCode)){
					//TODO 交易已受理，等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。
					LogUtil.writeLog("交易已受理，等待接收后台通知更新订单状态,也可以主动发起 查询交易确定交易状态。");
					LogUtil.writeLog("后面查询接口要用的参数：");
					LogUtil.writeLog("acqInsCode=" + data.get("acqInsCode"));
					LogUtil.writeLog("merId=" + data.get("merId"));
					LogUtil.writeLog("orderId=" + data.get("orderId"));
					LogUtil.writeLog("txnTime=" + data.get("txnTime"));
				}else{
					//TODO 其他应答码为失败请排查原因
					LogUtil.writeErrorLog("失败：" +rspData.get("respMsg"));
				}
			}else{
				//TODO 检查验证签名失败的原因
				LogUtil.writeErrorLog("验证签名失败。");
			}
		}else{
			//未返回正确的http状态
			LogUtil.writeErrorLog("未获取到返回报文或返回http状态码非200");
			if("".equals(data.get("acqInsCode"))){
				LogUtil.writeErrorLog("【【【【【我知道你第一次测试，请把acqInsCode填下。】】】】】");
			}
		}
		String reqMessage = DemoBase.genHtmlResult(reqData);
		String rspMessage = DemoBase.genHtmlResult(rspData);
		resp.getWriter().write("请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
	}
	
}
