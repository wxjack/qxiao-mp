package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QmLessonExpression {
	@Id
	@GeneratedValue
	private Long expressionId;
	private Long studentId;
	private Long lessonId;
	private String day;
	private int starCount;
	private Date postTime;
	
	public Long getExpressionId() {
		return expressionId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public Long getLessonId() {
		return lessonId;
	}
	public String getDay() {
		return day;
	}
	public int getStarCount() {
		return starCount;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setExpressionId(Long expressionId) {
		this.expressionId = expressionId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
