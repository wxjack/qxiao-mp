package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.openedition.jpa.entity.QmExpressionRule;
import com.spring.jpa.dao.JPADao;

public interface QmExpressionRuleDao  extends JPADao<QmExpressionRule>  {

	List<QmExpressionRule> findByActionId(Long actionId);

	@Query(value="SELECT qer* FROM qm_expression_rule AS qer JOIN qm_student_rule AS qsr " + 
			"where qer.rule_id=qsr.rule_id and qer.action_id=?",nativeQuery=true)
	List<QmExpressionRule> findByStudentActionId(Long actionId);

	QmExpressionRule findByRuleIdAndOpenId(Long ruleId,String openId);
	
	@Query(value="SELECT qer.* FROM qm_expression_rule AS qer JOIN qm_student_rule AS qsr " + 
			"where qer.rule_id=qsr.rule_id and qer.action_id= ? and qsr.student_id=?",nativeQuery=true)
	List<QmExpressionRule> findByChoiceActionId(Long actionId,Long studentId);
}
