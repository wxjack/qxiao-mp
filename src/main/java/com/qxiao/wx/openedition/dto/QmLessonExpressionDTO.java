package com.qxiao.wx.openedition.dto;

public class QmLessonExpressionDTO {
	
	private Long lessonId;
	private String title; // 课程名称
	private int startCount; // 星星数量
	private String scoreRank; // top 百分比排名

	public Long getLessonId() {
		return lessonId;
	}

	public String getTitle() {
		return title;
	}

	public int getStartCount() {
		return startCount;
	}

	public String getScoreRank() {
		return scoreRank;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStartCount(int startCount) {
		this.startCount = startCount;
	}

	public void setScoreRank(String scoreRank) {
		this.scoreRank = scoreRank;
	}
}
