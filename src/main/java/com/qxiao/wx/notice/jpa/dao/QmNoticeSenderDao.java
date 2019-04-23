package com.qxiao.wx.notice.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.notice.jpa.entity.QmNoticeSender;
import com.spring.jpa.dao.JPADao;

public interface QmNoticeSenderDao extends JPADao<QmNoticeSender> {

	@Modifying
	@Query(value = "delete from QmNoticeSender q where q.noticeId = ?1")
	void deleteByNoticeId(Long noticeId);

	@Query(value = "SELECT * FROM qm_notice_sender WHERE notice_id = ?1 "
			+ "AND sender_type = 1 AND sender_id = 	?2 ", nativeQuery = true)
	QmNoticeSender findByNoticeId(Long noticeId, Long classId);

	List<QmNoticeSender> findByNoticeId(Long noticeId);
	
	@Query(nativeQuery = true,value="select qns.* FROM qm_message_send AS qms JOIN qm_notice_sender AS qns where " + 
			"qms.message_id=qns.notice_id and qns.sender_type=1  and qms.message_id=?1 and qms.open_id=?2 and qms.type=1")
	List<QmNoticeSender> findByNoticeIdAndOpenId(Long noticeId,String openId);
}
