package com.qxiao.wx.community.jpa.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.common.JsonMapper;
import com.qxiao.wx.community.dto.QmCommunityCommentDTO;
import com.qxiao.wx.community.dto.QmCommunityInfoDTO;
import com.qxiao.wx.community.dto.QmCommunityPraiseDTO;
import com.qxiao.wx.community.jpa.dao.QmClassCommunityDao;
import com.qxiao.wx.community.jpa.dao.QmCommunityImageDao;
import com.qxiao.wx.community.jpa.dao.QmCommunityInfoDao;
import com.qxiao.wx.community.jpa.dao.QmCommunityVideoDao;
import com.qxiao.wx.community.jpa.entity.QmClassCommunity;
import com.qxiao.wx.community.jpa.entity.QmCommunityImage;
import com.qxiao.wx.community.jpa.entity.QmCommunityInfo;
import com.qxiao.wx.community.jpa.entity.QmCommunityVideo;
import com.qxiao.wx.community.jpa.service.IQmCommunityInfoService;
import com.qxiao.wx.community.sqlconst.CommunitySQL;
import com.qxiao.wx.componse.GetIdentityService;
import com.qxiao.wx.componse.UserInfo;
import com.qxiao.wx.fresh.vo.QmImage;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;
import com.vdurmont.emoji.EmojiParser;

