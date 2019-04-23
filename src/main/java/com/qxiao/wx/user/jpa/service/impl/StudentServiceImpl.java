package com.qxiao.wx.user.jpa.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.CopyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.community.jpa.dao.QmCommunityInfoDao;
import com.qxiao.wx.community.jpa.entity.QmClassStudent;
import com.qxiao.wx.openedition.jpa.dao.QmExpressionActionDefaultDao;
import com.qxiao.wx.openedition.jpa.dao.QmExpressionRuleDefaultDao;
import com.qxiao.wx.openedition.jpa.dao.QmStudentActionDao;
import com.qxiao.wx.openedition.jpa.dao.QmStudentRuleDao;
import com.qxiao.wx.openedition.jpa.entity.QmExpressionActionDefault;
import com.qxiao.wx.openedition.jpa.entity.QmExpressionRuleDefault;
import com.qxiao.wx.openedition.jpa.entity.QmStudentAction;
import com.qxiao.wx.openedition.jpa.entity.QmStudentRule;
import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchStudentDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolClassDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPatriarchStudent;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.qxiao.wx.user.jpa.service.StudentService;
import com.qxiao.wx.user.util.FilerReadExcel;
import com.qxiao.wx.user.vo.QuerySchoolClassVo;
import com.qxiao.wx.user.vo.QueryStudentByNotice;
import com.qxiao.wx.user.vo.QueryStudentListVo;
import com.qxiao.wx.user.vo.QueryStudentSupplyVo;
import com.qxiao.wx.user.vo.QueryStudentVo;
import com.qxiao.wx.user.vo.StudentAddJsonVo;
import com.qxiao.wx.user.vo.StudentInfoVo;
import com.qxiao.wx.user.vo.StudentParentVo;
import com.qxiao.wx.user.vo.StudentSupplyVo;
import com.qxiao.wx.user.vo.StudentUpdateVo;
import com.qxiao.wx.webapi_tts.WebTTs;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;
import com.talkweb.weixin.main.StartOnLoad;
import com.talkweb.weixin.util.WeiXinUtil;;

@Service
public class StudentServiceImpl extends AbstractJdbcService<QmStudent> implements StudentService {

	@Autowired
	QmStudentDao studentDao;
	@Autowired
	QmPatriarchDao patriarchDao;
	@Autowired
	QmPatriarchStudentDao patriarchStudentDao;
	@Autowired
	QmClassStudentDao classDao;
	@Autowired
	QmPlaySchoolClassDao playClassDao;
	@Autowired
	QmAccountDao accountDao;
	@Autowired
	QmCommunityInfoDao communityInfoDao;
	@Autowired
	QmPlaySchoolInfoDao schoolInfoDao;
	@Autowired
	QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	QmExpressionActionDefaultDao defaultDao;
	@Autowired
	QmExpressionRuleDefaultDao rdDao;
	@Autowired
	QmStudentActionDao actionDao;
	@Autowired
	QmStudentRuleDao srDao;

