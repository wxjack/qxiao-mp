package com.talkweb.weixin.main;

import net.sf.json.JSONObject;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import com.qxiao.wx.user.jpa.dao.QmMessageSendDao;
import com.qxiao.wx.user.jpa.entity.QmMessageSend;
import com.talkweb.weixin.pojo.AccessToken;
import com.talkweb.weixin.util.WeiXinUtil;

@Component
public class SendMsgThread extends Thread {

	@Autowired
	QmMessageSendDao messageDao;

	class TemplateMessage {
		Long sendId;
		String openId;
		String title;
		String textContent;
		Long messageId;
		int type;
		int result;
		Date postTime;
//		int		Id;
//		int		ActivityId;
//		String	Title;
//		String	PositionDesc;
//		String	StartTime;
//		String 	FormId;
//		String	OpenId;
	};

	class TemplateMessage2 {
		int Id;
		int BookId;
		String Title;
		int MembersCount;
		String FormId;
		String OpenId;
	};

	class TemplateMessage21 {
		int Id;
		int uId;
		int NoteId;
		int ReplyId;
		String Title;
		String FormId;
		String Desc;
		String RemindTime;
		String OpenId;
	};

	class TemplateMessage3 {
		int Id;
		int uId;
		int RecipeId;
		String Title;
		int Desc;
		String RecipeDateStr;
		String OpenId;
	}

	private static Logger log = LoggerFactory.getLogger(SendMsgThread.class);

	public void run() {

		ArrayList<TemplateMessage> activityTmList = new ArrayList<TemplateMessage>();
		ArrayList<TemplateMessage2> bjTmList = new ArrayList<TemplateMessage2>();
		ArrayList<TemplateMessage21> rjTmList = new ArrayList<TemplateMessage21>();
		ArrayList<TemplateMessage21> rjTmList2 = new ArrayList<TemplateMessage21>();
		ArrayList<TemplateMessage3> xqTmList = new ArrayList<TemplateMessage3>();

		int cnt = 0;

		// 随机停2000毫秒-10000毫秒
		Random rand = new Random();
		try {
			Thread.sleep(rand.nextInt(8000) + 2000);
		} catch (Exception e) {
		}

		while (true) {

			try {

				AccessToken accessToken = StartOnLoad.tokenThread1.accessToken;

				if (accessToken == null) {
					Thread.sleep(5 * 1000);
					continue;
				}

				// 随机停200毫秒
				Random rand2 = new Random();
				try {
					Thread.sleep(rand2.nextInt(200) + 100);
				} catch (Exception e) {
				}

//				String token = StartOnLoad.tokenThread1.accessToken.token;
////				JSONObject result;
//				List<QmMessageSend> notice1 = messageDao.findByType(1);
//				String temlateId1 = StartOnLoad.TEMPLATE_ID;
//				String url1 = "http://232a9x6385.51mypc.cn/#/notice/show/";
//				this.message(notice1, token, temlateId1, url1);
//				
//				List<QmMessageSend> notice2 = messageDao.findByType(2);
//				String temlateId2 = StartOnLoad.TEMPLATE_ID;
//				String url2 = "http://232a9x6385.51mypc.cn/#/fresh/show/";
//				this.message(notice2, token, temlateId2, url2);
//				
//				List<QmMessageSend> notice3 = messageDao.findByType(3);
//				String temlateId3 = StartOnLoad.TEMPLATE_ID;
//				String url3 = "";
//				this.message(notice3, token, temlateId3, url3);
//				List<QmMessageSend> notice4 = messageDao.findByType(4);
//				List<QmMessageSend> notice5 = messageDao.findByType(5);

			} catch (Exception e) {
				e.printStackTrace();
				log.error("SendMsgThread-run:"+e.getMessage());
			}

		}
	}

	public void message(List<QmMessageSend> message, String token, String temlateId, String url) {
		try {
			if (CollectionUtils.isNotEmpty(message)) {
				for (QmMessageSend qms : message) {
					JSONObject data = new JSONObject();
					JSONObject value = new JSONObject();
					value.put("value", qms.getTitle());
					value.put("color", "#FFA500");
					data.put("keyword1", value);

					value.put("value", qms.getTextContent());
					value.put("color", "#000000");
					data.put("keyword2", value);

					value.put("value", qms.getPostTime());
					value.put("color", "#000000");
					data.put("keyword3", value);
					WeiXinUtil.sendTemplateMsg3(token, qms.getOpenId(), url + qms.getMessageId(), temlateId, data);
					qms.setResult(1);
				}
				messageDao.save(message);
				Thread.sleep(5 * 1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("SendMsgThread-run:"+e.getMessage());
		}
	}

}
