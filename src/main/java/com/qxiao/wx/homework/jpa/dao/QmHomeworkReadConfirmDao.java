package com.qxiao.wx.homework.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.homework.jpa.entity.QmHomeworkReadConfirm;
import com.spring.jpa.dao.JPADao;

public interface QmHomeworkReadConfirmDao extends JPADao<QmHomeworkReadConfirm> {

	List<QmHomeworkReadConfirm> findByHomeId(Long homeId);

	@Query(nativeQuery = true, value = "SELECT qhr.* FROM qm_homework_read_confirm AS qhr "
			+ "JOIN qm_patriarch_student AS qps ON qps.student_id = qhr.student_id "
			+ "JOIN qm_patriarch AS qp ON qp.id = qps.patriarch_id WHERE qp.open_id = ?1")
	QmHomeworkReadConfirm findByOpenId(String openId);

	QmHomeworkReadConfirm findByHomeIdAndStudentId(Long homeId, Long studentId);

}
