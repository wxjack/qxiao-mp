package com.qxiao.wx.user.jpa.service.impl;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.community.jpa.dao.QmClassTeacherDao;
import com.qxiao.wx.community.jpa.entity.QmClassStudent;
import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.dao.QmLeaderInitDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolClassDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.dao.QmTeleVerifyCodeDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.entity.QmLeaderInit;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.qxiao.wx.user.jpa.entity.QmTeleVerifyCode;
import com.qxiao.wx.user.jpa.service.RegisterUserService;
import com.qxiao.wx.user.jpa.service.SchoolClassService;
import com.qxiao.wx.user.util.VeriftCode;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.talkweb.weixin.main.StartOnLoad;
import com.talkweb.weixin.pojo.AccessToken;
import com.talkweb.weixin.util.WeiXinUtil;
import com.vdurmont.emoji.EmojiParser;

import net.sf.json.JSONObject;

@Service
public class RegisterUserServiceImpl extends AbstractJdbcService<QmAccount> implements RegisterUserService {

	@Autowired
	QmAccountDao accountDao;
	@Autowired
	QmLeaderInitDao leaderInitDao;
	@Autowired
	QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	QmStudentDao studentDao;
	@Autowired
	QmPatriarchDao patriarchDao;
	@Autowired
	QmTeleVerifyCodeDao codeDao;
	@Autowired
	QmPlaySchoolInfoDao schoolInfoDao;
	@Autowired
	QmClassTeacherDao classTeacherDao;
	@Autowired
	QmPlaySchoolClassDao schoolClassDao;
	@Autowired
	SchoolClassService classService;
	@Autowired
	QmPlaySchoolClassDao classDao;
	@Autowired
	QmClassStudentDao classStudentDao;

