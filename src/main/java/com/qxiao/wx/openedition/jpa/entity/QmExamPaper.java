package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 试卷表Qm_Exam_Paper
 * 
 * @author xiaojiao
 *
 * @创建时间：2019年4月9日
 */
@Entity
public class QmExamPaper {
	@Id
	@GeneratedValue
	private Long paperId;// 试卷ID
	private String title;// 试卷标题
	private Long lessonId;// 课程ID
	private Long stageId;// 阶段ID
	private String schoolName;// 学校名称
	private Date postTime;// 时间

	public Long getPaperId() {
		return paperId;
	}

	public String getTitle() {
		return title;
	}

	public Long getLessonId() {
		return lessonId;
	}

	public Long getStageId() {
		return stageId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
