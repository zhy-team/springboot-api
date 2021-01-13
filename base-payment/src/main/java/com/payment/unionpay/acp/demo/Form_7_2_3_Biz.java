package com.payment.unionpay.acp.demo;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.payment.unionpay.acp.sdk.AcpService;
/**
 * 声明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己需要，按照技术文档编写。该代码仅供参考，不提供编码，性能，规范性等方面的保障<br>
 */
public class Form_7_2_3_Biz extends HttpServlet{

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//7.2.3　获取业务要素（biz）
		String bussCode = req.getParameter("bussCode");
		String reqUrl ="https://gateway.95516.com/jiaofei/config/s/biz/"+bussCode;
		String rspStr = AcpService.get(reqUrl, DemoBase.encoding);
		resp.getWriter().write(rspStr);
		
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
