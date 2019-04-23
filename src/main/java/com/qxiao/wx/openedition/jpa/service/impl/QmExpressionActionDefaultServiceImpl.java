package com.qxiao.wx.openedition.jpa.service.impl;

import com.qxiao.wx.openedition.jpa.entity.QmExpressionActionDefault;
import com.qxiao.wx.openedition.jpa.service.IQmExpressionActionDefaultService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

public class QmExpressionActionDefaultServiceImpl extends AbstractJdbcService<QmExpressionActionDefault> implements IQmExpressionActionDefaultService {

	@Override
	public JPADao<QmExpressionActionDefault> getDao() {
		return null;
	}

	@Override
	public Class<QmExpressionActionDefault> getEntityClass() {
		return null;
	}

}
