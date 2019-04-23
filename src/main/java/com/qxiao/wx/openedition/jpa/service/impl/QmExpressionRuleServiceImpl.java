package com.qxiao.wx.openedition.jpa.service.impl;

import com.qxiao.wx.openedition.jpa.entity.QmExpressionRule;
import com.qxiao.wx.openedition.jpa.service.IQmExpressionRuleService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

public class QmExpressionRuleServiceImpl extends AbstractJdbcService<QmExpressionRule> implements IQmExpressionRuleService {

	@Override
	public JPADao<QmExpressionRule> getDao() {
		return null;
	}

	@Override
	public Class<QmExpressionRule> getEntityClass() {
		return null;
	}

}
