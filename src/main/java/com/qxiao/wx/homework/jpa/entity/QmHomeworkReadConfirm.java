package com.qxiao.wx.homework.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class QmHomeworkReadConfirm {
	@Id
	@GeneratedValue
	private Long id;
	private Long homeId;
	private Long studentId;
	private int confirmFlag;
	private String openId;
	private Date postTime;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHomeId() {
		return homeId;
	}
	public void setHomeId(Long homeId) {
		this.homeId = homeId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public int getConfirmFlag() {
		return confirmFlag;
	}
	public void setConfirmFlag(int confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
