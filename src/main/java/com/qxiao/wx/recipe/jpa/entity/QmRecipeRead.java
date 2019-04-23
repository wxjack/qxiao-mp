package com.qxiao.wx.recipe.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class QmRecipeRead {

	@Id
	@GeneratedValue
	private Long id;
	private Long recipeId;
	private Long studentId;
	private String openId;
	private Date postTime;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Long getId() {
		return id;
	}
	public Long getRecipeId() {
		return recipeId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
