package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 学生测验阶段
 * 
 * @author xiaojiao
 *
 * @创建时间：2019年4月9日
 */
@Entity
public class QmExamStage {
	@Id
	@GeneratedValue
	private Long stageId;// 阶段ID
	private String title;// 阶段名称
	private Date startTime;// 开始时间
	private Date endTime;// 结束时间
	private Date postTime;// 时间

	public Long getStageId() {
		return stageId;
	}

	public String getTitle() {
		return title;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
