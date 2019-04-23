package com.qxiao.wx.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;


import com.talkweb.weixin.main.StartOnLoad;
import com.talkweb.weixin.service.CoreService;
import com.talkweb.weixin.util.MessageUtil;
import com.talkweb.weixin.util.SignUtil;
import com.talkweb.weixin.main.StartOnLoad;


public class WeixinAuthServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public WeixinAuthServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * 确认请求来自微信服务器
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 // 将请求、响应的编码均设置为UTF-8（防止中文乱码）  
/*
		//为了获取openId,必先获取到CODE
		String code = request.getParameter("code");
		if((code != null)&&(!code.equals(""))){
			System.out.println("CODE=" + code);			
			
			//此次去获取OpenId
			
			response.sendRedirect("https://www.huoler.com/action/mod-xiaoq/getUserInfo.action?uId=1&recipeId=1&operaType=0");
			
			return;
		}
*/		
		//微信加密签名
		String signature =request.getParameter("signature");
		//时间戳 
		String timestamp=request.getParameter("timestamp");
		//随机数
		String nonce=request.getParameter("nonce");
		//随机字符串
		String echostr=request.getParameter("echostr");
	
		System.out.println("signature=" + signature);
		System.out.println("timestamp=" + timestamp);		
		System.out.println("nonce=" + nonce);

		
		PrintWriter out=response.getWriter();
		//通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败   
		if(signature==null||timestamp==null||nonce==null||echostr==null){
			out.write("you records has recorded,please leave it now !");
		}else {
			if(SignUtil.checkSignature(signature, timestamp, nonce)){
				out.write(echostr);
			}			
		}
		
		out.close();
		out=null;
        
	}

   /**
    * 处理来自微信的消息
    */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		 // 将请求、响应的编码均设置为UTF-8（防止中文乱码）   
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//调用核心业务类处理微信请求
		String respMsg=CoreService.processRequest(request,response);
		if(respMsg==null){
			return;
		}
		PrintWriter writer=response.getWriter();
		writer.write(respMsg);
		writer.close();
		writer=null;
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	
	}	
	
}