package com.qxiao.wx.openedition.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmLessonScoreVO {
	
	private String stageTitle; // 阶段测验名称
	private Long stageId; // 阶段测验ID
	private Long score; // 分数
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date day;// 考试时间
	
	public String getStageTitle() {
		return stageTitle;
	}
	public Long getStageId() {
		return stageId;
	}
	public Long getScore() {
		return score;
	}
	public Date getDay() {
		return day;
	}
	public void setStageTitle(String stageTitle) {
		this.stageTitle = stageTitle;
	}
	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}
	public void setScore(Long score) {
		this.score = score;
	}
	public void setDay(Date day) {
		this.day = day;
	}
}
