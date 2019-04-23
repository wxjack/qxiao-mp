package com.qxiao.wx.notice.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.qxiao.wx.notice.jpa.entity.QmNoticeImage;
import com.spring.jpa.dao.JPADao;

public interface QmNoticeImageDao extends JPADao<QmNoticeImage>{

	@Modifying
	@Query(value = "delete from QmNoticeImage q where q.noticeId = ?1 ")
	void deleteByNoticeId(Long noticeId);

	@Query(nativeQuery = true,value = "select * from qm_notice_image where notice_id = :noticeId limit 1 ")
	QmNoticeImage findByNoticeIdLimit(@Param("noticeId")Long noticeId);

	List<QmNoticeImage> findByNoticeId(Long noticeId);

}
