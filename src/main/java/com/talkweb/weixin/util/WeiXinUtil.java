package com.talkweb.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.talkweb.weixin.main.Constants;
import com.talkweb.weixin.pojo.AccessToken;
import com.talkweb.weixin.pojo.OpenId;
import com.talkweb.weixin.pojo.Menu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.talkweb.weixin.util.AesCbcUtil;

/**
 * 微信公众接口工具类
 * 
 * 项目名称：WeiChatService 类名称：WeiXinUtil 类描述： 创建人：zhouling 创建时间：Nov 15, 2013
 * 11:29:07 AM 修改备注：
 * 
 * @version
 * 
 */
public class WeiXinUtil {

	private static LogUtil log = new LogUtil(WeiXinUtil.class);

	/*微信模板接口*/
	public static final String SEND_TEMPLAYE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	/*删除微信模板接口*/
	public static final String DEL_TEMPLAYE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN";
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	// 获取服务号的CODE;
	public static String access_authorize = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
	// 获取服务号的openId;
	public static String access_getopenid_fuwu_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

	//获取用户信息
	public static String access_getuserinfo_url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	// 得到带参数的二维码
	public final static String access_getwxacode_url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN";
	// 得到带参数的二维码
	public final static String access_getwxacodeunlimit_url = "https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN";

	// 获取小程序的openId;
	public final static String access_getopenid_url = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=APPSECRET&js_code=JS_CODE&grant_type=authorization_code";
	// 发送小程序模板消息
	public final static String access_sendtemplate_url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";
	// 发送小程序客服消息
	public final static String access_sendcustom_url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	// 发送公众号模板消息
	public final static String access_sendtemplate_url2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	// 模板ID
	public final static String access_template_id = "Botk5ztCsJer8Q8Ox7N17sfsISj3jyCBSETisxA_rwQ";
	// 二维码文件保存路径
	public final static String access_wxacode_path = "";
	public final static String webUrl_https = Constants.WXAPP_URL_BASEPATH_HTTPS;

	/**
	 * 发起https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（Get或者post）
	 * @param outputStr
	 *            提交数据
	 * @return
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLcontext管理器对像，使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpsUrlConn.setSSLSocketFactory(ssf);
			httpsUrlConn.setDoInput(true);
			httpsUrlConn.setDoOutput(true);
			httpsUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpsUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsUrlConn.connect();
			}

			// 当有数据需要提交时
			if (outputStr != null) {
				OutputStream outputStream = httpsUrlConn.getOutputStream();
				// 防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
				outputStream = null;
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpsUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			bufferedReader.close();
			inputStreamReader.close();

			inputStream.close();
			inputStream = null;

			httpsUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
			// System.out.println(jsonObject);

		} catch (ConnectException ce) {
			// TODO: handle exception
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("https request error:{}", e);
		}

		return jsonObject;
	}

    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
    }  

    public static JSONObject httpGetPic(String url) throws IOException{
    	return null;
    }	
		
	/**
	 * 将二进制转换成文件保存
	 * 
	 * @param instreams
	 *            二进制流
	 * @param imgPath
	 *            图片的保存路径
	 * @param imgName
	 *            图片的名称
	 * @return 1：保存正常 0：保存失败
	 */
	public static String saveToImgByInputStream(InputStream instreams) {
		return "";
	}

	public static String saveToQrcodeByInputStream(InputStream instreams) {
		return "";
	}

	public static String getHttpsQrcode(String requestUrl,
			String requestMethod, String outputStr) {
		try {
			// 创建SSLcontext管理器对像，使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpsUrlConn.setSSLSocketFactory(ssf);
			httpsUrlConn.setDoInput(true);
			httpsUrlConn.setDoOutput(true);
			httpsUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpsUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsUrlConn.connect();
			}

			// 当有数据需要提交时
			if (outputStr != null) {
				OutputStream outputStream = httpsUrlConn.getOutputStream();
				// 防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
				outputStream = null;
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpsUrlConn.getInputStream();
			String imgFileName = saveToQrcodeByInputStream(inputStream);

			inputStream.close();
			inputStream = null;

			httpsUrlConn.disconnect();

			return imgFileName;
		} catch (ConnectException ce) {
			// TODO: handle exception
			System.out.println("Weixin server connection timed out.");			
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("https request error:{}"+ e);
			log.error("https request error:{}", e);
		}

		return "";
	}
	
	public static String httpsRequestImg(String requestUrl,
			String requestMethod, String outputStr) {
		try {
			// 创建SSLcontext管理器对像，使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpsUrlConn.setSSLSocketFactory(ssf);
			httpsUrlConn.setDoInput(true);
			httpsUrlConn.setDoOutput(true);
			httpsUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpsUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsUrlConn.connect();
			}

			// 当有数据需要提交时
			if (outputStr != null) {
				OutputStream outputStream = httpsUrlConn.getOutputStream();
				// 防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
				outputStream = null;
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpsUrlConn.getInputStream();
			String imgFileName = saveToImgByInputStream(inputStream);

			inputStream.close();
			inputStream = null;

			httpsUrlConn.disconnect();

			return imgFileName;
		} catch (ConnectException ce) {
			// TODO: handle exception
			System.out.println("Weixin server connection timed out.");			
			log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("https request error:{}"+ e);
			log.error("https request error:{}", e);
		}

		return "";
	}

