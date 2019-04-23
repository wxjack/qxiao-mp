package com.qxiao.wx.homework.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.homework.jpa.entity.QmHomeworkSender;
import com.spring.jpa.dao.JPADao;

public interface QmHomeworkSenderDao extends JPADao<QmHomeworkSender>{

	@Query(nativeQuery = true,value="SELECT qns.* FROM qm_message_send AS qms " + 
			"JOIN qm_homework_sender AS qns WHERE qms.message_id = qns.home_id " + 
			"AND qms.message_id = ?1 AND qms.open_id =?2 and qms.type=5")
	List<QmHomeworkSender> findByHomeIdAndOpenId(Long messageId,String openId);
}
