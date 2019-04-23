package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.openedition.jpa.entity.QmExpressionRuleDefault;
import com.spring.jpa.dao.JPADao;

public interface QmExpressionRuleDefaultDao  extends JPADao<QmExpressionRuleDefault>  {

	List<QmExpressionRuleDefault> findByActionId(Long actionId);

	
	List<QmExpressionRuleDefault> findAll();
	
	@Query(value="SELECT qer.* FROM qm_expression_rule_default AS qer JOIN qm_student_rule AS qsr\r\n" + 
			"where qer.rule_id=qsr.rule_id and qer.action_id=? and qsr.student_id=?",nativeQuery=true)
	List<QmExpressionRuleDefault> findByChoiceActionId(Long actionId,Long studentId);
}
