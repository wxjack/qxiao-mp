package com.qxiao.wx.user.vo;

import java.util.List;

public class QueryTeacherInfoVo {
	private Long teacherId;
	private String teacherName;
	private String photo;
	private int sex;
	private String tel;
	private int type;
	private String openId;
	private List<Object> classes;
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public List<Object> getClasses() {
		return classes;
	}
	public void setClasses(List<Object> classes) {
		this.classes = classes;
	}
	
}
