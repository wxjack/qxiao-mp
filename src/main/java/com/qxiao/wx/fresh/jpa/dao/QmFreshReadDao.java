package com.qxiao.wx.fresh.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.fresh.jpa.entity.QmFreshRead;
import com.spring.jpa.dao.JPADao;

public interface QmFreshReadDao extends JPADao<QmFreshRead> {

	@Query(value = "SELECT qfr.* FROM qm_fresh_read AS qfr JOIN qm_class_student stu ON stu.student_id = qfr.student_id "
			+ "WHERE qfr.fresh_id = ?1 AND stu.class_id = ?2 ", nativeQuery = true)
	List<QmFreshRead> findByFreshIdAndClassId(Long freshId, Long classId);

//	QmFreshRead findByStudentId(Long studentId);

	@Query(value = "SELECT qfr.* FROM qm_fresh_read AS qfr "
			+ "JOIN qm_class_student AS qcs ON qfr.student_id = qcs.student_id "
			+ "JOIN qm_fresh_sender AS qfs ON qfs.class_id = qcs.class_id "
			+ "JOIN qm_play_school_class AS qp ON qp.class_id = qfs.class_id WHERE "
			+ "	qfr.fresh_id = ?1 AND qp.school_id = ?2 GROUP BY qfr.id", nativeQuery = true)
	List<QmFreshRead> findByFreschIdAndSchoolId(Long freshId, Long schoolId);

	QmFreshRead findByFreshIdAndStudentId(Long freshId, Long studentId);

}
