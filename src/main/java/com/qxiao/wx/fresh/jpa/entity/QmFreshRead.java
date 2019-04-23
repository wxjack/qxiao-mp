package com.qxiao.wx.fresh.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class QmFreshRead {
	
	@Id
	@GeneratedValue
	private Long id;
	private Long freshId;
	private Long studentId;
	private Date postTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getFreshId() {
		return freshId;
	}
	public void setFreshId(Long freshId) {
		this.freshId = freshId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
