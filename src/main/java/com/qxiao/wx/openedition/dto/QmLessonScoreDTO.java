package com.qxiao.wx.openedition.dto;

public class QmLessonScoreDTO {

	private String stageTitle; // 阶段测验名称
	private Long stageId; // 阶段测验ID
	private Long score; // 分数
	private String scoreRank; // 排名
	private String day;// 考试时间
	
	public String getStageTitle() {
		return stageTitle;
	}
	public Long getStageId() {
		return stageId;
	}
	public Long getScore() {
		return score;
	}
	public String getScoreRank() {
		return scoreRank;
	}
	public String getDay() {
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
	public void setScoreRank(String scoreRank) {
		this.scoreRank = scoreRank;
	}
	public void setDay(String day) {
		this.day = day;
	}
}
