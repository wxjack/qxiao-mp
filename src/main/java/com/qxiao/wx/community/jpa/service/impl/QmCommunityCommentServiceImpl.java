package com.qxiao.wx.community.jpa.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.community.dto.QmCommunityCommentDTO;
import com.qxiao.wx.community.jpa.dao.QmCommunityCommentDao;
import com.qxiao.wx.community.jpa.entity.QmCommunityComment;
import com.qxiao.wx.community.jpa.service.IQmCommunityCommentService;
import com.qxiao.wx.componse.GetIdentityService;
import com.qxiao.wx.componse.UserInfo;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.vdurmont.emoji.EmojiParser;

@Service
public class QmCommunityCommentServiceImpl extends AbstractJdbcService<QmCommunityComment>
		implements IQmCommunityCommentService {

	@Autowired
	private QmCommunityCommentDao commentDao;
	@Autowired
	private GetIdentityService idservice;

	@Override
	@Transactional
	public QmCommunityCommentDTO addCommunityComment(String openId, String textContent, Long communityId,
			Long studentId) throws IOException {

		UserInfo user = idservice.getIdentity(openId, studentId);
		QmCommunityComment comment = new QmCommunityComment();
		comment.setCommunityId(communityId);
		comment.setTextContent(textContent);
		comment.setOpenId(openId);
		comment.setRelation(user.getRelation());
		comment.setPhoto(user.getPhoto());
		comment.setStudentId(studentId);
		comment.setPostTime(new Date());
		comment = commentDao.save(comment);

		QmCommunityCommentDTO commentDTO = new QmCommunityCommentDTO();
		commentDTO.setOpenId(openId);
		commentDTO.setTextContent( comment.getTextContent());
		commentDTO.setPhoto(user.getPhoto());
		commentDTO.setRelation(comment.getRelation());
		commentDTO.setStudentName(user.getUsername());
		commentDTO.setCommentId(comment.getCommentId());
		return commentDTO;
	}

	@Override
	public JPADao<QmCommunityComment> getDao() {
		return this.commentDao;
	}

	@Override
	public Class<QmCommunityComment> getEntityClass() {
		return QmCommunityComment.class;
	}

	@Override
	@Transactional
	public void deleteComment(String openId, Long commentId) {
		if (commentId != null) {
			commentDao.delete(commentId);
		}
	}
}
