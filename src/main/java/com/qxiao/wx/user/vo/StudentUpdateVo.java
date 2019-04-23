package com.qxiao.wx.user.vo;

import java.util.List;
import java.util.Map;

public class StudentUpdateVo {
	private String openId;
	private Long studentId;
	private String studentName;
	private int sex;
	private Long classId;
	private String className;
	private Long patriarchId;
	private List<Map<String, Object>> linkMan;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Long getPatriarchId() {
		return patriarchId;
	}
	public void setPatriarchId(Long patriarchId) {
		this.patriarchId = patriarchId;
	}
	public List<Map<String, Object>> getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(List<Map<String, Object>> linkMan) {
		this.linkMan = linkMan;
	}
	
}
