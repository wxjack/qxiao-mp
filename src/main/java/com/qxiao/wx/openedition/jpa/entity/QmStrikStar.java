package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QmStrikStar {
	@Id
	@GeneratedValue
	private Long id;
	private Long studentId;
	private Long actionId;
	private int actionType;
	private int starCount;
	private String day;
	private Date postTime;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public Long getId() {
		return id;
	}

	public Long getStudentId() {
		return studentId;
	}

	public Long getActionId() {
		return actionId;
	}

	public int getActionType() {
		return actionType;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
