package com.qxiao.wx.community.jpa.dao;

import java.util.List;

import com.qxiao.wx.community.jpa.entity.QmCommunityInfo;
import com.spring.jpa.dao.JPADao;

public interface QmCommunityInfoDao extends JPADao<QmCommunityInfo>{

	List<QmCommunityInfo> findByOpenId(String openId);

}
