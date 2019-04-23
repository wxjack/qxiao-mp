package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmClockInfo;
import com.spring.jpa.dao.JPADao;

public interface QmClockInfoDao extends JPADao<QmClockInfo>{
	
	@Query(value="SELECT info.post_time FROM qm_patriarch AS qp left JOIN qm_patriarch_student AS qps on qp.id = qps.patriarch_id " + 
			"left JOIN qm_student AS qs on qps.student_id = qs.student_id left JOIN qm_clock_info AS info on ( " + 
			"qs.nfc_id = info.nfc_id OR qs.ibeacon_id = info.ibeacon_id ) WHERE " + 
			"qp.open_id = ? AND DATE_FORMAT(info.post_time, '%Y-%m-%d') = ?",nativeQuery=true)
	List<Object> queryClockInfoByDate(String openId,String date);
}
