package com.qxiao.wx.user.vo;

public class QueryStudentSupplyVo {
	private String studentName;
	private Integer sex;
	private String tel;
	private Integer relation;
	private String className;
	private Long classId;
	private Long studentId;
	private Long patriarchId;
	private Long patriarchStudentId;
	
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
}
