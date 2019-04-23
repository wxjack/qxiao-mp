package com.qxiao.wx.recipe.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QmRecipeInfo {

	@Id
	@GeneratedValue
	private Long recipeId;
	private String title;
	private String textContent;
	private int messageSend;
	private String openId;
	private int isDel;
	private Date startDate;
	private Date endDate;
	private Date sendDate;
	private Date postTime;
	
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Long getRecipeId() {
		return recipeId;
	}
	public String getTitle() {
		return title;
	}
	public String getTextContent() {
		return textContent;
	}
	public int getMessageSend() {
		return messageSend;
	}
	public String getOpenId() {
		return openId;
	}
	public int getIsDel() {
		return isDel;
	}
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public void setMessageSend(int messageSend) {
		this.messageSend = messageSend;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
