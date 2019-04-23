package com.qxiao.wx.notice.jpa.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.notice.jpa.entity.QmNoticeReadConfirm;
import com.spring.jpa.dao.JPADao;

public interface QmNoticeReadConfirmDao extends JPADao<QmNoticeReadConfirm>{

	@Modifying
	@Query(value = "delete from QmNoticeReadConfirm q where q.noticeId = ?1")
	void deleteByNoticeId(Long noticeId);

	QmNoticeReadConfirm findByNoticeIdAndStudentId(Long noticeId, Long studentId);

}
