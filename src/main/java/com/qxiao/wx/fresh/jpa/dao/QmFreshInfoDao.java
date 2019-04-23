package com.qxiao.wx.fresh.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.fresh.jpa.entity.QmFreshInfo;
import com.spring.jpa.dao.JPADao;

public interface QmFreshInfoDao extends JPADao<QmFreshInfo> {

	@Query(value = "SELECT qfi.fresh_id AS freshId, qfi.title AS title, SUBSTRING(qfi.text_content, 1, 50) AS textContent,"
			+ "qfi.post_time AS postTime FROM qm_fresh_info AS qfi JOIN qm_fresh_sender AS qds ON qfi.fresh_id = qds.fresh_id "
			+ "WHERE qds.class_id = ?1 AND qfi.message_send = 1 GROUP BY freshId ", nativeQuery = true)
	List<QmFreshInfo> findByclassId(Long classId);

	QmFreshInfo findByFreshIdAndIsDel(Long freshId, int i);

}
