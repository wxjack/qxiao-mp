package com.qxiao.wx.openedition.jpa.service.impl;

import com.qxiao.wx.openedition.jpa.entity.QmStudentAction;
import com.qxiao.wx.openedition.jpa.service.IQmStudentActionService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

public class QmStudentActionServiceImpl extends AbstractJdbcService<QmStudentAction> implements IQmStudentActionService {

	@Override
	public JPADao<QmStudentAction> getDao() {
		return null;
	}

	@Override
	public Class<QmStudentAction> getEntityClass() {
		return null;
	}

}
