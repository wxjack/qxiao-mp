package com.qxiao.wx.openedition.vo;

public class QmLessonVO {

	private Long lessonId;
	private String title; // 课程名称
	private int starCount; // 星星数量

	public Long getLessonId() {
		return lessonId;
	}

	public String getTitle() {
		return title;
	}

	public int getStarCount() {
		return starCount;
	}

	public void setLessonId(Long lessonId) {
		this.lessonId = lessonId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

}
