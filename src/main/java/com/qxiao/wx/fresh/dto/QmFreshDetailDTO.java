package com.qxiao.wx.fresh.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qxiao.wx.fresh.vo.QmCommentVO;

public class QmFreshDetailDTO {

	private Long freshId;
	private String title;
	private String schoolName;
	private String textContent;
	private List<Map<String, String>> images; /* ": [{"imageUrl":"/image/1.png"},{"imageUrl":"/image/2.png"}], */
	private int classReadCount;
	private int classCommentCount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date postTime;
	private int isDel;
	private List<QmCommentVO> commentList;

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

	public Long getFreshId() {
		return freshId;
	}

	public String getTitle() {
		return title;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public String getTextContent() {
		return textContent;
	}

	public List<Map<String, String>> getImages() {
		return images;
	}

	public int getClassReadCount() {
		return classReadCount;
	}

	public int getClassCommentCount() {
		return classCommentCount;
	}

	public List<QmCommentVO> getCommentList() {
		return commentList;
	}

	public void setFreshId(Long freshId) {
		this.freshId = freshId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setImages(List<Map<String, String>> images) {
		this.images = images;
	}

	public void setClassReadCount(int classReadCount) {
		this.classReadCount = classReadCount;
	}

	public void setClassCommentCount(int classCommentCount) {
		this.classCommentCount = classCommentCount;
	}

	public void setCommentList(List<QmCommentVO> commentList) {
		this.commentList = commentList;
	}

}
