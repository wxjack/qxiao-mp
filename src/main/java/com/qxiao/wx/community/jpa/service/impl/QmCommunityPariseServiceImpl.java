package com.qxiao.wx.community.jpa.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qxiao.wx.community.dto.QmCommunityPraiseDTO;
import com.qxiao.wx.community.jpa.dao.QmCommunityPraiseDao;
import com.qxiao.wx.community.jpa.entity.QmCommunityPraise;
import com.qxiao.wx.community.jpa.service.IQmCommunityPraiseService;
import com.qxiao.wx.componse.GetIdentityService;
import com.qxiao.wx.componse.UserInfo;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QmCommunityPariseServiceImpl extends AbstractJdbcService<QmCommunityPraise>
		implements IQmCommunityPraiseService {

	@Autowired
	private QmCommunityPraiseDao praiseDao;
	@Autowired
	private GetIdentityService idService;

	@Override
	public JPADao<QmCommunityPraise> getDao() {
		return praiseDao;
	}

	@Override
	public Class<QmCommunityPraise> getEntityClass() {
		return QmCommunityPraise.class;
	}

	@Override
	@Transactional
	public QmCommunityPraiseDTO addCommunityPraise(String openId, Long communityId, Long studentId) {
		UserInfo user = idService.getIdentity(openId, studentId);
		QmCommunityPraise praise = praiseDao.findByCommunityIdAndStudentIdAndOpenId(communityId, studentId,openId);
		if (praise == null) {
			praise = new QmCommunityPraise();
			praise.setCommunityId(communityId);
			praise.setOpenId(openId);
			praise.setRelation(user.getRelation());
			praise.setStudentId(studentId);
			praise.setSerial(0);
			praise.setPostTime(new Date());
			praiseDao.save(praise);

			QmCommunityPraiseDTO praiseDTO = new QmCommunityPraiseDTO();
			praiseDTO.setStudentId(studentId);
			praiseDTO.setStudentName(user.getUsername());
			praiseDTO.setRelation(user.getRelation());
			praiseDTO.setPhoto(user.getPhoto());
			praiseDTO.setOpenId(openId);
			return praiseDTO;
		}
		praiseDao.deletePraise(studentId, communityId,openId);
		return null;
	}
}
