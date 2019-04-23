package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 学生成绩 Qm_Lesson_Score
 * 
 * @author xiaojiao
 *
 * @创建时间：2019年4月9日
 */
@Entity
public class QmLessonScore {
	@Id
	@GeneratedValue
	private Long scoreId;// 成绩ID
	private Long studentId;// 学生ID
	private Long lessonId;// 课程ID
	private float score;// 成绩分数
	private Long stageId;// 阶段ID
	private String examTitle;// 测验标题
	private Date day;// 测验日期
	private Date postTime;// 录入时间

	public Long getScoreId() {
		return scoreId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public Long getLessonId() {
		return lessonId;
	}

	public float getScore() {
		return score;
	}

	public Long getStageId() {
		return stageId;
	}

	public String getExamTitle() {
		return examTitle;
	}

	public Date getDay() {
		return day;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setScoreId(Long scoreId) {
		this.scoreId = scoreId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public void setExamTitle(String examTitle) {
		this.examTitle = examTitle;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
