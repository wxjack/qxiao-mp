package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmMessageSend;
import com.spring.jpa.dao.JPADao;

public interface QmMessageSendDao extends JPADao<QmMessageSend> {

	@Query(nativeQuery = true, value = "select * from qm_message_send where type = ?1 and result = 0 limit 20 ")
	List<QmMessageSend> findByType(int type);

	@Query(nativeQuery = true, value = "SELECT send.* FROM qm_message_send as send "
			+ "JOIN qm_notice_info AS info ON send.message_id = info.notice_id WHERE send.type = ?1 "
			+ "AND info.message_send =1 AND send.result = 0 LIMIT 20")
	List<QmMessageSend> findNotice(int type);

	QmMessageSend findByMessageIdAndOpenIdAndType(Long messageId, String openId, int type);

}
