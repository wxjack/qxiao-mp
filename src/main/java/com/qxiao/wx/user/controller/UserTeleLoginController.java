package com.qxiao.wx.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@RequestMapping("/action/mod-xiaojiao/manage")
@Controller
public class UserTeleLoginController {
	private Logger log = Logger.getLogger(UserTeleLoginController.class);

	@Autowired
	RegisterUserService userService;

	@RequestMapping(value = "/registerUser.do", method = RequestMethod.GET)
	@ResponseBody
	public void registerUser(HttpServletRequest request, HttpServletResponse response) {
		try {
			String type = request.getParameter("type");
			userService.registerUser(request, response,type);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/getOpenId.do", method = RequestMethod.GET)
	@ResponseBody
	public void getOpenId(HttpServletRequest request, HttpServletResponse response) {
		try {
			String type = request.getParameter("type");
			   userService.getOpenId1(request, response,type);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/userTeleLogin.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage userTeleLogin(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String tel = jsonObject.get("tel").toString();
			String verifyCode = jsonObject.get("verifyCode").toString();
			Map<String, Object> userTeleLogin = userService.userTeleLogin(tel, verifyCode);
			rm.setData(userTeleLogin);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	
	
}
