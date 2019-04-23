package com.qxiao.wx.community.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class QmCommunityPraise {
	@Id
	@GeneratedValue
	private Long praiseId;
	private Long communityId;
	private String openId;
	private int serial;
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
	public String getPhoto() {
		return photo;
	}
	public int getRelation() {
		return relation;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	public Long getPraiseId() {
		return praiseId;
	}
	public void setPraiseId(Long praiseId) {
		this.praiseId = praiseId;
	}
	public Long getCommunityId() {
		return communityId;
	}
	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
