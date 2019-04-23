package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 小Q表现课堂表现统计表 Qm_Lesson_Stat
 * @author xiaojiao
 * @创建时间：2019年4月12日
 */
@Entity
public class QmLessonStat {
	
	@Id
	@GeneratedValue
	private Long id;// ID
	private Long classId;// 班级ID
	private Long lessonId;// 课程ID
	private String day;// 表现日期
	private Long starCount;// 表现，以星星表示
	private Date postTime;// 时间
	
	public Long getId() {
		return id;
	}
	public Long getClassId() {
		return classId;
	}
	public Long getLessonId() {
		return lessonId;
	}
	public String getDay() {
		return day;
	}
	public Long getStarCount() {
		return starCount;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setStarCount(Long starCount) {
		this.starCount = starCount;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
