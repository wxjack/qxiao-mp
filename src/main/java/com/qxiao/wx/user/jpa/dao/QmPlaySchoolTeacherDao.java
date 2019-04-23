package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.spring.jpa.dao.JPADao;

public interface QmPlaySchoolTeacherDao extends JPADao<QmPlaySchoolTeacher> {

	// @Query(value="select * from qm_play_school_teacher where tel=?1 and
	// `status`=0",nativeQuery = true)
	QmPlaySchoolTeacher findByTel(String tel);
	
	QmPlaySchoolTeacher findByTelAndStatus(String tel,int status);

	@Query("select a from QmPlaySchoolTeacher as a where teacher_id=?1 and status=0")
	QmPlaySchoolTeacher findByTeacherId(Long teacherId);

	QmPlaySchoolTeacher findByOpenId(String openId);

	@Query(nativeQuery = true, value = "SELECT qpst.* FROM qm_play_school_teacher AS qpst "
			+ "JOIN qm_class_teacher AS qct ON qct.teacher_id = qpst.teacher_id "
			+ "JOIN qm_play_school_class AS qpsc ON qpsc.class_id = qct.class_id "
			+ "WHERE qpsc.school_id = ?1 AND qpst.status = 0 GROUP BY qpst.teacher_id ")
	List<QmPlaySchoolTeacher> findBySchoolId(Long schoolId);

	@Query(nativeQuery = true, value = "SELECT qpst.* FROM qm_play_school_teacher AS qpst "
			+ "JOIN qm_class_teacher AS qct ON qct.teacher_id = qpst.teacher_id "
			+ "WHERE qct.class_id = ?1 AND qpst.status = 0 GROUP BY qpst.teacher_id ")
	List<QmPlaySchoolTeacher> findByClassId(Long senderId);

	@Query(value = "SELECT qct.class_id FROM qm_class_teacher AS qct JOIN qm_play_school_teacher AS qpst "
			+ "WHERE qct.teacher_id = qpst.teacher_id AND qpst.open_id =?1", nativeQuery = true)
	List<Long> findClassIdByTeacherOpenId(String openId);

	@Query(value = "SELECT qpsc.school_id FROM qm_play_school_info AS qpsi join qm_play_school_class as qpsc "
			+ "JOIN qm_play_school_teacher AS qpst JOIN qm_class_teacher AS qct where "
			+ "qpsc.class_id=qct.class_id and qct.teacher_id=qpst.teacher_id and qpst.open_id=?1", nativeQuery = true)
	Long findSchoolId(String openId);

	@Query(value = "SELECT a.* FROM qm_play_school_teacher AS a JOIN qm_notice_info AS b ON a.open_id = b.open_id WHERE b.notice_id = ?1", nativeQuery = true)
	QmPlaySchoolTeacher findByNoticeId(Long noticeId);

}
