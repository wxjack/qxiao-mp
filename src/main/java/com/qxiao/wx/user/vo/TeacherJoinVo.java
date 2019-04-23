package com.qxiao.wx.user.vo;

public class TeacherJoinVo {
	private String openId;
	private Long schoolId;
	private String teacherName;
	private int sex;
	private String tel;
	private String veriftCode;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	
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
	public String getVeriftCode() {
		return veriftCode;
	}
	public void setVeriftCode(String veriftCode) {
		this.veriftCode = veriftCode;
	}
	
	
}
