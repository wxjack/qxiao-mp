package com.talkweb.weixin.service;

import java.nio.CharBuffer;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;

import net.sf.json.JSONObject;

import com.talkweb.weixin.main.StartOnLoad;
import com.talkweb.weixin.message.resp.TextMessage;
import com.talkweb.weixin.pojo.OpenId;
import com.talkweb.weixin.util.HttpRequestUtils;
import com.talkweb.weixin.util.MessageUtil;
//import com.talkweb.weixin.util.WeiXinUtil;

public class CoreService {

	public static String processRequest(HttpServletRequest request, HttpServletResponse response) {
		String respMessage = null;
		try {
			// 默认的文本回复
			String respContent = null;

			// 解析发过来的xml请求
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送发方账号（open_id）
			String fromUserName = requestMap.get("FromUserName");

			// 公众账号
			String toUserName = requestMap.get("ToUserName");

			// 消息类型
			String msgType = requestMap.get("MsgType");

			String conten = null;
			if (null != requestMap.get("Content")) {
				conten = requestMap.get("Content");
			}
			// 回复文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			String key = null;
			JSONObject json = new JSONObject();
			if (requestMap.get("EventKey") != null) {
				key = requestMap.get("EventKey").toString();
				key = key.substring(key.indexOf("_") + 1);
			}
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 得到OpenId
				if(conten.equals("111")) {
					respContent = "点击首页 http://zc.qxiao.net/qxiao-mp/#/author";					
				}else				
					respContent = "谢谢您的支持和意见！";
			}
			// 图片消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
				respContent = "已收到您发的信息！";

			}
			// 地理位置消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
				respContent = "您发送的是地理位置消息！";
			}
			// 链接消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "已收到您发的信息！";
			}
			// 事件推送消息
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {

				respContent = "谢谢您的意见!";

				// 事件类型
				String eventType = requestMap.get("Event");
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
					/*
					 * String strs="" + "【每日羽球】提供羽毛球的知识和视频，一个学习羽毛球的好地方！\n\n" + "也可以和球友进行交流！\n\n" +
					 * "愿您天天能享受羽毛球的快乐！";
					 */
					String strs = "欢迎关注小Q智慧!";
					respContent = strs;

					// 带参数的二维码，插入到用户表中
					if (!key.equals(null) && !key.equals("")) {
						;
					}
				}
				// 取消订阅
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				}
				// 菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String url = null;
					String title = null;
					String description = null;

					// String
					// postUrl="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+TokenThread.accessToken.token;

					// 事件KEY值
					String eventKey = requestMap.get("EventKey");
					String menuName = "";
					// TODO 根据自定义菜单值与eventKey比较来进行下一步操作
					switch (Integer.valueOf(eventKey)) {

					default:
						break;
					}

					/*
					 * //得到OpenId System.out.println("fromUserName = " + fromUserName);
					 * System.out.println("toUserName = " + toUserName);
					 * System.out.println("MsgType = " + msgType);
					 * 
					 * //放到Cookie中 Cookie cookie = new Cookie("xiaoq-openid",fromUserName);
					 * cookie.setMaxAge(3600*24*3); response.addCookie(cookie);
					 */
					// respContent=menuName+",菜单建设中，谢谢关注！";
				} else if (eventType.equals("SCAN")) {
					if (!key.equals(null) && !key.equals("")) {

					}
				} else if (eventType.equals("WifiConnected")) {

				} else {
				}
			} else {
				respContent = "谢谢您的意见!";
			}

			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;

	}

}