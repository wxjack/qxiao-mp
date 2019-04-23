package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 小Q表现缺省行为
 * 
 * @author xiaojiao
 *
 * @创建时间：2019年4月8日
 */
@Entity
public class QmExpressionActionDefault {

	@Id
	@GeneratedValue
	private Long actionId;// 行为ID
	private String title;// 行为标题
	private String textContent;// 行为说明
	private int actionType;// 行为类型0-系统默认 1-自定义
	private int serial;// 序号
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
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
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
