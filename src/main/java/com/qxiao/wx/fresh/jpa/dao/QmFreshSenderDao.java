package com.qxiao.wx.fresh.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.fresh.jpa.entity.QmFreshSender;
import com.spring.jpa.dao.JPADao;

public interface QmFreshSenderDao extends JPADao<QmFreshSender>{

	@Query(value="SELECT qns.* FROM qm_message_send AS qms JOIN qm_fresh_sender AS qns WHERE " + 
			"qms.message_id = qns.fresh_id AND qms.message_id = ?1 AND qms.open_id =?2 and qms.type=2",nativeQuery=true)
	List<QmFreshSender> findByFreshIdAndOpenId(Long messageId, String openId);

}
