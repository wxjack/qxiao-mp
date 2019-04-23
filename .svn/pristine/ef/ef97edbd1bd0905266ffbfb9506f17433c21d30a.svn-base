package com.qxiao.wx.community.jpa.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.community.jpa.entity.QmCommunityComment;
import com.spring.jpa.dao.JPADao;

public interface QmCommunityCommentDao extends JPADao<QmCommunityComment>{

	@Modifying
	@Query(value = "delete from QmCommunityComment q where q.openId = ?1 and q.communityId = ?2")
	void deleteComment(String openId, String communityId);

}
