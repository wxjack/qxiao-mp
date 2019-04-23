package com.qxiao.wx.homework.jpa.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmReaderVO {

	private Long studentId;
	private String studentName;
	private String photo; // 学生头像
	private int relation;
	private int confirmFlag;// 確認标识
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;

	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
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
