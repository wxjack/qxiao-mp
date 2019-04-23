package com.qxiao.wx.fresh.jpa.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.componse.GetIdentityService;
import com.qxiao.wx.componse.UserInfo;
import com.qxiao.wx.fresh.dto.QmFreshCommentDTO;
import com.qxiao.wx.fresh.jpa.dao.QmFreshCommentDao;
import com.qxiao.wx.fresh.jpa.dao.QmFreshInfoDao;
import com.qxiao.wx.fresh.jpa.entity.QmFreshComment;
import com.qxiao.wx.fresh.jpa.entity.QmFreshInfo;
import com.qxiao.wx.fresh.jpa.service.IQmFreshCommentService;
import com.qxiao.wx.fresh.vo.QmCommentInfoVO;
import com.qxiao.wx.fresh.vo.QmCommentVO;
import com.qxiao.wx.fresh.vo.QmFreshCommentStuVO;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmFreshCommentServiceImpl extends AbstractJdbcService<QmFreshComment> implements IQmFreshCommentService {

	@Autowired
	private QmFreshCommentDao commentDao;
	@Autowired
	private QmFreshInfoDao freshDao;
	@Autowired
	private GetIdentityService identityService;
	@Autowired
	private QmPlaySchoolInfoDao schoolDao;

	@Override
	public JPADao<QmFreshComment> getDao() {
		return this.commentDao;
	}

	@Override
	public Class<QmFreshComment> getEntityClass() {
		return QmFreshComment.class;
	}

	@Override
	@Transactional
	// 留言
	public QmCommentVO addComment(String openId, String textContent, Long freshId, Long classId, Long studentId)
			throws ServiceException, IOException {
		QmFreshComment comment = new QmFreshComment();
		UserInfo user = identityService.getIdentity(openId, studentId);
		comment.setFreshId(freshId);
		comment.setTextContent(textContent);
		comment.setPhoto(user.getPhoto());
		comment.setRelation(user.getRelation());
		comment.setUserName(user.getUsername());
		comment.setOpenId(openId);
		comment.setClassId(classId);
		comment.setPostTime(new Date());
		comment.setStudentId(studentId);
		comment = commentDao.save(comment);

		QmCommentVO commentVO = new QmCommentVO();
		commentVO.setCommentId(comment.getCommentId());
		commentVO.setName(user.getUsername());
		commentVO.setPhoto(user.getPhoto());
		commentVO.setRelation(user.getRelation());
		commentVO.setTextContent(new String(Base64.decodeBase64(comment.getTextContent().getBytes()), "utf-8"));
		return commentVO;
	}

	@Override
	// 速报列表查询
	public QmFreshCommentDTO findFreshComment(Long freshId, Long classId, Long studentId) {
		QmFreshInfo freshInfo = freshDao.findOne(freshId);
		QmFreshCommentDTO commentDTO = new QmFreshCommentDTO();
		commentDTO.setFreshId(freshInfo.getFreshId());
		commentDTO.setTitle(freshInfo.getTitle());
		commentDTO.setPostTime(freshInfo.getPostTime());
		List<QmFreshComment> comments = commentDao.findByFreshIdAndClassId(freshId, classId);
		List<QmCommentInfoVO> infoVOs = new ArrayList<>();
		for (QmFreshComment comment : comments) {
			QmCommentInfoVO infoVO = new QmCommentInfoVO();
			QmFreshCommentStuVO commentVo = this.findCommentVo(comment.getOpenId(), studentId);
			QmPlaySchoolInfo schoolInfo = schoolDao.findByOpenId(comment.getOpenId());
			infoVO.setCommentId(comment.getCommentId());
			infoVO.setName(
					commentVo.getName() + "(" + this.nickName(comment.getRelation(), schoolInfo.getType()) + ")");
			infoVO.setRelation(comment.getRelation());
			infoVO.setPostTime(comment.getPostTime());
			infoVOs.add(infoVO);
		}
		commentDTO.setContents(infoVOs);
		return commentDTO;
	}

	/* 和学生关系 1-妈妈 2-爸爸 3-爷爷 4-奶奶 5-外公 6-外婆 7-园长 8-老师 */
	private String nickName(int relation, int type) {
		switch (relation) {
		case 1:
			return "妈妈";
		case 2:
			return "爸爸";
		case 3:
			return "爷爷";
		case 4:
			return "奶奶";
		case 5:
			return "外公";
		case 6:
			return "外婆";
		case 7:
			return type == 0 ? "园长" : "校长";
		case 8:
			return "老师";
		default:
			return "妈妈";
		}
	}

	private QmFreshCommentStuVO findCommentVo(String openId, Long studentId) {
		UserInfo userInfo = identityService.getIdentity(openId, studentId);
		if (userInfo.getType() == 2) {
			// 家长留言
			return this.getByStudent(studentId, openId);
		}
		QmFreshCommentStuVO commentStuVO = new QmFreshCommentStuVO();
		commentStuVO.setName(userInfo.getUsername());
		if (userInfo.getType() == 1) {
			commentStuVO.setRelation(8);
			// 老师留言
			return commentStuVO;
		}
		// 园长留言
		commentStuVO.setRelation(7);
		return commentStuVO;
	}

	private QmFreshCommentStuVO getByStudent(Long studentId, String openId) {
		String sql = "SELECT qs.student_name AS NAME, qp.relation AS relation FROM "
				+ "qm_student AS qs JOIN qm_patriarch_student AS qps ON qs.student_id = qps.student_id "
				+ "JOIN qm_patriarch AS qp ON qp.id = qps.patriarch_id WHERE qs.student_id = ? "
				+ "AND qp.open_id = ? AND qs. STATUS = 0 AND qp.is_del = 0";
		List<QmFreshCommentStuVO> findList = (List<QmFreshCommentStuVO>) super.findList(sql,
				new Object[] { studentId, openId }, QmFreshCommentStuVO.class);
		if (CollectionUtils.isNotEmpty(findList)) {
			return findList.get(0);
		}
		return null;
	}

	@Override
	public void deleteComment(Long commentId, String openId) {
		if (commentId != null) {
			commentDao.delete(commentId);
		}
	}

}
