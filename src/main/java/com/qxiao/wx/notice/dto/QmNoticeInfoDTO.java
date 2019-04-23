package com.qxiao.wx.notice.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmNoticeInfoDTO {

	private Long noticeId;
	private String title;
	private String textContent;
	private String topImage;
	private int personType;
	private Long classId;
	private String name;
	private int needConfirm; // 确认标志 0-无需确认 1-需要确认
	private int classReadCount; // 班级阅读人数
	private int totalCount; // 班级确认人数
	private int classUnreadCount; // 班级未读人数
	private int status; // 阅读状态 0-未阅读 ,1-已阅读
	private Long studentId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;

	public Long getClassId() {
		return classId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount() {
		this.totalCount = this.classReadCount + this.classUnreadCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getNoticeId() {
		return noticeId;
	}

	public String getTitle() {
		return title;
	}

	public int getPersonType() {
		return personType;
	}

	public void setPersonType(int personType) {
		this.personType = personType;
	}

	public String getTextContent() {
		return textContent;
	}

	public String getTopImage() {
		return topImage;
	}

	public String getName() {
		return name;
	}

	public int getNeedConfirm() {
		return needConfirm;
	}

	public int getClassReadCount() {
		return classReadCount;
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

	public void setTopImage(String topImage) {
		this.topImage = topImage;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNeedConfirm(int needConfirm) {
		this.needConfirm = needConfirm;
	}

	public void setClassReadCount(int classReadCount) {
		this.classReadCount = classReadCount;
	}

	public void setClassUnreadCount(int classUnreadCount) {
		this.classUnreadCount = classUnreadCount;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
