package com.qxiao.wx.community.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.community.jpa.entity.QmClassTeacher;
import com.spring.jpa.dao.JPADao;

public interface QmClassTeacherDao extends JPADao<QmClassTeacher> {
	@Query(value = "select a from QmClassTeacher a where class_id=?1 and teacher_id=?2")
	QmClassTeacher findATeacher(Long classId, Long teacherId);

	@Query(value = "SELECT qct.* FROM qm_class_teacher AS qct JOIN qm_play_school_teacher AS qpst WHERE qct.teacher_id =qpst.teacher_id "
			+ "and qct.class_id=?1 and qpst.`status`=0",nativeQuery = true)
	List<QmClassTeacher> findByClassId(Long classId);

	List<QmClassTeacher> findByTeacherId(Long teacherId);

}
