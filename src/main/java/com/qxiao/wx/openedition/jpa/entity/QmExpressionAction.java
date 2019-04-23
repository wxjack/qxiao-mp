package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QmExpressionAction {

	@Id
	@GeneratedValue
	private Long actionId;
	private String title;
	private String textContent;
	private int actionType;
	private int serial;
	private String openId;
	private Date postTime;

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public Long getActionId() {
		return actionId;
	}

	public String getTitle() {
		return title;
	}

	public String getTextContent() {
		return textContent;
	}

	public int getSerial() {
		return serial;
	}

	public String getOpenId() {
		return openId;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
