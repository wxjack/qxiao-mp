package com.qxiao.wx.openedition.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmNewRemarkDTO {

	private String teacherText;// 最新老师评语
	private String sysText;// 最新系统评语
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date teacherTime;// 老师评语时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date sysTime;// 系统评语时间

	public String getTeacherText() {
		return teacherText;
	}

	public String getSysText() {
		return sysText;
	}

	public Date getTeacherTime() {
		return teacherTime;
	}

	public Date getSysTime() {
		return sysTime;
	}

	public void setTeacherText(String teacherText) {
		this.teacherText = teacherText;
	}

	public void setSysText(String sysText) {
		this.sysText = sysText;
	}

	public void setTeacherTime(Date teacherTime) {
		this.teacherTime = teacherTime;
	}

	public void setSysTime(Date sysTime) {
		this.sysTime = sysTime;
	}

}
