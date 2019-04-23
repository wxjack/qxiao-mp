package com.qxiao.wx.user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qxiao.wx.user.jpa.service.RegisterUserService;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/manage")
public class TelVeriftCode {
	private Logger log = Logger.getLogger(TelVeriftCode.class);

	@Autowired
	RegisterUserService userService;

	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/telVeriftCode.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage telVeriftCode(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String tel = jsonObject.get("tel").toString();
			if (tel.length() != 11) {
				rm.setErrorCode(-1);
				rm.setErrorMsg("手机号码有误");
			} else {
				String code = userService.code(tel);
				this.sendCode(tel, code);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	public void sendCode(String tel, String code) {
		try {
			HttpClient client = new HttpClient();
//			
//			PostMethod post = new PostMethod("http://120.27.42.224:8126/sms.aspx");
//			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//			NameValuePair[] data = { new NameValuePair("action", "send"), 
//					new NameValuePair("userid", "355"), 
//					new NameValuePair("account", "zhu"), 
//					new NameValuePair("password", "zhu"),
//					new NameValuePair("mobile", tel),
//					new NameValuePair("content", "您的验证码为："+code+"【小Q智慧】"),
//					new NameValuePair("sendTime", ""),
//					new NameValuePair("extno", "")};// 设置短信内容

			PostMethod post = new PostMethod("http://47.98.99.4:7862/sms");
			post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			NameValuePair[] data = { new NameValuePair("action", "send"), new NameValuePair("account", "900064"),
					new NameValuePair("password", "Ar4ncC"), new NameValuePair("mobile", tel),
					new NameValuePair("content", "您的验证码为：" + code + "【小Q智慧】"), new NameValuePair("extno", "1069064"),
					new NameValuePair("rt", "json") };// 设置短信内容
			post.setRequestBody(data);
			client.executeMethod(post);
			post.releaseConnection();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
}
