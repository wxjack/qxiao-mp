package com.qxiao.wx.homework.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity
public class QmHomeworkInfo {
	
	@Id
	@GeneratedValue
	private Long homeId;
	private String openId;
	private String title;
	private String textContent;
	private int isDel;
	private Long teacherId;
	private int needConfirm;
	private int messageSend;
	private Date sendTime;
	private Date postTime;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getHomeId() {
		return homeId;
	}
	public void setHomeId(Long homeId) {
		this.homeId = homeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	public int getNeedConfirm() {
		return needConfirm;
	}
	public void setNeedConfirm(int needConfirm) {
		this.needConfirm = needConfirm;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public int getMessageSend() {
		return messageSend;
	}
	public void setMessageSend(int messageSend) {
		this.messageSend = messageSend;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
}
