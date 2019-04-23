package com.qxiao.wx.user.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class QmClockStat {
	@Id
	@GeneratedValue
	private Long statId;
	private String statDate;
	private Long classId;
	private int classCount;
	private int clockCount;
	private Float clockRate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date postTime;
	
	public Float getClockRate() {
		return clockRate;
	}
	public void setClockRate(Float clockRate) {
		this.clockRate = clockRate;
	}
	public Long getStatId() {
		return statId;
	}
	public void setStatId(Long statId) {
		this.statId = statId;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public int getClassCount() {
		return classCount;
	}
	public void setClassCount(int classCount) {
		this.classCount = classCount;
	}
	public int getClockCount() {
		return clockCount;
	}
	public void setClockCount(int clockCount) {
		this.clockCount = clockCount;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
