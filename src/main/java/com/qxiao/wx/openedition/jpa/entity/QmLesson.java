package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 表现课程
 * 
 * @author xiaojiao
 *
 * @创建时间：2019年4月8日
 */
@Entity
public class QmLesson {
	@Id
	@GeneratedValue
	private Long lessonId; // 课程ID
	private String title; // 课程名称
	private String textContent; // 课程说明
	private Date postTime; // 时间

	public Long getLessonId() {
		return lessonId;
	}

	public String getTitle() {
		return title;
	}

	public String getTextContent() {
		return textContent;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
