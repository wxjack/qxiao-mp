package com.qxiao.wx.user.vo;

import java.util.List;

public class StudentInfoVo {

	private String openId;
	private Long studentId;
	private Integer sex;
	private String StudentName;
	private Long classId;
	private Long patriarchId;
	private String className;
	private List<StudentParentVo> linkMan;

	public Long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public Long getPatriarchId() {
		return patriarchId;
	}

	public void setPatriarchId(Long patriarchId) {
		this.patriarchId = patriarchId;
	}

	public List<StudentParentVo> getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(List<StudentParentVo> linkMan) {
		this.linkMan = linkMan;
	}

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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getStudentName() {
		return StudentName;
	}

	public void setStudentName(String studentName) {
		StudentName = studentName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
