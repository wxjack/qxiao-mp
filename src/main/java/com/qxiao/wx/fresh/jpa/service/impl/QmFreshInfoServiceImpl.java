package com.qxiao.wx.fresh.jpa.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.common.JsonMapper;
import com.qxiao.wx.fresh.dto.QmFreshDetailDTO;
import com.qxiao.wx.fresh.dto.QmFreshInfoDTO;
import com.qxiao.wx.fresh.jpa.dao.QmFreshCommentDao;
import com.qxiao.wx.fresh.jpa.dao.QmFreshImageDao;
import com.qxiao.wx.fresh.jpa.dao.QmFreshInfoDao;
import com.qxiao.wx.fresh.jpa.dao.QmFreshReadDao;
import com.qxiao.wx.fresh.jpa.dao.QmFreshSenderDao;
import com.qxiao.wx.fresh.jpa.entity.QmFreshComment;
import com.qxiao.wx.fresh.jpa.entity.QmFreshImage;
import com.qxiao.wx.fresh.jpa.entity.QmFreshInfo;
import com.qxiao.wx.fresh.jpa.entity.QmFreshRead;
import com.qxiao.wx.fresh.jpa.entity.QmFreshSender;
import com.qxiao.wx.fresh.jpa.service.IQmFreshInfoService;
import com.qxiao.wx.fresh.vo.Clazz;
import com.qxiao.wx.fresh.vo.QmCommentVO;
import com.qxiao.wx.fresh.vo.QmFreshInfoVO;
import com.qxiao.wx.fresh.vo.QmImage;
import com.qxiao.wx.user.jpa.dao.QmMessageSendDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmMessageSend;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmFreshInfoServiceImpl extends AbstractJdbcService<QmFreshInfo> implements IQmFreshInfoService {

	@Autowired
	private QmFreshInfoDao freshInfoDao;
	@Autowired
	private QmFreshImageDao imageDao;
	@Autowired
	private QmFreshReadDao readDao;
	@Autowired
	private QmFreshCommentDao commentDao;
	@Autowired
	private QmPlaySchoolInfoDao schoolInfoDao;
	@Autowired
	private QmFreshSenderDao senderDao;
	@Autowired
	private QmStudentDao stuDao;
	@Autowired
	private QmPatriarchDao patriarchDao;
	@Autowired
	private QmMessageSendDao messageDao;

	@Override
	public JPADao<QmFreshInfo> getDao() {
		return this.freshInfoDao;
	}

	@Override
	public Class<QmFreshInfo> getEntityClass() {
		return QmFreshInfo.class;
	}

	// 速报列表

	@Override
	public DataPage<QmFreshInfoDTO> freshQuery(String openId, Long classId, Long studentId, int page, int pageSize)
			throws ServiceException {

		List<QmFreshInfoVO> freshs = null;
		DataPage<QmFreshInfoVO> data = this.findFreshInfoVOs(classId, page, pageSize);
		freshs = (List<QmFreshInfoVO>) data.getData();
		List<QmFreshInfoDTO> freshInfoDTOs = new ArrayList<>();
		DataPage<QmFreshInfoDTO> dataPage = new DataPage<>();
		if (CollectionUtils.isNotEmpty(freshs)) {
			for (QmFreshInfoVO fresh : freshs) {
				QmFreshInfoDTO fi = new QmFreshInfoDTO();
				Long freshId = fresh.getFreshId();
				try {
					BeanUtils.copyProperties(fi, fresh);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				QmFreshImage image = imageDao.findOne(freshId);
				if (image != null) {
					fi.setTopImage(image.getImageUrl());
				}
				fi.setClassId(classId);
				fi.setStudentId(studentId);
				// 阅读人数
				this.findClassReadCount(fi, freshId, classId);
				// 留言人数
				this.findClassCommentCount(fi, freshId, classId);

				this.setStatus(fi, openId, studentId);
				freshInfoDTOs.add(fi);
			}
			try {
				BeanUtils.copyProperties(dataPage, data);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}

			dataPage.setData(freshInfoDTOs);
		} else {
			data.setData(new ArrayList<>());
		}
		return dataPage;
	}

	private void setStatus(QmFreshInfoDTO fi, String openId, Long studentId) {
		QmStudent stu = stuDao.findOne(studentId);
		if (stu == null) {
			fi.setStatus(1);
		} else {
			QmFreshRead read = readDao.findByFreshIdAndStudentId(fi.getFreshId(), stu.getStudentId());
			fi.setStatus(read == null ? 0 : 1);
		}
	}

	private DataPage<QmFreshInfoVO> findFreshInfoVOs(Long classId, int page, int pageSize) throws ServiceException {
		String sql = "SELECT qds.class_id,qfi.fresh_id AS freshId, qfi.title AS title, SUBSTRING(qfi.text_content, 1, 50) AS textContent,"
				+ "qfi.post_time AS postTime FROM qm_fresh_info AS qfi JOIN qm_fresh_sender AS qds ON qfi.fresh_id = qds.fresh_id "
				+ "WHERE qds.class_id = ? and qfi.is_del = 0 GROUP BY freshId ORDER BY qfi.post_time DESC ";
		return (DataPage<QmFreshInfoVO>) super.getPage(sql, page, pageSize, new Object[] { classId },
				QmFreshInfoVO.class);
	}

	private void findClassCommentCount(QmFreshInfoDTO fi, Long freshId, Long classId) {
		List<QmFreshComment> comments = commentDao.findByFreshIdAndClassId(freshId, classId);
		if (CollectionUtils.isNotEmpty(comments)) {
			fi.setClassCommentCount(comments.size());
		} else {
			fi.setClassCommentCount(0);
		}
	}

	private void findClassReadCount(QmFreshInfoDTO fi, Long freshId, Long classId) {
		List<QmFreshRead> reads = readDao.findByFreshIdAndClassId(freshId, classId);
		if (CollectionUtils.isNotEmpty(reads)) {
			fi.setClassReadCount(reads.size());
		} else {
			fi.setClassReadCount(0);
		}
	}

	// 速报详情
	@Override
	public QmFreshDetailDTO findFreshDetail(String openId, Long freshId, Long classId, Long studentId) throws IOException {
		// 添加阅读记录
		this.addFreshRead(classId, studentId, freshId);

		QmPlaySchoolInfo school = schoolInfoDao.findByClassId(classId);
		QmFreshInfo freshInfo = freshInfoDao.findByFreshIdAndIsDel(freshId,0);
		QmFreshDetailDTO detail = new QmFreshDetailDTO();
		try {
			BeanUtils.copyProperties(detail, freshInfo);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		detail.setSchoolName(school.getSchoolName());
		List<Map<String, String>> images = new ArrayList<>();

		this.findImages(images, freshId);

		detail.setImages(images);
		QmPlaySchoolInfo c = schoolInfoDao.findByClassId(classId);
		detail.setSchoolName(c.getSchoolName());

		this.findClassReadCount(detail, freshId, classId);

		this.findClassCommentCount(detail, freshId, classId);

		List<QmCommentVO> commentVOs = this.findQmCommentVO(freshId, classId, openId);

		for (QmCommentVO comment : commentVOs) {
			comment.setName(comment.getName() + this.nickName(comment.getRelation(), school.getType()));
			comment.setTextContent(comment.getTextContent());
		}
		detail.setCommentList(commentVOs);
		return detail;
	}

	/* 和学生关系 1-妈妈 2-爸爸 3-爷爷 4-奶奶 5-外公 6-外婆 7-园长 8-老师 */
	private String nickName(int relation, int type) {
		switch (relation) {
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
			return type == 1 ? "(校长)" : "(园长)";
		case 8:
			return "(老师)";
		default:
			return "(妈妈)";
		}
	}

	@Transactional
	// 记录阅读人数
	private void addFreshRead(Long classId, Long studentId, Long freshId) {
		QmStudent student = stuDao.findOne(studentId);
		if (student != null) {
			QmFreshRead read = readDao.findByFreshIdAndStudentId(freshId, studentId);
			if (read == null) {
				read = new QmFreshRead();
				read.setFreshId(freshId);
				read.setStudentId(student.getStudentId());
				read.setPostTime(new Date());
				readDao.save(read);
			}
		}
	}

	// 获取的速报图片
	private void findImages(List<Map<String, String>> images, Long freshId) {
		List<QmFreshImage> list = imageDao.findByFreshId(freshId);
		if (CollectionUtils.isNotEmpty(list)) {
			for (QmFreshImage image : list) {
				Map<String, String> map = new HashMap<>();
				map.put("imageUrl", image.getImageUrl());
				images.add(map);
			}
		}
	}

	// 获取班级留言人数
	private void findClassCommentCount(QmFreshDetailDTO detail, Long freshId, Long classId) {
		List<QmFreshComment> comments = commentDao.findByFreshIdAndClassId(freshId, classId);
		if (CollectionUtils.isNotEmpty(comments)) {
			detail.setClassCommentCount(comments.size());
		} else {
			detail.setClassCommentCount(0);
		}
	}

	// 获取班级阅读人数
	private void findClassReadCount(QmFreshDetailDTO detail, Long freshId, Long classId) {
		List<QmFreshRead> reads = readDao.findByFreshIdAndClassId(freshId, classId);
		if (CollectionUtils.isNotEmpty(reads)) {
			detail.setClassReadCount(reads.size());
		} else {
			detail.setClassReadCount(0);
		}
	}

	private List<QmCommentVO> findQmCommentVO(Long freshId, Long classId, String openId) {
		String sql = "SELECT qfc.comment_id AS commentId, qfc.text_content AS textContent, "
				+ "	qfc.user_name AS NAME, qfc.relation AS relation, qfc.photo AS photo "
				+ "FROM qm_fresh_comment AS qfc WHERE qfc.fresh_id = ? "
				+ "AND qfc.class_id = ? ORDER BY qfc.post_time DESC ";
		return (List<QmCommentVO>) this.findList(sql, new Object[] { freshId, classId }, QmCommentVO.class);
	}

	@Override
	@Transactional
	public QmFreshInfo addFresh(String openId, String title, String textContent, JSONArray images, JSONArray senders) {
		QmFreshInfo freshInfo = new QmFreshInfo();
		freshInfo.setIsDel(0);
		freshInfo.setMessageSend(1);
		freshInfo.setTextContent(textContent);
		freshInfo.setTitle(title);
		freshInfo.setPostTime(new Date());
		freshInfo.setOpenId(openId);
		freshInfo = freshInfoDao.save(freshInfo);
		// 保存图片
		if (images != null && images.length() > 0) {
			this.addImage(images, freshInfo.getFreshId());
		}
		// 记录推送到那些班级
		if (senders != null && senders.length() > 0) {
			this.addSender(senders, freshInfo.getFreshId());
		}

		// 添加推送模板
		this.sendMessage(freshInfo, senders);

		return freshInfo;
	}

	private void sendMessage(QmFreshInfo info, JSONArray senders) {
		Iterator<Object> it = senders.iterator();
		List<QmMessageSend> msges = new ArrayList<>();
		while (it.hasNext()) {
			Map<String, Integer> map = JsonMapper.toMap(it.next().toString());
			Long classId = Long.parseLong(map.get("classId").toString());
			List<QmPatriarch> patriarchs = patriarchDao.findByClassId(classId);
			for (QmPatriarch par : patriarchs) {
				if (StringUtils.isNotBlank(par.getOpenId())) {
					this.templateMessage(msges, info, par.getOpenId());
				}
			}
		}
		messageDao.save(msges);
	}

	private void templateMessage(List<QmMessageSend> msges, QmFreshInfo info, String openId) {
		QmMessageSend message = messageDao.findByMessageIdAndOpenIdAndType(info.getFreshId(), openId, 2);
		if (message == null) {
			message = new QmMessageSend();
			message.setMessageId(info.getFreshId());
			message.setType(2);
			message.setOpenId(openId);
			message.setResult(0);
			message.setTitle(info.getTitle());
			message.setTextContent(info.getTextContent().length() > 50 ? info.getTextContent().substring(0, 50)
					: info.getTextContent());
			message.setPostTime(new Date());
			messageDao.save(message);
		}
	}

	@Transactional
	private void addSender(JSONArray senders, Long freshId) {
		Iterator<Object> it = senders.iterator();
		List<QmFreshSender> freshSenders = new ArrayList<>();
		while (it.hasNext()) {
			String string = it.next().toString();
			QmFreshSender sender = new QmFreshSender();
			Clazz clazz = JsonMapper.obj2Instance(string, Clazz.class);
			sender.setFreshId(freshId);
			sender.setClassId(clazz.getClassId());
			sender.setPostTime(new Date());
			freshSenders.add(sender);
		}
		senderDao.save(freshSenders);
	}

	@Transactional
	private void addImage(JSONArray images, Long freshId) {
		Iterator<Object> it = images.iterator();
		List<QmFreshImage> freshImages = new ArrayList<>();
		while (it.hasNext()) {
			String string = it.next().toString();
			QmFreshImage freshImage = new QmFreshImage();
			QmImage image = JsonMapper.obj2Instance(string, QmImage.class);
			freshImage.setFreshId(freshId);
			freshImage.setImageUrl(image.getImageUrl());
			freshImage.setPostTime(new Date());
			freshImages.add(freshImage);
		}
		imageDao.save(freshImages);
	}

	@Override
	// 推送消息
	@Transactional
	public void sendFresh(Long freshId, String openId) {
		QmFreshInfo fresh = freshInfoDao.findOne(freshId);
		fresh.setMessageSend(1);
		fresh.setSendTime(new Date());
		freshInfoDao.save(fresh);
	}

	@Override
	public void deleteFresh(Long freshId, String openId) {
		QmFreshInfo freshInfo = freshInfoDao.findOne(freshId);
		freshInfo.setIsDel(1);
		freshInfoDao.save(freshInfo);
	}

}
