package com.qxiao.wx.recipe.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QmRecipeImage {

	@Id
	@GeneratedValue
	private Long imageId; 
	private String imageUrl;
	private Long recipeId;
	private String smallUrl;
	private Date postTime;
	
	public Long getImageId() {
		return imageId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public Long getRecipeId() {
		return recipeId;
	}
	public String getSmallUrl() {
		return smallUrl;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
	public void setSmallUrl(String smallUrl) {
		
		this.smallUrl = smallUrl;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
