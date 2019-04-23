package com.qxiao.wx.openedition.jpa.service.impl;

import com.qxiao.wx.openedition.jpa.entity.QmPrizeItemDefault;
import com.qxiao.wx.openedition.jpa.service.IQmPrizeItemDefaultService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

public class QmPrizeItemDefaultServiceImpl extends AbstractJdbcService<QmPrizeItemDefault> implements IQmPrizeItemDefaultService {

	@Override
	public JPADao<QmPrizeItemDefault> getDao() {
		return null;
	}

	@Override
	public Class<QmPrizeItemDefault> getEntityClass() {
		return null;
	}

}
