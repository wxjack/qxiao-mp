package com.qxiao.wx.user.jpa.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.community.jpa.entity.QmClassStudent;
import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.dao.QmClockInfoDao;
import com.qxiao.wx.user.jpa.dao.QmClockStatDao;
import com.qxiao.wx.user.jpa.dao.QmMessageSendDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolClassDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.entity.QmClockInfo;
import com.qxiao.wx.user.jpa.entity.QmClockStat;
import com.qxiao.wx.user.jpa.entity.QmMessageSend;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.qxiao.wx.user.jpa.service.PunchService;
import com.qxiao.wx.user.vo.ClassClockQueryVo;
import com.qxiao.wx.user.vo.CountClockStatVo;
import com.qxiao.wx.user.vo.QueryPunchTime;
import com.qxiao.wx.user.vo.RealShuttleVo;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.talkweb.weixin.main.StartOnLoad;

@Service
public class PunchServiceImpl extends AbstractJdbcService<QmClockInfo> implements PunchService {

	@Autowired
	QmClockInfoDao infoDao;
	@Autowired
	QmClockStatDao statDao;
	@Autowired
	QmStudentDao studentDao;
	@Autowired
	QmClassStudentDao classStudentDao;
	@Autowired
	QmPlaySchoolInfoDao schoolInfoDao;
	@Autowired
	QmPlaySchoolClassDao classDao;
	@Autowired
	QmPatriarchDao patriarchDao;
	@Autowired
	QmMessageSendDao messageDao;
	@Autowired
	QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	QmAccountDao accountDao;

	public Integer queryPunch(Long studentId) throws Exception {
		QmStudent qmStudent = studentDao.findByStudentId(studentId);
//		if(qmStudent!=null&&StringUtils.isEmpty(qmStudent.getNfcId())) {
//			return 1;
//			//throw new Exception("暂时不能打卡，请联系管理员");
//		}
		if (qmStudent != null && StringUtils.isNotEmpty(qmStudent.getNfcId())) {
			this.punchClock(0, qmStudent.getNfcId(), "");
			return 0;
		}
		if (qmStudent != null && StringUtils.isNotEmpty(qmStudent.getNfcId2())) {
			this.punchClock(0, qmStudent.getNfcId2(), "");
			return 0;
		}
		if (qmStudent != null && StringUtils.isNotEmpty(qmStudent.getIbeaconId())) {
			this.punchClock(1, "", qmStudent.getIbeaconId());
			return 0;
		}
		return -1;
	}

	public List<QueryPunchTime> queryStudentTime(Long studentId, String date) {
		String sql = "SELECT DATE_FORMAT(qci.post_time, '%H:%i') AS postTime, qs.student_name "
				+ "FROM qm_clock_info AS qci LEFT JOIN qm_student AS qs ON ( qci.nfc_id = qs.nfc_id "
				+ "OR qci.ibeacon_id = qs.ibeacon_id ) WHERE qs.student_id = ? AND DATE_FORMAT(qci.post_time, '%Y-%m-%d') = ?";
		List<QueryPunchTime> findList = (List<QueryPunchTime>) this.findList(sql, new Object[] { studentId, date },
				QueryPunchTime.class);
		return findList;
	}

