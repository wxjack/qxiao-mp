package com.qxiao.wx.recipe.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RecipeDetailDTO {

	private Long recipeId;
	private String title;
	private String textContent;
	private int isDel;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date startDate;
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date endDate;
	private int readCount;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date postTime;
	private List<Map<String, Object>> images;

	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
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
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public int getReadCount() {
		return readCount;
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
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public List<Map<String, Object>> getImages() {
		return images;
	}
	public void setImages(List<Map<String, Object>> images) {
		this.images = images;
	}

}
