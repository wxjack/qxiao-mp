package com.qxiao.wx.notice.jpa.dao;

import java.util.Date;
import java.util.List;

import com.qxiao.wx.notice.jpa.entity.QmNoticeInfo;
import com.spring.jpa.dao.JPADao;

public interface QmNoticeInfoDao extends JPADao<QmNoticeInfo> {

	List<QmNoticeInfo> findByClockTime(Date date);
}
