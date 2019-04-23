package com.qxiao.wx.user.jpa.service.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;

public class AddTeacherServiceImpl {

	@Autowired
	QmPlaySchoolTeacherDao teacherDao;
	public void AddTeacher(QmPlaySchoolTeacher qm) throws IllegalAccessException, InvocationTargetException {
		QmPlaySchoolTeacher qt=new QmPlaySchoolTeacher();
		BeanUtils.copyProperties(qm, qt);
		qt.setPostTime(Calendar.getInstance().getTime());
		teacherDao.save(qt);
	}
	
	public void AddTeachers(File file) {
		
		
		
	}
}
