package com.qxiao.wx.fresh.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QmFreshComment {
	
	@Id
	@GeneratedValue
	private Long commentId;
	private Long freshId;
	private String textContent;
	private String userName;
	private Long classId;
	private String openId;
	private String photo;
	private int relation;
	private Long studentId;
	private Date postTime;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public int getRelation() {
		return relation;
	}
	public String getPhoto() {
		return photo;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public QmFreshComment() {
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public Long getFreshId() {
		return freshId;
	}
	public void setFreshId(Long freshId) {
		this.freshId = freshId;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
