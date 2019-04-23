package com.qxiao.wx.openedition.jpa.service.impl;

import com.qxiao.wx.openedition.jpa.entity.QmExpressionRuleDefault;
import com.qxiao.wx.openedition.jpa.service.IQmExpressionRuleDefaultService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

public class QmExpressionRuleDefaultServiceImpl extends AbstractJdbcService<QmExpressionRuleDefault> implements IQmExpressionRuleDefaultService {

	@Override
	public JPADao<QmExpressionRuleDefault> getDao() {
		return null;
	}

	@Override
	public Class<QmExpressionRuleDefault> getEntityClass() {
		return null;
	}

}
