package com.qxiao.wx.templatemsg;

import java.util.Map;

import org.apache.log4j.Logger;

import com.talkweb.weixin.main.StartOnLoad;
import com.talkweb.weixin.pojo.AccessToken;
import com.talkweb.weixin.util.WeiXinUtil;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class SendMessageUtils {

	private static Logger log = Logger.getLogger(SendMessageUtils.class);

	/**
	 * 获取access_token 封装对象
	 * 
	 * @return
	 */
	public static AccessToken getWXToken() {
		String appid = StartOnLoad.TOKEN_APPID;
		String secret = StartOnLoad.TOKEN_SECRET;
		AccessToken access_token = StartOnLoad.tokenThread1.accessToken;
		if (access_token == null || access_token.getToken().equals("")) {
			String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid
					+ "&secret=" + secret;
			JSONObject jsonObject = WeiXinUtil.httpsRequest(tokenUrl, "GET", null);
			if (null != jsonObject) {
				try {
					access_token = new AccessToken();
					access_token.setToken(jsonObject.getString("access_token"));
					access_token.setExpiresIn(jsonObject.get("expires_in").toString());
				} catch (JSONException e) {
					access_token = null;
					// 获取token失败
					log.error("获取用户是否订阅 errcode: " + jsonObject.getInt("errcode") + ",errmsg: "
							+ jsonObject.getString("errmsg"));
				}
			}
		}
		return access_token;
	}

	/**
	 * 发送模板消息
	 * 
	 * @param openid    用户openId
	 * @param templatId 模板Id
	 * @param clickurl  跳转url
	 * @param topcolor  字体颜色
	 * @param data      传递的数据
	 * @return
	 */
	public static String sendWechatMsgToUser(String openid, String templatId, String clickurl, String topcolor,
			JSONObject data) {
		String tmpurl = WeiXinUtil.SEND_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", getWXToken().getToken());
		JSONObject json = new JSONObject();
		json.put("touser", openid);
		json.put("template_id", templatId);
		json.put("url", clickurl);
		json.put("topcolor", topcolor);
		json.put("data", data);
		try {
			JSONObject result = WeiXinUtil.httpsRequest(tmpurl, "POST", json.toString());
			log.info("发送微信消息返回信息：" + result.get("errcode"));
			String errmsg = (String) result.get("errmsg");
			if (!"ok".equals(errmsg)) { // 如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	/**
	 * 封装模板详细信息
	 * 
	 * @return
	 */
	public static JSONObject packJsonmsg(Map<String, TemplateData> param) {
		JSONObject json = new JSONObject();
		for (Map.Entry<String, TemplateData> entry : param.entrySet()) {
			JSONObject keyJson = new JSONObject();
			TemplateData dta = entry.getValue();
			keyJson.put("value", dta.getValue());
			keyJson.put("color", dta.getColor());
			json.put(entry.getKey(), keyJson);
		}
		return json;
	}

	/**
	 * 根据模板ID 删除模板消息
	 * 
	 * @param templateId 模板ID
	 * @return
	 */
	public static String deleteWXTemplateMsgById(String templateId) {
		String tmpurl = WeiXinUtil.DEL_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", getWXToken().getToken());
		JSONObject json = new JSONObject();
		json.put("template_id", templateId);
		try {
			JSONObject result = WeiXinUtil.httpsRequest(tmpurl, "POST", json.toString());
			log.info("删除" + templateId + ",模板消息,返回CODE：" + result.get("errcode"));
			String errmsg = (String) result.get("errmsg");
			if (!"ok".equals(errmsg)) {
				return "error";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * 根据微信openId 获取用户是否订阅
	 * 
	 * @param openId 微信openId
	 * @return 是否订阅该公众号标识
	 */
	public static Integer subscribeState(String openId) {
		String tmpurl = WeiXinUtil.SEND_TEMPLAYE_MESSAGE_URL.replace("ACCESS_TOKEN", getWXToken().getToken())
				+ "&openid=" + openId;
		JSONObject result = WeiXinUtil.httpsRequest(tmpurl, "GET", null);
		log.error("获取用户是否订阅 errcode: " + result.getInt("errcode") + ",errmsg: " + result.getString("errmsg"));
		String errmsg = result.getString("errmsg");
		if (errmsg == null) {
			// 用户是否订阅该公众号标识（0代表此用户没有关注该公众号 1表示关注了该公众号）。
			Integer subscribe = result.getInt("subscribe");
			return subscribe;
		}
		return -1;
	}

}
