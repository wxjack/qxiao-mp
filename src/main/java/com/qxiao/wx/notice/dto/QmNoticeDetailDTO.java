package com.qxiao.wx.notice.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmNoticeDetailDTO {

	private Long noticeId;
    private String title;
    private String textContent;
    private int isDel;
    private List<Map<String, String>> images; 
    private int confirmFlag; // 确认标志 0-未确认 1-已确认
    private int classReadCount; // 班级阅读人数
    private int totalCount; // 班级确认人数
    private int classUnreadCount; // 班级未读人数
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date postTime;
    
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public int getConfirmFlag() {
		return confirmFlag;
	}
	public void setConfirmFlag(int confirmFlag) {
		this.confirmFlag = confirmFlag;
	}
	public Long getNoticeId() {
		return noticeId;
	}
	public String getTitle() {
		return title;
	}
	public String getTextContent() {
		return textContent;
	}
	public int getClassReadCount() {
		return classReadCount;
	}
	public int getTotalCount() {
		return this.totalCount;
	}
	public int getClassUnreadCount() {
		return classUnreadCount;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public List<Map<String, String>> getImages() {
		return images;
	}
	public void setImages(List<Map<String, String>> images) {
		this.images = images;
	}
	public void setClassReadCount(int classReadCount) {
		this.classReadCount = classReadCount;
	}
	public void setTotalCount() {
		this.totalCount = this.classReadCount + this.classUnreadCount;
	}
	public void setClassUnreadCount(int classUnreadCount) {
		this.classUnreadCount = classUnreadCount;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
