package com.qxiao.wx.openedition.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmExamPaperDTO {

	private Long paperId;// 试卷ID
	private String schoolName;// 学校名称
	private String title;// 试卷名称
	private String stageTitle;// 测验阶段名称
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;// 时间

	public Long getPaperId() {
		return paperId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public String getTitle() {
		return title;
	}

	public String getStageTitle() {
		return stageTitle;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStageTitle(String stageTitle) {
		this.stageTitle = stageTitle;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
