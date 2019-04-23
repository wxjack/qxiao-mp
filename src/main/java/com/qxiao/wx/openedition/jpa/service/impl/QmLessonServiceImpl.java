package com.qxiao.wx.openedition.jpa.service.impl;

import com.qxiao.wx.openedition.jpa.entity.QmLesson;
import com.qxiao.wx.openedition.jpa.service.IQmLessonService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

public class QmLessonServiceImpl extends AbstractJdbcService<QmLesson> implements IQmLessonService {

	@Override
	public JPADao<QmLesson> getDao() {
		return null;
	}

	@Override
	public Class<QmLesson> getEntityClass() {
		return null;
	}

}
