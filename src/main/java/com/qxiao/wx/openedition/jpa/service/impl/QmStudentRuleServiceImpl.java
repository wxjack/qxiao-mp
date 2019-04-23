package com.qxiao.wx.openedition.jpa.service.impl;

import com.qxiao.wx.openedition.jpa.entity.QmStudentRule;
import com.qxiao.wx.openedition.jpa.service.IQmStudentRuleService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

public class QmStudentRuleServiceImpl extends AbstractJdbcService<QmStudentRule> implements IQmStudentRuleService {

	@Override
	public JPADao<QmStudentRule> getDao() {
		return null;
	}

	@Override
	public Class<QmStudentRule> getEntityClass() {
		return null;
	}

}