	// 取服务号OpenID时先要得到的Code
	public static String getFuwuCode(HttpServletResponse response, String appid, String redirect_url) {		
		String code = "";
		log.info("开始调用getFuwuCode");
		try{
			String url = URLEncoder.encode(redirect_url, "UTF-8");
			String requestUrl = access_authorize.replace("APPID", appid).replace(
					"REDIRECT_URI", url);

			log.info("requestUrl：" + requestUrl);
			
			response.sendRedirect(requestUrl);
			
		}catch (Exception e) {

		}

		return code;
	}
	
	public static JSONObject getUserInfoOpenId(String appid, String secret, String code) {
		String requestUrl = access_getopenid_fuwu_url.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
		
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		return jsonObject;
	}
	
	public static String getWxAcodeUnlimit(String accessToken, String path) {
		String requestUrl = access_getwxacodeunlimit_url.replace(
				"ACCESS_TOKEN", accessToken);

		JSONObject dataJson = new JSONObject();
		dataJson.put("path", path);
		dataJson.put("width", 430);

		String imgFileName = httpsRequestImg(requestUrl, "POST", dataJson
				.toString());

		return imgFileName;
	}

	
	public static JSONObject getWxUserInfo(String accessToken, String openId) {
		String requestUrl = access_getuserinfo_url.replace("ACCESS_TOKEN",
				accessToken).replace("OPENID", openId);
		
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		return jsonObject;
	}
	
	
	public static JSONObject getWxAcode(String accessToken, String path) {
		String requestUrl = access_getwxacode_url.replace("ACCESS_TOKEN",
				accessToken);

		JSONObject dataJson = new JSONObject();
		dataJson.put("path", path);
		dataJson.put("width", 430);

		JSONObject jsonObject = httpsRequest(requestUrl, "POST", dataJson
				.toString());

		return jsonObject;
	}

	public static JSONObject sendCustomURL(String accessToken, String openid) {
		String requestUrl = access_sendcustom_url.replace("ACCESS_TOKEN",
				accessToken);

		JSONObject dataJson = new JSONObject();
		dataJson.put("touser", openid);
		dataJson.put("msgtype", "link");

		JSONObject subDataJson = new JSONObject();
		subDataJson.put("title", "祝福驿站");
		subDataJson.put("description", "关注祝福驿站的官方微信公众号，商务咨询合作，更多好玩应用更多精彩~~！");

		// subDataJson.put("url",
		// "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzA5Nzc2Nzk1Ng==&scene=124#wechat_redirect");
		// subDataJson.put("thumb_url",
		// "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzA5Nzc2Nzk1Ng==&scene=124#wechat_redirect");

		subDataJson.put("url", "http://dwz.cn/7hX59z");
		subDataJson
				.put(
						"thumb_url",
						"https://mmbiz.qpic.cn/mmbiz_jpg/JE4qPEkE6AzqjZaDEvGNBXXKibegaPZTmA9GviayUJ7uNFFib2FEVaF8EKQIQXUia41rd3oj81lVyO4EksIjKBkdsw/?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1");

		dataJson.put("link", subDataJson);

		JSONObject jsonObject = httpsRequest(requestUrl, "POST", dataJson
				.toString());
		/*
		 * //如果请求成功 if(jsonObject!=null){ try{
		 * System.out.println(jsonObject.toString()); }catch (Exception e) {
		 * System.out.println(e.toString()); } }
		 */
		return jsonObject;
	}

	public static JSONObject sendTemplateMsg3(String accessToken,
			String openid, String url, String template_id,
			JSONObject data) {
		String requestUrl = access_sendtemplate_url2.replace("ACCESS_TOKEN",
				accessToken);

		JSONObject dataJson = new JSONObject();
		dataJson.put("touser", openid);
		dataJson.put("template_id", template_id);
		dataJson.put("url", url);
		dataJson.put("topcolor", "#FF0000");
		dataJson.put("data", data);

		JSONObject jsonObject = httpsRequest(requestUrl, "POST", dataJson
				.toString());

		// 如果请求成功
		if (jsonObject != null) {
			try {
				log.info("WeiXinUtil-sendTemplateMsg3:"
						+ jsonObject.toString());
			} catch (Exception e) {
				log.error("WeiXinUtil-sendTemplateMsg3:"
						+ e.getMessage());
			}
		}

		return jsonObject;
	}		
	public static JSONObject sendTemplateMsg(String accessToken, String openid,
			String page, String form_id, JSONObject data) {
		String requestUrl = access_sendtemplate_url2.replace("ACCESS_TOKEN",
				accessToken);

		JSONObject dataJson = new JSONObject();
		dataJson.put("touser", openid);
		dataJson.put("template_id", access_template_id);
		dataJson.put("page", page);
		dataJson.put("form_id", form_id);
		dataJson.put("data", data);

		JSONObject jsonObject = httpsRequest(requestUrl, "POST", dataJson
				.toString());

		// 如果请求成功
		if (jsonObject != null) {
			try {
				log.info("WeiXinUtil-sendTemplateMsg:form_id=" + form_id
						+ jsonObject.toString());
			} catch (Exception e) {
				log.error("WeiXinUtil-sendTemplateMsg:form_id=" + form_id
						+ e.getMessage());
			}
		}

		return jsonObject;
	}

