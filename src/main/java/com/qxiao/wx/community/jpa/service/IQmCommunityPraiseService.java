package com.qxiao.wx.community.jpa.service;

import com.qxiao.wx.community.dto.QmCommunityPraiseDTO;

public interface IQmCommunityPraiseService {

	// 点赞
	QmCommunityPraiseDTO addCommunityPraise(String openId, Long communityId, Long studentId);
}
