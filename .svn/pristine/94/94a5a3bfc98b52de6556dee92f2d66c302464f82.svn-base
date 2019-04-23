package com.qxiao.wx.user.util;

import java.util.Random;

public class VeriftCode {

	//随机生成验证码
	public static String veriftCode() {
		Random ran=new Random();
		String code = "";
		for(int i=0;i<6;i++) {
			int nextInt = ran.nextInt(10);
			code+=nextInt;
		}
		
		return code;
	}
}
