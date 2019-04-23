package com.qxiao.wx.community.jpa.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.community.jpa.entity.QmCommunityPraise;
import com.spring.jpa.dao.JPADao;

public interface QmCommunityPraiseDao extends JPADao<QmCommunityPraise> {

	@Modifying
	@Query(value = "delete from QmCommunityPraise q where q.studentId = ?1 and q.communityId = ?2 and q.openId = ?3")
	void deletePraise(Long studentId, Long communityId, String openId);

	QmCommunityPraise findByCommunityIdAndStudentIdAndOpenId(Long communityId, Long studentId, String openId);
	
	

}
