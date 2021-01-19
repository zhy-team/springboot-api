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
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class Form_6_2_BillQuery extends HttpServlet{
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
		String bussCode = req.getParameter("bussCode");
		String billQueryInfo = req.getParameter("billQueryInfo");
		String origQryId = req.getParameter("origQryId");
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);                  //版本号
		data.put("encoding", DemoBase.encoding);           //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "73");                              //交易类型 73 账单查询
		data.put("txnSubType", "01");                           //交易子类型 01 便民缴费
		data.put("bizType", "000601");                          //业务类型 
		data.put("channelType", "07");                          //渠道类型
		data.put("accessType", "0");
		
		/***商户接入参数***/
		data.put("merId", merId);                  			   //商户号码（商户号码777290058110097仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		data.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		data.put("txnTime", txnTime);         				   //订单发送时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效
		
		data.put("bussCode", bussCode);// 业务类型号，此处默认取demo演示页面传递的参数
		data.put("billQueryInfo", AcpService.base64Encode(billQueryInfo,DemoBase.encoding));// 账单要素，根据前文显示要素列表由用户填写值，此处默认取demo演示页面传递的参数
		
		if(null!=origQryId && !"".equals(origQryId)){
			data.put("origQryId", origQryId);
		}
		
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
					String queryId = rspData.get("queryId");
					String billDetailInfo = AcpService.base64Decode(rspData.get("billDetailInfo"),DemoBase.encoding);
					
					//*****以下注释的账单查询结果可以自行调试使用********//
					//账单查询查询到的 bill类型账单支付展示页面（账单条目项）
					//String billDetailInfo="{\"title\":\"账单缴费 - 青岛联通GSM\",\"form\":[{\"type\":\"string\",\"label\":\"合同号\",\"value\":\"3200401849\"},{\"type\":\"string\",\"label\":\"用户名\",\"value\":\"马振海\"},{\"type\":\"string\",\"label\":\"代表号\",\"value\":\"13061339218\"},{\"type\":\"string\",\"label\":\"手机区域\",\"value\":\"0532\"},{\"type\":\"string\",\"label\":\"用户数量\",\"value\":\"1\"},{\"value\":\"后付费\",\"label\":\"付费标志\",\"type\":\"String\"},{\"type\":\"hidden\",\"name\":\"amount\",\"value\":\"45.71\"},{\"type\":\"string\",\"label\":\"账单金额(元)\",\"value\":\"45.71\",\"name\":\"amountstr\"},{\"name\":\"owe_tag\",\"value\":\"D\",\"label\":\"欠费标志\",\"type\":\"hidden\"}],\"action\":\"prepay\",\"code\":\"I1_4520_0001\"}";
					
					//账单查询查询到的 singlebill类型账单支付展示页面（包含详情的单选账单）
					//String billDetailInfo="{\"title\": \"账单缴费 - 青岛联通GSM\",\"form\": [{\"type\":\"singlebill\",\"label\":\"账单条目\",\"name\":\"bill_id\",\"options\":[{\"disable\":\"0\",\"label\":\"物业费6.00元\",\"value\":\"1234567890\",\"amount\":\"6.00\",\"detail\":[{\"label\":\"订单编号\",\"value\":\"00001\"},{\"value\":\"物业费\"},{\"label\":\"账期\",\"value\":\"3月\"},{\"label\":\"收费金额\",\"value\":\"6.00元\"}]},{\"label\":\"取暖费600元\",\"disable\":\"1\",\"value\":\"1234567891\",\"amount\":\"600\",\"detail\":[{\"label\": \"订单编号\",\"value\":\"00002\"},{\"label\":\"收费名称\",\"value\":\"取暖费\"},{\"label\":\"账期\",\"value\":\"3月\"},{\"label\":\"收费金额\",\"value\":\"600元\"}]},{\"label\":\"垃圾费21元\",\"disable\":\"1\",\"value\":\"1234567892\",\"amount\":\"21.00\",\"detail\":[{\"label\":\"订单编号\",\"value\":\"00003\"},{\"label\":\"收费名称\",\"value\":\"垃圾费\"},{\"label\":\"账期\",\"value\":\"3月\"},{\"label\":\"收费金额\",\"value\":\"21.00元\"}]}]}],\"action\": \"prepay\",\"code\": \"I1_4520_0001\"}";
					
					//账单查询查询到的  multibill类型账单支付展示页面（包含详情的可多选账单）
					//String billDetailInfo="{\"title\": \"账单缴费 - 青岛联通GSM\",\"form\": [{\"type\": \"multibill\",\"label\": \"账单条目\",\"name\": \"mbill_no\",\"options\": [{\"label\": \"物业费6.00元\",\"value\": \"1\",\"amount\": \"6.00\",\"detail\": [{\"label\": \"订单编号\",\"value\": \"00001\"},{\"label\": \"收费名称\",\"value\": \"物业费\"},{\"label\": \"账期\",\"value\": \"3月\"},{\"label\": \"收费金额\",\"value\": \"6.00元\"}]},{\"label\": \"取暖费600元\",\"value\": \"2\",\"amount\": \"600\",\"detail\": [{\"label\": \"订单编号\",\"value\": \"00002\"},{\"label\": \"收费名称\",\"value\": \"取暖费\"},{\"label\": \"账期\",\"value\": \"3月\"},{\"label\": \"收费金额\",\"value\": \"600元\"}]},{\"label\": \"垃圾费21元\",\"value\": \"3\",\"amount\": \"21.00\",\"detail\": [{\"label\": \"订单编号\",\"value\": \"00003\"},{\"label\": \"收费名称\",\"value\": \"垃圾费\"},{\"label\": \"账期\",\"value\": \"3月\"},{\"label\": \"收费金额\",\"value\": \"21.00元\"}]}]}],\"action\": \"prepay\",\"code\": \"I1_4520_0001\"}";
					
					//账单查询查询到的  multibillstring类型账单支付展示页面（包含详情的多账单明细展示项）
					//String billDetailInfo ="{\"title\": \"账单缴费 - 青岛联通GSM\",\"form\": [{\"type\": \"multibillstring\",\"label\": \"账单条目\",\"options\": [{\"label\": \"物业费6.00元\",\"amount\": \"6.00\",\"detail\": [{\"label\": \"订单编号\",\"value\": \"00001\"},{\"label\": \"收费名称\",\"value\": \"物业费\"},{\"label\": \"账期\",\"value\": \"3月\"},{\"label\": \"收费金额\",\"value\": \"6.00元\"}]},{\"label\": \"取暖费600元\",\"amount\": \"600\",\"detail\": [{\"label\": \"订单编号\",\"value\": \"00002\"},{\"label\": \"收费名称\",\"value\": \"取暖费\"},{\"label\": \"账期\",\"value\": \"3月\"},{\"label\": \"收费金额\",\"value\": \"600元\"}]},{\"label\": \"垃圾费21元\",\"amount\": \"21.00\",\"detail\": [{\"label\": \"订单编号\",\"value\": \"00003\"},{\"label\": \"收费名称\",\"value\": \"垃圾费\"},{\"label\": \"账期\",\"value\": \"3月\"},{\"label\": \"收费金额\",\"value\": \"21.00元\"}]}]}],\"action\": \"prepay\",\"code\": \"I1_4520_0001\"}";
					
					// 带button的
					//String billDetailInfo ="{\"code\":\"I1_8700_0821\",\"action\":\"none\",\"title\":\"账单缴费 - 宁夏有线电视\",\"form\":[{\"value\":\"张三\",\"label\":\"客户姓名\",\"type\":\"string\"},{\"value\":\"XX路XX号\",\"label\":\"用户地址\",\"type\":\"string\"},{\"value\":\"正常\",\"label\":\"用户状态\",\"type\":\"string\"},{\"value\":\"0.00\",\"label\":\"本期余额(元)\",\"type\":\"string\"},{\"name\":\"businessType\",\"value\":\"2\",\"action\":\"prequery\",\"label\":\"确认\",\"type\":\"button\"},{\"name\":\"businessType\",\"value\":\"3\",\"action\":\"prepay\",\"label\":\"随便点点\",\"type\":\"button\"}]}";
					
					String respJson = "{\"queryId\":\""+queryId+"\",\"billDetailInfo\":"+billDetailInfo+"}";
					System.out.println("respJson:"+respJson);
					resp.getWriter().write(respJson);
					return;
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
