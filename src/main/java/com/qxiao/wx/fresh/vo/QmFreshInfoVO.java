package com.qxiao.wx.fresh.vo;

import java.util.Date;

public class QmFreshInfoVO {

	private Long freshId;
	private String title;
	private String textContent;
	private Long classId;
	private Date postTime;
	
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public Long getFreshId() {
		return freshId;
	}
	public String getTitle() {
		return title;
	}
	public String getTextContent() {
		return textContent;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setFreshId(Long freshId) {
		this.freshId = freshId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
