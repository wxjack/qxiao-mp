package com.qxiao.wx.openedition.dto;

import net.sf.json.JSONArray;

public class ActionAddDto {

	private String openId;
	private Integer actionType;
	private String title;
	private String textContent;
	private JSONArray rules;
	private Long studentId;
	
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
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
	public JSONArray getRules() {
		return rules;
	}
	public void setRules(JSONArray rules) {
		this.rules = rules;
	}
	
}
