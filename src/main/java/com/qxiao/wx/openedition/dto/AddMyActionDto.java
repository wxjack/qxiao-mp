package com.qxiao.wx.openedition.dto;

import net.sf.json.JSONArray;

public class AddMyActionDto {

	private Long studentId;
	private String openId;
	private JSONArray action;
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
	public JSONArray getAction() {
		return action;
	}
	public void setAction(JSONArray action) {
		this.action = action;
	}
	
}
