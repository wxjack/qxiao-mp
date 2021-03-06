package com.talkweb.weixin.pojo;

import java.io.Serializable;

/**
 * 微信通用接口凭证
*    
* 项目名称：WeiChatService   
* 类名称：AccessToken   
* 类描述：   
* 创建人：zhouling
* 创建时间：Nov 15, 2013 3:16:07 PM    
* 修改备注：   
* @version    
*
 */
public class AccessToken implements Serializable{
	
	private static final long serialVersionUID = -2806373124333924956L;
	//获取到的凭证
	public String token;
	//凭证有效时间
	public String expiresIn;
	
	public String getToken() {
		return token;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
