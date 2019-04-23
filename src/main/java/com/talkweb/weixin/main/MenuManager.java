package com.talkweb.weixin.main;

import com.talkweb.weixin.pojo.AccessToken;
import com.talkweb.weixin.pojo.Button;
import com.talkweb.weixin.pojo.Menu;
import com.talkweb.weixin.pojo.ViewButton;
import com.talkweb.weixin.util.LogUtil;
import com.talkweb.weixin.util.WeiXinUtil;

/**
 * 菜单管理器
 */
public class MenuManager {

	private static LogUtil log = new LogUtil(MenuManager.class);

	public static String oauth2URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
			+ StartOnLoad.TOKEN_APPID + "&redirect_uri=" + StartOnLoad.GOAL_URL
			+ "jumpPage/URL?action=viewtest&response_type=code&scope=snsapi_base&state=1#wechat_redirect";

	public static void CreateMenu() {
		log.info("------------开始创建菜单--------------------");
		// 第三方用户唯一凭证w
		String appId = StartOnLoad.TOKEN_APPID;
		// 第三方用户唯一凭证密钥
		String appSecret = StartOnLoad.TOKEN_SECRET;

		// 调用接口获取access_token
		AccessToken at = WeiXinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单，生成小Q食谱菜单
			int result = WeiXinUtil.createMenu(getMenu3(), at.token);

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	// 组装小Q食谱菜单
	private static Menu getMenu3() {

		ViewButton shiPuBtn = new ViewButton();
		shiPuBtn.setName("小Q表现");
		shiPuBtn.setType("view");
		String shiPuUrl = StartOnLoad.GOAL_LOCAL_URL+"action/mod-xiaojiao/manage/registerUser.do?type=1";
		shiPuBtn.setUrl(shiPuUrl);

		ViewButton baikeBtn = new ViewButton();
		baikeBtn.setName("小Q智慧 ");
		baikeBtn.setType("view");
		String baikeURL = StartOnLoad.GOAL_LOCAL_URL+"action/mod-xiaojiao/manage/registerUser.do?type=2";
		baikeBtn.setUrl(baikeURL);

		ViewButton myBtn = new ViewButton();
		myBtn.setName("小Q班级");
		myBtn.setType("view");
		String content2URL = StartOnLoad.GOAL_LOCAL_URL+"action/mod-xiaojiao/manage/registerUser.do?type=3";
		myBtn.setUrl(content2URL);

		Menu menu = new Menu();

		menu.setButton(new Button[] { shiPuBtn, baikeBtn, myBtn });

		return menu;

	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
//	private static Menu getMenu() {
//
//		ViewButton hisBtn = new ViewButton();
//		hisBtn.setName("历史消息");
//		hisBtn.setType("view");
//		String hisURL = Constants.WEIXIN_HIS_URL;
//		hisBtn.setUrl(hisURL);
//
//		ViewButton topBtn = new ViewButton();
//		topBtn.setName("头条图文");
//		topBtn.setType("view");
//		String topURL = Constants.WEIXIN_TOP_URL;
//		topBtn.setUrl(topURL);
//		
//		ViewButton jiqiaoBtn = new ViewButton();
//		jiqiaoBtn.setName("技巧攻略");
//		jiqiaoBtn.setType("view");
//		String jiqiaoURL = Constants.WEIXIN_JIQIAO_URL;
//		jiqiaoBtn.setUrl(jiqiaoURL);
//				
//		ViewButton xunlianBtn = new ViewButton();
//		xunlianBtn.setName("羽球训练");
//		xunlianBtn.setType("view");
//		String xunlianURL = Constants.WEIXIN_XUNLIAN_URL;
//		xunlianBtn.setUrl(xunlianURL);
//				
//		ViewButton qicaiBtn = new ViewButton();
//		qicaiBtn.setName("器材保健");
//		qicaiBtn.setType("view");
//		String qicaiURL = Constants.WEIXIN_QICAI_URL;
//		qicaiBtn.setUrl(qicaiURL);
//				
//		ViewButton dahuaBtn = new ViewButton();
//		dahuaBtn.setName("大话羽球");
//		dahuaBtn.setType("view");
//		String dahuaURL = Constants.WEIXIN_DAHUA_URL;
//		dahuaBtn.setUrl(dahuaURL);
//		
//		ViewButton zhishiBtn = new ViewButton();
//		zhishiBtn.setName("羽球知识");
//		zhishiBtn.setType("view");
//		String zhishiURL = Constants.WEIXIN_ZHISHI_URL;
//		zhishiBtn.setUrl(zhishiURL);
//		
//		
//		ViewButton gaokaoBtn = new ViewButton();
//		gaokaoBtn.setName("羽球高考");
//		gaokaoBtn.setType("view");
//		String gaokaoURL = Constants.WEIXIN_GAOKAO_URL;
//		gaokaoBtn.setUrl(gaokaoURL);		
//
//		ViewButton testVideoBtn = new ViewButton();
//		testVideoBtn.setName("测    试");
//		testVideoBtn.setType("view");
//		String testURL = Constants.WEIXIN_TEST_URL;
//		testVideoBtn.setUrl(testURL);		
//		
//		ViewButton growUpBtn = new ViewButton();
//		growUpBtn.setName("球友自拍");
//		growUpBtn.setType("view");
//		String growUpURL = Constants.WEIXIN_GROWUP_URL;
//		growUpBtn.setUrl(growUpURL);		
//				
//		ViewButton qunBtn = new ViewButton();
//		qunBtn.setName("进群交流");
//		qunBtn.setType("view");
//		String qunURL = Constants.WEIXIN_QUN_URL;
//		qunBtn.setUrl(qunURL);		
//		
//		ViewButton liPinBtn = new ViewButton();
//		liPinBtn.setName("赢礼品");
//		liPinBtn.setType("view");
//		String liPinURL = Constants.WEIXIN_LIPIN_URL;
//		liPinBtn.setUrl(liPinURL);		
//		
//		
//		ViewButtonS viewButtonContent = new ViewButtonS();
//		viewButtonContent.setName("内  容");
//		//viewButtonContent.setSub_button(new Button[] { hisBtn, topBtn});		
//		//viewButtonContent.setSub_button(new Button[] { hisBtn, jiqiaoBtn, xunlianBtn, qicaiBtn, dahuaBtn});
//		viewButtonContent.setSub_button(new Button[] { zhishiBtn, growUpBtn, hisBtn});
//		
//		ViewButton content2Btn = new ViewButton();
//		content2Btn.setName("知  识");
//		content2Btn.setType("view");
//		String content2URL = Constants.WEIXIN_CONTENT2_URL;
//		content2Btn.setUrl(content2URL);		
//					
//		ViewButtonS viewButtonGu = new ViewButtonS();
//		//viewButtonGu.setName("自  拍");
//		viewButtonGu.setName("有  料");
////		viewButtonGu.setSub_button(new Button[] { growUpBtn,qunBtn,liPinBtn,testVideoBtn});		
//		viewButtonGu.setSub_button(new Button[] { growUpBtn,qunBtn});
//		
//		ViewButton tShirtBtn = new ViewButton();
//		tShirtBtn.setName("个性T恤");
//		tShirtBtn.setType("view");
//		String tShirtURL = Constants.WEIXIN_TSHIRT_URL;
//		tShirtBtn.setUrl(tShirtURL);				
//		
//		CommonButton hongBaoBtn = new CommonButton();
//		hongBaoBtn.setName("领红包");		
//		hongBaoBtn.setType("click");
//		hongBaoBtn.setKey("12");		    						
//		
//		ViewButton hongBaoURLBtn = new ViewButton();
//		hongBaoURLBtn.setName("领红包");
//		hongBaoURLBtn.setType("view");
//		String hongBaoURL = Constants.WEIXIN_HONGBAO_URL;
//		hongBaoURLBtn.setUrl(hongBaoURL);
//		
//		CommonButton contactMeBtn = new CommonButton();
//		contactMeBtn.setName("与我联系");		
//		contactMeBtn.setType("click");
//		contactMeBtn.setKey("13");		    						
//
//		ViewButton surveyURLBtn = new ViewButton();
//		surveyURLBtn.setName("问卷调查");
//		surveyURLBtn.setType("view");
//		String surveyURL = Constants.WEIXIN_SURVEY_URL;
//		surveyURLBtn.setUrl(surveyURL);
//		
//		
//
///*		ViewButton actualBtn = new ViewButton();
//		actualBtn.setName("实  战");
//		actualBtn.setType("view");
//		String actualURL = Constants.WEIXIN_ACTUAL_URL;
//		actualBtn.setUrl(actualURL);
//		
//		ViewButton topicBtn = new ViewButton();
//		topicBtn.setName("话  题");
//		topicBtn.setType("view");
//		String topicURL = Constants.WEIXIN_TOPIC_URL;
//		topicBtn.setUrl(topicURL);
//
//		CommonButton techBtn = new CommonButton();
//		techBtn.setName("分类技术");		
//		techBtn.setType("click");
//		techBtn.setKey("10");		    
//
//		ViewButtonS viewButtonContent = new ViewButtonS();
//		viewButtonContent.setName("内  容");
//		viewButtonContent.setSub_button(new Button[] { actualBtn, topicBtn, techBtn});		
//*/
//		ViewButton girlVideoBtn = new ViewButton();
//		girlVideoBtn.setName("美女在线教球");
//		girlVideoBtn.setType("view");
//		String girlVideoURL = Constants.WEIXIN_EDUVIDEO_GIRL_URL;
//		girlVideoBtn.setUrl(girlVideoURL);
//		
//		ViewButton rioVideoBtn = new ViewButton();
//		rioVideoBtn.setName("里约奥运赛事");
//		rioVideoBtn.setType("view");
//		String rioVideoURL = Constants.WEIXIN_RIO_URL;
//		rioVideoBtn.setUrl(rioVideoURL);
//		
//		ViewButton pickVideoBtn = new ViewButton();
//		pickVideoBtn.setName("精选视频");
//		pickVideoBtn.setType("view");
//		String pickVideoURL = Constants.WEIXIN_PICKVIDEO_URL;
//		pickVideoBtn.setUrl(pickVideoURL);
//		
///*		ViewButton eduVideoBtn = new ViewButton();
//		eduVideoBtn.setName("教学视频");
//		eduVideoBtn.setType("view");
//		String eduVideoURL = Constants.WEIXIN_EDUVIDEO_URL;
//		eduVideoBtn.setUrl(eduVideoURL);
//*/
//		ViewButton eduVideoBtn = new ViewButton();
//		eduVideoBtn.setName("教学视频");		
//		eduVideoBtn.setType("view");
//		String eduVideoURL = Constants.WEIXIN_EDUVIDEO_URL;
//		eduVideoBtn.setUrl(eduVideoURL);
//						
//		ViewButton quweiVideoBtn = new ViewButton();
//		quweiVideoBtn.setName("趣味视频");		
//		quweiVideoBtn.setType("view");
//		String quweiVideoURL = Constants.WEIXIN_QUWEI_URL;
//		quweiVideoBtn.setUrl(quweiVideoURL);
//				
//		CommonButton techBtn = new CommonButton();
//		techBtn.setName("分类图文");		
//		techBtn.setType("click");
//		techBtn.setKey("10");		    
//		
//		ViewButtonS viewButtonPic = new ViewButtonS();
//		viewButtonPic.setName("图  文");
//		viewButtonPic.setSub_button(new Button[] { topBtn, techBtn});		
//		
//				
//		ViewButtonS viewButtonVideo = new ViewButtonS();
//		viewButtonVideo.setName("视  频");
//		//viewButtonVideo.setSub_button(new Button[] { pickVideoBtn, eduVideoBtn, techBtn});		
//		viewButtonVideo.setSub_button(new Button[] { eduVideoBtn, pickVideoBtn, quweiVideoBtn, rioVideoBtn});
//		
//		Menu menu = new Menu();
////		menu.setButton(new Button[] {topBtn,viewButtonContent,viewButtonVideo});
////		menu.setButton(new Button[] {hisBtn,topBtn,viewButtonVideo});
////		menu.setButton(new Button[] {viewButtonContent,hongBaoURLBtn,viewButtonVideo});		
////		menu.setButton(new Button[] {viewButtonContent,hongBaoBtn,viewButtonVideo});
////		menu.setButton(new Button[] {viewButtonContent,contactMeBtn,viewButtonVideo});			
////		menu.setButton(new Button[] {viewButtonContent,surveyURLBtn,viewButtonVideo});		
//		
//		//menu.setButton(new Button[] {viewButtonContent,testVideoBtn,viewButtonVideo});		
////		menu.setButton(new Button[] {viewButtonContent,viewButtonGu,viewButtonVideo});				
//				
//		menu.setButton(new Button[] {content2Btn,qunBtn,viewButtonVideo});				
//		
//		return menu;
//	}

}