@Service
public class QmCommunityInfoServiceImpl extends AbstractJdbcService<QmCommunityInfo>
		implements IQmCommunityInfoService {

	@Autowired
	private QmCommunityInfoDao communityInfoDao;
	@Autowired
	private QmCommunityImageDao imageDao;
	@Autowired
	private QmClassCommunityDao classDao;
	@Autowired
	private QmCommunityVideoDao videoDao;
	@Autowired
	private GetIdentityService iService;
	@Autowired
	private QmPlaySchoolInfoDao schoolDao;

	@Override
	public JPADao<QmCommunityInfo> getDao() {
		return this.communityInfoDao;
	}

	@Override
	public Class<QmCommunityInfo> getEntityClass() {
		return QmCommunityInfo.class;
	}

	@Override
	@Transactional
	public QmCommunityInfo addCommunityInfo(String openId, String textContent, JSONArray images, String video,
			int contentType, Long classId, Long studentId) {
		QmCommunityInfo info = new QmCommunityInfo();
		info.setOpenId(openId);
		info.setTextContent(textContent);
		info.setContentType(contentType);
		info.setIsDel(0);
		info.setSerial(0);
		info.setPostTime(new Date());
		info.setStudentId(studentId);
		QmCommunityInfo communityInfo = communityInfoDao.save(info);
		// 班级信息
		this.addCommunityClass(classId, communityInfo);
		// 保存图片
		if (images.length() > 0)
			this.addCommunityImage(communityInfo, images);

		// 保存视频
		if (StringUtils.isNotEmpty(video))
			this.addCommunityVideo(video, communityInfo);

		return communityInfo;
	}

	private void addCommunityClass(Long classId, QmCommunityInfo communityInfo) {
		QmClassCommunity classCommunity = new QmClassCommunity();
		classCommunity.setClassId(classId);
		classCommunity.setCommunityId(communityInfo.getCommunityId());
		classCommunity.setPostTime(new Date());
		classDao.save(classCommunity);
	}

	private void addCommunityVideo(String videoUrl, QmCommunityInfo communityInfo) {
		QmCommunityVideo communityVideo = new QmCommunityVideo();
		communityVideo.setCommunityId(communityInfo.getCommunityId());
		communityVideo.setVideoUrl(videoUrl);
		communityVideo.setPostTime(new Date());
		videoDao.save(communityVideo);
	}

	private void addCommunityImage(QmCommunityInfo communityInfo, JSONArray imageUrls) {
		List<QmCommunityImage> images = new ArrayList<>();
		Iterator<Object> it = imageUrls.iterator();
		while (it.hasNext()) {
			String str = it.next().toString();
			QmImage image = JsonMapper.obj2Instance(str, QmImage.class);
			QmCommunityImage communityImage = new QmCommunityImage();
			communityImage.setCommunityId(communityInfo.getCommunityId());
			communityImage.setImageUrl(image.getImageUrl());
			communityImage.setSmallUrl(image.getImageUrl());
			communityImage.setPostTime(new Date());
			images.add(communityImage);
		}
		imageDao.save(images);
	}

	@Override
	// 班级圈信息查询
	public DataPage<QmCommunityInfoDTO> communityQuery(String openId, Long classId, Long studentId, int page,
			int pageSize) throws Exception {
		DataPage<QmCommunityInfoDTO> dataPage = (DataPage<QmCommunityInfoDTO>) this.getPage(
				CommunitySQL.findWirhTeacher(), page, pageSize, new Object[] { classId }, QmCommunityInfoDTO.class);
		List<QmCommunityInfoDTO> infoDTOs = (List<QmCommunityInfoDTO>) dataPage.getData();
		if (CollectionUtils.isNotEmpty(infoDTOs)) {
			for (QmCommunityInfoDTO info : infoDTOs) {
				UserInfo user = iService.getIdentity(info.getOpenId(), info.getStudentId());
				info.setName(user.getUsername() + this.nickName(user));
				// 获取图片列表
				this.getCommunityImageList(info);
				// 获取点赞列表
				this.getCommunityPraiseList(info, classId);
				// 获取评论列表
				this.getCommunityCommentList(info, classId);
			}
			dataPage.setData(infoDTOs);
		} else
			dataPage.setData(new ArrayList<>());

		List<QmCommunityInfoDTO> data = (List<QmCommunityInfoDTO>) dataPage.getData();
		if (CollectionUtils.isNotEmpty(data)) {
			for (QmCommunityInfoDTO qci : data) {
				String textContent = qci.getTextContent();
				qci.setTextContent(textContent);
			}
		}
		return dataPage;
	}

	/* 和学生关系 1-妈妈 2-爸爸 3-爷爷 4-奶奶 5-外公 6-外婆 7-园长8-老师 */
	private String nickName(UserInfo user) {
		QmPlaySchoolInfo schoolInfo = schoolDao.findOne(user.getSchoolId());
		switch (user.getRelation()) {
		case 1:
			return "(妈妈)";
		case 2:
			return "(爸爸)";
		case 3:
			return "(爷爷)";
		case 4:
			return "(奶奶)";
		case 5:
			return "(外公)";
		case 6:
			return "(外婆)";
		case 7:
			return schoolInfo.getType() == 1 ? "(校长)" : "(园长)";
		case 8:
			return "(老师)";
		default:
			return "(妈妈)";
		}
	}

	private void getCommunityCommentList(QmCommunityInfoDTO info, Long classId) throws ServiceException {
		List<QmCommunityCommentDTO> commentList = (List<QmCommunityCommentDTO>) super.findList(
				CommunitySQL.getCommentList(), new Object[] { info.getCommunityId(), classId },
				QmCommunityCommentDTO.class);
		if (CollectionUtils.isNotEmpty(commentList)) {
			for (QmCommunityCommentDTO commentDTO : commentList) {
				UserInfo user = iService.getIdentity(commentDTO.getOpenId(), commentDTO.getStudentId());
				commentDTO.setStudentName(user.getUsername());
				commentDTO.setTextContent(commentDTO.getTextContent());
			}
			info.setCommentCount(commentList.size());
		} else {
			info.setCommentCount(0);
		}
		info.setCommentList(commentList);
	}

	private void getCommunityPraiseList(QmCommunityInfoDTO info, Long classId) throws ServiceException {
		List<QmCommunityPraiseDTO> praiseList = (List<QmCommunityPraiseDTO>) super.findList(
				CommunitySQL.getPraiseList(), new Object[] { info.getCommunityId(), classId },
				QmCommunityPraiseDTO.class);
		if (CollectionUtils.isNotEmpty(praiseList)) {
			for (QmCommunityPraiseDTO praise : praiseList) {
				UserInfo user = iService.getIdentity(praise.getOpenId(), praise.getStudentId());
				praise.setStudentName(user.getUsername());
			}
			info.setPraiseCount(praiseList.size());
		} else {
			info.setPraiseCount(0);
		}
		info.setPraiseList(praiseList);
	}

	private void getCommunityImageList(QmCommunityInfoDTO info) {
		String sql = "select image_url as imageUrl from qm_community_image where community_id = :communityId ";
		Map<String, Object> param = new HashMap<>();
		param.put("communityId", info.getCommunityId());
		List<Map<String, Object>> images = super.findList(sql, param);
		info.setImages(images);
	}

	// 删除班级圈
	@Override
	@Transactional
	public void deleteCommuityInfo(String openId, Long communityId) {
		if (communityId != null) {
			QmCommunityInfo communityInfo = communityInfoDao.findOne(communityId);
			communityInfo.setIsDel(1);
			communityInfoDao.save(communityInfo);
		}
	}

}
