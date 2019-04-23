package com.qxiao.wx.user.jpa.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

@Service
public interface RegisterUserService {
	
	void  getOpenId1(HttpServletRequest request,HttpServletResponse response,String type);
	
	void registerUser(HttpServletRequest request,HttpServletResponse response,String type);
	
	Map<String, Object> userTeleLogin(String tele, String verifyCode) throws Exception;
	
	String code(String tel) throws Exception;
}
