package com.qxiao.wx.openedition.dto;

import net.sf.json.JSONArray;

public class StarAddVo {

	private String openId;
	private Long studentId;
	private String day;
	private JSONArray actionArray;
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public JSONArray getActionArray() {
		return actionArray;
	}
	public void setActionArray(JSONArray actionArray) {
		this.actionArray = actionArray;
	}
	
}
