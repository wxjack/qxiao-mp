package com.qxiao.wx.common.upload;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.qxiao.wx.common.ConstPool;

import net.sf.json.JSONObject;

public class JsApiTicketUtils {

	public static String getTicket(String access_token) {
		String ticket = null;
		String url = ConstPool.JSAPI_TICKET_URL.replace("ACCESS_TOKEN", access_token);// 这个url链接和参数不能变
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = JSONObject.fromObject(message);
			ticket = demoJson.getString("ticket");
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}
}
