package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.spring.jpa.dao.JPADao;

public interface QmPlaySchoolClassDao extends JPADao<QmPlaySchoolClass> {

	List<QmPlaySchoolClass> findBySchoolId(Long schoolId);

	@Query(value = "SELECT qpsc.* FROM qm_play_school_class AS qpsc "
			+ "left JOIN qm_class_teacher AS qct on qpsc.class_id = qct.class_id "
			+ "left JOIN qm_play_school_teacher AS qpst on qct.teacher_id = qpst.teacher_id and qpst.`status` = 0 "
			+ "WHERE qpsc.class_id =?1", nativeQuery = true)
	QmPlaySchoolClass findByClassId(Long classId);

	@Query(value = "SELECT qsc.* FROM qm_play_school_class AS qsc "
			+ "JOIN qm_class_student AS qcs ON qsc.class_id = qcs.class_id "
			+ "JOIN qm_patriarch_student AS qps ON qps.student_id = qcs.student_id "
			+ "JOIN qm_patriarch AS qp ON qp.id = qps.patriarch_id WHERE qp.open_id = ?1 "
			+ "GROUP BY qsc.class_id", nativeQuery = true)
	List<QmPlaySchoolClass> findWithPatriarch(String openId);

	@Query(value = "SELECT qsc.* FROM qm_play_school_class AS qsc join qm_class_teacher as qct on qsc.class_id=qct.class_id " + 
			"JOIN qm_play_school_teacher AS qpsc ON qpsc.teacher_id = qct.teacher_id WHERE " + 
			"	qpsc.open_id = ? GROUP BY qsc.class_id ", nativeQuery = true)
	List<QmPlaySchoolClass> findWithTeacher(String openId);

	@Query(value="select * from qm_play_school_class where school_id=?1 and class_id=?2", nativeQuery = true)
	List<QmPlaySchoolClass> findBySchoolIdAndClassId(Long schoolId, Long classId);

	@Query( nativeQuery = true,value="SELECT qpsc.* FROM qm_patriarch AS qp " + 
			"left JOIN qm_patriarch_student AS qps on qp.id=qps.patriarch_id " + 
			"left JOIN qm_class_student as qcs on qps.student_id=qcs.student_id " + 
			"left join qm_play_school_class as qpsc on qcs.class_id=qpsc.class_id where qp.open_id=?1 and qcs.student_id=?2")
	List<QmPlaySchoolClass> findByStudentOpenId(String openId,Long studentId);
	
}
