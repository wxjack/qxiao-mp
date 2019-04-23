package com.qxiao.wx.homework.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.homework.jpa.entity.QmHomeworkInfo;
import com.spring.jpa.dao.JPADao;

public interface QmHomeworkInfoDao extends JPADao<QmHomeworkInfo> {

	QmHomeworkInfo findByHomeId(Long homeId);

	@Query(value = "select qhi.* from qm_homework_info as qhi join qm_homework_sender as qhs "
			+ "on qhs.home_id = qhi.home_id where qhi.home_id = ?1 and qhs.class_id = ?2", nativeQuery = true)
	QmHomeworkInfo findByHomeIdAndClassId(Long homeId, Long classId);

	List<QmHomeworkInfo> findByOpenId(String openId);

	@Query(value = "SELECT count(q.home_id) AS count FROM qm_homework_read_confirm AS q "
			+ "JOIN qm_class_student AS stu ON stu.student_id = q.student_id WHERE q.home_id = ?1 "
			+ "AND stu.class_id = ?2 ", nativeQuery = true)
	Integer findForCount(Long homeId, Long classId);

}