	public void getOpenId1(HttpServletRequest request, HttpServletResponse response, String type) {
		// 为了获取openId,必先获取到CODE
		String openId = "";
		String code = request.getParameter("code");
		if ((code != null) && (!code.equals(""))) {
			// 此次去获取OpenId
			JSONObject jsonObject = WeiXinUtil.getUserInfoOpenId(StartOnLoad.TOKEN_APPID, StartOnLoad.TOKEN_SECRET,
					code);
			try {
				if (jsonObject != null) {
					openId = jsonObject.getString("openid");
				}
				AccessToken accessToken = StartOnLoad.tokenThread1.accessToken;
				JSONObject object = WeiXinUtil.getWxUserInfo(accessToken.getToken(), openId);
				QmAccount findByOpenId = accountDao.findByOpenId(openId);
				if (findByOpenId == null && object != null) {
					QmAccount qm = new QmAccount();
					qm.setLocation(
							object.getString("country") + object.getString("province") + object.getString("city"));
					qm.setOpenId(openId);
					qm.setPhoto(object.getString("headimgurl"));
					qm.setSex(object.getInt("sex"));
					qm.setuName(EmojiParser.parseToAliases(object.getString("nickname")));
					qm.setPersonType(9);
					qm.setPostTime(new Date());
					findByOpenId = accountDao.save(qm);
				}
				if (findByOpenId.getPersonType() == 9) {
					List<QmStudent> list = studentDao.findWithOpenId(openId);
					long openStudentId = 0;
					String openStudentName = "";
					if (CollectionUtils.isNotEmpty(list)) {
						openStudentId = list.get(0).getStudentId();
						openStudentName=list.get(0).getStudentName();
					}
					if (type.trim().equals("1")) {
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/single?openId=" + openId + "&photo="
								+ findByOpenId.getPhoto()+"&roleType="+9 + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
					if (type.trim().equals("2")) {
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/wisdom?openId=" + openId + "&photo="
								+ findByOpenId.getPhoto() +"&roleType="+9+ "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
					if (type.trim().equals("3")) {
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/login?openId=" + openId + "&photo="
								+ findByOpenId.getPhoto()+"&roleType="+9 + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}

				}
				String classId = null;
				String className = null;
				if (findByOpenId.getPersonType() == 0) {
					QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByOpenId(findByOpenId.getOpenId());
					List<Map<String, Object>> queryClassBySchool = classService
							.queryClassBySchool(findByOpenId.getRoleId());
					if (queryClassBySchool.size() > 0) {
						classId = queryClassBySchool.get(0).get("classId").toString();
						className = queryClassBySchool.get(0).get("className").toString();
					}
					List<QmStudent> list = studentDao.findWithOpenId(openId);
					long openStudentId = 0;
					String openStudentName = "";
					if (CollectionUtils.isNotEmpty(list)) {
						openStudentId = list.get(0).getStudentId();
						openStudentName=list.get(0).getStudentName();
					}
					if (type.trim().equals("1")) {// single 小Q表现
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/single?openId=" + openId + "&roleType=" + 1
								+ "&studentId=" + 0 + "&photo=" + findByOpenId.getPhoto() + "&id="
								+ findByOpenId.getRoleId() + "&classId=" + classId + "&className="
								+ URLEncoder.encode(className, "UTF-8") + "&type=" + schoolInfo.getType() + "&isOpen="
								+ true + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
					if (type.trim().equals("2")) {// wisdom 小Q智慧
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/wisdom?openId=" + openId + "&roleType=" + 1
								+ "&studentId=" + 0 + "&photo=" + findByOpenId.getPhoto() + "&id="
								+ findByOpenId.getRoleId() + "&classId=" + classId + "&className="
								+ URLEncoder.encode(className, "UTF-8") + "&type=" + schoolInfo.getType() + "&isOpen="
								+ true + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
					if (type.trim().equals("3")) {
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/home?openId=" + openId + "&roleType=" + 1
								+ "&studentId=" + 0 + "&photo=" + findByOpenId.getPhoto() + "&id="
								+ findByOpenId.getRoleId() + "&classId=" + classId + "&className="
								+ URLEncoder.encode(className, "UTF-8") + "&type=" + schoolInfo.getType() + "&isOpen="
								+ true + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
				}
				if (findByOpenId.getPersonType() == 1) {
					Long schoolId = teacherDao.findSchoolId(openId);
					QmPlaySchoolInfo schoolInfo = schoolInfoDao.findBySchoolId(schoolId);
					List<Map<String, Object>> queryClassBySchool = classService.queryClassBySchool(schoolId);
					List<QmStudent> list = studentDao.findWithOpenId(openId);
					long openStudentId = 0;
					String openStudentName = "";
					if (CollectionUtils.isNotEmpty(list)) {
						openStudentId = list.get(0).getStudentId();
						openStudentName=list.get(0).getStudentName();
					}
					if (queryClassBySchool.size() > 0) {
						classId = queryClassBySchool.get(0).get("classId").toString();
						className = queryClassBySchool.get(0).get("className").toString();
					}
					if (type.trim().equals("1")) {
						response.sendRedirect(
								StartOnLoad.GOAL_URL + "#/single?openId=" + openId + "&roleType=" + 4 + "&studentId=" + 0
										+ "&photo=" + findByOpenId.getPhoto() + "&id=" + schoolId + "&classId="
										+ classId + "&className=" + URLEncoder.encode(className, "UTF-8") + "&type="
										+ schoolInfo.getType() + "&isOpen=" + true + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
					if (type.trim().equals("2")) {
						response.sendRedirect(
								StartOnLoad.GOAL_URL + "#/wisdom?openId=" + openId + "&roleType=" + 4 + "&studentId="
										+ 0 + "&photo=" + findByOpenId.getPhoto() + "&id=" + schoolId + "&classId="
										+ classId + "&className=" + URLEncoder.encode(className, "UTF-8") + "&type="
										+ schoolInfo.getType() + "&isOpen=" + true + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
					if (type.trim().equals("3")) {
						response.sendRedirect(
								StartOnLoad.GOAL_URL + "#/home?openId=" + openId + "&roleType=" + 4 + "&studentId=" + 0
										+ "&photo=" + findByOpenId.getPhoto() + "&id=" + schoolId + "&classId="
										+ classId + "&className=" + URLEncoder.encode(className, "UTF-8") + "&type="
										+ schoolInfo.getType() + "&isOpen=" + true + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
				}
				if (findByOpenId.getPersonType() == 2) {
					List<QmPlaySchoolClass> teacher = classDao.findWithTeacher(openId);
					QmPlaySchoolInfo schoolInfo = new QmPlaySchoolInfo();
					List<QmStudent> list = studentDao.findWithOpenId(openId);
					long openStudentId = 0;
					String openStudentName = "";
					if (CollectionUtils.isNotEmpty(list)) {
						openStudentId = list.get(0).getStudentId();
						openStudentName=list.get(0).getStudentName();
					}

					if (teacher.size() > 0) {
						classId = teacher.get(0).getClassId().toString();
						schoolInfo = schoolInfoDao.findBySchoolId(teacher.get(0).getSchoolId().longValue());
						className = teacher.get(0).getClassName().toString();
					}
					if (type.trim().equals("1")) {
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/single?openId=" + openId + "&roleType="
								+ findByOpenId.getPersonType() + "&photo=" + findByOpenId.getPhoto() + "&id="
								+ findByOpenId.getRoleId() + "&classId=" + classId + "&className="
								+ URLEncoder.encode(className, "UTF-8") + "&type=" + schoolInfo.getType() + "&isOpen="
								+ true + "&studentId=" + 0 + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
					if (type.trim().equals("2")) {
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/wisdom?openId=" + openId + "&roleType="
								+ findByOpenId.getPersonType() + "&photo=" + findByOpenId.getPhoto() + "&id="
								+ findByOpenId.getRoleId() + "&classId=" + classId + "&className="
								+ URLEncoder.encode(className, "UTF-8") + "&type=" + schoolInfo.getType() + "&isOpen="
								+ true + "&studentId=" + 0 + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
					if (type.trim().equals("3")) {
						response.sendRedirect(StartOnLoad.GOAL_URL + "#/home?openId=" + openId + "&roleType="
								+ findByOpenId.getPersonType() + "&photo=" + findByOpenId.getPhoto() + "&id="
								+ findByOpenId.getRoleId() + "&classId=" + classId + "&className="
								+ URLEncoder.encode(className, "UTF-8") + "&type=" + schoolInfo.getType() + "&isOpen="
								+ true + "&studentId=" + 0 + "&openStudentId=" + openStudentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
					}
				}
				if (findByOpenId.getPersonType() == 3) {
					List<QmClassStudent> classStudent = classStudentDao.findClassId(openId);
					if (CollectionUtils.isNotEmpty(classStudent)) {
						QmPlaySchoolClass schoolClass = classDao.findByClassId(classStudent.get(0).getClassId());
						Long studentId = classStudent.get(0).getStudentId();
						List<QmStudent> list = studentDao.findWithOpenId(openId);
						String openStudentName = "";
						if (CollectionUtils.isNotEmpty(list)) {
							openStudentName=list.get(0).getStudentName();
						}
						QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByStudentId(classStudent.get(0).getStudentId());
						Boolean boll;
						if (schoolInfo.getIsOpen() == 0) {
							boll = true;
						} else {
							boll = false;
						}
						if (type.trim().equals("1")) {
							response.sendRedirect(StartOnLoad.GOAL_URL + "#/single?openId=" + openId + "&roleType="
									+ findByOpenId.getPersonType() + "&photo=" + findByOpenId.getPhoto() + "&id="
									+ findByOpenId.getRoleId() + "&classId=" + schoolClass.getClassId() + "&className="
									+ URLEncoder.encode(schoolClass.getClassName(), "UTF-8") + "&type="
									+ schoolInfo.getType() + "&isOpen=" + boll + "&studentId=" + studentId
									+ "&openStudentId=" + studentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
						}
						if (type.trim().equals("2")) {
							response.sendRedirect(StartOnLoad.GOAL_URL + "#/wisdom?openId=" + openId + "&roleType="
									+ findByOpenId.getPersonType() + "&photo=" + findByOpenId.getPhoto() + "&id="
									+ findByOpenId.getRoleId() + "&classId=" + schoolClass.getClassId() + "&className="
									+ URLEncoder.encode(schoolClass.getClassName(), "UTF-8") + "&type="
									+ schoolInfo.getType() + "&isOpen=" + boll + "&studentId=" + studentId
									+ "&openStudentId=" + studentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
						}
						if (type.trim().equals("3")) {
							response.sendRedirect(StartOnLoad.GOAL_URL + "#/home?openId=" + openId + "&roleType="
									+ findByOpenId.getPersonType() + "&photo=" + findByOpenId.getPhoto() + "&id="
									+ findByOpenId.getRoleId() + "&classId=" + schoolClass.getClassId() + "&className="
									+ URLEncoder.encode(schoolClass.getClassName(), "UTF-8") + "&type="
									+ schoolInfo.getType() + "&isOpen=" + boll + "&studentId=" + studentId
									+ "&openStudentId=" + studentId+"&openStudentName="+URLEncoder.encode(openStudentName, "UTF-8"));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void registerUser(HttpServletRequest request, HttpServletResponse response, String type) {
		// 获取code
		WeiXinUtil.getFuwuCode(response, StartOnLoad.TOKEN_APPID,
				StartOnLoad.GOAL_LOCAL_URL + "action/mod-xiaojiao/manage/getOpenId.do?type=" + type);
	}

	public Map<String, Object> userTeleLogin(String tele, String verifyCode) throws Exception {
		QmLeaderInit leader = leaderInitDao.findByTel(tele);
		List<QmTeleVerifyCode> findList = codeDao.findByTel(tele);
		Map<String, Object> map = new HashMap<>();
		if (!findList.get(0).getVeriftCode().equals(verifyCode)) {
			throw new Exception("验证码错误");// 验证码错误
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(findList.get(0).getPostTime());
		long time = sdf.parse(format).getTime();// 生成时间
		long millis = Calendar.getInstance().getTimeInMillis();
		if (millis - time > 300000) {
			throw new Exception("验证码过期");// 验证码过期
		}

		if (leader != null && verifyCode.trim().equals(findList.get(0).getVeriftCode())) {
			QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByTel(tele);
			if (schoolInfo != null && schoolInfo.getOpenId() == null) {
				map.put("roleType", 4);
				map.put("openId", "");
				map.put("schoolId", 0);
				map.put("tel", tele);
				return map;// 进入注册幼儿园信息界面
			}
			map.put("roleType", 1);
			map.put("openId", schoolInfo.getOpenId());
			map.put("schoolId", schoolInfo.getSchoolId());
			map.put("tel", tele);
			return map;// 园长-直接首页
		}
		QmPlaySchoolTeacher teacher = teacherDao.findByTelAndStatus(tele, 0);
		if (teacher != null && verifyCode.trim().equals(findList.get(0).getVeriftCode())) {

			if (teacher.getOpenId() == null || teacher.getOpenId().equals("")) {
				map.put("roleType", 5);
				map.put("openId", "");
				map.put("teacherId", teacher.getTeacherId());
				map.put("tel", tele);
				return map;
			}
			if (teacher.getType() == 0) {
				QmPlaySchoolInfo info = schoolInfoDao.findWithTeacher(teacher.getOpenId());
				map.put("roleType", 1);
				map.put("openId", teacher.getOpenId());
				map.put("schoolId", info.getSchoolId());
				map.put("tel", tele);
				return map;
			}
			map.put("roleType", 2);
			map.put("openId", teacher.getOpenId());
			map.put("teacherId", teacher.getTeacherId());
			map.put("tel", tele);
			return map;// 老师
		}
		QmPatriarch patroarch = patriarchDao.findByTelAndIsDel(tele, 0);
		if (patroarch != null && verifyCode.trim().equals(findList.get(0).getVeriftCode())) {
			if (patroarch.getOpenId() == null || patroarch.getOpenId().equals("")) {
				map.put("roleType", 6);
				map.put("openId", "");
				map.put("patroarchId", patroarch.getId());
				map.put("tel", tele);
				return map;
			}
			map.put("roleType", 3);
			map.put("openId", patroarch.getOpenId());
			map.put("patroarchId", patroarch.getId());
			map.put("tel", tele);
			return map;// 家长
		}
		map.put("roleType", 0);
		map.put("openId", "");
		map.put("id", 0);
		map.put("tel", tele);
		return map;// 未关联

	}

	public String code(String tel) throws Exception {
		QmPatriarch qmPatriarch = patriarchDao.findByOne(tel);
		QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByTel(tel);
		QmPlaySchoolTeacher schoolTeacher = teacherDao.findByTelAndStatus(tel, 0);
		if (qmPatriarch == null && schoolInfo == null && schoolTeacher == null) {
			throw new Exception("此号未加入，请联系老师");
		}
		String veriftCode = VeriftCode.veriftCode();
		QmTeleVerifyCode code = new QmTeleVerifyCode();
		code.setTel(tel);
		code.setVeriftCode(veriftCode);
		code.setPostTime(new Date());
		codeDao.save(code);
		return veriftCode;

	}

	@Override
	public JPADao<QmAccount> getDao() {
		return accountDao;
	}

	@Override
	public Class<QmAccount> getEntityClass() {
		return QmAccount.class;
	}

}
