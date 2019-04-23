package com.qxiao.wx.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ClockStatVo {

	private int classCount;
	private Long studentId;
	@JsonFormat(pattern="yyyy-MM",timezone="GMT+8")
	private Date date;
	private Long classId;
	
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public int getClassCount() {
		return classCount;
	}
	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	
	
}
