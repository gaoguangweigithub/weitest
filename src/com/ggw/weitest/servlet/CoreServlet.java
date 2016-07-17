package com.ggw.weitest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ggw.weitest.pojo.AccessToken;
import com.ggw.weitest.service.CoreService;
import com.ggw.weitest.util.MenuManagerUtil;
import com.ggw.weitest.util.SignUtil;
import com.ggw.weitest.util.WeixinUtil;

public class CoreServlet extends HttpServlet {
	
	public void init() throws ServletException {
		
		// 第三方用户唯一凭证  
        String appId = "wx7390fec51e44edcb";  
        // 第三方用户唯一凭证密钥  
        String appSecret = "c419f538925abdbe653aff5368f6dd4c";  
  
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);  
  
        if (null != at) {  
            // 调用接口创建菜单  
            int result = WeixinUtil.createMenu(MenuManagerUtil.getMenu(), at.getToken());  
  
            // 判断菜单创建结果  
            if (0 == result)  
            	System.out.println("菜单创建成功！");
                //log.info("菜单创建成功！");  
            else  
            	System.out.println("菜单创建失败，错误码：" + result);
                //log.info("菜单创建失败，错误码：" + result);  
        } 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String signature = request.getParameter("signature");  
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");  
        String echostr = request.getParameter("echostr");  
  
        PrintWriter out = response.getWriter();  
        if (SignUtil.checkSignature(signature, timestamp, nonce)) {  
            out.print(echostr);  
        }  
        out.close();  
        out = null;  
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
  
        // 调用核心业务类接收消息、处理消息  
        String respMessage = CoreService.processRequest(request);  
          
        // 响应消息  
        PrintWriter out = response.getWriter();  
        out.print(respMessage);  
        out.close();
		
	}

}