	public static JSONObject sendTemplateMsg2(String accessToken,
			String openid, String page, String form_id, String template_id,
			JSONObject data) {
		String requestUrl = access_sendtemplate_url.replace("ACCESS_TOKEN",
				accessToken);

		JSONObject dataJson = new JSONObject();
		dataJson.put("touser", openid);
		dataJson.put("template_id", template_id);
		dataJson.put("page", page);
		dataJson.put("form_id", form_id);
		dataJson.put("data", data);

		JSONObject jsonObject = httpsRequest(requestUrl, "POST", dataJson
				.toString());

		// 如果请求成功
		if (jsonObject != null) {
			try {
				log.info("WeiXinUtil-sendTemplateMsg:form_id=" + form_id
						+ jsonObject.toString());
			} catch (Exception e) {
				log.error("WeiXinUtil-sendTemplateMsg:form_id=" + form_id
						+ e.getMessage());
			}
		}

		return jsonObject;
	}

	
	public static String getGroupGid(String appid, String appsecret, String js_code, String encryptedData, String iv){
		if (js_code == null)
			js_code = "";

		String openGId = "";
		String requestUrl = access_getopenid_url.replace("APPID", appid)
				.replace("APPSECRET", appsecret).replace("JS_CODE", js_code);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		// 如果请求成功
		if (jsonObject != null) {
			try {
				String session_key = jsonObject.getString("session_key");
				String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
				if (null != result && result.length() > 0){
					JSONObject userInfoJSON = JSONObject.fromObject(result);
					openGId=userInfoJSON.getString("openGId");
				}

			} catch (Exception e) {

			}
		}
				
		return openGId;		
	}
	
	public static OpenId getOpenId(String appid, String appsecret,
			String js_code) {
		OpenId openId = null;

		if (js_code == null)
			js_code = "";

		String requestUrl = access_getopenid_url.replace("APPID", appid)
				.replace("APPSECRET", appsecret).replace("JS_CODE", js_code);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		log.debug("APPID:" + appid + ",APPSECRET:" + appsecret);
		log.debug("请求地址：" + requestUrl);

		// System.out.println("返回JSON："+jsonObject);

		// 如果请求成功
		if (jsonObject != null) {
			try {
				openId = new OpenId();

				openId.openId = jsonObject.getString("openid");
				// 不明白为什么有时没有这个字段
				// openId.expiresIn=jsonObject.getString("expires_in");

				// System.out.println("openid=" + openId.openId);

			} catch (Exception e) {
				// TODO: handle exception
				// TODO: handle exception
				openId = null;
				// 获取token失败
				System.out.println(e);

				log.error("获取token失败 errcode:{" + jsonObject.getInt("errcode")
						+ "} errmsg:{" + jsonObject.getString("errmsg") + "}");
				log.debug("APPID:" + appid + ",APPSECRET:" + appsecret);
				log.debug("请求地址：" + requestUrl);
			}
		}
		return openId;
	}

	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl = access_token_url.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		log.debug("APPID:" + appid + ",APPSECRET:" + appsecret);
		log.debug("请求地址：" + requestUrl);
		// 如果请求成功
		if (jsonObject != null) {
			try {

				accessToken = new AccessToken();
				accessToken.token = jsonObject.getString("access_token");
				accessToken.expiresIn = jsonObject.getString("expires_in");
			} catch (Exception e) {
				// TODO: handle exception
				// TODO: handle exception
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{" + jsonObject.getInt("errcode")
						+ "} errmsg:{" + jsonObject.getString("errmsg") + "}");
				log.debug("APPID:" + appid + ",APPSECRET:" + appsecret);
				log.debug("请求地址：" + requestUrl);
			}
		}
		return accessToken;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);
		System.out.println(jsonMenu);
		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败  errcode:{" + jsonObject.getInt("errcode")
						+ "} errmsg:{" + jsonObject.getString("errmsg") + "}");
			}
		}

		return result;
	}
}
