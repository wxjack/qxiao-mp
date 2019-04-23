package com.qxiao.wx.openedition.dto;

public class HomeStatQueryDto {
	private Long actionId;
	private String title;
	private String day;
	private Integer myStartCount;
	private Integer aveStartCount;
	private Long studentId;

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

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Integer getMyStartCount() {
		return myStartCount;
	}

	public void setMyStartCount(Integer myStartCount) {
		this.myStartCount = myStartCount;
	}

	public Integer getAveStartCount() {
		return aveStartCount;
	}

	public void setAveStartCount(Integer aveStartCount) {
		this.aveStartCount = aveStartCount;
	}

}
