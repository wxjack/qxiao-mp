package com.qxiao.wx.fresh.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.fresh.jpa.entity.QmFreshImage;
import com.spring.jpa.dao.JPADao;

public interface QmFreshImageDao extends JPADao<QmFreshImage>{

	@Query(value = "SELECT * FROM qm_fresh_image where fresh_id = ?1 ORDER BY post_time ASC limit 1 ",nativeQuery = true)
	QmFreshImage findOne(Long freshId);

	List<QmFreshImage> findByFreshId(Long freshId);

	@Modifying
	@Query(value = "delete from QmFreshImage q where q.freshId = ?1")
	void deleteByFreshId(long freshId);

}
