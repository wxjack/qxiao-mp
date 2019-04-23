package com.talkweb.weixin.main;

public class Constants {
	private Constants() {
	}
	/*登录用户SESSION*/
	public static final String COMMGATE_USERSESSION="COMMGATE.ADMIN.USERSESSION";
	/*登录用户操作权限URL*/
	public static final String COMMGATE_USERSESSION_URL="COMMGATE.ADMIN.USERSESSION_URL";
	/*登录跳转SESSION*/
	public static final String COMMGATE_SENDFROMSESSION="COMMGATE.ADMIN.SENDFROMSESSION";
	
	/*登录用户COOKIE*/
	public static final String COMMGATE_COOKIE_USERNAME="COMMGATE.COOKIE.ADMIN.USERNAME";
	public static final String COMMGATE_COOKIE_PASSWORD="COMMGATE.COOKIE.ADMIN.PASSWORD";
	/*登录用户菜单SESSION*/
	public static final String COMMGATE_MENUSESSION="COMMGATE.ADMIN.MENUSESSION";

	public static final String COMMGATE_WEBSERVICE_PASSWORD="3eb47f8059cf3a2cdc98b31e156cdfd6a958ae1e";
	
	/*电信文件上传路径*/
	public static final String COMMGATE_TELWX_BASEPATH="D:\\tomcat(smsadmin)\\execl\\";
	public static final String COMMGATE_UPLOAD_WEBSERVICE_URLSTR_ENDPOINT="http://www.qiye001.com/web/com/corpuc/webservice/NetFaxService.jws";
	
	/*项目URL的根路径*/
//	public static  String WXAPP_URL_BASEPATH="http://app.qiye008.com:80";
//	public static  String WXAPP_URL_BASEPATH="http://badmi.qiye001.com:80";
	
//	public static  String WXAPP_URL_BASEPATH="http://mp.huoler.com:80";		
//	public static  String WXAPP_URL_BASEPATH_HTTPS="https://www.huoler.com";	
	
//	public static  String WXAPP_URL_BASEPATH="http://www.huoler.com:8080";		
	public static  String WXAPP_URL_BASEPATH="https://www.huoler.com";			
	public static  String WXAPP_URL_BASEPATH_HTTPS="https://www.huoler.com";	

	
	//微信支付约聚活动商户API密	
	public static String YUEJU_MERCHANT_API_SERCRET = "o9sN0VtVtpL82xcy3bUzaSqnou012345";
	//微信支付约聚活动回调URL
	public static String YUEJU_MERCHANT_NOTIFY_URL = "https://www.huoler.com/servlet/NativeNotifyUrl2";
	//微信支付约聚活动APPID	
	public static String YUEJU_APP_ID = "wx1067cf99ebdaaec5";
	//微信支付约聚活动商户ID	
	public static String YUEJU_MCH_ID = "1491062812";
/*	
	//微信支付约聚活动商户API密	
	public static String ZHUFU_MERCHANT_API_SERCRET = "o9sN0VtVtpL82xcy3bUzaSqnou012345";
	//微信支付约聚活动回调URL
	public static String ZHUFU_MERCHANT_NOTIFY_URL = "https://www.huoler.com/servlet/NativeNotifyUrl";
	//微信支付约聚活动APPID	
	public static String ZHUFU_APP_ID = "wx4fe366538265d2cb";
	//微信支付约聚活动商户ID	
	public static String ZHUFU_MCH_ID = "1495601442";
*/	
	//小Q食谱
	//微信支付约聚活动商户API密	
	public static String ZHUFU_MERCHANT_API_SERCRET = "o9sN0VtVtpL82xcy3bUzaSqnou012345";
	//微信支付约聚活动回调URL
	public static String ZHUFU_MERCHANT_NOTIFY_URL = "https://www.huoler.com/servlet/NativeNotifyUrl";
	//微信支付约聚活动APPID	
	public static String ZHUFU_APP_ID = "wx3685a85741d11f3b";
	//微信支付约聚活动商户ID	
	public static String ZHUFU_MCH_ID = "1494922082";

	
	/*企业管理员ID*/
	public static  int WXAPP_BUS_ID=9;
	public static  String WXAPP_BUS_ROLENAME = "企业管理员";
	
	/*wineCategory是否按用户来分*/
	public static  boolean CATE_USE_THESAME = false;
	
	/*wineGoods是否按用户来分*/
	public static  boolean GOODS_USE_THESAME = false;
	
	
	/*用户设置头像上传的目录*/
	public static String WXAPP_USER_PHOTO="/photo";
	
	/*交流区帖子图片上传的目录*/
	public static String WXAPP_ZONE_PIC="/zonePic";
	
	/*交流区帖子Logo上传的目录*/
	public static String WXAPP_ZONE_LOGO="/zonePost";
	
	/*通知图片上传的目录*/
	public static String WXAPP_NOTICE_PHOTO="/noticePhoto";
	
	/*实时视频上传图片的目录*/
	public static String WXAPP_LIVE_PHOTO="/livePhoto";
	
	/*文档类别默认LOGO目录*/
	public static String WXAPP_DOCU_LOGODEFAULT="/defaultPic/docuCateDefaultPic.jpg";
	
	/*文档类别LOGO上传的目录*/
	public static String WXAPP_DOCU_LOGO="/docuCate";
	
	/*文档图片上传的目录*/
	public static String WXAPP_DOCU_PIC="/docuPic";
	
	/*文档编辑器图片上传的目录*/
	public static String WXAPP_DOCUEDIT_PIC="/docuPhoto";
	
	//微信菜单链接

	//历史
	public static String WEIXIN_HIS_URL="http://mp.weixin.qq.com/mp/getmasssendmsg?__biz=MzA5Nzc2Nzk1Ng==#wechat_webview_type=1&wechat_redirect";

