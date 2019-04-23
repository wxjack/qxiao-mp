package com.qxiao.wx.notice.jpa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.common.DateUtil;
import com.qxiao.wx.common.JsonMapper;
import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.componse.GetIdentityService;
import com.qxiao.wx.componse.UserInfo;
import com.qxiao.wx.fresh.vo.QmImage;
import com.qxiao.wx.notice.dto.QmNoticeDetailDTO;
import com.qxiao.wx.notice.dto.QmNoticeInfoDTO;
import com.qxiao.wx.notice.dto.QmNoticeInfoReadDTO;
import com.qxiao.wx.notice.dto.QmNoticeReadDTO;
import com.qxiao.wx.notice.jpa.dao.QmNoticeImageDao;
import com.qxiao.wx.notice.jpa.dao.QmNoticeInfoDao;
import com.qxiao.wx.notice.jpa.dao.QmNoticeReadConfirmDao;
import com.qxiao.wx.notice.jpa.dao.QmNoticeSenderDao;
import com.qxiao.wx.notice.jpa.entity.QmNoticeImage;
import com.qxiao.wx.notice.jpa.entity.QmNoticeInfo;
import com.qxiao.wx.notice.jpa.entity.QmNoticeReadConfirm;
import com.qxiao.wx.notice.jpa.entity.QmNoticeSender;
import com.qxiao.wx.notice.jpa.service.IQmNoticeInfoService;
import com.qxiao.wx.notice.sqlconst.SQLConst;
import com.qxiao.wx.notice.vo.SenderVO;
import com.qxiao.wx.user.jpa.dao.QmMessageSendDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmMessageSend;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmNoticeInfoServiceImpl extends AbstractJdbcService<QmNoticeInfo> implements IQmNoticeInfoService {

	@Autowired
	private QmNoticeInfoDao noticeInfoDao;
	@Autowired
	private QmNoticeImageDao imageDao;
	@Autowired
	private QmNoticeSenderDao senderDao;
	@Autowired
	private QmNoticeReadConfirmDao confirmDao;
	@Autowired
	private QmStudentDao studentDao;
	@Autowired
	private QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	private QmPlaySchoolInfoDao schoolDao;
	@Autowired
	private QmPatriarchDao patriarchDao;
	@Autowired
	private QmMessageSendDao messageDao;
	@Autowired
	private GetIdentityService idService;
	@Autowired
	QmClassStudentDao classStudentDao;

	@Override
	public JPADao<QmNoticeInfo> getDao() {
		return this.noticeInfoDao;
	}

	@Override
	public Class<QmNoticeInfo> getEntityClass() {
		return QmNoticeInfo.class;
	}

	public List<Object> query() {
		List<QmMessageSend> message = messageDao.findByType(1);
		List<Object> li = new ArrayList<>();
		for (QmMessageSend qms : message) {
			List<QmNoticeSender> noticeSender = senderDao.findByNoticeIdAndOpenId(qms.getMessageId(), qms.getOpenId());
			if (CollectionUtils.isEmpty(noticeSender)) {
				continue;
			}
			for (QmNoticeSender qns : noticeSender) {
				List<QmStudent> student = studentDao.findByOpenIdAndClassId(qms.getOpenId(), qns.getSerderId());
				if (CollectionUtils.isEmpty(student)) {
					continue;
				}
				for (QmStudent qs : student) {
					Map<String, Object> map = new HashMap<>();
					map.put("studentId", qs.getStudentId());
					map.put("studentName", qs.getStudentName());
					li.add(map);
				}
			}
		}
		return li;
	}

	@Override
	// 新建通知
	public QmNoticeInfo addNoticeInfo(String openId, String title, String textContent, JSONArray images,
			int needConfirm, JSONArray senders, int clockType, String clockTime) throws Exception {

		QmNoticeInfo noticeInfo = new QmNoticeInfo();
		noticeInfo.setTextContent(textContent);
		noticeInfo.setTitle(title);
		noticeInfo.setClockType(clockType);
		noticeInfo.setNeedConfirm(needConfirm);
		if (clockType == 1) {
			noticeInfo.setClockTime(DateUtil.parsrTime(clockTime));
		}
		noticeInfo.setOpenId(openId);
		noticeInfo.setMessageSend(1);
		noticeInfo.setIsDel(0);
		if (StringUtils.isNotEmpty(clockTime)) {
			noticeInfo.setClockTime(DateUtil.parsrTime(clockTime));
		}
		noticeInfo.setSendTime(new Date());
		noticeInfo.setPostTime(new Date());
		QmNoticeInfo info = noticeInfoDao.save(noticeInfo);
		// 添加图片
		if (images != null && images.length() > 0) {
			this.addImage(images, info);
		}
		// 添加发送对象
		this.addNoticeSender(senders, info);
		this.sendMessage(info, senders);
		return info;
	}

	private void sendMessage(QmNoticeInfo info, JSONArray senders) {
		Iterator<Object> it = senders.iterator();
		while (it.hasNext()) {
			String str = it.next().toString();
			SenderVO senderVO = JsonMapper.obj2Instance(str, SenderVO.class);
			if (senderVO.getSendType() == 1) {// 1班级 2 老师
				List<QmPatriarch> patriarchs = patriarchDao.findByClassId(senderVO.getSenderId());
				for (QmPatriarch par : patriarchs) {
					if (StringUtils.isNotBlank(par.getOpenId()))
						this.templateMessage(info, par.getOpenId());
				}
				List<QmPlaySchoolTeacher> teacher = teacherDao.findByClassId(senderVO.getSenderId());
				// 本班的老师openId添加进推送表中
				for (QmPlaySchoolTeacher teach : teacher) {
					if (StringUtils.isNotBlank(teach.getOpenId()))
						this.templateMessage(info, teach.getOpenId());
				}
			} else {
				QmPlaySchoolTeacher teacher = teacherDao.findByTeacherId(senderVO.getSenderId());
				if(teacher!=null&&StringUtils.isNotBlank(teacher.getOpenId())) {
					this.templateMessage(info, teacher.getOpenId());
				}
			}
		}
	}

	private void templateMessage(QmNoticeInfo info, String openId) {
		QmMessageSend message = messageDao.findByMessageIdAndOpenIdAndType(info.getNoticeId(), openId, 1);
		if (message == null) {
			message = new QmMessageSend();
			message.setMessageId(info.getNoticeId());
			message.setType(1);
			message.setOpenId(openId);
			message.setResult(0);
			message.setTitle(info.getTitle());
			message.setTextContent(info.getTextContent().length() > 50 ? info.getTextContent().substring(0, 50)
					: info.getTextContent());
			message.setPostTime(new Date());
			messageDao.save(message);
		}
	}

	private void addNoticeSender(JSONArray senders, QmNoticeInfo info) {
		List<QmNoticeSender> sends = new ArrayList<>();
		Iterator<Object> it = senders.iterator();
		while (it.hasNext()) {
			String str = it.next().toString();
			SenderVO senderVO = JsonMapper.obj2Instance(str, SenderVO.class);
			QmNoticeSender sender = new QmNoticeSender();
			sender.setNoticeId(info.getNoticeId());
			sender.setSenderId(senderVO.getSenderId());
			sender.setSenderType(senderVO.getSendType());
			sender.setPostTime(new Date());
			if (sender.getSenderType() == 2) {
				info.setNeedConfirm(1);
				noticeInfoDao.save(info);
			}
			sends.add(sender);
		}
		senderDao.save(sends);
	}

	private QmNoticeImage addImage(JSONArray images, QmNoticeInfo info) {
		List<QmNoticeImage> imageList = new ArrayList<>();
		Iterator<Object> it = images.iterator();
		while (it.hasNext()) {
			String imageUrl = it.next().toString();
			QmImage img = JsonMapper.obj2Instance(imageUrl, QmImage.class);
			QmNoticeImage image = new QmNoticeImage();
			image.setNoticeId(info.getNoticeId());
			image.setImageUrl(img.getImageUrl());
			image.setSmallUrl(img.getImageUrl());
			image.setPostTime(new Date());
			imageList.add(image);
		}
		Iterable<QmNoticeImage> save = imageDao.save(imageList);
		Iterator<QmNoticeImage> iterator = save.iterator();
		return iterator.next();
	}

	@Override
	// 定时推送通知
	public void sendNotice(String openId, Long noticeId) {
		QmNoticeInfo noticeInfo = noticeInfoDao.findOne(noticeId);
		noticeInfo.setMessageSend(1);
		noticeInfo.setSendTime(new Date());
		noticeInfoDao.save(noticeInfo);
	}

	@Override
	// 查询所有通知
	public DataPage<QmNoticeInfoDTO> noticeQuery(String openId, Long classId, Long studentId, int type, int page,
			int pageSize) throws ServiceException {
		List<QmNoticeInfoDTO> infoDTOs = null;
		DataPage<QmNoticeInfoDTO> dataPage = null;
		// 查询班级通告
		if (type == 0) {
			dataPage = this.queryByclassId(classId, openId, page, pageSize, studentId);
			infoDTOs = (List<QmNoticeInfoDTO>) dataPage.getData();
		}
		// 查询自己的
		if (type == 1) {
			dataPage = this.queryMyNotice(openId, classId, page, pageSize);
			infoDTOs = (List<QmNoticeInfoDTO>) dataPage.getData();
		}
		if (CollectionUtils.isNotEmpty(infoDTOs)) {
			for (QmNoticeInfoDTO dto : infoDTOs) {
				dto.setClassUnreadCount(dto.getClassUnreadCount() > 0 ? dto.getClassUnreadCount() : 0);
				dto.setTotalCount();
				dto.setClassId(classId);
				dto.setStudentId(studentId);
				// 获取推送人姓名
				this.getSender(dto);
				this.getStatus(dto.getNoticeId(), dto, studentId);
			}
			// 获取图片
			this.getImageUrl(infoDTOs);
			dataPage.setData(infoDTOs);
		} else {
			dataPage.setData(new ArrayList<>());
		}
		return dataPage;
	}

	private void getStatus(Long noticeId, QmNoticeInfoDTO dto, Long studentId) {
		QmStudent stu = studentDao.findOne(studentId);
		if (stu == null) {
			dto.setStatus(1);
		} else {
			QmNoticeReadConfirm confirm = confirmDao.findByNoticeIdAndStudentId(noticeId, stu.getStudentId());
			dto.setStatus(confirm == null ? 0 : 1);
		}
	}

	// 按班级查询通知 O
	private DataPage<QmNoticeInfoDTO> queryByclassId(Long classId, String openId, int page, int pageSize,
			Long studentId) throws ServiceException {
		UserInfo userInfo = idService.getIdentity(openId, studentId);
		int type = userInfo.getType();
		if (type == 2)
			// 查询发送到班级的通知 + 老师的通知、 老师端
			return (DataPage<QmNoticeInfoDTO>) this.findSendClassAndTeach(openId, classId, page, pageSize);
		if (type == 3)
			// 查询发送到班级的通知、家长端
			return (DataPage<QmNoticeInfoDTO>) this.findSendClass(classId, page, pageSize);
		else
			// 园长、管理员
			return (DataPage<QmNoticeInfoDTO>) super.getPage(SQLConst.queryAll(), page, pageSize,
					new Object[] { classId, userInfo.getSchoolId() }, QmNoticeInfoDTO.class);
	}

	// 获取发送人姓名
	private void getSender(QmNoticeInfoDTO dto) {
		QmNoticeInfo noticeInfo = noticeInfoDao.findOne(dto.getNoticeId());
		String openId = noticeInfo.getOpenId();
		QmPlaySchoolTeacher teacher = teacherDao.findByOpenId(openId);
		if (teacher != null) {
			dto.setName(teacher.getTeacherName() + "(老师)");
			dto.setPersonType(2);
		} else {
			QmPlaySchoolInfo school = schoolDao.findByOpenId(openId);
			if (school != null) {
				dto.setName(school.getLeaderName() + (school.getType() == 1 ? "(校长)" : "(园长)"));
				dto.setPersonType(1);
			}
		}
	}

	// 查询发送到班级的通知 + 老师的通知、老师端 1
	private DataPage<QmNoticeInfoDTO> findSendClassAndTeach(String openId, Long classId, int page, int pageSize)
			throws ServiceException {
		return (DataPage<QmNoticeInfoDTO>) super.getPage(SQLConst.teacherQuery(), page, pageSize,
				new Object[] { classId, openId }, QmNoticeInfoDTO.class);
	}

	// 查询发送到班级的通知、家长端 1
	private DataPage<QmNoticeInfoDTO> findSendClass(Long classId, int page, int pageSize) throws ServiceException {
		return (DataPage<QmNoticeInfoDTO>) super.getPage(SQLConst.findSendClass(), page, pageSize,
				new Object[] { classId }, QmNoticeInfoDTO.class);
	}

	// O
	private DataPage<QmNoticeInfoDTO> queryMyNotice(String openId, Long classId, int page, int pageSize)
			throws ServiceException {
		return (DataPage<QmNoticeInfoDTO>) this.getPage(SQLConst.queryTeacherNotice(), page, pageSize,
				new Object[] { classId, openId, openId }, QmNoticeInfoDTO.class);
	}

	// 获取图片信息
	private void getImageUrl(List<QmNoticeInfoDTO> infoDTOs) {
		for (QmNoticeInfoDTO infoDTO : infoDTOs) {
			QmNoticeImage image = imageDao.findByNoticeIdLimit(infoDTO.getNoticeId());
			if (image != null)
				infoDTO.setTopImage(image.getImageUrl());
		}
	}

	@Override
	// 通知详情
	public QmNoticeDetailDTO noticeDetail(String openId, Long noticeId, Long classId, Long studentId) {
		QmNoticeDetailDTO infoDTO = null;
		// 添加阅读记录
		QmStudent stu = studentDao.findOne(studentId);
		QmNoticeInfo findOne = noticeInfoDao.findOne(noticeId);
		if (stu != null) {
			// 家长端查询通知详情 0
			QmNoticeReadConfirm confirm = confirmDao.findByNoticeIdAndStudentId(noticeId, studentId);
			if (confirm == null) {
				confirm = new QmNoticeReadConfirm();
				confirm.setConfirmFlag(0);
				confirm.setNoticeId(noticeId);
				confirm.setPostTime(new Date());
				confirm.setStudentId(studentId);
				confirm.setOpenId(openId);
				confirm = confirmDao.save(confirm);
			}
			infoDTO = this.findForClass(classId, noticeId);
			infoDTO.setConfirmFlag(confirm.getConfirmFlag());
		} else {
			QmPlaySchoolTeacher teacher = teacherDao.findByOpenId(openId);
			if (teacher != null) {
				if (teacher.getType() == 0)
					// 管理端通知详情 O
					infoDTO = this.findWithLeader(noticeId, openId, classId, studentId);
				else
					// 带班老师 1
					infoDTO = this.findWithTeach(noticeId, classId);
			} else
				// 园长端通知详情
				infoDTO = this.findWithLeader(noticeId, openId, classId, studentId);
			if (infoDTO != null)
				infoDTO.setConfirmFlag(0);
		}
		// 获取图片
		if (infoDTO != null) {
			infoDTO.setTotalCount();
			this.setDetailWithImageUrl(infoDTO);
		}
		infoDTO.setIsDel(findOne.getIsDel());
		return infoDTO;
	}

	private QmNoticeDetailDTO findWithLeader(Long noticeId, String openId, Long classId, Long studentId) {
		List<QmNoticeSender> senders = senderDao.findByNoticeId(noticeId);
		UserInfo userinfo = idService.getIdentity(openId, studentId);
		if (userinfo == null)
			return null;
		if (senders.get(0).getSenderType() == 2)
			// 只发给老师 0
			return this.findWithSchool(noticeId);
		// 查询的结果应是所有班级的集合 0
		return this.findWithClass(noticeId, classId);
	}

	private QmNoticeDetailDTO findWithSchool(Long noticeId) {
		List<QmNoticeDetailDTO> infoDTO = ((List<QmNoticeDetailDTO>) super.findList(SQLConst.findWithSchool(),
				new Object[] { noticeId }, QmNoticeDetailDTO.class));
		if (CollectionUtils.isNotEmpty(infoDTO))
			return infoDTO.get(0);
		return null;
	}

	private QmNoticeDetailDTO findWithClass(Long noticeId, Long classId) {
		List<QmNoticeDetailDTO> infoDTO = ((List<QmNoticeDetailDTO>) super.findList(SQLConst.findWithClass(),
				new Object[] { noticeId, classId }, QmNoticeDetailDTO.class));
		if (CollectionUtils.isNotEmpty(infoDTO))
			return infoDTO.get(0);
		return null;
	}

	// 通知详情图片信息
	private void setDetailWithImageUrl(QmNoticeDetailDTO infoDTO) {
		List<QmNoticeImage> images = imageDao.findByNoticeId(infoDTO.getNoticeId());
		List<Map<String, String>> mpas = new ArrayList<>();
		for (QmNoticeImage image : images) {
			Map<String, String> map = new HashMap<>();
			map.put("imageUrl", image.getImageUrl());
			mpas.add(map);
		}
		infoDTO.setImages(mpas);
	}

	// 老师端通知详情
	private QmNoticeDetailDTO findWithTeach(Long noticeId, Long classId) {
		QmNoticeSender sender = senderDao.findByNoticeId(noticeId, classId);
		QmNoticeDetailDTO infoDTO = sender != null ? this.findForClass(classId, noticeId)
				: this.findForTeacher(noticeId);
		return infoDTO;
	}

	// 查询发送到老师端通知
	private QmNoticeDetailDTO findForTeacher(Long noticeId) {
		List<QmNoticeDetailDTO> infoDTO = ((List<QmNoticeDetailDTO>) super.findList(SQLConst.findWithTeacher(),
				new Object[] { noticeId }, QmNoticeDetailDTO.class));
		if (CollectionUtils.isNotEmpty(infoDTO))
			return infoDTO.get(0);
		return null;
	}

	// 家长端查询通知详情 O
	private QmNoticeDetailDTO findForClass(Long classId, Long noticeId) {
		List<QmNoticeDetailDTO> findList = (List<QmNoticeDetailDTO>) super.findList(SQLConst.findWithPatriarch(),
				new Object[] { noticeId, classId }, QmNoticeDetailDTO.class);
		QmNoticeDetailDTO infoDTO = null;
		if (CollectionUtils.isNotEmpty(findList))
			infoDTO = findList.get(0);
		return infoDTO;
	}

	@Override
	// 删除通知
	@Transactional
	public void deleteNotice(String openId, Long noticeId, Long classId) {
		QmNoticeInfo noticeInfo = noticeInfoDao.findOne(noticeId);
		noticeInfo.setIsDel(1);
		noticeInfoDao.save(noticeInfo);
	}

	@Override
	// 通知确认
	@Transactional
	public void insertConfirm(Long studentId, Long noticeId,String openId) {
		QmStudent stu = studentDao.findOne(studentId);
		if (stu != null) {
			QmNoticeReadConfirm confirm = confirmDao.findByNoticeIdAndStudentId(noticeId, stu.getStudentId());
			if (confirm != null && confirm.getConfirmFlag() == 0) {
				confirm.setConfirmFlag(1);
				confirmDao.save(confirm);
			} else {
				confirm = new QmNoticeReadConfirm();
				confirm.setNoticeId(noticeId);
				confirm.setConfirmFlag(1);
				confirm.setOpenId(openId);
				confirm.setStudentId(studentId);
				confirm.setPostTime(new Date());
				confirmDao.save(confirm);
			}
		}
	}

	@Override
	// 公告阅读人员查询
	public QmNoticeInfoReadDTO noticeReaders(String openId, Long noticeId, Long classId, int readFlag) {
		List<QmNoticeSender> senders = senderDao.findByNoticeId(noticeId);
		QmNoticeSender sender = senders.get(0);
		if (sender.getSenderType() == 2) {
			return null;
		}
		QmNoticeInfoReadDTO readDTO = this.findNoticeInfoReaders(noticeId);
		this.findForReaders(readDTO, openId, noticeId, classId, readFlag);
		return readDTO;
	}

	private void findForReaders(QmNoticeInfoReadDTO readDTO, String openId, Long noticeId, Long classId, int readFlag) {
		List<QmNoticeReadDTO> readers = this.readInfo(classId, noticeId);
		List<QmNoticeReadDTO> unReaders = this.unReadInfo(classId, noticeId);
		readDTO.setReadCount(CollectionUtils.isNotEmpty(readers) ? readers.size() : 0);
		readDTO.setUnReadCount(CollectionUtils.isNotEmpty(unReaders) ? unReaders.size() : 0);
		readDTO.setReaders(readFlag == 0 ? readers : unReaders);
	}

	private List<QmNoticeReadDTO> unReadInfo(Long classId, Long noticeId) {
		List<QmNoticeReadDTO> findList = (List<QmNoticeReadDTO>) this.findList(SQLConst.findUnReader(),
				new Object[] { classId, noticeId }, QmNoticeReadDTO.class);
		if (CollectionUtils.isNotEmpty(findList)) {
			return findList;
		}
		return new ArrayList<>();
	}

	private List<QmNoticeReadDTO> readInfo(Long classId, Long noticeId) {
		List<QmNoticeReadDTO> findList = (List<QmNoticeReadDTO>) this.findList(SQLConst.findReader(),
				new Object[] { classId, noticeId }, QmNoticeReadDTO.class);
		if (CollectionUtils.isNotEmpty(findList)) {
			return findList;
		}
		return new ArrayList<>();
	}

	private QmNoticeInfoReadDTO findNoticeInfoReaders(Long noticeId) {
		QmNoticeInfo info = noticeInfoDao.findOne(noticeId);
		QmNoticeInfoReadDTO readDTO = null;
		if (info != null) {
			readDTO = new QmNoticeInfoReadDTO();
			readDTO.setNoticeId(noticeId);
			readDTO.setTitle(info.getTitle());
			readDTO.setPostTime(info.getPostTime());
		}
		return readDTO;
	}

	@Override
	public List<QmNoticeInfo> findByClockTime(Date date) {
		return noticeInfoDao.findByClockTime(date);
	}

	@Override
	public boolean update(List<QmNoticeInfo> notices) {
		Iterable<QmNoticeInfo> iterable = noticeInfoDao.save(notices);
		Iterator<QmNoticeInfo> it = iterable.iterator();
		while (it.hasNext()) {
			return true;
		}
		return false;
	}
}
