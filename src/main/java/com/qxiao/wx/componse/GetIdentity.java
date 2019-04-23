package com.qxiao.wx.componse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolClassDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;

@Component
public class GetIdentity implements GetIdentityService {

	@Autowired
	private QmPlaySchoolInfoDao schoolDao;
	@Autowired
	private QmPlaySchoolTeacherDao teachDao;
	@Autowired
	private QmStudentDao stuDao;
	@Autowired
	private QmPlaySchoolClassDao classDao;
	@Autowired
	private QmAccountDao accountDao;
	@Autowired
	private QmPatriarchDao patDao;

	private QmPlaySchoolInfo getSchoolInfo(String openId) {
		return schoolDao.findByOpenId(openId);
	}

	private QmPlaySchoolTeacher getTeachInfo(String openId) {
		return teachDao.findByOpenId(openId);
	}

	private QmPlaySchoolInfo getSchoolInfoWithTeacher(String openId) {
		return schoolDao.findWithTeacher(openId);
	}

	public UserInfo getIdentity(String openId, Long studentId) {
		QmAccount account = accountDao.findByOpenId(openId);
		UserInfo user = new UserInfo();
		QmPlaySchoolInfo school = null;
		if (account.getPersonType() == 0) {
			school = this.getSchoolInfo(openId);
			user.setOpenId(openId);
			if (school != null) {
				user.setSchoolId(school.getSchoolId());
				user.setSchoolName(school.getSchoolName());
				user.setUsername(school.getLeaderName());
			} else {
				user.setSchoolId(0l);
				user.setSchoolName("");
				user.setUsername("");
			}
			user.setPhoto(account.getPhoto());
			user.setRelation(7);
			user.setType(0);
			return user;
		}
		if (account.getPersonType() == 1 || account.getPersonType() == 2) {
			QmPlaySchoolTeacher teacher = this.getTeachInfo(openId);
			school = schoolDao.findWithTeacher(openId);
			if (school != null && teacher != null) {
				user.setSchoolId(school.getSchoolId());
				user.setUsername(teacher.getTeacherName());
				user.setSchoolName(school.getSchoolName());
			} else {
				user.setSchoolId(0l);
				user.setSchoolName("");
				user.setUsername("");
			}
			user.setPhoto(account.getPhoto());
			user.setOpenId(openId);
			user.setRelation(8);
			if (account.getPersonType() == 1)
				user.setType(1);
			else
				user.setType(2);
			return user;
		} else {
			QmStudent stu = stuDao.findOne(studentId);
			QmPatriarch p = patDao.findByOpenId(openId);
			if (stu != null && stu.getStatus() == 1)
				return null;
			if (stu != null) {
				school = schoolDao.findByStudentId(studentId);
				if (school != null) {
					user.setSchoolId(school.getSchoolId());
					user.setSchoolName(school.getSchoolName());
				} else {
					user.setSchoolId(0l);
					user.setSchoolName("");
					user.setUsername("");
				}
			} else
				return null;
			user.setUsername(stu.getStudentName());
			user.setPhoto(account.getPhoto());
			user.setRelation(p.getRelation());
			user.setOpenId(openId);
			user.setType(3);
			return user;
		}
	}

	public UserInfo getIdentity(String openId) {
		QmAccount account = accountDao.findByOpenId(openId);
		UserInfo user = new UserInfo();
		QmPlaySchoolInfo school = null;
		if (account.getPersonType() == 0) {
			school = this.getSchoolInfo(openId);
			user.setOpenId(openId);
			if (school != null) {
				user.setSchoolId(school.getSchoolId());
				user.setSchoolName(school.getSchoolName());
				user.setUsername(school.getLeaderName());
			} else {
				user.setSchoolId(0l);
				user.setSchoolName("");
				user.setUsername("");
			}
			user.setPhoto(account.getPhoto());
			user.setRelation(7);
			user.setType(0);
			return user;
		}
		if (account.getPersonType() == 1 || account.getPersonType() == 2) {
			QmPlaySchoolTeacher teacher = this.getTeachInfo(openId);
			school = schoolDao.findWithTeacher(openId);
			if (school != null && teacher != null) {
				user.setSchoolId(school.getSchoolId());
				user.setUsername(teacher.getTeacherName());
				user.setSchoolName(school.getSchoolName());
			} else {
				user.setSchoolId(0l);
				user.setSchoolName("");
				user.setUsername("");
			}
			user.setPhoto(account.getPhoto());
			user.setOpenId(openId);
			user.setRelation(8);
			user.setType(account.getPersonType() == 1 ? 1 : 2);
			return user;
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getClassInfo(String openId) {
		List<QmPlaySchoolClass> classes = this.findClasses(openId);
		List<Map<String, Object>> maps = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(classes)) {
			for (QmPlaySchoolClass clz : classes) {
				Map<String, Object> map = new HashMap<>();
				map.put("classId", clz.getClassId());
				map.put("className", clz.getClassName());
				maps.add(map);
			}
		}
		return maps;
	}

	private List<QmPlaySchoolClass> findClasses(String openId) {
		QmPlaySchoolInfo school = getSchoolInfo(openId);
		// 园长
		if (school != null)
			return classDao.findBySchoolId(school.getSchoolId());
		QmPlaySchoolTeacher teacher = getTeachInfo(openId);
		if (teacher != null) {
			// 管理员
			if (teacher.getType() == 1) {
				school = getSchoolInfoWithTeacher(openId);
				return classDao.findBySchoolId(school.getSchoolId());
			}
			// 普通老师
			return classDao.findWithTeacher(openId);
		}
		// 家长
		return classDao.findWithPatriarch(openId);
	}

	@Override
	public List<Map<String, Object>> getTeacherInfo(String openId) {
		QmPlaySchoolInfo school = getSchoolInfo(openId);
		if (school == null)
			school = getSchoolInfoWithTeacher(openId);
		return this.findTeachsInfoBySchoolId(school.getSchoolId());
	}

	private List<Map<String, Object>> findTeachsInfoBySchoolId(Long schoolId) {
		List<QmPlaySchoolTeacher> teacheres = teachDao.findBySchoolId(schoolId);
		List<Map<String, Object>> maps = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(teacheres)) {
			for (QmPlaySchoolTeacher teach : teacheres) {
				Map<String, Object> map = new HashMap<>();
				map.put("teacherId", teach.getTeacherId());
				map.put("teacherName", teach.getTeacherName());
				maps.add(map);
			}
		}
		return maps;
	}

//	@Override
//	public List<Map<String, Object>> getStudentInfo(String openId,Long studentId) {
//		QmStudent stus = stuDao.findByOpenId(openId,studentId);
//		List<Map<String, Object>> maps = new ArrayList<>();
//		if (CollectionUtils.isNotEmpty(stus)) {
//			for (QmStudent stu : stus) {
//				Map<String, Object> map = new HashMap<>();
//				map.put("studentId", stu.getStudentId());
//				map.put("studentName", stu.getStudentName());
//				maps.add(map);
//			}
//		}
//		return maps;
//	}


}
