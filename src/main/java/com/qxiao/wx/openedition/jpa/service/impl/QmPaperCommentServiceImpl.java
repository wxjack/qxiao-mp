package com.qxiao.wx.openedition.jpa.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.openedition.dto.QmPaperCommentDTO;
import com.qxiao.wx.openedition.jpa.dao.QmPaperCommentDao;
import com.qxiao.wx.openedition.jpa.entity.QmPaperComment;
import com.qxiao.wx.openedition.jpa.service.IQmPaperCommentService;
import com.qxiao.wx.openedition.sql.ConstSql;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QmPaperCommentServiceImpl extends AbstractJdbcService<QmPaperComment> implements IQmPaperCommentService {

	@Autowired
	private QmPaperCommentDao commentDao;

	@Override
	public JPADao<QmPaperComment> getDao() {
		return null;
	}

	@Override
	public Class<QmPaperComment> getEntityClass() {
		return null;
	}

	@Override
	public List<QmPaperCommentDTO> examPaperCommentQuery(String openId, Long paperId) {
		if (paperId > 0) {
			return (List<QmPaperCommentDTO>) this.findList(ConstSql.findComments(), new Object[] { paperId },
					QmPaperCommentDTO.class);
		}
		return null;
	}

	@Override
	@Transactional
	public QmPaperComment addexamPaperComment(String openId, Long paperId, Long studentId, String textContent) {
		QmPaperComment comment = null;
		if (StringUtils.isNotBlank(textContent) && StringUtils.isNotBlank(openId) && paperId != null
				&& studentId != null) {
			comment = new QmPaperComment();
			comment.setOpenId(openId);
			comment.setPaperId(paperId);
			comment.setTextContent(textContent);
			comment.setStudentId(studentId);
			comment.setPostTime(new Date());
			comment = commentDao.save(comment);
		}
		return comment;
	}

}
