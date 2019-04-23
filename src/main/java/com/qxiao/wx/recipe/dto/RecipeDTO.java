package com.qxiao.wx.recipe.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RecipeDTO {

	private Long recipeId;
	private String title;
	private String textContent;
	private String topImage;
	private String name; // 发送人
	private int readCount;
	private int status;
	private Long studentId;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date endDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date postTime;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
	public String getTopImage() {
		return topImage;
	}
	public String getName() {
		return name;
	}
	public int getReadCount() {
		return readCount;
	}
	public int getStatus() {
		return status;
	}
	public Date getEndDate() {
		return endDate;
	}
	public Date getStartDate() {
		return startDate;
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
	public void setTopImage(String topImage) {
		this.topImage = topImage;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
