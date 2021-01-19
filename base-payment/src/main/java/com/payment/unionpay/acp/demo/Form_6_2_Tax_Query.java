package com.payment.unionpay.acp.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.payment.unionpay.acp.sdk.AcpService;
import com.payment.unionpay.acp.sdk.LogUtil;
import com.payment.unionpay.acp.sdk.SDKConfig;
/**
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class Form_6_2_Tax_Query extends HttpServlet{
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
		
		System.out.println("Form_6_2_BillQuery ....");
		
		String merId = req.getParameter("merId");
		String orderId = req.getParameter("orderId");
		String txnTime = req.getParameter("txnTime");
		
		String usrNum = req.getParameter("usr_num");
		String colOrgaCd = req.getParameter("col_organ_cd");
		String colVoucherNo = req.getParameter("col_voucher_no");
		String procFlg = req.getParameter("proc_flg");
		
		Map<String, String> data = new HashMap<String, String>();
		
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		data.put("version", DemoBase.version);                  //版本号
		data.put("encoding", DemoBase.encoding);           //字符集编码 可以使用UTF-8,GBK两种方式
		data.put("signMethod", SDKConfig.getConfig().getSignMethod()); //签名方法
		data.put("txnType", "73");                              //交易类型 73 账单查询
		data.put("txnSubType", "02");                           //交易子类型 02网上缴税
		data.put("bizType", "000601");                          //业务类型 
		data.put("channelType", "07");                          //渠道类型
		data.put("accessType", "0");
		
		/***商户接入参数***/
		data.put("merId", merId);                  			   //商户号码（商户号码777290058110097仅做为测试调通交易使用，该商户号配置了需要对敏感信息加密）测试时请改成自己申请的商户号，【自己注册的测试777开头的商户号不支持代收产品】
		data.put("orderId", orderId);             			   //商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	
		data.put("txnTime", txnTime);         				   //订单发送时间，格式为yyyyMMddHHmmss，必须取当前时间，否则会报txnTime无效
		
		data.put("bussCode", "S0_9800_0000");		// 业务类型号
		JSONObject jsonObj= new JSONObject();
		try {
			jsonObj.put("usr_num", usrNum);
			jsonObj.put("col_organ_cd", colOrgaCd);
			jsonObj.put("col_voucher_no", colVoucherNo);
			jsonObj.put("proc_flg", procFlg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		data.put("billQueryInfo", AcpService.base64Encode(jsonObj.toString(), DemoBase.encoding));// 账单要素，根据前文显示要素列表由用户填写值，此处默认取demo演示页面传递的参数
		
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
					//proc_flg非融合流程时查询接口调用后会锁单半小时，请本地留存queryId，否则万一用户终止了支付流程，税单锁单期间没有queryId就无法再次调起支付。
					System.out.println("queryId:"+queryId);
					System.out.println("billDetailInfo:"+billDetailInfo);
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
