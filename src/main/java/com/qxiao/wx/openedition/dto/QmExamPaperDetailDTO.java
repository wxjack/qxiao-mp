package com.qxiao.wx.openedition.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmExamPaperDetailDTO {

	private String title;// 试卷标题
	private String textContent;// 详细内容
	private String videoUrl;// 视频URL
	private float fee;// 试卷费用
	private int downloadCount;// 下载次数
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;// 发布时间

	public String getTitle() {
		return title;
	}

	public String getTextContent() {
		return textContent;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public float getFee() {
		return fee;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
