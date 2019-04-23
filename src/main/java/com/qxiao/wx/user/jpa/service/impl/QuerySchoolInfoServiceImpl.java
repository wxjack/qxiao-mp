package com.qxiao.wx.user.jpa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.service.QuerySchoolInfoService;
import com.qxiao.wx.user.vo.QueryBySchoolCodeVo;
import com.qxiao.wx.user.vo.SchoolInfoVo;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QuerySchoolInfoServiceImpl extends AbstractJdbcService<QmPlaySchoolInfo>
		implements QuerySchoolInfoService {

	@Autowired
	QmPlaySchoolInfoDao infoDao;
	@Autowired
	QmPlaySchoolTeacherDao teacherDao;

	public SchoolInfoVo queryByOpenId(String openId) throws Exception {
		QmPlaySchoolInfo findByOpenId = infoDao.findByOpenId(openId);
		SchoolInfoVo vo = new SchoolInfoVo();
		if (findByOpenId != null) {
			vo.setLeaderName(findByOpenId.getLeaderName());
			vo.setLocation(findByOpenId.getLocation());
			vo.setOpenId(findByOpenId.getOpenId());
			vo.setPostTime(findByOpenId.getPostTime());
			vo.setSchoolCode(findByOpenId.getSchoolCode());
			vo.setSchoolId(findByOpenId.getSchoolId());
			vo.setSchoolName(findByOpenId.getSchoolName());
			vo.setTel(findByOpenId.getTel());
			vo.setType(findByOpenId.getType());
			if (findByOpenId.getIsOpen() == 0) {
				vo.setIsOpen(true);
			} else {
				vo.setIsOpen(false);
			}
			return vo;

		} else {
			Long id = teacherDao.findSchoolId(openId);
			QmPlaySchoolTeacher teacher = teacherDao.findByOpenId(openId);
			QmPlaySchoolInfo findOne = infoDao.findOne(id);
			vo.setLeaderName(findOne.getLeaderName());
			vo.setLocation(findOne.getLocation());
			vo.setOpenId(findOne.getOpenId());
			vo.setPostTime(findOne.getPostTime());
			vo.setSchoolCode(findOne.getSchoolCode());
			vo.setSchoolId(findOne.getSchoolId());
			vo.setSchoolName(findOne.getSchoolName());
			vo.setTel(findOne.getTel());
			vo.setType(findOne.getType());
			vo.setTel(teacher.getTel());
			vo.setLeaderName(teacher.getTeacherName());
			if (findOne.getIsOpen() == 0) {
				vo.setIsOpen(true);
			} else {
				vo.setIsOpen(false);
			}
			return vo;
		}
	}

	@SuppressWarnings("unchecked")
	public QueryBySchoolCodeVo queryBySchoolCode(String openId) {
		String sql = "SELECT info.school_name,info.location,info.school_code,st.tel,st.teacher_id,"
				+ "st.sex,st.teacher_name,qa.photo FROM qm_play_school_info AS info JOIN qm_play_school_class AS sc "
				+ "JOIN qm_class_teacher AS qct JOIN qm_play_school_teacher AS st JOIN qm_account AS qa "
				+ "WHERE info.school_id = sc.school_id AND sc.class_id = qct.class_id AND qct.teacher_id = st.teacher_id "
				+ "AND qa.open_id = st.open_id and st.open_id=? AND `status` = 0";
		List<QueryBySchoolCodeVo> findList = (List<QueryBySchoolCodeVo>) this.findList(sql, new Object[] { openId },
				QueryBySchoolCodeVo.class);
		if (findList.size() > 0) {
			return findList.get(0);
		}
		return null;
	}

	public QmPlaySchoolInfo querySchoolInfo(String schoolCode) {
		QmPlaySchoolInfo findBySchoolCode = infoDao.findBySchoolCode(schoolCode);
		return findBySchoolCode;
	}

	@Override
	public JPADao<QmPlaySchoolInfo> getDao() {
		return infoDao;
	}

	@Override
	public Class<QmPlaySchoolInfo> getEntityClass() {
		return QmPlaySchoolInfo.class;
	}
}
