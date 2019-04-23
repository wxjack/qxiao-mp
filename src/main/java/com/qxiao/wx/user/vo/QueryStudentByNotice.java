package com.qxiao.wx.user.vo;

public class QueryStudentByNotice {

	private String studentName;
	private Long studentId;
	private Integer confirmFlag;
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Integer getConfirmFlag() {
		return confirmFlag;
	}
	public void setConfirmFlag(Integer confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	
}
