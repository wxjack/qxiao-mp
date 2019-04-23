package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.openedition.jpa.entity.QmExpressionActionDefault;
import com.spring.jpa.dao.JPADao;

public interface QmExpressionActionDefaultDao  extends JPADao<QmExpressionActionDefault>  {

	QmExpressionActionDefault findByActionId(Long actionId);

	@Query(value="SELECT qer.* FROM qm_expression_action_default AS qer JOIN qm_student_action AS qsr " + 
			"WHERE qer.action_id  = qsr.action_id AND qsr.student_id= ? and qsr.action_type=0",nativeQuery=true)
	List<QmExpressionActionDefault> findByStudentId(Long studentId);
	
	List<QmExpressionActionDefault> findAll();
}
