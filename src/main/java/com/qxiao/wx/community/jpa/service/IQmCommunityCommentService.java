package com.qxiao.wx.community.jpa.service;

import java.io.IOException;

import com.qxiao.wx.community.dto.QmCommunityCommentDTO;

public interface IQmCommunityCommentService {

	QmCommunityCommentDTO addCommunityComment(String openId, String textContent, Long communityId,
			Long studentId) throws IOException;

	void deleteComment(String openId, Long commentId);

}
