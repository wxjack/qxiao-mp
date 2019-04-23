package com.qxiao.wx.user.jpa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.community.jpa.dao.QmClassTeacherDao;
import com.qxiao.wx.community.jpa.dao.QmCommunityInfoDao;
import com.qxiao.wx.community.jpa.entity.QmClassStudent;
import com.qxiao.wx.community.jpa.entity.QmClassTeacher;
import com.qxiao.wx.community.jpa.entity.QmCommunityInfo;
import com.qxiao.wx.user.jpa.dao.QmAccountDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolClassDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.qxiao.wx.user.jpa.service.SchoolClassService;
import com.qxiao.wx.user.vo.ClassStudentVo;
import com.qxiao.wx.user.vo.ClassTeacherVo;
import com.qxiao.wx.user.vo.QuerySchoolClassVo;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class SchoolClassServiceImpl extends AbstractJdbcService<QmPlaySchoolClass> implements SchoolClassService {

	@Autowired
	QmPlaySchoolClassDao classDao;
	@Autowired
	QmPlaySchoolTeacherDao teacherDao;
	@Autowired
	QmClassTeacherDao classTeacherDao;
	@Autowired
	QmStudentDao studentDao;
	@Autowired
	QmClassStudentDao classStudentDao;
	@Autowired
	QmPlaySchoolInfoDao infoDao;
	@Autowired
	QmPatriarchDao parDao;
	@Autowired
	QmCommunityInfoDao comInfoDao;
	@Autowired
	QmAccountDao accountDao;

	public List<Map<String, Object>> queryClassByPatroarch(Long id,Long studentId) {
		String sql = "SELECT qpsc.class_name,qpsc.class_id FROM qm_patriarch AS qp "
				+ "LEFT JOIN qm_patriarch_student AS qps on qp.id=qps.patriarch_id "
				+ "LEFT JOIN qm_student AS qs on qps.student_id=qs.student_id "
				+ "LEFT JOIN qm_class_student AS qcs on qcs.student_id=qs.student_id "
				+ "LEFT JOIN qm_play_school_class AS qpsc on qcs.class_id=qpsc.class_id WHERE qp.id = ? and qs.student_id=? GROUP BY qpsc.class_id";
		List<QmPlaySchoolClass> findList = (List<QmPlaySchoolClass>) this.findList(sql, new Object[] { id,studentId },
				QmPlaySchoolClass.class);
		List<Map<String, Object>> li = new ArrayList<>();
		if (findList.size() > 0) {
			for (QmPlaySchoolClass qpsc : findList) {
				Map<String, Object> map = new HashMap<>();
				map.put("classId", qpsc.getClassId());
				map.put("className", qpsc.getClassName());
				li.add(map);
			}
		}
		return li;
	}

	public List<Map<String, Object>> queryClassBySchool(Long schoolId) {
		List<QmPlaySchoolClass> findBySchoolId = classDao.findBySchoolId(schoolId);
		List<Map<String, Object>> li = new ArrayList<>();
		if (findBySchoolId.size() > 0) {
			for (QmPlaySchoolClass qpsc : findBySchoolId) {
				Map<String, Object> map = new HashMap<>();
				map.put("classId", qpsc.getClassId());
				map.put("className", qpsc.getClassName());
				li.add(map);
			}
		}
		return li;

	}

	public List<Map<String, Object>> queryClassByTeacher(Long teacherId) {
		String sql = "SELECT qpsc.class_name,qpsc.class_id FROM qm_class_teacher AS qct "
				+ "JOIN qm_play_school_class AS qpsc where qct.class_id=qpsc.class_id and qct.teacher_id=?";
		@SuppressWarnings("unchecked")
		List<QmPlaySchoolClass> findList = (List<QmPlaySchoolClass>) this.findList(sql, new Object[] { teacherId },
				QmPlaySchoolClass.class);
		List<Map<String, Object>> li = new ArrayList<>();
		if (findList.size() > 0) {
			for (QmPlaySchoolClass qpsc : findList) {
				Map<String, Object> map = new HashMap<>();
				map.put("className", qpsc.getClassName());
				map.put("classId", qpsc.getClassId());
				li.add(map);
			}
		}
		return li;
	}

	public void classAdd(String openId, Long schoolId, String className) {
		QmPlaySchoolClass schoolClass = new QmPlaySchoolClass();
		schoolClass.setClassName(className);
		schoolClass.setSchoolId(schoolId);
		schoolClass.setPostTime(new Date());
		schoolClass.setGrade(0);
		classDao.save(schoolClass);
	}

	@SuppressWarnings("unchecked")
	public void deleteSchoolClass(String openId, Long classId) throws Exception {
		String sql = "SELECT qmstudent.* FROM qm_student AS qmstudent "
				+ "JOIN qm_play_school_class AS school JOIN qm_class_student AS student "
				+ "WHERE qmstudent.student_id = student.student_id AND school.class_id = student.class_id "
				+ "AND student.class_id = ?";
		List<QmStudent> findList = (List<QmStudent>) this.findList(sql, new Object[] { classId }, QmStudent.class);
		String teacherSql = "SELECT teacherinfo.* FROM qm_play_school_teacher as teacherinfo "
				+ "JOIN qm_play_school_class AS school JOIN qm_class_teacher as teacher "
				+ "WHERE teacherinfo.teacher_id=teacher.teacher_id "
				+ "and teacher.class_id=school.class_id and school.class_id=? ";
		List<QmPlaySchoolTeacher> list = (List<QmPlaySchoolTeacher>) this.findList(teacherSql, new Object[] { classId },
				QmPlaySchoolTeacher.class);
		if (findList.size() > 0 || list.size() > 0) {
			throw new Exception("班级还有关联学生或者老师不能删除班级");
		}

		classDao.delete(classId);
	}

	@SuppressWarnings("unchecked")
	public List<ClassTeacherVo> queryTeacher(String openId, Long classId) {
		String sql = "SELECT teacher.teacher_id,teacher.teacher_name,"
				+ "account.photo,class.class_id FROM qm_play_school_teacher AS teacher "
				+ "left JOIN qm_class_teacher AS ct on teacher.teacher_id = ct.teacher_id "
				+ "LEFT JOIN qm_play_school_class AS class on ct.class_id = class.class_id "
				+ "LEFT join qm_account as account on teacher.open_id=account.open_id WHERE ct.class_id = ? and teacher.status=0";
		List<ClassTeacherVo> findList = (List<ClassTeacherVo>) this.findList(sql, new Object[] { classId },
				ClassTeacherVo.class);
		return findList;
	}

	public void deleteTeacher(String openId, Long classId, Long teacherId) {
		String sql = "select * from qm_class_teacher where class_id=? and teacher_id=?";
		@SuppressWarnings("unchecked")
		List<QmClassTeacher> findList = (List<QmClassTeacher>) this.findList(sql, new Object[] { classId, teacherId },
				QmClassTeacher.class);
		for (QmClassTeacher qct : findList) {
			classTeacherDao.delete(qct.getId());
		}
	}

	@SuppressWarnings("unchecked")
	public List<ClassStudentVo> queryStudent(String openId, Long classId) {
		String sql = "SELECT student.student_id,student.student_name,class.class_id FROM "
				+ "qm_student AS student JOIN qm_class_student AS class "
				+ "WHERE class.student_id= student.student_id AND class.class_id = ? and student.status=0";
		List<ClassStudentVo> findList = (List<ClassStudentVo>) this.findList(sql, new Object[] { classId },
				ClassStudentVo.class);
		for(ClassStudentVo vo:findList) {
			List<QmAccount> list = accountDao.findByStudentId(vo.getStudentId());
			if(CollectionUtils.isNotEmpty(list)) {
				vo.setPhoto(list.get(0).getPhoto());
			}
		}
		return findList;
	}

	@SuppressWarnings("unchecked")
	public void deleteStudent(String openId, Long classId, Long studentId) {
		String sql = "select * from qm_class_student  where class_id=? and student_id=?";
		List<QmClassStudent> findList = (List<QmClassStudent>) this.findList(sql, new Object[] { classId, studentId },
				QmClassStudent.class);
		List<QmPatriarch> patriarch = parDao.findByStudentId(studentId);
		for (QmPatriarch qp : patriarch) {
			qp.setIsDel(1);
			parDao.save(patriarch);
			List<QmCommunityInfo> list = comInfoDao.findByOpenId(qp.getOpenId());
			if (list.size() > 0) {
				for (QmCommunityInfo info : list) {
					info.setIsDel(1);
					comInfoDao.save(info);
				}
			}
			QmStudent student = studentDao.findByStudentId(studentId);
			student.setStatus(1);
			studentDao.save(student);
			QmAccount account = accountDao.findByOpenId(qp.getOpenId());
			if (account != null) {
				account.setPersonType(9);
				accountDao.save(account);
			}
		}
		if (findList.size() > 0) {
			for (QmClassStudent qcs : findList) {
				classStudentDao.delete(qcs.getId());
			}
		}
	}

	public List<Object> queryClass(Long schoolId) {
		List<QmPlaySchoolClass> findBySchoolId = classDao.findBySchoolId(schoolId);
		List<Object> li = new ArrayList<>();
		if (findBySchoolId.size() > 0) {
			for (QmPlaySchoolClass cl : findBySchoolId) {
				QuerySchoolClassVo vo = new QuerySchoolClassVo();
				List<QmClassTeacher> teacher = classTeacherDao.findByClassId(cl.getClassId());
				List<QmClassStudent> sd = classStudentDao.findByClassId(cl.getClassId());
				vo.setClassId(cl.getClassId());
				vo.setClassName(cl.getClassName());
				vo.setCountStudent(sd.size());
				vo.setCountTeacher(teacher.size());
				li.add(vo);
			}
		}
		return li;
	}

	public List<QmPlaySchoolClass> queryClassByTel(String tel) {
		String sql = "SELECT qpsc.* FROM qm_patriarch AS qp JOIN qm_patriarch_student as qps "
				+ "join qm_class_student as qcs join qm_play_school_class as qpsc where  qcs.student_id=qps.student_id "
				+ "and qpsc.class_id=qcs.class_id and qp.id=qps.patriarch_id AND qp.tel=?";
		@SuppressWarnings("unchecked")
		List<QmPlaySchoolClass> findList = (List<QmPlaySchoolClass>) this.findList(sql, new Object[] { tel },
				QmPlaySchoolClass.class);
		return findList;
	}

	public List<QmPlaySchoolClass> queryClassinfo(Long schoolId) {
		List<QmPlaySchoolClass> findBySchoolId = classDao.findBySchoolId(schoolId);
		return findBySchoolId;
	}

	public QmPlaySchoolClass queryClassById(Long classId) {
		QmPlaySchoolClass findByClassId = classDao.findByClassId(classId);
		return findByClassId;
	}

	@Override
	public JPADao<QmPlaySchoolClass> getDao() {
		return classDao;
	}

	@Override
	public Class<QmPlaySchoolClass> getEntityClass() {
		return QmPlaySchoolClass.class;
	}
}
