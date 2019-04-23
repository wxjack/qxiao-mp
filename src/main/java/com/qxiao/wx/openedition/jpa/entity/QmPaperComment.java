package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 
 * @author xiaojiao
 *
 * @创建时间：2019年4月9日
 */
@Entity
public class QmPaperComment {
	@Id
	@GeneratedValue
	private Long commentId;//评论ID
	private Long paperId;// 试卷ID
	private String textContent;//评论内容
	private String openId;// 评论人openId
	private Long studentId;
	private Date postTime;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getCommentId() {
		return commentId;
	}
	public Long getPaperId() {
		return paperId;
	}
	public String getTextContent() {
		return textContent;
	}
	public String getOpenId() {
		return openId;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
