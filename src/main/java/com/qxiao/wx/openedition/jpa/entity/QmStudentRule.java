package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QmStudentRule {
	@Id
	@GeneratedValue
	private Long id;
	private Long studentId;//学生ID
	private Long ruleId;//ID
	private int actionType;//行为类型 0-缺省行为 1-自定义行为
	private Date postTime;//时间

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public Long getId() {
		return id;
	}

	public Long getStudentId() {
		return studentId;
	}

	public int getActionType() {
		return actionType;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
