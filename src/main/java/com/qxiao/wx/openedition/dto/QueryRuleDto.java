package com.qxiao.wx.openedition.dto;

import java.util.List;
import java.util.Map;

public class QueryRuleDto {
	
	private Long actionId;
	private String title;
	private String textContent;
	private List<Map<String, Object>> rules;
	private Integer actionType;
	
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public List<Map<String, Object>> getRules() {
		return rules;
	}
	public void setRules(List<Map<String, Object>> rules) {
		this.rules = rules;
	}
	
}
