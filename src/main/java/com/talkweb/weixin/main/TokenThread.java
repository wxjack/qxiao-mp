package com.talkweb.weixin.main;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.talkweb.weixin.pojo.AccessToken;
import com.talkweb.weixin.util.WeiXinUtil;

public class TokenThread extends Thread{
	private static Logger log = LoggerFactory.getLogger(TokenThread.class);
/*
	// 第三方用户唯一凭证
	public static String appid;
	// 第三方用户唯一凭证密钥
	public static String appsecret;

	public static AccessToken accessToken = null;
*/
	// 第三方用户唯一凭证
	public String appid;
	// 第三方用户唯一凭证密钥
	public String appsecret;

	public  AccessToken accessToken = null;
		
	//缺省构造函数
	public TokenThread(){		
		appid = StartOnLoad.TOKEN_APPID;
		appsecret = StartOnLoad.TOKEN_SECRET;
	}
	
	//带参数构造函数
	public TokenThread(String appId, String appSecret){
		appid = appId;
		appsecret = appSecret;
	}
	
	public void run() {
		while (true) {
			try {
				accessToken = WeiXinUtil.getAccessToken(appid, appsecret);
				if (null != accessToken) {
					log.debug("获取access_token成功，有效时长{}秒 token:{}",
							accessToken.expiresIn, accessToken.token);
					// 休眠7000秒
					Thread.sleep((Integer.parseInt(accessToken.expiresIn) - 3600) * 1000);
					System.out.println("token=="+accessToken.token);
				} else {
					// 如果access_token为null，60秒后再获取
					Thread.sleep(60 * 1000);
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					log.error("{}", e1);
				}
				log.error("{}", e);
			}
		}
	}
	
}