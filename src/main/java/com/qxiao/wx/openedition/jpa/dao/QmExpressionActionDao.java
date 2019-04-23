package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.openedition.jpa.entity.QmExpressionAction;
import com.spring.jpa.dao.JPADao;

public interface QmExpressionActionDao  extends JPADao<QmExpressionAction>  {

	QmExpressionAction findByOpenIdAndActionId(String openId, Long actionId);

	@Query(value="SELECT qer.* FROM qm_expression_action AS qer JOIN qm_student_action AS qsr " + 
			"WHERE qer.action_id  = qsr.action_id AND qsr.student_id=?1 and qsr.action_type=1 ",nativeQuery=true)
	List<QmExpressionAction> findByStudentId(Long studentId);

	QmExpressionAction findByActionId(Long actionId);
}
