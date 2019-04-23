package com.qxiao.wx.user.vo;

import java.util.List;
import java.util.Map;

public class StudentAddJsonVo {

	private String openId;
	private String studentName;
	private Integer sex;
	private String className;
	private Long classId;
	private Long studentId;
	private Long patriarchId;
	private Long patriarchStudentId;
	private List<Map<String, Object>> linkMan;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public Long getPatriarchId() {
		return patriarchId;
	}
	public void setPatriarchId(Long patriarchId) {
		this.patriarchId = patriarchId;
	}
	public Long getPatriarchStudentId() {
		return patriarchStudentId;
	}
	public void setPatriarchStudentId(Long patriarchStudentId) {
		this.patriarchStudentId = patriarchStudentId;
	}
	public List<Map<String, Object>> getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(List<Map<String, Object>> linkMan) {
		this.linkMan = linkMan;
	}
	
}
