package com.qxiao.wx.user.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ClassClockQueryVo {

	private Long studentId;
	private String studentName;
	private Integer clockFlag;
	private Long clockId;
	private Integer broadcast;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date postTime;
	private String photo;
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Long getClockId() {
		return clockId;
	}
	public void setClockId(Long clockId) {
		this.clockId = clockId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getClockFlag() {
		return clockFlag;
	}
	public void setClockFlag(Integer clockFlag) {
		this.clockFlag = clockFlag;
	}
	public Integer getBroadcast() {
		return broadcast;
	}
	public void setBroadcast(Integer broadcast) {
		this.broadcast = broadcast;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
