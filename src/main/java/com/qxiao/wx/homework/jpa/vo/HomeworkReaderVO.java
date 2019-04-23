package com.qxiao.wx.homework.jpa.vo;

import java.util.Date;

public class HomeworkReaderVO {

	private Long studentId;
    private String studentName;
    private int relation;
    private int confirmFlag;
    private Date postTime;
    
	public Long getStudentId() {
		return studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public int getRelation() {
		return relation;
	}
	public int getConfirmFlag() {
		return confirmFlag;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	public void setConfirmFlag(int confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
