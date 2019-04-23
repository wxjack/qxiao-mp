package com.qxiao.wx.user.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.componse.GetIdentityService;
import com.qxiao.wx.componse.UserInfo;
import com.qxiao.wx.fresh.jpa.dao.QmFreshInfoDao;
import com.qxiao.wx.fresh.jpa.dao.QmFreshSenderDao;
import com.qxiao.wx.fresh.jpa.entity.QmFreshSender;
import com.qxiao.wx.homework.jpa.dao.QmHomeworkInfoDao;
import com.qxiao.wx.homework.jpa.dao.QmHomeworkSenderDao;
import com.qxiao.wx.homework.jpa.entity.QmHomeworkInfo;
import com.qxiao.wx.homework.jpa.entity.QmHomeworkSender;
import com.qxiao.wx.notice.jpa.dao.QmNoticeInfoDao;
import com.qxiao.wx.notice.jpa.dao.QmNoticeSenderDao;
import com.qxiao.wx.notice.jpa.entity.QmNoticeInfo;
import com.qxiao.wx.notice.jpa.entity.QmNoticeSender;
import com.qxiao.wx.openedition.jpa.dao.QmStrikStarDao;
import com.qxiao.wx.openedition.jpa.service.IQmStrikStarService;
import com.qxiao.wx.recipe.jpa.dao.QmRecipeInfoDao;
import com.qxiao.wx.recipe.jpa.entity.QmRecipeInfo;
import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.dao.QmClockInfoDao;
import com.qxiao.wx.user.jpa.dao.QmMessageSendDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolClassDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.entity.QmMessageSend;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.qxiao.wx.user.jpa.service.PunchService;
import com.talkweb.weixin.main.StartOnLoad;
import com.talkweb.weixin.pojo.AccessToken;
import com.talkweb.weixin.util.WeiXinUtil;

import net.sf.json.JSONObject;

/**
 * 基于spring的定时器任务
 * 
 * @author xiaojiao
 *
 * @创建时间：2018年11月8日
 */
@Component
public class MyTask {

	private Logger log = Logger.getLogger(MyTask.class);

	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private String url1 = StartOnLoad.GOAL_URL + "#/notice/show?noticeId=";
	private String url2 = StartOnLoad.GOAL_URL + "#/fresh/show?freshId=";
	private String url5 = StartOnLoad.GOAL_URL + "#/homework/show?homeId=";
	private String punchUrl = StartOnLoad.GOAL_URL + "#/shuttle/show?studentId=";
	private String recipe = StartOnLoad.GOAL_URL + "#/recipe/show?recipeId=";
	private static int count = 0;

	@Autowired
	PunchService punService;
	@Autowired
	private QmNoticeInfoDao infoDao;
	@Autowired
	private QmMessageSendDao messageDao;
	@Autowired
	QmPlaySchoolClassDao classDao;
	@Autowired
	QmAccountDao accountDao;
	@Autowired
	private QmNoticeInfoDao noticeDao;
	@Autowired
	private QmHomeworkInfoDao homeDao;
	@Autowired
	QmPlaySchoolInfoDao schoolInfoDao;
	@Autowired
	QmClassStudentDao classStudentDao;
	@Autowired
	GetIdentityService idService;
	@Autowired
	QmStudentDao studentDao;
	@Autowired
	QmFreshInfoDao freshDao;
	@Autowired
	QmNoticeSenderDao sendDao;
	@Autowired
	QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	QmHomeworkSenderDao HsendDao;
	@Autowired
	QmFreshSenderDao FsendDao;
	@Autowired
	QmClockInfoDao clockDao;
	@Autowired
	QmRecipeInfoDao RInfoDao;
	@Autowired
	QmPatriarchDao pDao;
	
