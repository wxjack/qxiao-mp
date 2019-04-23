package com.qxiao.wx.openedition.dto;

import java.util.List;

public class RuleConnectDto {
	private String openId;
	private Long studentId;
	private Long actionId;
	private Integer actionType;
	private List<Long> rules;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	public List<Long> getRules() {
		return rules;
	}
	public void setRules(List<Long> rules) {
		this.rules = rules;
	}
}
