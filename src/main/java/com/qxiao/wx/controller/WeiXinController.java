package com.qxiao.wx.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qxiao.wx.common.upload.JsApiTicketUtils;
import com.spring.web.utils.HttpServletRequestBody;
import com.talkweb.weixin.main.StartOnLoad;
import com.talkweb.weixin.util.Sign;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/weixin")
public class WeiXinController {

	@RequestMapping("/sign.do")
	@ResponseBody
	public static Map<String, String> main(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ParseException, IOException {
		System.out.println("token====="+StartOnLoad.tokenThread1.accessToken);
		String jsapi_ticket = JsApiTicketUtils
				.getTicket(StartOnLoad.tokenThread1.accessToken.getToken());
		response.setContentType("appliaction/json");
		// jsapi_ticket不存在的时候，通过判断去存，然后再取到作为参数使用
	//	jsapi_ticket = (String) session.getAttribute("ticket");

		JSONObject object = HttpServletRequestBody.toJSONObject(request);
		String url = object.get("url").toString();
		Map<String, String> ret = Sign.sign(jsapi_ticket, url);
		ret.put("appid",StartOnLoad.TOKEN_APPID);
		return ret;
	};

//	@RequestMapping("/sign")
//	@ResponseBody
//	private static Map<String, String> sign(String jsapi_ticket, String url) {
//		Map<String, String> ret = new HashMap<String, String>();
//		String nonce_str = create_nonce_str();
//		String timestamp = create_timestamp();
//		String signature = "";
//		String string1;
//
//		// 注意这里参数名必须全部小写，且必须有序
//		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
//		try {
//			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
//			crypt.reset();
//			crypt.update(string1.getBytes("UTF-8"));
//			signature = byteToHex(crypt.digest());
//		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//
//		ret.put("url", url);
////		ret.put("jsapi_ticket", jsapi_ticket);
//		ret.put("appid", StartOnLoad.TOKEN_APPID);
//		ret.put("nonceStr", nonce_str);
//		ret.put("timestamp", timestamp);
//		ret.put("signature", signature);
//
//		return ret;
//	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString().substring(0, 10);
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

}
