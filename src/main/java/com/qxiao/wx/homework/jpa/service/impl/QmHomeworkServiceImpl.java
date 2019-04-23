package com.qxiao.wx.homework.jpa.service.impl;

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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qxiao.wx.common.JsonMapper;
import com.qxiao.wx.fresh.vo.QmImage;
import com.qxiao.wx.homework.dto.QmHomeworkInfoDetailDTO;
import com.qxiao.wx.homework.dto.QmHomeworkInfoListDTO;
import com.qxiao.wx.homework.dto.QmHomeworkReaderDTO;
import com.qxiao.wx.homework.jpa.dao.QmHomeworkImageDao;
import com.qxiao.wx.homework.jpa.dao.QmHomeworkInfoDao;
import com.qxiao.wx.homework.jpa.dao.QmHomeworkReadConfirmDao;
import com.qxiao.wx.homework.jpa.dao.QmHomeworkSenderDao;
import com.qxiao.wx.homework.jpa.entity.QmHomeworkImage;
import com.qxiao.wx.homework.jpa.entity.QmHomeworkInfo;
import com.qxiao.wx.homework.jpa.entity.QmHomeworkReadConfirm;
import com.qxiao.wx.homework.jpa.entity.QmHomeworkSender;
import com.qxiao.wx.homework.jpa.service.IQmHomeworkService;
import com.qxiao.wx.homework.jpa.vo.QmReaderVO;
import com.qxiao.wx.user.jpa.dao.QmMessageSendDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmMessageSend;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmHomeworkServiceImpl extends AbstractJdbcService<QmHomeworkInfo> implements IQmHomeworkService {

	@Autowired
	private QmHomeworkInfoDao homeworkInfoDao;
	@Autowired
	private QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	private QmHomeworkSenderDao senderDao;
	@Autowired
	private QmHomeworkImageDao imageDao;
	@Autowired
	private QmStudentDao stuDao;
	@Autowired
	private QmHomeworkReadConfirmDao confirmDao;
	@Autowired
	private QmPatriarchDao patriarchDao;
	@Autowired
	private QmMessageSendDao messageDao;

	@Override
	public JPADao<QmHomeworkInfo> getDao() {
		return this.homeworkInfoDao;
	}

	@Override
	public Class<QmHomeworkInfo> getEntityClass() {
		return QmHomeworkInfo.class;
	}

	@Override
	/**
	 * 发布作业
	 */
	@Transactional
	public void homeworkAdd(String openId, String title, String textContent, JSONArray images, JSONArray senders,
			int needConfirm) throws ServiceException, JsonParseException, JsonMappingException, IOException {
		QmPlaySchoolTeacher teacher = this.getTeacher(openId);
		if (teacher.getType() != 1)
			throw new ServiceException("亲，您没有权限哦！");
		// 发布作业
		QmHomeworkInfo info = this.addHomework(openId, teacher.getTeacherId(), title, textContent, needConfirm);
		// 添加图片
		this.addImages(info.getHomeId(), images);
		// 添加推送对象
		this.addSender(senders, info.getHomeId());

		this.sendMessage(info, senders);
	}

	private void sendMessage(QmHomeworkInfo info, JSONArray senders) {
		Iterator<Object> it = senders.iterator();
		List<QmMessageSend> msges = new ArrayList<>();
		while (it.hasNext()) {
			Map<String, Integer> map = JsonMapper.toMap(it.next().toString());
			Long classId = Long.valueOf(map.get("classId").toString());
			List<QmPatriarch> patriarchs = patriarchDao.findByClassId(classId);
			for (QmPatriarch par : patriarchs) {
				if (StringUtils.isNotBlank(par.getOpenId()))
					this.templateMessage(msges, info, par.getOpenId());
			}
		}
	}

	private void templateMessage(List<QmMessageSend> msges, QmHomeworkInfo info, String openId) {
		QmMessageSend message = messageDao.findByMessageIdAndOpenIdAndType(info.getHomeId(), openId, 5);
		if (message == null) {
			message = new QmMessageSend();
			message.setMessageId(info.getHomeId());
			message.setType(5);
			message.setOpenId(openId);
			message.setResult(0);
			message.setTitle(info.getTitle());
			message.setTextContent(info.getTextContent().length() > 50 ? info.getTextContent().substring(0, 50)
					: info.getTextContent());
			message.setPostTime(new Date());
			messageDao.save(message);
		}
	}

	private QmPlaySchoolTeacher getTeacher(String openId) {
		return teacherDao.findByOpenId(openId);
	}

	@Transactional
	private void addSender(JSONArray senders, Long homeId)
			throws JsonParseException, JsonMappingException, IOException {
		if (senders != null) {
			List<QmHomeworkSender> sends = new ArrayList<>();
			Iterator<Object> it = senders.iterator();
			while (it.hasNext()) {
				QmHomeworkSender sender = new QmHomeworkSender();
				String str = it.next().toString();
				Map<String, Object> map = JsonMapper.getMapper().readValue(str, HashMap.class);
				Long classId = Long.parseLong(map.get("classId").toString());
				sender.setHomeId(homeId);
				sender.setClassId(classId);
				sender.setPostTime(new Date());
				sends.add(sender);
			}
			senderDao.save(sends);
		}
	}

	@Transactional
	private void addImages(Long homeId, JSONArray images) {
		if (images != null) {
			Iterator<Object> it = images.iterator();
			List<QmHomeworkImage> imgs = new ArrayList<>();
			while (it.hasNext()) {
				QmHomeworkImage image = new QmHomeworkImage();
				String str = it.next().toString();
				QmImage img = JsonMapper.obj2Instance(str, QmImage.class);
				image.setHomeId(homeId);
				image.setImageUrl(img.getImageUrl());
				image.setSmallUrl(img.getImageUrl());
				image.setPostTime(new Date());
				imgs.add(image);
			}
			imageDao.save(imgs);
		}
	}

	@Transactional
	private QmHomeworkInfo addHomework(String openId, Long teacherId, String title, String textContent,
			int needConfirm) {
		QmHomeworkInfo info = new QmHomeworkInfo();
		info.setTeacherId(teacherId);
		info.setNeedConfirm(needConfirm);
		info.setIsDel(0);
		info.setMessageSend(1);
		info.setTitle(title);
		info.setTextContent(textContent);
		info.setOpenId(openId);
		info.setSendTime(new Date());
		info.setPostTime(new Date());
		return homeworkInfoDao.save(info);
	}

	@Override
	@Transactional /** 删除作业 */
	public void homeworkDelete(String openId, Long homeId) throws ServiceException {
		QmHomeworkInfo homeworkInfo = homeworkInfoDao.findOne(homeId);
		homeworkInfo.setIsDel(1);
		homeworkInfoDao.save(homeworkInfo);
	}

	@Override
	/**
	 * 作业详情查询
	 */
	public QmHomeworkInfoDetailDTO homeworkDetail(String openId, Long homeId, Long classId, Long studentId) {

		QmHomeworkInfoDetailDTO detailDTO = new QmHomeworkInfoDetailDTO();
		QmHomeworkInfo info = homeworkInfoDao.findByHomeId(homeId);
		detailDTO.setHomeId(info.getHomeId());
		detailDTO.setTextContent(info.getTextContent());
		detailDTO.setTitle(info.getTitle());
		detailDTO.setPostTime(info.getPostTime());
		detailDTO.setIsDel(info.getIsDel());
		QmStudent stu = stuDao.findOne(studentId);
		if (stu != null) {
			QmHomeworkReadConfirm confirm = confirmDao.findByHomeIdAndStudentId(homeId, studentId);
			if (confirm == null) {
				// 记录阅读人数
				confirm = this.addHomeworkReadConfirm(homeId, classId, studentId, openId);
			}
			detailDTO.setConfirmFlag(confirm.getConfirmFlag());
		} else
			detailDTO.setConfirmFlag(0);
		// 获取图片
		this.getImages(detailDTO, homeId, classId);
		// 获取阅读人数
		this.getReadCount(detailDTO, homeId, classId);

		return detailDTO;
	}

	private QmHomeworkReadConfirm addHomeworkReadConfirm(Long homeId, Long classId, Long studentId, String openId) {
		QmHomeworkReadConfirm readConfirm = new QmHomeworkReadConfirm();
		readConfirm.setConfirmFlag(0);
		readConfirm.setHomeId(homeId);
		readConfirm.setStudentId(studentId);
		readConfirm.setOpenId(openId);
		readConfirm.setPostTime(new Date());
		return confirmDao.save(readConfirm);
	}

	private void getReadCount(QmHomeworkInfoDetailDTO detailDTO, Long homeId, Long classId) {
		Integer readCount = stuDao.findForReadCount(homeId, classId);
		Integer totalCount = stuDao.findForTotalCount(classId);
		detailDTO.setClassReadCount(readCount);
		detailDTO.setClassUnreadCount(totalCount - readCount > 0 ? totalCount - readCount : 0);
		detailDTO.setTotalCount();
	}

	private void getImages(QmHomeworkInfoDetailDTO detailDTO, Long homeId, Long classId) {
		String sql = "SELECT qhi.image_url AS imageUrl FROM qm_homework_info AS qi "
				+ "JOIN qm_homework_image AS qhi ON qi.home_id = qhi.home_id "
				+ "JOIN qm_homework_sender AS qhs ON qhs.home_id = qi.home_id "
				+ "WHERE qi.home_id = :homeId AND qhs.class_id = :classId ";
		Map<String, Object> paras = new HashMap<>();
		paras.put("homeId", homeId);
		paras.put("classId", classId);
		List<Map<String, Object>> images = super.findList(sql, paras);
		detailDTO.setImages(images);
	}

	@Override
	/**
	 * 作业列表
	 */
	public DataPage<QmHomeworkInfoListDTO> homeworkQuery(String openId, Long classId, Long studentId, int page,
			int pageSize) throws ServiceException {
		DataPage<QmHomeworkInfo> pages = this.getHomeWorkInfo(classId, page, pageSize);
		List<QmHomeworkInfo> homes = (List<QmHomeworkInfo>) pages.getData();
		List<QmHomeworkInfoListDTO> infoListDTOs = new ArrayList<>();
		DataPage<QmHomeworkInfoListDTO> dataPage = new DataPage<>();
		if (CollectionUtils.isNotEmpty(homes)) {
			for (QmHomeworkInfo info : homes) {
				QmHomeworkInfoListDTO infoDto = new QmHomeworkInfoListDTO();
				try {
					BeanUtils.copyProperties(infoDto, info);
					if (infoDto.getTextContent().length() > 50) {
						String context = infoDto.getTextContent().substring(0, 50);
						infoDto.setTextContent(context);
					}
					infoDto.setClassId(classId);
					infoDto.setStudentId(studentId);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				// 获取首图
				this.getTopImage(infoDto, info.getHomeId());
				// 获取阅读人数
				this.getClassReaders(infoDto, info.getHomeId(), classId);
				this.getStatus(info.getHomeId(), infoDto, openId, studentId);
				infoListDTOs.add(infoDto);
			}
			try {
				BeanUtils.copyProperties(dataPage, pages);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			dataPage.setData(infoListDTOs);
		} else
			dataPage.setData(new ArrayList<>());
		return dataPage;
	}

	private DataPage<QmHomeworkInfo> getHomeWorkInfo(Long classId, int page, int pageSize) throws ServiceException {
		String sql = "select qhi.* from qm_homework_info as qhi join qm_homework_sender as qhs "
				+ "on qhs.home_id = qhi.home_id where qhs.class_id = ? and qhi.is_del = 0 order by qhi.post_time desc";
		return (DataPage<QmHomeworkInfo>) this.getPage(sql, page, pageSize, new Object[] { classId },
				QmHomeworkInfo.class);
	}

	private void getStatus(Long homeId, QmHomeworkInfoListDTO infoDto, String openId, Long studentId) {
		QmStudent stu = stuDao.findOne(studentId);
		if (stu == null)
			infoDto.setStatus(1);
		else {
			QmHomeworkReadConfirm confirm = confirmDao.findByHomeIdAndStudentId(homeId, studentId);
			infoDto.setStatus(confirm == null ? 0 : 1);
		}
	}

	private void getClassReaders(QmHomeworkInfoListDTO infoDto, Long homeId, Long classId) throws ServiceException {
		Integer count = homeworkInfoDao.findForCount(homeId, classId);
		infoDto.setClassReadCount(count);
	}

	private void getTopImage(QmHomeworkInfoListDTO infoDto, Long homeId) {
		List<QmHomeworkImage> images = imageDao.findByHomeId(homeId);
		if (CollectionUtils.isNotEmpty(images)) {
			QmHomeworkImage image = images.get(0);
			infoDto.setTopImage(image.getImageUrl());
		}
	}

	@Override
	// 作业阅读人员查询
	public QmHomeworkReaderDTO homeworkReaders(String openId, Long classId, Long homeId, int readFlag) {
		// 获取家庭作业信息
		QmHomeworkReaderDTO readerDTO = this.findHomeworkInfo(classId, homeId);
		// 获取阅读家庭作业的学生信息
		this.findReaderInfo(readerDTO, classId, homeId, readFlag);
		return readerDTO;
	}

	private void findReaderInfo(QmHomeworkReaderDTO readerDTO, Long classId, Long homeId, int readFlag) {
		List<QmReaderVO> readers = this.readInfo(classId, homeId);
		List<QmReaderVO> unReaders = this.unReadInfo(classId, homeId);
		readerDTO.setReadCount(CollectionUtils.isNotEmpty(readers) ? readers.size() : 0);
		readerDTO.setUnReadCount(CollectionUtils.isNotEmpty(unReaders) ? unReaders.size() : 0);
		readerDTO.setReaders(readFlag == 0 ? readers : unReaders);
	}

	private List<QmReaderVO> unReadInfo(Long classId, Long homeId) {
		String sql = "SELECT qs.student_id AS studentId, qs.student_name AS studentName, "
				+ "qs.photo AS photo FROM qm_student AS qs "
				+ "JOIN qm_class_student AS qc ON qc.student_id = qs.student_id WHERE "
				+ "qc.class_id = ? AND qs.student_id NOT IN ( SELECT q.student_id FROM qm_homework_read_confirm AS q "
				+ "JOIN qm_homework_sender AS qh ON qh.home_id = q.home_id WHERE "
				+ "q.home_id = ? AND qh.class_id = qc.class_id ) ";
		List<QmReaderVO> findList = (List<QmReaderVO>) this.findList(sql, new Object[] { classId, homeId },
				QmReaderVO.class);
		return findList;
	}

	private List<QmReaderVO> readInfo(Long classId, Long homeId) {
		String sql = "SELECT qs.student_id AS studentId, qs.student_name AS studentName, "
				+ "qa.photo AS photo, qhr.confirm_flag AS confirmFlag, qhr.post_time AS postTime "
				+ "FROM qm_student AS qs JOIN qm_homework_read_confirm AS qhr ON qs.student_id = qhr.student_id "
				+ "JOIN qm_class_student AS qcs ON qs.student_id = qcs.student_id "
				+ "JOIN qm_account AS qa ON qa.open_id = qhr.open_id WHERE qcs.class_id = ? AND qhr.home_id = ? ";
		List<QmReaderVO> findList = (List<QmReaderVO>) this.findList(sql, new Object[] { classId, homeId },
				QmReaderVO.class);
		return findList;
	}

	private QmHomeworkReaderDTO findHomeworkInfo(Long classId, Long homeId) {
		QmHomeworkInfo hInfo = homeworkInfoDao.findByHomeIdAndClassId(homeId, classId);
		QmHomeworkReaderDTO readerDTO = new QmHomeworkReaderDTO();
		readerDTO.setHomeId(homeId);
		readerDTO.setTitle(hInfo.getTitle());
		readerDTO.setPostTime(hInfo.getPostTime());
		return readerDTO;
	}

	@Override
	/* 作业确认 */
	public void homeWorkConfirm(String openId, Long homeId, Long studentId) {
		QmHomeworkReadConfirm confirm = confirmDao.findByHomeIdAndStudentId(homeId, studentId);
		if (confirm != null) {
			if (confirm.getConfirmFlag() != 1) {
				confirm.setConfirmFlag(1);
				confirmDao.save(confirm);
			}
		}
	}
}
