package com.qxiao.wx.fresh.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmFreshInfoDTO {

	private Long freshId; // 速报ID
	private String title; // 标题
	private String textContent; // 缩略内容，取前50个字符
	private String topImage; // 图片头图
	private Long classId;
	private int classReadCount; // 阅读人数
	private int classCommentCount; // 留言人数
	private int status; // 阅读状态  0-未阅读，1-已阅读
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime; // 发布时间
	private Long studentId;

	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getFreshId() {
		return freshId;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public int getClassReadCount() {
		return classReadCount;
	}
	public int getClassCommentCount() {
		return classCommentCount;
	}
	public void setClassReadCount(int classReadCount) {
		this.classReadCount = classReadCount;
	}
	public void setClassCommentCount(int classCommentCount) {
		this.classCommentCount = classCommentCount;
	}
	public String getTitle() {
		return title;
	}
	public String getTextContent() {
		return textContent;
	}
	public String getTopImage() {
		return topImage;
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
	public void setTopImage(String topImage) {
		this.topImage = topImage;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}