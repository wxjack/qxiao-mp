package com.qxiao.wx.community.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.community.jpa.entity.QmClassStudent;
import com.spring.jpa.dao.JPADao;

public interface QmClassStudentDao extends JPADao<QmClassStudent> {

	@Query("select a from QmClassStudent a where class_id=?1 and student_id=?2")
	QmClassStudent findStudent(Long classId, Long studentId);

//	@Query(value = "SELECT qs.* FROM qm_class_student AS qs JOIN qm_student AS q "
//			+ "ON q.student_id = qs.student_id WHERE qs.class_id = ?1", nativeQuery = true)
//	QmClassStudent findByOpenId(String openId);

	List<QmClassStudent> findByClassId(Long classId);

	QmClassStudent findByStudentId(Long studentId);

	@Query(value = "SELECT qcs.* FROM qm_patriarch AS qp JOIN qm_patriarch_student AS qps "
			+ "JOIN qm_student AS qs JOIN qm_class_student AS qcs WHERE qp.id = qps.patriarch_id "
			+ "AND qps.student_id = qs.student_id AND qs.student_id = qcs.student_id "
			+ "AND qp.open_id = ?1 ", nativeQuery = true)
	List<QmClassStudent> findClassId(String openId);
	
	QmClassStudent findByStudentIdAndClassId(Long studentId, Long classId);

	@Query(value = "SELECT qcs.* FROM qm_patriarch AS qp JOIN qm_patriarch_student AS qps "
			+ "JOIN qm_student AS qs JOIN qm_class_student AS qcs WHERE qp.id = qps.patriarch_id "
			+ "AND qps.student_id = qs.student_id AND qs.student_id = qcs.student_id "
			+ "AND qp.open_id = ?1 and qcs.class_id= ?2", nativeQuery = true)
	List<QmClassStudent> findClassIdByopenId(String openId,Long classId);

	@Query(value="SELECT * FROM qm_class_student WHERE class_id IN ( SELECT qcs.class_id FROM " + 
			"qm_class_student AS qcs WHERE qcs.student_id = ?1 )",nativeQuery=true)
	List<QmClassStudent> queryClassByStudentId(Long studentId);
	
	@Query(value = "SELECT count(1) FROM qm_class_student WHERE class_id IN (SELECT class_id FROM "
			+ "qm_class_student AS cla JOIN qm_student AS stu ON stu.student_id = cla.student_id WHERE "
			+ "stu.student_id = ?1 )", nativeQuery = true)
	int findStudentCount(Long studentId);

}
