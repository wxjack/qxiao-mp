package com.qxiao.wx.community.dto;

public class QmCommunityPraiseDTO {

	private String openId;
    private int relation;
    private String studentName;
    private Long studentId;
    private String photo;
    
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
}
