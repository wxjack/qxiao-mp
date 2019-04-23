package com.qxiao.wx.user.vo;

public class QuerySchoolClassVo {

	private String className;
	private Integer countTeacher;
	private Integer countStudent;
	private Long classId;
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
	public Integer getCountTeacher() {
		return countTeacher;
	}
	public void setCountTeacher(Integer countTeacher) {
		this.countTeacher = countTeacher;
	}
	public Integer getCountStudent() {
		return countStudent;
	}
	public void setCountStudent(Integer countStudent) {
		this.countStudent = countStudent;
	}
	
}
