package com.qxiao.wx.openedition.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmRemarkDTO {

	private int remarkType;// 评语类型 0-系统评语 1-老师评语
	private String textContent;// 评语内容
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;// 时间

	public int getRemarkType() {
		return remarkType;
	}

	public String getTextContent() {
		return textContent;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setRemarkType(int remarkType) {
		this.remarkType = remarkType;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
