package com.qxiao.wx.user.vo;

import java.util.List;
import java.util.Map;

public class AddTeacherVo {
	private String teacherName;
	private int sex;
	private String tel;
	private int type;
	private String openId;
	private int status;
	private List<Map<String, String>> classes;
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Map<String, String>> getClasses() {
		return classes;
	}
	public void setClasses(List<Map<String, String>> classes) {
		this.classes = classes;
	}
	
}