	/**
	 * 每分钟执行一次
	 */
	@Scheduled(cron = "0 0/1 * * * ? ")
	public void clockSendMessage() {
		Date time = null;
		try {
			String fm = format.format(new Date());
			time = format.parse(fm);
			List<QmNoticeInfo> notices = infoDao.findByClockTime(time);
			if (CollectionUtils.isNotEmpty(notices)) {
				for (QmNoticeInfo info : notices) {
					info.setMessageSend(1);
				}
				infoDao.save(notices);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info("通知推送失败：" + e.getMessage() + ", 时间：" + time);
		}
	}
	
	@Scheduled(cron = "0/20 * * * * ?")
	public void sendTempLateMessage() {

		if (count == 0) {
			this.executeThread();
		}
		if (count == Integer.MAX_VALUE) {
			count = 1;
		}
		count++;
	}

	/**
	 * 
	 */
	@Scheduled(cron = "0 0 21 ? * *")
	public void saveClockStat() {

		try {
			String format = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
			punService.saveClockStat(format);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("定时器异常：" + e.getMessage());
		}
	}

	public void executeThread() {
		SendMsg1 msg = new SendMsg1();
		msg.start();
	}

	public class SendMsg1 extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					AccessToken accessToken = StartOnLoad.tokenThread1.accessToken;
					if (accessToken == null) {
						Thread.sleep(1 * 1000);
						continue;
					}

					// 随机停2000毫秒-10000毫秒
					Random rand = new Random();
					Thread.sleep(rand.nextInt(8000) + 2000);

					// 公告
					List<QmMessageSend> notice = messageDao.findByType(1);
					if (CollectionUtils.isNotEmpty(notice)) {
						this.message2(notice, accessToken.token, StartOnLoad.NOTICE_ID, url1);
					}
					// 速报
					List<QmMessageSend> fresh = messageDao.findByType(2);
					if (CollectionUtils.isNotEmpty(fresh)) {
						this.message6(fresh, accessToken.token, StartOnLoad.FRESH_ID, url2);
					}

					List<QmMessageSend> recip = messageDao.findByType(3);
					if (CollectionUtils.isNotEmpty(recip)) {
						this.message3(recip, accessToken.token, StartOnLoad.RECIPE_ID, recipe);
					}
					List<QmMessageSend> punch = messageDao.findByType(4);
					if (CollectionUtils.isNotEmpty(punch)) {
						this.message4(punch, accessToken.token, StartOnLoad.PUNCH_ID, punchUrl);
					}

					List<QmMessageSend> homework = messageDao.findByType(5);
					if (CollectionUtils.isNotEmpty(homework)) {
						this.message5(homework, accessToken.token, StartOnLoad.HOMEWORK_ID, url5);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		// 公告
		private void message2(List<QmMessageSend> messages, String token, String tEMPLATE_ID, String url2)
				throws Exception {
			try {
				for (QmMessageSend qms : messages) {
					if(StringUtils.isBlank(qms.getOpenId())) {
						qms.setResult(1);
						messageDao.save(messages);
						continue;	
					}
					QmPlaySchoolTeacher schoolTeacher = teacherDao.findByOpenId(qms.getOpenId());
					QmNoticeInfo findOne = noticeDao.findOne(qms.getMessageId());
					UserInfo info = idService.getIdentity(findOne.getOpenId());
					if (schoolTeacher == null) {// 推送给家长
						List<QmNoticeSender> noticeSender = sendDao.findByNoticeIdAndOpenId(qms.getMessageId(),
								qms.getOpenId());
						for (QmNoticeSender qns : noticeSender) {
							QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByNoticeId(findOne.getNoticeId());
							if (schoolInfo == null) {
								schoolInfo = schoolInfoDao.queryByTeacherOpenId(findOne.getNoticeId());
							}
							List<QmStudent> list = studentDao.findByOpenIdAndClassId(qms.getOpenId(),
									qns.getSerderId());
							QmPlaySchoolClass findByClassId = classDao.findByClassId(qns.getSerderId());
							for (QmStudent qs : list) {
								JSONObject data = new JSONObject();
								JSONObject value = new JSONObject();

								value.put("value", schoolInfo.getSchoolName());
								value.put("color", "#FFA500");
								data.put("keyword1", value);

								value.put("value", info.getUsername());
								value.put("color", "#000000");
								data.put("keyword2", value);

								value.put("value",
										new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qms.getPostTime()));
								value.put("color", "#000000");
								data.put("keyword3", value);
								String parseToHtmlDecimal = qms.getTitle();
								value.put("value", parseToHtmlDecimal);
								value.put("color", "#000000");
								data.put("keyword4", value);

								value.put("value",
										"接收人：" + qs.getStudentName() + "(" + findByClassId.getClassName() + ")");
								value.put("color", "#FFA500");
								data.put("remark", value);
								if (CollectionUtils.isNotEmpty(list)) {
									String url = url2 + qms.getMessageId() + "&classId=" + findByClassId.getClassId()
											+ "&openId=" + qms.getOpenId() + "&roleType=" + 3 + "&studentId="
											+ qs.getStudentId();
									if (qms.getType() == 1) {
										QmNoticeInfo noticeInfo = noticeDao.findOne(qms.getMessageId());
										url += "&needConfirm=" + noticeInfo.getNeedConfirm();
									}
									WeiXinUtil.sendTemplateMsg3(token, qms.getOpenId(), url, tEMPLATE_ID, data);
								}
							}
						}
						qms.setResult(1);
						messageDao.save(messages);
					} else {
						List<QmNoticeSender> noticeSender = sendDao.findByNoticeIdAndOpenId(qms.getMessageId(),
								qms.getOpenId());
						List<QmPlaySchoolClass> teacher = classDao.findWithTeacher(qms.getOpenId());
						QmAccount account = accountDao.findByOpenId(qms.getOpenId());
						UserInfo userInfo = idService.getIdentity(qms.getOpenId());
						if (teacher.size() > 0 && account != null) {
							JSONObject data = new JSONObject();
							JSONObject value = new JSONObject();

							value.put("value", userInfo.getSchoolName());
							value.put("color", "#FFA500");
							data.put("keyword1", value);

							value.put("value", info.getUsername());
							value.put("color", "#000000");
							data.put("keyword2", value);

							value.put("value", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qms.getPostTime()));
							value.put("color", "#000000");
							data.put("keyword3", value);

							value.put("value", qms.getTitle());
							value.put("color", "#000000");
							data.put("keyword4", value);
							String url3 = null;
							if (account.getPersonType() == 0 || account.getPersonType() == 1) {
								url3 = url2 + qms.getMessageId() + "&classId=" + 0 + "&openId=" + qms.getOpenId()
										+ "&roleType=" + 1 + "&studentId=0";
							}
							Long classId = 0L;
							if (CollectionUtils.isEmpty(noticeSender)) {
								classId = teacher.get(0).getClassId();
							} else {
								classId = noticeSender.get(0).getSerderId();
							}
							if (account.getPersonType() == 2) {
								url3 = url2 + qms.getMessageId() + "&classId=" + classId + "&openId=" + qms.getOpenId()
										+ "&roleType=2&studentId=0";
							}
							WeiXinUtil.sendTemplateMsg3(token, qms.getOpenId(), url3, tEMPLATE_ID, data);
						}
					}

					qms.setResult(1);
					messageDao.save(messages);
					Thread.sleep(2 * 1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("SendMsgThread-run:" + e.getMessage());
			}
		}

		// 营养食谱
		private void message3(List<QmMessageSend> messages, String token, String tEMPLATE_ID, String url2)
				throws Exception {
			try {
				for (QmMessageSend qms : messages) {
					if(StringUtils.isBlank(qms.getOpenId())) {
						qms.setResult(1);
						messageDao.save(messages);
						continue;	
					}
					QmPlaySchoolTeacher schoolTeacher = teacherDao.findByOpenId(qms.getOpenId());
					if (schoolTeacher == null) {
						QmRecipeInfo info = RInfoDao.findByRecipeId(qms.getMessageId());
						QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByOpenId(info.getOpenId());
						if (schoolInfo == null) {
							schoolInfo = schoolInfoDao.queryByRecipe(qms.getMessageId());
						}
						List<QmPlaySchoolClass> findBySchoolId = classDao.findBySchoolId(schoolInfo.getSchoolId());

						List<QmPatriarch> list = pDao.findByClassId(findBySchoolId.get(0).getClassId());
						List<QmStudent> lqs = studentDao.findByOpenIdAndClassId(qms.getOpenId(),
								findBySchoolId.get(0).getClassId());
						if (CollectionUtils.isNotEmpty(list) && CollectionUtils.isNotEmpty(lqs)) {
							JSONObject data = new JSONObject();
							JSONObject value = new JSONObject();

							value.put("value", "营养食谱");
							value.put("color", "#FFA500");
							data.put("first", value);

							value.put("value", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qms.getPostTime()));
							value.put("color", "#FFA500");
							data.put("keyword1", value);

							value.put("value", qms.getTitle());
							value.put("color", "#000000");
							data.put("keyword2", value);
							String url = url2 + qms.getMessageId() + "&classId=" + findBySchoolId.get(0).getClassId()
									+ "&openId=" + qms.getOpenId() + "&roleType=" + 3 + "&studentId="
									+ lqs.get(0).getStudentId();
							WeiXinUtil.sendTemplateMsg3(token, qms.getOpenId(), url, tEMPLATE_ID, data);
						}
						qms.setResult(1);
					} else {
						List<QmPlaySchoolClass> teacher = classDao.findWithTeacher(qms.getOpenId());
						QmAccount account = accountDao.findByOpenId(qms.getOpenId());
						if (teacher.size() > 0 && account != null) {
							JSONObject data = new JSONObject();
							JSONObject value = new JSONObject();

							value.put("value", "营养食谱");
							value.put("color", "#FFA500");
							data.put("first", value);

							value.put("value", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qms.getPostTime()));
							value.put("color", "#FFA500");
							data.put("keyword1", value);

							value.put("value", qms.getTitle());
							value.put("color", "#000000");
							data.put("keyword2", value);
							String url3 = null;
							if (account.getPersonType() == 0 || account.getPersonType() == 1) {
								url3 = url2 + qms.getMessageId() + "&classId=" + teacher.get(0).getClassId()
										+ "&openId=" + qms.getOpenId() + "&roleType=" + 1 + "&studentId=0";
							}
							Long classId = teacher.get(0).getClassId();
							if (account.getPersonType() == 2) {
								url3 = url2 + qms.getMessageId() + "&classId=" + classId + "&openId=" + qms.getOpenId()
										+ "&roleType=" + 2 + "&studentId=0";
							}
							WeiXinUtil.sendTemplateMsg3(token, qms.getOpenId(), url3, tEMPLATE_ID, data);
						}
						qms.setResult(1);
						messageDao.save(messages);
					}

					Thread.sleep(2 * 1000);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("SendMsgThread-run:" + e.getMessage());
			}
		}

		// 作业
		private void message5(List<QmMessageSend> messages, String token, String tEMPLATE_ID, String url2)
				throws Exception {
			try {
				for (QmMessageSend qms : messages) {
					if(StringUtils.isBlank(qms.getOpenId())) {
						qms.setResult(1);
						messageDao.save(messages);
						continue;	
					}
					List<QmHomeworkSender> homeSender = HsendDao.findByHomeIdAndOpenId(qms.getMessageId(),
							qms.getOpenId());
					if (CollectionUtils.isNotEmpty(homeSender)) {
						for (QmHomeworkSender qhs : homeSender) {
							List<QmStudent> list = studentDao.findByOpenIdAndClassId(qms.getOpenId(), qhs.getClassId());
							if (CollectionUtils.isNotEmpty(list)) {
								for (QmStudent qs : list) {
									QmPlaySchoolClass schoolClass = classDao.findByClassId(qhs.getClassId());
									JSONObject data = new JSONObject();
									JSONObject value = new JSONObject();

									value.put("value", schoolClass.getClassName());
									value.put("color", "#FFA500");
									data.put("keyword1", value);

									value.put("value", qms.getTitle());
									value.put("color", "#000000");
									data.put("keyword2", value);

									value.put("value",
											"接收人：" + qs.getStudentName() + "(" + schoolClass.getClassName() + ")");
									value.put("color", "#FFA500");
									data.put("remark", value);
									String url = url2 + qms.getMessageId() + "&classId=" + qhs.getClassId() + "&openId="
											+ qms.getOpenId() + "&roleType=" + 3 + "&studentId=" + qs.getStudentId();
									if (qms.getType() == 5) {
										QmHomeworkInfo homework = homeDao.findOne(qms.getMessageId());
										url += "&needConfirm=" + homework.getNeedConfirm();
									}
									WeiXinUtil.sendTemplateMsg3(token, qms.getOpenId(), url, tEMPLATE_ID, data);
								}
							}
						}
					}
					qms.setResult(1);
					messageDao.save(messages);
				}
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("SendMsgThread-run:" + e.getMessage());
			}
		}

		// 考勤打卡
		private void message4(List<QmMessageSend> messages, String token, String tEMPLATE_ID, String url2) {
			try {
				for (QmMessageSend qms : messages) {
					if(StringUtils.isBlank(qms.getOpenId())) {
						qms.setResult(1);
						messageDao.save(messages);
						continue;	
					}
					QmStudent student = studentDao.findByClockId(qms.getMessageId(), qms.getOpenId());
					if (student == null) {
						student = studentDao.findWithIbeaconId(qms.getMessageId(), qms.getOpenId());
					}
					JSONObject data = new JSONObject();
					JSONObject value = new JSONObject();

					value.put("value", "考勤签到");
					value.put("color", "#FFA500");
					data.put("first", value);

					value.put("value", student.getStudentName());
					value.put("color", "#FFA500");
					data.put("keyword1", value);

					value.put("value", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qms.getPostTime()));
					value.put("color", "#000000");
					data.put("keyword2", value);

					value.put("value", "打卡");
					value.put("color", "#000000");
					data.put("keyword3", value);

					value.put("value", "如果时间不正常，请及时联系老师！");
					value.put("color", "#000000");
					data.put("remark", value);

					WeiXinUtil.sendTemplateMsg3(token, qms.getOpenId(), "", tEMPLATE_ID, data);

					qms.setResult(1);
					messageDao.save(messages);
				}
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("SendMsgThread-run:" + e.getMessage());
			}
		}

