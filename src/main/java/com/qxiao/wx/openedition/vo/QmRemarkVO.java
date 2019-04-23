package com.qxiao.wx.openedition.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmRemarkVO {

	private String textContent; // 评语
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime; // 评语时间

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
