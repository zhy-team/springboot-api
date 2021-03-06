package com.payment.unionpay.acp.demo.batchDaishou;

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
 * 交易：批量交易状态查询类交易：后台交易，用户查询批量结果文件<br>
 * 日期： 2015-09<br>
 * 版权： 中国银联<br>
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 * 提示：该接口参考文档位置：open.unionpay.com帮助中心 下载  产品接口规范  《代付产品接口规范》，<br>
 *                  《全渠道平台接入接口规范 第3部分 文件接口》（4.批量文件基本约定）<br>
 * 测试过程中的如果遇到疑问或问题您可以：1）优先在open平台中查找答案：
 * 							        调试过程中的问题或其他问题请在 https://open.unionpay.com/ajweb/help/faq/list 帮助中心 FAQ 搜索解决方案
 *                             测试过程中产生的7位应答码问题疑问请在https://open.unionpay.com/ajweb/help/respCode/respCodeList 输入应答码搜索解决方案
 *                          2） 咨询在线人工支持： open.unionpay.com注册一个用户并登陆在右上角点击“在线客服”，咨询人工QQ测试支持。
 *                          3）  测试环境测试支付请使用测试卡号测试， FAQ搜索“测试卡号”
 *                          4） 切换生产环境要点请FAQ搜索“切换”
 * 交易说明:1)确定批量结果请调用此交易。
 *       2)批量文件格式请参考 《全渠道平台接入接口规范 第3部分 文件接口》（4.批量文件基本约定）
 *       3)批量交易状态查询的时间机制： 建议间隔1小时后查询
 *       4)批量查询成功的情况下对一笔批量交易只能查询5次。
 */
public class Form09_6_11_BatchQuery extends HttpServlet {

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
		String batchNo = req.getParameter("batchNo");
		String txnTime = req.getParameter("txnTime");
		
		Map<String, String> contentData = new HashMap<String, String>();
		/***银联全渠道系统，产品参数，除了encoding自行选择外其他不需修改***/
		contentData.put("version", DemoBase.version);       	 //版本号
		contentData.put("encoding", DemoBase.encoding);     	 //字符集编码 可以使用UTF-8,GBK两种方式
		contentData.put("signMethod", SDKConfig.getConfig().getSignMethod());					 //签名方法 目前只支持01-RSA方式证书加密
		contentData.put("txnType", "22");						 //交易类型 22 批量查询
		contentData.put("txnSubType", "02");                	 //交易子类 02代收
		contentData.put("bizType", "000000");					 //代收 000000
		contentData.put("channelType", "07");					 //渠道类型
		
		/***商户接入参数***/
		contentData.put("accessType", "0");						 //接入类型，商户接入填0 ，不需修改（0：直连商户 2：平台商户）
		contentData.put("merId", merId);			             //商户号码，请改成自己申请的商户号，【测试777开通的商户号不支持代收产品】
		
		/**与批量查询相关的参数**/
		contentData.put("batchNo", batchNo);					  //被查询批量交易批次号
		contentData.put("txnTime", txnTime);   		 			  //原批量代收请求的交易时间
		
		/**对请求参数进行签名并发送http post请求，接收同步应答报文**/
		String requestBatchQueryUrl = SDKConfig.getConfig().getBatchTransUrl();									//交易请求url从配置文件读取对应属性文件acp_sdk.properties中的acpsdk.batchTransUrl
		Map<String, String> reqData = AcpService.sign(contentData,DemoBase.encoding);    		 			//报文中certId,signature的值是在signData方法中获取并自动赋值的，只要证书配置正确即可
		Map<String, String> rspData = AcpService.post(reqData,requestBatchQueryUrl,DemoBase.encoding);     //发送请求报文并接受同步应答（默认连接超时时间30秒，读取返回结果超时时间30秒）;这里调用signData之后，调用submitUrl之前不能对submitFromData中的键值对做任何修改，如果修改会导致验签不通过
		
		/**对应答码的处理，请根据您的业务逻辑来编写程序,以下应答码处理逻辑仅供参考------------->**/
		//应答码规范参考open.unionpay.com帮助中心 下载  产品接口规范  《平台接入接口规范-第5部分-附录》
		if(!rspData.isEmpty()){
			if(AcpService.validate(rspData, DemoBase.encoding)){
				LogUtil.writeLog("验证签名成功");
				if(("00").equals(rspData.get("respCode"))){
					//成功
					//落地查询结果样例
					String fileContent = rspData.get("fileContent");
					String queryResult =AcpService.getFileContent(fileContent,DemoBase.encoding);
					System.out.println("查询结果文件内容：\n"+queryResult);
					//批量应答如果提示某笔交易查询超时处理机制：
					//需要对单笔交易发起交易状态查询，查询机制参考单笔代收查询机制
				}else{
					//其他应答码为失败请排查原因
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
		resp.getWriter().write("批量代收查询交易</br>请求报文:<br/>"+reqMessage+"<br/>" + "应答报文:</br>"+rspMessage+"");
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