	public List<Map<String, Object>> queryOpenStudentList(String openId) {
		List<QmStudent> list = studentDao.findByOpenId(openId);
		List<Map<String, Object>> li = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (QmStudent qs : list) {
				Map<String, Object> map = new HashMap<>();
				map.put("openStudentId", qs.getStudentId());
				map.put("openStudentName", qs.getStudentName());
				li.add(map);
			}
		}
		return li;
	}

	public void addStudentWithStudentId(String tel, Long studentId, Long classId, Integer relation) {
		QmPatriarch patriarch = patriarchDao.findByTel(tel);
		if (patriarch == null) {
			patriarch.setIsDel(0);
			patriarch.setOpenId("");
			patriarch.setPostTime(Calendar.getInstance().getTime());
			patriarch.setRelation(relation);
			patriarch.setTel(tel);
			patriarchDao.save(patriarch);
		}
		QmClassStudent classStudent = classDao.findByStudentIdAndClassId(studentId, classId);
		if (classStudent == null) {
			classStudent.setClassId(classId);
			classStudent.setPostTime(Calendar.getInstance().getTime());
			classStudent.setStudentId(studentId);
			classDao.save(classStudent);
		}
	}

	@Transactional
	public Map<String, Object> addStudentWithOpen(String openId, String studentName, Integer sex, Integer relation,
			String tel, HttpServletResponse response) throws Exception {
		QmPatriarch qp = patriarchDao.findByTel(tel);
		if (qp == null) {
			qp = new QmPatriarch();
			qp.setIsDel(0);
			qp.setOpenId(openId);
			qp.setRelation(relation);
			qp.setTel(tel);
			qp.setPostTime(Calendar.getInstance().getTime());
			patriarchDao.save(qp);
		}else {
			QmPatriarch qp1 = patriarchDao.findByTelAndOpenId(tel,openId);
			if(qp1==null) {
				throw new Exception("手机号码："+tel+"已关联其他微信号");
			}
		}
		QmStudent qs = new QmStudent();
		QmAccount account = accountDao.findByOpenId(openId);
		if (account == null) {
			WeiXinUtil.getFuwuCode(response, StartOnLoad.TOKEN_APPID,
					StartOnLoad.GOAL_LOCAL_URL + "action/mod-xiaojiao/manage/getOpenId.do");
			account = accountDao.findByOpenId(openId);
		}
		qs.setPhoto(account.getPhoto());
		qs.setSex(sex);
		qs.setStudentName(studentName);
		qs.setTotalStarCount(0);
		qs.setStatus(0);
		qs.setPostTime(Calendar.getInstance().getTime());
		studentDao.save(qs);
		QmPatriarchStudent qps = new QmPatriarchStudent();
		qps.setPatriarchId(qp.getId());
		qps.setStudentId(qs.getStudentId());
		qps.setPostTime(Calendar.getInstance().getTime());
		patriarchStudentDao.save(qps);
		List<QmExpressionActionDefault> list = defaultDao.findAll();
		for (QmExpressionActionDefault qead : list) {
			QmStudentAction qsa = new QmStudentAction();
			qsa.setActionId(qead.getActionId());
			qsa.setActionType(0);
			qsa.setStudentId(qs.getStudentId());
			qsa.setPostTime(Calendar.getInstance().getTime());
			actionDao.save(qsa);
			List<QmExpressionRuleDefault> findAll = rdDao.findByActionId(qead.getActionId());
			for (QmExpressionRuleDefault qerd : findAll) {
				QmStudentRule qst = new QmStudentRule();
				qst.setActionType(0);
				qst.setRuleId(qerd.getRuleId());
				qst.setStudentId(qs.getStudentId());
				qst.setPostTime(Calendar.getInstance().getTime());
				srDao.save(qst);
			}

		}
		Map<String, Object> map = new HashMap<>();
		map.put("openStudentId", qs.getStudentId());
		map.put("openStudentName", studentName);
		return map;
	}

	public List<QueryStudentByNotice> queryStudentByNotice(Long noticeId, String openId) {
		String sql = "SELECT DISTINCT st.student_id,st.student_name,qnrc.confirm_flag FROM "
				+ "qm_student AS st JOIN qm_class_student AS stu ON stu.student_id = st.student_id  "
				+ "JOIN qm_notice_sender AS qns ON qns.sender_id = stu.class_id "
				+ "JOIN qm_patriarch_student AS p ON p.student_id = st.student_id  "
				+ "JOIN qm_patriarch AS pa ON p.patriarch_id = pa.id "
				+ "join qm_notice_read_confirm as qnrc on qnrc.notice_id=qns.notice_id WHERE "
				+ "qns.sender_type = 1 AND qns.notice_id = ? and st.status=0 AND pa.open_id = ?";
		List<QueryStudentByNotice> list = (List<QueryStudentByNotice>) this.findList(sql,
				new Object[] { noticeId, openId }, QueryStudentByNotice.class);
		return list;
	}

	public List<Map<String, Object>> queryAllStudent(String openId) {
		List<QmStudent> findWithOpenId = studentDao.findWithOpenId(openId);
		List<Map<String, Object>> li = new ArrayList<>();

		for (QmStudent qs : findWithOpenId) {
			Map<String, Object> map = new HashMap<>();
			map.put("studentId", qs.getStudentId());
			map.put("studentName", qs.getStudentName());
			List<QmPlaySchoolClass> student = playClassDao.findByStudentOpenId(openId, qs.getStudentId());
			if (CollectionUtils.isNotEmpty(student)) {
				for (QmPlaySchoolClass qpsc : student) {
					map.put("classId", qpsc.getClassId());
					map.put("className", qpsc.getClassName());
				}
			}
			li.add(map);
		}
		return li;
	}

	public List<Map<String, Object>> queryStudentOpen(String tel) {
		String sql = "SELECT qs.student_id,qs.student_name FROM qm_student AS qs JOIN qm_patriarch_student AS qps "
				+ "JOIN qm_patriarch AS qp JOIN qm_class_student AS qcs WHERE qs.student_id = qps.student_id "
				+ "AND qps.patriarch_id = qp.id AND qp.tel = ? AND qs.student_id NOT IN ( SELECT "
				+ "qcsd.student_id FROM qm_class_student as qcsd ) GROUP BY qs.student_id";
		List<QmStudent> list = (List<QmStudent>) this.findList(sql, new Object[] { tel }, QmStudent.class);
		List<Map<String, Object>> li = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (QmStudent qs : list) {
				Map<String, Object> map = new HashMap<>();
				map.put("studentId", qs.getStudentId());
				map.put("studentName", qs.getStudentName());
				li.add(map);
			}
		}
		return li;
	}

	@Transactional(rollbackFor = Exception.class)
	public List<String> studentAddJson(StudentAddJsonVo vo) throws Exception {

		List<String> li = new ArrayList<>();
		List<Map<String, Object>> linkMan = vo.getLinkMan();
		QmPatriarch save = null;
		QmStudent student = new QmStudent();
		student.setSex(vo.getSex());
		student.setStudentName(vo.getStudentName());
		student.setNfcId("");
		student.setIbeaconId("");
		student.setTotalStarCount(0);
		student.setStatus(0);// 0-启用 1-冻结
		student.setPostTime(new Date());
		WebTTs web = new WebTTs();// 语音生成
		String build = web.build(vo.getStudentName() + "已签到");
		student.setUrl(build);
		QmClassStudent classStudent = new QmClassStudent();
		classStudent.setClassId(vo.getClassId());

		classStudent.setPostTime(new Date());
		studentDao.save(student);
		classStudent.setStudentId(student.getStudentId());
		classDao.save(classStudent);
		for (Map<String, Object> obj : linkMan) {
			String tel2 = obj.get("tel").toString();
			int relation = Integer.parseInt(obj.get("relation").toString());
			QmPatriarch qmPatriarch = patriarchDao.findByTel(tel2);
			QmPlaySchoolInfo findByTel = schoolInfoDao.findByTel(tel2);
			QmPlaySchoolTeacher tea = teacherDao.findByTel(tel2);
			if (findByTel != null || tea != null) {
				li.add(tel2);
			}
			if (qmPatriarch == null) {// 号码不存在
				QmPatriarch patriarch = new QmPatriarch();
				patriarch.setTel(tel2);
				patriarch.setRelation(relation);
				patriarch.setPostTime(new Date());
				save = patriarchDao.save(patriarch);
				QmPatriarchStudent patriarchStudent = new QmPatriarchStudent();
				patriarchStudent.setPatriarchId(save.getId());
				patriarchStudent.setStudentId(student.getStudentId());
				patriarchStudent.setPostTime(new Date());
				patriarchStudentDao.save(patriarchStudent);
			} else {// 号码存在
//				QmPatriarch qmPatriarch3 = patriarchDao.findByTelAndIsDel(tel2, 1);
				if (qmPatriarch.getIsDel() == 1) {// 号码存在并删除了
					List<QmPatriarchStudent> patriarchStudent = patriarchStudentDao
							.findByPatriarchId(qmPatriarch.getId());
					for (QmPatriarchStudent qps : patriarchStudent) {
						patriarchStudentDao.delete(qps.getId());
					}
					QmAccount account = accountDao.findByOpenId(qmPatriarch.getOpenId());
					if (account != null) {
						account.setPersonType(9);
						accountDao.save(account);
					}
					qmPatriarch.setIsDel(0);
					qmPatriarch.setOpenId("");
					qmPatriarch.setRelation(relation);
					patriarchDao.save(qmPatriarch);
					QmPatriarchStudent qps = new QmPatriarchStudent();
					qps.setPatriarchId(qmPatriarch.getId());
					qps.setStudentId(student.getStudentId());
					qps.setPostTime(new Date());
					patriarchStudentDao.save(qps);
				} else {// 不能添加的号码
					QmAccount account = accountDao.findByOpenId(qmPatriarch.getOpenId());
					if (account != null) {
						student.setPhoto(account.getPhoto());
						studentDao.save(student);
					}
					QmPatriarchStudent patriarchStudent = new QmPatriarchStudent();
					patriarchStudent.setPatriarchId(qmPatriarch.getId());
					patriarchStudent.setStudentId(student.getStudentId());
					patriarchStudent.setPostTime(new Date());
					patriarchStudentDao.save(patriarchStudent);

				}
			}
		}
		return li;
	}

	public List<StudentInfoVo> queryStudentJson(String tel, Long studentId) {
		String sql = "SELECT qp.open_id,student.student_id, student.sex,student.student_name,qp.id as patriarchId,"
				+ "	psc.class_name,psc.class_id FROM"
				+ "	qm_patriarch AS qp LEFT JOIN qm_patriarch_student AS qps ON qp.id = qps.patriarch_id"
				+ " LEFT JOIN qm_student AS student ON qps.student_id = student.student_id AND student. STATUS = 0"
				+ " LEFT JOIN qm_class_student AS cs ON student.student_id = cs.student_id"
				+ " LEFT JOIN qm_play_school_class AS psc ON cs.class_id = psc.class_id WHERE qp.tel=? and student.student_id=?";
		List<StudentInfoVo> findList = (List<StudentInfoVo>) this.findList(sql, new Object[] { tel, studentId },
				StudentInfoVo.class);
		if (findList.size() > 0) {
			for (StudentInfoVo vo : findList) {
				vo.setLinkMan(this.queryParent(tel, studentId));
			}
		}
		return findList;
	}

	public List<StudentParentVo> queryParent(String tel, Long studentId) {
		String sql = "SELECT qp.tel,qp.relation FROM qm_patriarch AS qp "
				+ "LEFT JOIN qm_patriarch_student AS qps ON qp.id = qps.patriarch_id "
				+ "LEFT JOIN qm_student AS student ON qps.student_id = student.student_id AND student. STATUS =0 "
				+ "LEFT JOIN qm_class_student AS cs ON student.student_id = cs.student_id "
				+ "LEFT JOIN qm_play_school_class AS psc ON cs.class_id = psc.class_id WHERE qp.tel=? and student.student_id=?";
		List<StudentParentVo> list = (List<StudentParentVo>) this.findList(sql, new Object[] { tel, studentId },
				StudentParentVo.class);
		return list;
	}

	public void updateStudent(String openId, String name, Integer sex, Integer relation, Long studentId) {
		QmStudent student = studentDao.findByOpenId(openId, studentId);
		student.setStudentName(name);
		student.setSex(sex);
		studentDao.save(student);
		QmPatriarch patriarch = patriarchDao.findByOpenId(openId);
		patriarch.setRelation(relation);
		patriarchDao.save(patriarch);
	}

	public List<QueryStudentListVo> queryStudent(String classId) {
		String sql = "SELECT qpsc.class_id,qa.photo,qp.open_id,student.student_id,student.student_name,qp.tel,qp.relation,qpsc.class_name "
				+ "FROM qm_play_school_class AS qpsc JOIN qm_class_student AS classStudnet JOIN qm_student AS student "
				+ "JOIN qm_patriarch AS qp JOIN qm_patriarch_student AS qps left join qm_account as qa on qa.open_id=qp.open_id WHERE classStudnet.student_id = student.student_id "
				+ "AND qps.student_id = student.student_id AND qps.patriarch_id = qp.id "
				+ "AND qpsc.class_id = classStudnet.class_id AND classStudnet.class_id IN " + classId
				+ "AND student. STATUS = 0 AND qp.is_del = 0";
		List<QueryStudentListVo> findList = (List<QueryStudentListVo>) this.findList(sql, new Object[] {},
				QueryStudentListVo.class);
		if (findList.size() > 0) {
			for (QueryStudentListVo vo : findList) {
				switch (vo.getRelation()) {
				case 1:
					vo.setStudentName(vo.getStudentName() + "(妈妈)");
					break;
				case 2:
					vo.setStudentName(vo.getStudentName() + "(爸爸)");
					break;
				case 3:
					vo.setStudentName(vo.getStudentName() + "(爷爷)");
					break;
				case 4:
					vo.setStudentName(vo.getStudentName() + "(奶奶)");
					break;
				case 5:
					vo.setStudentName(vo.getStudentName() + "(外公)");
					break;
				default:
					vo.setStudentName(vo.getStudentName() + "(外婆)");
					break;
				}
			}
		}

		return findList;
	}

	public List<QueryStudentListVo> queryClassIdByTeacherId(Long teacherId) {
		String sql = "SELECT qct.class_id FROM qm_play_school_teacher AS qpst JOIN qm_class_teacher AS qct "
				+ "WHERE qpst.teacher_id = qct.teacher_id AND qpst.teacher_id =?";
		List<QuerySchoolClassVo> findList = (List<QuerySchoolClassVo>) this.findList(sql, new Object[] { teacherId },
				QuerySchoolClassVo.class);
		List<QueryStudentListVo> queryStudent = new ArrayList<>();
		if (findList.size() > 0) {
			List<String> li = new ArrayList<>();
			for (QuerySchoolClassVo vo : findList) {
				li.add(vo.getClassId().toString());
			}
			String join = String.join(",", li);
			queryStudent = this.queryStudent("(" + join + ")");
		}
		return queryStudent;
	}

	public List<QueryStudentSupplyVo> queryStudentSupply(String tele) {
		String sql = "SELECT sd.student_name,sd.sex,qp.tel,qp.relation,qpsc.class_name,sd.student_id,"
				+ "qp.id as patriarchId,qps.id as patriarchStudentId "
				+ "FROM qm_student AS sd LEFT JOIN qm_patriarch_student AS qps on sd.student_id=qps.student_id and sd.status=0"
				+ "LEFT JOIN qm_patriarch AS qp on qps.patriarch_id=qp.id "
				+ "LEFT JOIN qm_class_student AS qcs on qcs.student_id=sd.student_id "
				+ "LEFT JOIN qm_play_school_class AS qpsc on qpsc.class_id=qcs.class_id WHERE qp.tel=?";
		List<QueryStudentSupplyVo> findList = (List<QueryStudentSupplyVo>) this.findList(sql, new Object[] { tele },
				QueryStudentSupplyVo.class);
		return findList;
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> studentSupply(StudentSupplyVo vo) throws Exception {
		String sql = "SELECT qps.* FROM qm_patriarch AS qp JOIN qm_patriarch_student as qps "
				+ "where qp.id=qps.patriarch_id AND qp.tel= ?";
		List<QmPatriarchStudent> findList = (List<QmPatriarchStudent>) this.findList(sql, new Object[] { vo.getTel() },
				QmPatriarchStudent.class);
		QmPatriarch patriarch = new QmPatriarch();
		patriarch.setId(findList.get(0).getPatriarchId());
		patriarch.setTel(vo.getTel());
		patriarch.setRelation(vo.getRelation());
		patriarch.setOpenId(vo.getOpenId());
		patriarch.setPostTime(new Date());
		patriarchDao.save(patriarch);
		QmAccount account = accountDao.findByOpenId(vo.getOpenId());
		if (account != null) {
			account.setRoleId(patriarch.getId());
			account.setPersonType(3);
			account.setPostTime(new Date());
			accountDao.save(account);
		}
		QmStudent student2 = studentDao.findByStudentId(findList.get(0).getStudentId());
		QmStudent student = new QmStudent();
		BeanUtils.copyProperties(student2, student);
		student.setSex(vo.getSex());
		student.setStatus(0);
		student.setPhoto(account.getPhoto());
		student.setStudentName(vo.getStudentName());
		student.setStudentId(findList.get(0).getStudentId());
		student.setPostTime(new Date());
		student.setTotalStarCount(0);
		studentDao.save(student);
		QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByStudentId(student.getStudentId());

		QmClassStudent classId = classDao.findByStudentId(student.getStudentId());
		QmPlaySchoolClass schoolClass = playClassDao.findByClassId(classId.getClassId());
		Map<String, Object> map = new HashMap<>();
		map.put("roleType", 3);
		map.put("id", patriarch.getId());
		map.put("classId", schoolClass.getClassId());
		map.put("className", schoolClass.getClassName());
		map.put("type", schoolInfo.getType());
		map.put("studentId", student.getStudentId());
		map.put("openStudentId", student.getStudentId());
		map.put("openStudentName", student.getStudentName());
		map.put("openId", vo.getOpenId());
		map.put("photo", account.getPhoto());
		map.put("tel", vo.getTel());
		if (schoolInfo.getIsOpen() == 0) {
			map.put("isOpen", true);
		} else {
			map.put("isOpen", false);
		}
		return map;
	}

	public List<QueryStudentVo> studentQuery(String openId, Long studentId) throws Exception {
		String sql = "SELECT qp.open_id,student.student_id,student.sex,student.student_name,qp.tel,qp.relation,qp.id as patriarchId,"
				+ "psc.class_name,psc.class_id FROM qm_patriarch AS qp LEFT JOIN qm_patriarch_student AS qps ON qp.id = qps.patriarch_id "
				+ "LEFT JOIN qm_student AS student ON qps.student_id = student.student_id AND student. STATUS = 0 "
				+ "LEFT JOIN qm_class_student AS cs ON student.student_id = cs.student_id "
				+ "LEFT JOIN qm_play_school_class AS psc ON cs.class_id = psc.class_id WHERE qp.open_id = ? and student.student_id=?";
		List<QueryStudentVo> findList = (List<QueryStudentVo>) this.findList(sql, new Object[] { openId, studentId },
				QueryStudentVo.class);
		return findList;
	}

	public List<QueryStudentVo> studentQueryInfo(String tel) throws Exception {
		String sql = "SELECT qp.open_id,student.student_id,student.sex,student.student_name,qp.tel,qp.relation,qp.id as patriarchId,"
				+ "psc.class_name,psc.class_id FROM qm_patriarch AS qp LEFT JOIN qm_patriarch_student AS qps ON qp.id = qps.patriarch_id "
				+ "LEFT JOIN qm_student AS student ON qps.student_id = student.student_id AND student. STATUS = 0 "
				+ "LEFT JOIN qm_class_student AS cs ON student.student_id = cs.student_id "
				+ "LEFT JOIN qm_play_school_class AS psc ON cs.class_id = psc.class_id WHERE qp.tel = ?";
		List<QueryStudentVo> findList = (List<QueryStudentVo>) this.findList(sql, new Object[] { tel },
				QueryStudentVo.class);
		return findList;
	}

	public List<String> studentBatchAdd(Long schoolId, File file) throws Exception {
		Long id = schoolInfoDao.findByterminalSchoolId(schoolId);
		List<List<String>> readExcel = FilerReadExcel.readExcel(file);
		List<String> li = new ArrayList<>();
		for (int i = 0; i < readExcel.size(); i++) {
			List<String> list = readExcel.get(i);
			String studentName = list.get(0);
			int sex = Integer.parseInt(list.get(1));
			String classId = list.get(2);
			List<QmPlaySchoolClass> qpsc = playClassDao.findBySchoolIdAndClassId(id, Long.valueOf(classId));
			QmStudent qs = new QmStudent();
			if (qpsc != null) {
				qs.setSex(sex);
				qs.setStatus(0);
				qs.setStudentName(studentName);
				qs.setPostTime(new Date());
				for (int j = 4; j < list.size(); j = j + 2) {
					String tel = list.get(j - 1);
					String relation = list.get(j);
					QmPatriarch patriarch = patriarchDao.findByTel(tel);
					if (patriarch == null) {
						if (StringUtils.isNotBlank(tel) && StringUtils.isNotBlank(relation)) {
							QmPatriarch qp = new QmPatriarch();
							QmPatriarchStudent qps = new QmPatriarchStudent();
							qp.setRelation(Integer.parseInt(relation));
							qp.setTel(tel);
							qp.setPostTime(new Date());
							studentDao.save(qs);
							patriarchDao.save(qp);
							qps.setPatriarchId(qp.getId());
							qps.setStudentId(qs.getStudentId());
							qps.setPostTime(new Date());
							patriarchStudentDao.save(qps);
							QmClassStudent qcs = new QmClassStudent();
							qcs.setClassId(Long.valueOf(classId));
							qcs.setStudentId(qs.getStudentId());
							qcs.setPostTime(new Date());
							classDao.save(qcs);
						}
					} else {
						QmPatriarchStudent qps = new QmPatriarchStudent();
						studentDao.save(qs);
						qps.setPatriarchId(patriarch.getId());
						qps.setStudentId(qs.getStudentId());
						qps.setPostTime(new Date());
						patriarchStudentDao.save(qps);
					}
				}
			} else {
				throw new Exception("班级序号：" + classId + " 有误");
			}
		}
		return li;
	}

	@Transactional(rollbackFor = Exception.class)
	public List<String> studentUpdate(StudentUpdateVo vo) throws Exception {
		List<String> li = new ArrayList<>();
		QmStudent student = studentDao.findByStudentId(vo.getStudentId());
		QmStudent qs = new QmStudent();
		BeanUtils.copyProperties(student, qs);
		qs.setStudentName(vo.getStudentName());
		qs.setSex(vo.getSex());
		qs.setPostTime(new Date());
		WebTTs web = new WebTTs();// 语音生成
		String build = web.build(vo.getStudentName() + "已签到");
		qs.setUrl(build);
		studentDao.save(qs);
		QmPatriarch one = patriarchDao.findOne(vo.getPatriarchId());
		if (vo.getLinkMan().size() == 1) {
			String tel1 = vo.getLinkMan().get(0).get("tel").toString();
			String relation1 = vo.getLinkMan().get(0).get("relation").toString();
			int parseInt = Integer.parseInt(relation1);
			QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByTel(tel1);
			QmPlaySchoolTeacher schoolTeacher = teacherDao.findByTel(tel1);
			if (schoolInfo != null || schoolTeacher != null) {
				throw new Exception("号码：" + tel1 + " 已有设定为其他角色，暂无法添加，请联系管理员");
			}

			QmPatriarch patriarch = patriarchDao.findByTel(tel1);
			if (one.getTel().equals(tel1)) {
				QmPatriarch qp = new QmPatriarch();
				BeanUtils.copyProperties(patriarch, qp);
				qp.setRelation(parseInt);
				qp.setPostTime(new Date());
				patriarchDao.save(qp);
			} else {
				if (patriarch == null) {
					QmPatriarch qp = new QmPatriarch();
					BeanUtils.copyProperties(one, qp);
					qp.setRelation(parseInt);
					qp.setTel(tel1);
					qp.setPostTime(new Date());
					patriarchDao.save(qp);
				} else {
					QmPatriarchStudent qps = new QmPatriarchStudent();
					qps.setPatriarchId(patriarch.getId());
					qps.setStudentId(student.getStudentId());
					qps.setPostTime(Calendar.getInstance().getTime());
					patriarchStudentDao.save(qps);
				}
			}
		}
		if (vo.getLinkMan().size() > 1) {
			String tel1 = vo.getLinkMan().get(0).get("tel").toString();
			String relation1 = vo.getLinkMan().get(0).get("relation").toString();
			int parseInt = Integer.parseInt(relation1);
			QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByTel(tel1);
			QmPlaySchoolTeacher schoolTeacher = teacherDao.findByTel(tel1);
			if (schoolInfo != null || schoolTeacher != null) {
				throw new Exception("号码：" + tel1 + " 已有设定为其他角色，暂无法添加，请联系管理员");
			}
			QmPatriarch patriarch = patriarchDao.findByTel(tel1);
			if (one.getTel().equals(tel1)) {
				QmPatriarch qp = new QmPatriarch();
				BeanUtils.copyProperties(patriarch, qp);
				qp.setRelation(parseInt);
				qp.setPostTime(new Date());
				patriarchDao.save(qp);
			}
			if (!one.getTel().equals(tel1)) {
				if (patriarch == null) {
					QmPatriarch qp = new QmPatriarch();
					BeanUtils.copyProperties(one, qp);
					qp.setRelation(parseInt);
					qp.setTel(tel1);
					qp.setPostTime(new Date());
					patriarchDao.save(qp);
				} else {
					QmPatriarchStudent qps = new QmPatriarchStudent();
					qps.setPatriarchId(patriarch.getId());
					qps.setStudentId(student.getStudentId());
					qps.setPostTime(Calendar.getInstance().getTime());
					patriarchStudentDao.save(qps);
				}
			}
			for (int i = 1; i < vo.getLinkMan().size(); i++) {
				String tel2 = vo.getLinkMan().get(i).get("tel").toString();
				String relation2 = vo.getLinkMan().get(i).get("relation").toString();
				int parseInt2 = Integer.parseInt(relation2);
				QmPlaySchoolInfo schoolInfo2 = schoolInfoDao.findByTel(tel2);
				QmPlaySchoolTeacher schoolTeacher2 = teacherDao.findByTel(tel2);
				if (schoolInfo2 != null || schoolTeacher2 != null) {
					throw new Exception("号码：" + tel1 + " 已有设定为其他角色，暂无法添加，请联系管理员");
				}
				QmPatriarch patriarch4 = patriarchDao.findByTel(tel2);
				if (patriarch4 != null) {
					QmPatriarch qmPatriarch3 = patriarchDao.findByTelAndIsDel(tel2, 1);
					if (qmPatriarch3 != null) {
						List<QmPatriarchStudent> patriarchStudent = patriarchStudentDao
								.findByPatriarchId(qmPatriarch3.getId());
						for (QmPatriarchStudent qps : patriarchStudent) {
							patriarchStudentDao.delete(qps.getId());
						}
//						QmAccount account = accountDao.findByOpenId(qmPatriarch3.getOpenId());
//						if (account != null) {
//							account.setPersonType(9);
//							accountDao.save(account);
//						}
						qmPatriarch3.setIsDel(0);
						qmPatriarch3.setOpenId("");
						qmPatriarch3.setRelation(parseInt2);
						patriarchDao.save(qmPatriarch3);
						QmPatriarchStudent qps = new QmPatriarchStudent();
						qps.setPatriarchId(qmPatriarch3.getId());
						qps.setStudentId(student.getStudentId());
						qps.setPostTime(new Date());
						patriarchStudentDao.save(qps);
					} else {
						QmPatriarchStudent qps = new QmPatriarchStudent();
						qps.setPatriarchId(patriarch4.getId());
						qps.setStudentId(student.getStudentId());
						qps.setPostTime(new Date());
						patriarchStudentDao.save(qps);
					}
				} else {
					QmPatriarch patriarch2 = new QmPatriarch();
					patriarch2.setTel(tel2);
					patriarch2.setRelation(parseInt2);
					patriarch2.setPostTime(new Date());
					patriarchDao.save(patriarch2);
					QmPatriarchStudent patriarchStudent = new QmPatriarchStudent();
					patriarchStudent.setPatriarchId(patriarch2.getId());
					patriarchStudent.setStudentId(student.getStudentId());
					patriarchStudent.setPostTime(new Date());
					patriarchStudentDao.save(patriarchStudent);
				}
			}
		}
		QmClassStudent classStudent = classDao.findByStudentIdAndClassId(vo.getStudentId(), vo.getClassId());
		QmClassStudent qcs = new QmClassStudent();
		BeanUtils.copyProperties(classStudent, qcs);
		qcs.setPostTime(new Date());
		qcs.setClassId(vo.getClassId());
		classDao.save(qcs);
		return li;
	}

	@Transactional
	public void studentDelete(Long studentId, String tel, Long classId) throws ServiceException {
		QmStudent student = studentDao.findByStudentId(studentId);
		if (student != null) {
			student.setStatus(1);
			studentDao.save(student);
		}
		QmClassStudent findByStudentId = classDao.findByStudentIdAndClassId(studentId, classId);
		if (findByStudentId != null) {
			classDao.delete(findByStudentId.getId());
		}
		List<QmPatriarchStudent> patriarchStudent = patriarchStudentDao.findByStudentId(studentId);
		for (QmPatriarchStudent qps : patriarchStudent) {
			patriarchStudentDao.delete(qps.getId());
		}
//		QmPatriarch p = patriarchDao.findByOne(tel);
//		if (p != null) {
//			p.setIsDel(1);
//			patriarchDao.save(p);
//			List<QmCommunityInfo> info = communityInfoDao.findByOpenId(p.getOpenId());// 班级圈
//			if (info.size() > 0) {
//				for (QmCommunityInfo inf : info) {
//					inf.setIsDel(1);
//					communityInfoDao.save(inf);
//				}
//			}
//			QmAccount account = accountDao.findByOpenId(p.getOpenId());
//			if (account != null) {
//				account.setPersonType(9);
//				accountDao.save(account);
//			}
//		}
	}

	@Override
	public JPADao<QmStudent> getDao() {
		return studentDao;
	}

	@Override
	public Class<QmStudent> getEntityClass() {
		return QmStudent.class;
	}
}
