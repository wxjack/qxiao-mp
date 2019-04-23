package com.qxiao.wx.community.dto;

public class QmCommunityCommentDTO {
	
	private String openId;
	private Long commentId;
	private String textContent;
	private String studentName;
    private int relation;
    private Long studentId;
    private String photo;
    
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getTextContent() {
		return textContent;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public String getOpenId() {
		return openId;
	}
	public int getRelation() {
		return relation;
	}
	public String getPhoto() {
		return photo;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
    
}
