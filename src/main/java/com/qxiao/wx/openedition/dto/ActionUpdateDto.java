package com.qxiao.wx.openedition.dto;

import net.sf.json.JSONArray;

public class ActionUpdateDto {
	private String openId;
	private String title;
	private String textContent;
	private JSONArray rules;
	private Long studentId;
	private Long actionId;
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
	
}