	//头条
	public static String WEIXIN_TOP_URL="http://mp.huoler.com/action/mod-android/toplineDataH5.action";
	
	//红包
	public static String WEIXIN_HONGBAO_URL="http://wx2.huoler.com/hongbao/getcode_2016.php?from=2016";
	
	//问卷调查
	public static String WEIXIN_SURVEY_URL="https://mp.weixin.qq.com/s?__biz=MzA5Nzc2Nzk1Ng==&mid=2651227077&idx=2&sn=e5c050f1cf26ad2fe675e44f0eddfd0e";	
	
	//实战
	public static String WEIXIN_ACTUAL_URL="http://mp.huoler.com/action/mod-android/noticeListH5.action?weiXinType=2";
	
	//话题
	public static String WEIXIN_TOPIC_URL="http://mp.huoler.com/action/mod-android/noticeListH5.action?weiXinType=3";
	
	//分类技术
	public static String WEIXIN_TECH_URL="http://mp.huoler.com/action/mod-android/docuPostListH5.action?weiXinType=6";
	
	//精选视频
	public static String WEIXIN_PICKVIDEO_URL="http://mp.huoler.com/action/mod-android/videoElementH5.action?weiXinType=4";
	
	//奥运赛事视频
	public static String WEIXIN_RIO_URL="http://mp.huoler.com/action/mod-android/videoElementH5.action?weiXinType=5&cateId=176";
		
	//教学视频
	public static String WEIXIN_EDUVIDEO_URL="http://mp.huoler.com/shareManage/H5Category.html";

	//美女在线教球
	public static String WEIXIN_EDUVIDEO_GIRL_URL="http://mp.huoler.com/action/mod-android/videoElementH5.action?weiXinType=5&cateId=175";
	
	//技巧攻略
	public static String WEIXIN_JIQIAO_URL="http://mp.weixin.qq.com/mp/homepage?__biz=MzA5Nzc2Nzk1Ng==&hid=1&sn=a249dba1ae546814557d54b28aa93b2d#wechat_redirect";
	
	//羽球训练
	public static String WEIXIN_XUNLIAN_URL="http://mp.weixin.qq.com/mp/homepage?__biz=MzA5Nzc2Nzk1Ng==&hid=2&sn=c31c9986874e5868b0ef5f1525956e34#wechat_redirect";
	
	//器材保健
	public static String WEIXIN_QICAI_URL="http://mp.weixin.qq.com/mp/homepage?__biz=MzA5Nzc2Nzk1Ng==&hid=3&sn=88f10d1f9cb570fc7e46ff3c63b063f1#wechat_redirect";
	
	//大话羽球
	public static String WEIXIN_DAHUA_URL="http://mp.weixin.qq.com/mp/homepage?__biz=MzA5Nzc2Nzk1Ng==&hid=4&sn=5aa3578949a0aa7bcd2fff4502976df7#wechat_redirect";
	
	//趣味视频
	public static String WEIXIN_QUWEI_URL="http://mp.weixin.qq.com/mp/homepage?__biz=MzA5Nzc2Nzk1Ng==&hid=5&sn=38824832fffc80b79b0ffbf4758e7860#wechat_redirect";
	
	//题目测试	
	//public static String WEIXIN_TEST_URL="http://wx2.huoler.com/qa2/gamebegin.php";
	public static String WEIXIN_TEST_URL="http://mp.weixin.qq.com/s?__biz=MzA5Nzc2Nzk1Ng==&mid=2651227276&idx=4&sn=39e050f0eb1e9ecfc736edaf896ca554&scene=4#wechat_redirect";
	
	//成长和故事
	public static String WEIXIN_GROWUP_URL="http://mp.weixin.qq.com/s?__biz=MzA5Nzc2Nzk1Ng==&mid=503743757&idx=1&sn=9c7a09cd07697d454822f4dfa6e32e91&scene=0#wechat_redirect";
	
	//羽球知识
	public static String WEIXIN_ZHISHI_URL="http://mp.weixin.qq.com/mp/homepage?__biz=MzA5Nzc2Nzk1Ng==&hid=6&sn=28e3f3df51b9b16374da5d705951981d#wechat_redirect";
	
	//羽球高考
	public static String WEIXIN_GAOKAO_URL="http://h5.gu778.com.cn/index.php/s/ACnWYgQdHC";
	
	//进群学习
	//public static String WEIXIN_QUN_URL="http://mp.weixin.qq.com/s?__biz=MzA5Nzc2Nzk1Ng==&mid=503743873&idx=5&sn=0ef6fdff1ffae24c40830b5eeb39ae89";
	public static String WEIXIN_QUN_URL="http://mp.weixin.qq.com/s?__biz=MzA5Nzc2Nzk1Ng==&mid=2651228193&idx=2&sn=ab1f307169ae41db179ee028f38342cd#rd";
	
	
	//赢礼品
	public static String WEIXIN_LIPIN_URL="http://mp.weixin.qq.com/s?__biz=MzA5Nzc2Nzk1Ng==&mid=2651227541&idx=1&sn=cf3021dd29fb113b0e4c20f2798999a2";
	
	//个性T恤
	public static String WEIXIN_TSHIRT_URL="http://mp.weixin.qq.com/s?__biz=MzA5Nzc2Nzk1Ng==&mid=2651227820&idx=1&sn=68d2b3935a4a12d42225e9461d18e608&scene=0#wechat_redirect";
	
	//技术内容
	public static String WEIXIN_CONTENT2_URL="http://mp.weixin.qq.com/s?__biz=MzA5Nzc2Nzk1Ng==&mid=2651228178&idx=1&sn=526195e2c3a7d9f67e829298e50d2f62#rd";
}