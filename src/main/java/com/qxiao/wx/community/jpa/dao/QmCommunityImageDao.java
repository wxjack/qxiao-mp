package com.qxiao.wx.community.jpa.dao;

import com.qxiao.wx.community.jpa.entity.QmCommunityImage;
import com.spring.jpa.dao.JPADao;

public interface QmCommunityImageDao extends JPADao<QmCommunityImage>{

	void deleteByCommunityId(Long communityId);

}
