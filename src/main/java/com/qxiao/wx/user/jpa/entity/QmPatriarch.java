package com.qxiao.wx.user.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class QmPatriarch {
	
	@Id
	@GeneratedValue
	private Long id;
	private String tel;
	private String openId;
	private int relation;
	private int isDel;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date postTime;
	
	public QmPatriarch () {}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Long getId() {
		return id;
	}

	public String getTel() {
		return tel;
	}

	public String getOpenId() {
		return openId;
	}

	public int getRelation() {
		return relation;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setRelation(int relation) {
		this.relation = relation;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
