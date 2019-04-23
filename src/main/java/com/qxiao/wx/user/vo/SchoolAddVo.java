package com.qxiao.wx.user.vo;

import java.util.List;
import java.util.Map;

public class SchoolAddVo {

	private String openId;
	private String schoolName;
	private int type;
	private String location;
	private String leadName;
	private String tel;
	private Boolean isOpen;
	private List<Map<String, String>> classes;

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public List<Map<String, String>> getClasses() {
		return classes;
	}

	public void setClasses(List<Map<String, String>> classes) {
		this.classes = classes;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leaderName) {
		this.leadName = leaderName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
}