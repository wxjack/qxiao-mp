package com.qxiao.wx.user.jpa.service.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qxiao.wx.community.jpa.dao.QmClassTeacherDao;
import com.qxiao.wx.community.jpa.dao.QmCommunityInfoDao;
import com.qxiao.wx.community.jpa.entity.QmClassTeacher;
import com.qxiao.wx.community.jpa.entity.QmCommunityInfo;
import com.qxiao.wx.homework.jpa.dao.QmHomeworkInfoDao;
import com.qxiao.wx.homework.jpa.entity.QmHomeworkInfo;
import com.qxiao.wx.notice.jpa.dao.QmNoticeInfoDao;
import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolClassDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.dao.QmTeleVerifyCodeDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.qxiao.wx.user.jpa.service.TeacherService;
import com.qxiao.wx.user.util.FilerReadExcel;
import com.qxiao.wx.user.vo.AddTeacherVo;
import com.qxiao.wx.user.vo.QueryTeacherInfoVo;
import com.qxiao.wx.user.vo.QueryTeacherVo;
import com.qxiao.wx.user.vo.TeacherInfoVo;
import com.qxiao.wx.user.vo.TeacherJoinVo;
import com.qxiao.wx.user.vo.UpdateTeacherVo;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class TeacherServiceImpl extends AbstractJdbcService<QmPlaySchoolTeacher> implements TeacherService {

	@Autowired
	QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	QmClassTeacherDao classTeacherDao;
	@Autowired
	QmTeleVerifyCodeDao codeDao;
	@Autowired
	QmPlaySchoolClassDao classDao;
	@Autowired
	QmAccountDao accountDao;
	@Autowired
	QmPlaySchoolInfoDao schoolInfoDao;
	@Autowired
	QmCommunityInfoDao communityInfoDao;
	@Autowired
	QmNoticeInfoDao noticeDao;
	@Autowired
	QmHomeworkInfoDao homeworkDao;
	@Autowired
	QmPatriarchDao parDao;
	@Autowired
	QmStudentDao qsDao;

	public void updateMe(String openId, String name, Integer sex) {
		QmPlaySchoolTeacher teacher = teacherDao.findByOpenId(openId);
		teacher.setTeacherName(name);
		teacher.setSex(sex);
		teacherDao.save(teacher);
	}

	public TeacherInfoVo queryTeacherInfoBytel(String tel, String openId) {
		String sql = "SELECT qpsi.school_name,qpsi.school_id,qpsi.school_code,qpst.teacher_name,qpsi.location,qa.photo,qpst.sex,"
				+ "	qpst.tel,qpst.teacher_id FROM qm_play_school_teacher AS qpst "
				+ "JOIN qm_class_teacher AS qct JOIN qm_play_school_class AS qpsc "
				+ "JOIN qm_play_school_info AS qpsi JOIN qm_account AS qa WHERE "
				+ "qpst.teacher_id = qct.teacher_id AND qct.class_id = qpsc.class_id "
				+ "AND qpsc.school_id = qpsi.school_id AND qpst.tel = ? AND qa.open_id = ?";
		List<TeacherInfoVo> findList = (List<TeacherInfoVo>) this.findList(sql, new Object[] { tel, openId },
				TeacherInfoVo.class);
		if (findList.size() > 0) {
			return findList.get(0);
		}
		return null;
	}

	public List<QueryTeacherVo> queryTeacher(Long schoolId) {
		String sql = "SELECT DISTINCT (teacher.teacher_id),teacher.teacher_name, "
				+ "teacher.open_id, account.photo FROM qm_play_school_teacher AS teacher "
				+ "LEFT JOIN qm_class_teacher AS ct ON ct.teacher_id = teacher.teacher_id "
				+ "LEFT JOIN qm_account AS account ON teacher.open_id = account.open_id  WHERE "
				+ "teacher.school_id = ? AND teacher. STATUS = 0";
		String teacherSql = "SELECT class_name FROM qm_play_school_class AS schoolClass,qm_class_teacher as teacher "
				+ "where schoolClass.class_id=teacher.class_id and teacher.teacher_id=?";
		List<QueryTeacherVo> findList = (List<QueryTeacherVo>) this.findList(sql, new Object[] { schoolId },
				QueryTeacherVo.class);
		if (findList.size() > 0) {
			for (QueryTeacherVo teacher : findList) {
				List<?> list = this.findList(teacherSql, new Object[] { teacher.getTeacherId() },
						QmPlaySchoolClass.class);
				teacher.setClasses(list);
			}
		}
		return findList;
	}
	@Transactional(rollbackFor = Exception.class)
	public void AddTeacher(AddTeacherVo qm) throws Exception {
		QmPlaySchoolTeacher teacher = teacherDao.findByTel(qm.getTel());
		QmPlaySchoolInfo schoolInfo2 = schoolInfoDao.findByOpenId(qm.getOpenId());
		QmPlaySchoolInfo schoolInfo = schoolInfoDao.findByTel(qm.getTel());
		QmPatriarch patriarch = parDao.findByTel(qm.getTel());
		if(schoolInfo!=null||patriarch!=null) {
			throw new Exception("此号码已设定为其他角色，暂无法添加为老师");
		}
		if (schoolInfo2 == null) {
			Long id = teacherDao.findSchoolId(qm.getOpenId());
			schoolInfo2 = schoolInfoDao.findOne(id);
		}
		if (teacher == null) {
			QmPlaySchoolTeacher qpst = new QmPlaySchoolTeacher();
			BeanUtils.copyProperties(qpst, qm);
			qpst.setStatus(0);
			qpst.setSchoolId(schoolInfo2.getSchoolId());
			qpst.setPostTime(new Date());
			qpst.setOpenId("");
			teacherDao.save(qpst);
			List<Map<String, String>> classes = qm.getClasses();
			if (classes.size() > 0) {
				for (Map<String, String> map : classes) {
					QmClassTeacher qc = new QmClassTeacher();
					qc.setClassId(Long.valueOf(map.get("classId")));
					qc.setTeacherId(qpst.getTeacherId());
					qc.setPostTime(new Date());
					classTeacherDao.save(qc);
				}
			}
		} else {
			List<QmClassTeacher> findByTeacherId = classTeacherDao.findByTeacherId(teacher.getTeacherId());
			if (findByTeacherId.size() > 0) {
				throw new Exception("此号码已添加");
			} else {
				teacher.setType(qm.getType());
				teacher.setStatus(0);
				teacherDao.save(teacher);
				QmAccount account = accountDao.findByOpenId(teacher.getOpenId());
				if (account != null) {
					if (qm.getType() == 0) {
						account.setPersonType(1);
					} else {
						account.setPersonType(2);
					}
					accountDao.save(account);
				}
				List<Map<String, String>> classes = qm.getClasses();
				if (classes.size() > 0) {
					for (Map<String, String> map : classes) {
						QmClassTeacher qc = new QmClassTeacher();
						qc.setClassId(Long.valueOf(map.get("classId")));
						qc.setTeacherId(teacher.getTeacherId());
						qc.setPostTime(new Date());
						classTeacherDao.save(qc);
					}
				}
			}
		}
	}

	public List<String> AddTeachers(File file,Long schoolId) {
		Long id = schoolInfoDao.findByterminalSchoolId(schoolId);
		List<List<String>> readExcel = FilerReadExcel.readExcel(file);
		List<String> AlreadyExistsTel=new ArrayList<>();
		for (List<String> obj : readExcel) {
			QmPlaySchoolTeacher schoolTeacher = teacherDao.findByTel(obj.get(2));
			if(schoolTeacher==null) {
				QmPlaySchoolTeacher teacher = new QmPlaySchoolTeacher();
				teacher.setOpenId("");
				teacher.setPostTime(new Date());
				teacher.setSex(Integer.parseInt(obj.get(1)));
				teacher.setStatus(0);
				teacher.setTeacherName(obj.get(0));
				teacher.setTel(obj.get(2));
				teacher.setType(Integer.parseInt(obj.get(3)));
				teacher.setSchoolId(id);
				teacherDao.save(teacher);
				String string = obj.get(4);
				String[] split = string.split(",");
				for (int i = 0; i < split.length; i++) {
					QmClassTeacher tea = new QmClassTeacher();
					List<QmPlaySchoolClass> list = classDao.findBySchoolIdAndClassId(id,Long.valueOf(split[i]));
					if(list.size()>0) {
						tea.setClassId(Long.valueOf(split[i]));
						tea.setTeacherId(teacher.getTeacherId());
						tea.setPostTime(new Date());
						classTeacherDao.save(tea);
					}
				}
			}else {
				AlreadyExistsTel.add(obj.get(2));
			}
		}
		return AlreadyExistsTel;
	}

	public void updateTeacher(UpdateTeacherVo vo) throws Exception {
		QmPlaySchoolTeacher teacher = new QmPlaySchoolTeacher();
		BeanUtils.copyProperties(teacher, vo);
		QmPlaySchoolTeacher schoolTeacher = teacherDao.findByTeacherId(vo.getTeacherId());
		if (!schoolTeacher.getTel().equals(vo.getTel())) {
			QmPlaySchoolTeacher playSchoolTeacher = teacherDao.findByTel(vo.getTel());
			if (playSchoolTeacher == null) {
				teacher.setOpenId(schoolTeacher.getOpenId());
				teacher.setSchoolId(schoolTeacher.getSchoolId());
				QmAccount account = accountDao.findByOpenId(schoolTeacher.getOpenId());
				if (account != null) {
					if (vo.getType() == 0) {
						account.setPersonType(1);
						accountDao.save(account);
					} else {
						account.setPersonType(2);
						accountDao.save(account);
					}
				}
			} else {
				throw new Exception("此号码已录入");
			}
		} else {
			teacher.setSchoolId(schoolTeacher.getSchoolId());
			if (StringUtils.isNoneEmpty(schoolTeacher.getOpenId())) {
				teacher.setOpenId(schoolTeacher.getOpenId());
				QmAccount account = accountDao.findByOpenId(schoolTeacher.getOpenId());
				if (account != null) {
					if (vo.getType() == 0) {
						account.setPersonType(1);
						accountDao.save(account);
					} else {
						account.setPersonType(2);
						accountDao.save(account);
					}
				}
			} else {
				teacher.setOpenId("");
			}
		}
		teacher.setPostTime(new Date());
		teacher.setStatus(0);
		teacherDao.save(teacher);

		List<QmClassTeacher> findByTeacherId = classTeacherDao.findByTeacherId(vo.getTeacherId());
		for (QmClassTeacher qct : findByTeacherId) {
			classTeacherDao.delete(qct.getId());
		}

		List<Map<String, Object>> classes = vo.getClasses();
		if (classes.size() > 0) {
			for (Map<String, Object> map : classes) {
				QmClassTeacher classTeacher = new QmClassTeacher();
				classTeacher.setClassId(Long.valueOf(map.get("classId").toString()));
				classTeacher.setTeacherId(teacher.getTeacherId());
				classTeacher.setPostTime(new Date());
				classTeacherDao.save(classTeacher);
			}
		}
	}

	public QueryTeacherInfoVo query(String openId, Long teacherId)
			throws IllegalAccessException, InvocationTargetException {
		QueryTeacherInfoVo vo = new QueryTeacherInfoVo();
		List<QmClassTeacher> findByTeacher = classTeacherDao.findByTeacherId(teacherId);
		QmPlaySchoolTeacher schoolTeacher = teacherDao.findByTeacherId(teacherId);
		if (schoolTeacher != null) {
			BeanUtils.copyProperties(vo, schoolTeacher);
			if (findByTeacher.size() > 0) {
				List<Object> li = new ArrayList<>();
				for (QmClassTeacher te : findByTeacher) {
					li.add(te.getClassId());
				}
				vo.setClasses(li);
			}
			return vo;
		}
		return null;
	}

	@Transactional(rollbackFor = Exception.class)
	public void deleteTeacher(String openId, Long teacherId) throws Exception {
		QmPlaySchoolTeacher st = teacherDao.findByTeacherId(teacherId);
		if (st != null) {
			st.setStatus(1);
			st.setPostTime(new Date());
			teacherDao.save(st);
			List<QmClassTeacher> list = classTeacherDao.findByTeacherId(teacherId);
			if (list.size() > 0) {
				for (QmClassTeacher qct : list) {
					classTeacherDao.delete(qct.getId());
				}
			}
			List<QmCommunityInfo> info = communityInfoDao.findByOpenId(st.getOpenId());// 班级圈
			if (info.size() > 0) {
				for (QmCommunityInfo inf : info) {
					inf.setIsDel(1);
					communityInfoDao.save(inf);
				}
			}
			List<QmHomeworkInfo> homeworkInfo = homeworkDao.findByOpenId(st.getOpenId());
			if (homeworkInfo.size() > 0) {
				for (QmHomeworkInfo home : homeworkInfo) {
					home.setIsDel(1);
					homeworkDao.save(home);
				}
			}
			QmAccount account = accountDao.findByOpenId(st.getOpenId());
			if (account != null) {
				account.setPersonType(9);
				accountDao.save(account);
			}

		}
	}

	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> teacherJoin(TeacherJoinVo vo) throws Exception {
		String sql = "SELECT qpst.* FROM qm_play_school_teacher as qpst "
				+ "JOIN qm_class_teacher as qct JOIN qm_play_school_class as qpsc "
				+ "JOIN qm_play_school_info as qpsi where qpst.teacher_id=qct.teacher_id "
				+ "and qct.class_id=qpsc.class_id and qpsc.school_id=qpsi.school_id and qpsi.school_id=?";
		List<QmPlaySchoolTeacher> findList = (List<QmPlaySchoolTeacher>) this.findList(sql,
				new Object[] { vo.getSchoolId() }, QmPlaySchoolTeacher.class);
		QmPlaySchoolTeacher teacher = new QmPlaySchoolTeacher();
		QmAccount account = accountDao.findByOpenId(vo.getOpenId());
		for (QmPlaySchoolTeacher qpst : findList) {
			if (qpst.getTel().equals(vo.getTel())) {
				BeanUtils.copyProperties(teacher, vo);
				teacher.setOpenId(vo.getOpenId());
				teacher.setTeacherId(qpst.getTeacherId());
				teacher.setType(qpst.getType());
				teacher.setPostTime(new Date());
				teacherDao.save(teacher);
				if (account != null) {
					if (qpst.getType() == 0) {
						account.setPersonType(1);
					} else {
						account.setPersonType(2);
					}
					account.setRoleId(qpst.getTeacherId());

					account.setPostTime(new Date());
					accountDao.save(account);
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		QmPlaySchoolInfo info = schoolInfoDao.findWithTeacher(teacher.getOpenId());
		List<QmClassTeacher> id = classTeacherDao.findByTeacherId(teacher.getTeacherId());
		QmPlaySchoolClass schoolClass = classDao.findByClassId(id.get(0).getClassId());
		List<QmStudent> qs = qsDao.findByOpenId(vo.getOpenId());
		if(CollectionUtils.isEmpty(qs)) {
			if(CollectionUtils.isEmpty(qs)) {
				map.put("studentId", "0");
				map.put("openStudentId", "0");
				map.put("openStudentName", "");
			}else {
				map.put("studentId", qs.get(0).getStudentId());
				map.put("openStudentId", qs.get(0).getStudentId());
				map.put("openStudentName",qs.get(0).getStudentName());
			}
		}
		if (teacher.getType() == 0) {
			map.put("roleType", 4);
			map.put("id", info.getSchoolId());
			map.put("type", info.getType());
			map.put("isOpen", true);
			map.put("classId", schoolClass.getClassId());
			map.put("className", schoolClass.getClassName());
			map.put("openId", vo.getOpenId());
			map.put("photo", account.getPhoto());
			map.put("tel", vo.getTel());
		} else {
			map.put("id", teacher.getTeacherId());
			map.put("roleType", 2);
			map.put("type", info.getType());
			map.put("isOpen", true);
			map.put("classId", schoolClass.getClassId());
			map.put("className", schoolClass.getClassName());
			map.put("openId", vo.getOpenId());
			map.put("photo", account.getPhoto());
			map.put("tel", vo.getTel());
		}
		return map;
	}

	@Override
	public JPADao<QmPlaySchoolTeacher> getDao() {
		return null;
	}

	@Override
	public Class<QmPlaySchoolTeacher> getEntityClass() {
		return null;
	}
}
