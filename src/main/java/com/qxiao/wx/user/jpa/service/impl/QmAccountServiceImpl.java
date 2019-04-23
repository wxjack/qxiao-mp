package com.qxiao.wx.user.jpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.service.IQmAccountService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QmAccountServiceImpl extends AbstractJdbcService<QmAccount> implements IQmAccountService {

	@Autowired
	private QmAccountDao accountDao;

	@Override
	public JPADao<QmAccount> getDao() {
		return this.accountDao;
	}

	@Override
	public Class<QmAccount> getEntityClass() {
		return QmAccount.class;
	}

	@Override
	public QmAccount exists(String openId) {
		return accountDao.findByOpenId(openId);
	}
	
}
