package com.qxiao.wx.user.jpa.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolClassDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.dao.QmTeleVerifyCodeDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.qxiao.wx.user.jpa.service.SchoolAddService;
import com.qxiao.wx.user.vo.SchoolAddVo;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class SchoolAddServiceImpl extends AbstractJdbcService<QmPlaySchoolInfoDao> implements SchoolAddService {

	@Autowired
	QmPlaySchoolInfoDao infoDao;
	@Autowired
	QmPlaySchoolClassDao classDao;
	@Autowired
	QmTeleVerifyCodeDao codeDao;
	@Autowired
	QmAccountDao accountDao;
	@Autowired
	QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	QmStudentDao qsDao;

	public void updateIsOpen(Long schoolId, Boolean isOpen) {
		QmPlaySchoolInfo schoolInfo = infoDao.findBySchoolId(schoolId);
		if (isOpen) {
			schoolInfo.setIsOpen(0);
		} else {
			schoolInfo.setIsOpen(1);
		}
		infoDao.save(schoolInfo);
	}

	public void updateSchool(String openId, String name, String schoolName, String location) {
		QmPlaySchoolInfo info = infoDao.findByOpenId(openId);
		if (info != null) {
			info.setLocation(location);
			info.setSchoolName(schoolName);
			info.setLeaderName(name);
			infoDao.save(info);
		} else {
			QmPlaySchoolTeacher teacher = teacherDao.findByOpenId(openId);
			if (teacher != null) {
				teacher.setTeacherName(name);
				teacherDao.save(teacher);
				QmPlaySchoolInfo schoolInfo = infoDao.findBySchoolId(teacher.getSchoolId());
				schoolInfo.setLocation(location);
				schoolInfo.setSchoolName(schoolName);
				infoDao.save(schoolInfo);
			}

		}
	}

	@Transactional(rollbackOn=Exception.class)
	public Map<String, Object> schoolAdd(SchoolAddVo vo) {
		QmPlaySchoolInfo info = infoDao.findByTel(vo.getTel());
		String substring = RandomStringUtils.random(8, "1234567890");
		BeanUtils.copyProperties(vo, info);
		info.setIsOpen(0);
		info.setPostTime(new Date());
		info.setSchoolCode(substring);
		info.setLeaderName(vo.getLeadName());
		QmPlaySchoolInfo save = infoDao.save(info);
		QmAccount account = accountDao.findByOpenId(vo.getOpenId());
		if (account != null) {
			account.setRoleId(save.getSchoolId());
			account.setPersonType(0);
			account.setPostTime(new Date());
			accountDao.save(account);
		}
		List<Map<String, String>> classes = vo.getClasses();
		if (classes.size() > 0) {
			for (Map<String, String> cs : classes) {
				QmPlaySchoolClass clas = new QmPlaySchoolClass();
				clas.setClassName(cs.get("className"));
				clas.setSchoolId(save.getSchoolId());
				clas.setPostTime(new Date());
				clas.setGrade(0);
				classDao.save(clas);
			}
		}
		List<QmPlaySchoolClass> schoolId = classDao.findBySchoolId(save.getSchoolId());
		List<QmStudent> qs = qsDao.findByOpenId(vo.getOpenId());
		Map<String, Object> map = new HashMap<>();
		if(CollectionUtils.isEmpty(qs)) {
			map.put("studentId", "0");
			map.put("openStudentId", "0");
			map.put("openStudentName", "");
		}else {
			map.put("studentId", qs.get(0).getStudentId());
			map.put("openStudentId", qs.get(0).getStudentId());
			map.put("openStudentName",qs.get(0).getStudentName());
		}
		map.put("id", save.getSchoolId());
//		map.put("schoolCode", save.getSchoolCode());
		map.put("roleType", 1);
		map.put("type", save.getType());
		map.put("isOpen", true);
		map.put("classId", schoolId.get(0).getClassId());
		map.put("className", schoolId.get(0).getClassName());
		map.put("openId", vo.getOpenId());
		map.put("photo", account.getPhoto());
		map.put("tel", vo.getTel());
		return map;
	}

	@Override
	public JPADao<QmPlaySchoolInfoDao> getDao() {
		return null;
	}

	@Override
	public Class<QmPlaySchoolInfoDao> getEntityClass() {
		return null;
	}
}