	public Map<String, Object> queryCount(Long classId, String date) {
		String sql = "SELECT DISTINCT(qcs.student_id) FROM qm_class_student AS qcs join qm_clock_info as qci "
				+ "join qm_student as qs where  qcs.student_id=qs.student_id "
				+ "and (qci.ibeacon_id=qs.ibeacon_id or qci.nfc_id=qs.nfc_id) and qcs.class_id= ? "
				+ "and DATE_FORMAT(qci.post_time, '%Y-%m-%d')= ?";
		List<QmStudent> findList = (List<QmStudent>) this.findList(sql, new Object[] { classId, date },
				QmStudent.class);
		List<Object> li = new ArrayList<>();
		for (QmStudent qs : findList) {
			List<QueryPunchTime> queryStudentTime = this.queryStudentTime(qs.getStudentId(), date);
			for (QueryPunchTime qpt : queryStudentTime) {
				li.add(qpt);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("count", findList.size());
		map.put("info", li);
		return map;
	}

	public Map<String, Object> queryNotPunch(Long classId, String date) {
		String sql = "SELECT qs.student_name FROM qm_student AS qs join qm_class_student as qsc "
				+ "WHERE  not exists ( SELECT * FROM qm_clock_info AS qci WHERE qs.ibeacon_id = ibeacon_id "
				+ "or qs.nfc_id=nfc_id and DATE_FORMAT(post_time,'%Y-%m-%d')= ?) "
				+ "and qs.student_id=qsc.student_id and qsc.class_id=? ";
		List<QmStudent> findList = (List<QmStudent>) this.findList(sql, new Object[] { date, classId },
				QmStudent.class);
		Map<String, Object> map = new HashMap<>();
		List<Object> li = new ArrayList<>();
		map.put("count", findList.size());
		for (QmStudent qs : findList) {
			Map<String, Object> ma = new HashMap<>();
			ma.put("studentName", qs.getStudentName());
			ma.put("time", "");
			li.add(ma);
		}
		map.put("info", li);
		return map;
	}

	public List<RealShuttleVo> realShuttle(String openId, Long classId, String date) {
		String sql = "SELECT qs.student_id,qs.photo,qs.url,qs.student_name,info.broadcast,info.post_time,info.clock_id "
				+ "FROM qm_student AS qs LEFT JOIN qm_class_student AS qcs ON qs.student_id = qcs.student_id "
				+ "LEFT JOIN qm_clock_info AS info ON (qs.ibeacon_id = info.ibeacon_id OR qs.nfc_id = info.nfc_id) "
				+ "WHERE qcs.class_id =? AND info.post_time BETWEEN ? AND ? and info.broadcast < ? ORDER BY info.broadcast,info.post_time ASC LIMIT 0,2";
		List<RealShuttleVo> findList = (List<RealShuttleVo>) this.findList(sql, new Object[] { classId,
				date + " " + StartOnLoad.START_TIME, date + " " + StartOnLoad.END_TIME, StartOnLoad.REALSHUTTLE },
				RealShuttleVo.class);
		if (findList.size() > 0) {
			for (RealShuttleVo vo : findList) {
				QmClockInfo one = infoDao.findOne(vo.getClockId());
				QmClockInfo info = new QmClockInfo();
				BeanUtils.copyProperties(one, info);
				info.setBroadcast(one.getBroadcast() + 1);
				vo.setStudentName(vo.getStudentName() + " 已打卡");
				vo.setUrl(vo.getUrl());
				infoDao.save(info);
			}
		}
		return findList;
	}

	public List<ClassClockQueryVo> classClockQuery(String date, Long classId) {
		String sql = "SELECT DISTINCT(student.student_id),student.student_name FROM qm_student AS student "
				+ "left JOIN qm_class_student AS cs ON student.student_id = cs.student_id AND student.`status` = 0 "
				+ "JOIN qm_patriarch_student AS qps ON student.student_id = qps.student_id "
				+ "JOIN qm_patriarch AS qp ON qps.patriarch_id = qp.id JOIN qm_account AS qa ON qp.open_id = qa.open_id "
				+ "WHERE cs.class_id = ? ";
		String sql2 = "SELECT qci.* FROM qm_student AS qs LEFT JOIN qm_clock_info AS qci ON ( "
				+ "	qs.nfc_id = qci.nfc_id 	OR qs.ibeacon_id = qci.ibeacon_id ) WHERE "
				+ "	qs.student_id = ? AND DATE_FORMAT(qci.post_time, '%Y-%m-%d') = ?";
		List<ClassClockQueryVo> list = (List<ClassClockQueryVo>) this.findList(sql, new Object[] { classId },
				ClassClockQueryVo.class);
		if (list.size() > 0) {
			for (ClassClockQueryVo vo : list) {
				List<?> findList = this.findList(sql2, new Object[] { vo.getStudentId(), date }, QmClockInfo.class);
				List<QmAccount> qmAccount = accountDao.findByStudentId(vo.getStudentId());
				if (CollectionUtils.isNotEmpty(qmAccount)) {
					vo.setPhoto(qmAccount.get(0).getPhoto());
				}
				if (findList.size() > 0) {
					vo.setClockFlag(1);
					vo.setBroadcast(1);
				} else {
					vo.setClockFlag(0);
					vo.setBroadcast(0);
				}
			}
		}
		return list;
	}

	public Map<String, Object> punchClock(Integer type, String nfcId, String ibeaconId) throws Exception {
		// QmStudent student = studentDao.findByNfcId(nfcId, ibeaconId);
		QmStudent student = null;
		if (type == 0) {
			student = studentDao.findByNfcIdOrNfcId2(nfcId, nfcId);
		}
		if (type == 1) {
			student = studentDao.findByIbeaconId(ibeaconId);
		}
		if (student == null) {
			throw new Exception("此卡无效");
		}
		QmClockInfo info = new QmClockInfo();
		info.setIbeaconId(ibeaconId);
		info.setNfcId(nfcId);
		info.setType(type);
		info.setBroadcast(0);// 播报次数
		info.setMessageSend(0);
		info.setSendTime(new Date());
		info.setPostTime(new Date());
		infoDao.save(info);
		List<QmPatriarch> patriarch = patriarchDao.findBynfcIdOrNfcId2(nfcId, nfcId);
		if (patriarch.size() > 0) {
			for (QmPatriarch qp : patriarch) {
				if (qp.getOpenId() != null) {
					QmMessageSend qms = new QmMessageSend();
					qms.setMessageId(info.getClockId());
					qms.setOpenId(qp.getOpenId());
					qms.setResult(0);
					qms.setType(type);
					qms.setTitle("考勤消息");
					qms.setTextContent("您的孩子：" + student.getStudentName() + "在"
							+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(Calendar.getInstance().getTime())
							+ "已打卡");
					qms.setType(4);
					qms.setPostTime(new Date());
					messageDao.save(qms);
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("studentName", student.getStudentName());
		if (student.getPhoto() == null) {
			map.put("photo", "");
		} else {
			map.put("photo", student.getPhoto());
		}
		map.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(Calendar.getInstance().getTime()));
		return map;
	}

	public List<String> clockDate(String openId, String date) {
		List<Object> list = infoDao.queryClockInfoByDate(openId, date);
		List<String> li = new ArrayList<>();
		if (list.size() > 0) {
			for (Object info : list) {
				li.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(info));
			}
		}
		return li;
	}

	public List<QmClockInfo> clockQuery(String openId, Long studentId, String month) {
		String sql = "SELECT DISTINCT DATE_FORMAT(info.post_time, '%Y-%m-%d') as post_time FROM qm_patriarch AS qp "
				+ "left JOIN qm_patriarch_student AS qps on qp.id = qps.patriarch_id "
				+ "left JOIN qm_student AS qs on qps.student_id = qs.student_id "
				+ "left JOIN qm_clock_info AS info on ( qs.nfc_id = info.nfc_id OR qs.ibeacon_id = info.ibeacon_id ) "
				+ "WHERE qp.open_id =?  AND DATE_FORMAT(info.post_time, '%Y-%m') = ? order by post_time desc";
		List<QmClockInfo> findList = (List<QmClockInfo>) this.findList(sql, new Object[] { openId, month },
				QmClockInfo.class);
		return findList;
	}

	public void saveClockStat(String date) {
		String sql = "select * from qm_play_school_info";
		List<QmPlaySchoolInfo> findAll = (List<QmPlaySchoolInfo>) this.findList(sql, new Object[] {},
				QmPlaySchoolInfo.class);
		if (findAll.size() > 0) {
			for (QmPlaySchoolInfo in : findAll) {
				List<QmPlaySchoolClass> list = classDao.findBySchoolId(in.getSchoolId());
				if (list.size() > 0) {
					for (QmPlaySchoolClass cal : list) {
						QmClockStat stat = new QmClockStat();
						Integer countByClassId = statDao.countByClassId(cal.getClassId());
						stat.setClassId(cal.getClassId());
						stat.setClassCount(countByClassId);// 总人数
						List<QmClassStudent> byClassId = classStudentDao.findByClassId(cal.getClassId());
						int i = 0;
						if (byClassId.size() > 0) {
							for (QmClassStudent st : byClassId) {
								Integer countByStudentId = statDao.countByStudentId(st.getStudentId(), date);
								if (countByStudentId > 0) {
									i++;
								}
							}
							stat.setClockCount(i);// 实到人数
							float size = (float) i / countByClassId;
							DecimalFormat df = new DecimalFormat("0.0000");// 格式化小数，不足的补0
							String filesize = df.format(size);// 返回的是String类型的
							float parseFloat = Float.valueOf(filesize);
							float round = (float) Math.round(parseFloat * 100);
							stat.setClockRate(round);
							stat.setStatDate(date);
							stat.setPostTime(new Date());
							statDao.save(stat);
						}
					}
				}
			}
		}
	}

	public List<Object> queryStat(String date, String openId) {
		List<Long> id = this.queryClassIdByOpenId(openId);
		List<Object> li = new ArrayList<>();
		if (id.size() > 0) {
			for (Object classId : id) {
				List<QmClockStat> findByStatDate = statDao.findByStatDateAndClassId(date,
						Long.valueOf(classId.toString()));
				for (QmClockStat stat : findByStatDate) {
					CountClockStatVo vo = new CountClockStatVo();
					QmPlaySchoolClass findByClassId = classDao.findByClassId(stat.getClassId());
					BeanUtils.copyProperties(stat, vo);
					vo.setClassName(findByClassId.getClassName());
					li.add(vo);
				}
			}
		}

		return li;
	}

	public List<Long> queryClassIdByOpenId(String openId) {
		List<Long> list = new ArrayList<>();
		list = schoolInfoDao.findClassIdByOpenId(openId);
		if (list.size() > 0) {
			return list;
		}
		list = teacherDao.findClassIdByTeacherOpenId(openId);
		if (list.size() > 0) {
			return list;
		}
		return list;
	}

	@Override
	public JPADao<QmClockInfo> getDao() {
		return infoDao;
	}

	@Override
	public Class<QmClockInfo> getEntityClass() {
		return QmClockInfo.class;
	}
}
