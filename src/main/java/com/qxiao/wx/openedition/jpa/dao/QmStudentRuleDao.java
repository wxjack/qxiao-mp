package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.openedition.jpa.entity.QmStudentRule;
import com.spring.jpa.dao.JPADao;

public interface QmStudentRuleDao extends JPADao<QmStudentRule> {

	QmStudentRule findByRuleIdAndStudentIdAndActionType(Long ruleId,Long studentId,Integer actionType);

	@Query(value="SELECT qsr.* FROM qm_student_rule as qsr JOIN qm_expression_rule as qer " + 
			"where qsr.rule_id=qer.rule_id and qer.action_id=?1 and qsr.student_id=?2",nativeQuery=true)
	List<QmStudentRule> findByActionIdAndStudentId(Long actionId, Long studentId);
	
	@Query(value="SELECT qsr.* FROM qm_student_rule as qsr JOIN qm_expression_rule as qer " + 
			"where qsr.rule_id=qer.rule_id and qer.action_id=?1 ",nativeQuery=true)
	List<QmStudentRule> findByActionId(Long actionId);
	
	@Query(value="SELECT qsr.* FROM qm_student_rule as qsr JOIN qm_expression_rule_default as qer " + 
			"where qsr.rule_id=qer.rule_id and qer.action_id=?1 ",nativeQuery=true)
	List<QmStudentRule> findByActionDefaultId(Long actionId);
}