		// 新鲜速报
		private void message6(List<QmMessageSend> messages, String token, String tEMPLATE_ID, String url2)
				throws Exception {
			try {
				for (QmMessageSend qms : messages) {
					if(StringUtils.isBlank(qms.getOpenId())) {
						qms.setResult(1);
						messageDao.save(messages);
						continue;	
					}
					List<QmFreshSender> noticeSender = FsendDao.findByFreshIdAndOpenId(qms.getMessageId(),
							qms.getOpenId());
					if (CollectionUtils.isNotEmpty(noticeSender)) {
						for (QmFreshSender qfs : noticeSender) {
							List<QmStudent> list = studentDao.findByOpenIdAndClassId(qms.getOpenId(), qfs.getClassId());
							if (CollectionUtils.isNotEmpty(list)) {
								for (QmStudent qs : list) {
									QmPlaySchoolClass schoolClass = classDao.findByClassId(qfs.getClassId());
									QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByStudentId(qs.getStudentId());
									JSONObject data = new JSONObject();
									JSONObject value = new JSONObject();

									value.put("value", "新鲜速报");
									value.put("color", "#FFA500");
									data.put("first", value);

									value.put("value", schoolInfo.getSchoolName());
									value.put("color", "#FFA500");
									data.put("keyword1", value);

									value.put("value", schoolInfo.getLeaderName());
									value.put("color", "#000000");
									data.put("keyword2", value);

									value.put("value",
											new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qms.getPostTime()));
									value.put("color", "#000000");
									data.put("keyword3", value);

									value.put("value", qms.getTitle());
									value.put("color", "#000000");
									data.put("keyword4", value);

									value.put("value",
											"接收人：" + qs.getStudentName() + "(" + schoolClass.getClassName() + ")");
									value.put("color", "#FFA500");
									data.put("remark", value);

									String url = url2 + qms.getMessageId() + "&classId=" + qfs.getClassId() + "&openId="
											+ qms.getOpenId() + "&roleType=" + 3 + "&studentId=" + qs.getStudentId();
									WeiXinUtil.sendTemplateMsg3(token, qms.getOpenId(), url, tEMPLATE_ID, data);
								}
							}
						}
					}
					qms.setResult(1);
					messageDao.save(messages);
				}
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("SendMsgThread-run:" + e.getMessage());
			}
		}
	}
}
