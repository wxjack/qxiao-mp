package com.qxiao.wx.openedition.dto;

public class ActionDto {
	private Long actionId;// 行为ID
	private String title;// 行为标题
	private String textContent;// 行为说明
	private int actionType;// 行为类型0-系统默认 1-自定义
	private int serial;// 序号
	public Long getActionId() {
		return actionId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
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
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public int getSerial() {
		return serial;
	}
	public void setSerial(int serial) {
		this.serial = serial;
	}
	
}
