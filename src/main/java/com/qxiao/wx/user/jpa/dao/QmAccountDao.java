package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.spring.jpa.dao.JPADao;

public interface QmAccountDao extends JPADao<QmAccount> {

	QmAccount findByOpenId(String openId);

	@Query(nativeQuery = true, value = "SELECT a.* FROM qm_account AS a "
			+ "JOIN qm_patriarch AS qp on qp.open_id = a.open_id "
			+ "JOIN qm_patriarch_student AS qps ON qps.patriarch_id = qp.id  WHERE qps.student_id = ?1 " )
	List<QmAccount> findByStudentId(Long studentId);
}
