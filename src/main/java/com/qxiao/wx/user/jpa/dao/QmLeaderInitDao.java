package com.qxiao.wx.user.jpa.dao;

import com.qxiao.wx.user.jpa.entity.QmLeaderInit;
import com.spring.jpa.dao.JPADao;

public interface QmLeaderInitDao extends JPADao<QmLeaderInit>{

	QmLeaderInit findByTel(String tel);

}
