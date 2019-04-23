package com.qxiao.wx.openedition.dto;

import java.util.Date;

public class QmPaperCommentDTO {

	private String commentId;// 留言Id
	private String textContent;// 留言内容
	private String name;// 评论人
	private String photo;// 头像
	private Date postTime;// 时间

	public String getCommentId() {
		return commentId;
	}

	public String getTextContent() {
		return textContent;
	}

	public String getName() {
		return name;
	}

	public String getPhoto() {
		return photo;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
