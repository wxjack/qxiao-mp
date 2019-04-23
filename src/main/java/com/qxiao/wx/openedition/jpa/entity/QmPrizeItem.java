package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 表现奖项
 * @author xiaojiao
 * @创建时间：2019年4月8日
 */
@Entity
public class QmPrizeItem {
	
	@Id
	@GeneratedValue
	private Long itemId;//奖项ID
	private String textContent;//奖项说明
	private int starCount;//星星兑换数量
	private String openId; // 当前登录用户Openid
	private Date postTime;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Long getItemId() {
		return itemId;
	}

	public String getTextContent() {
		return textContent;
	}

	public int getStarCount() {
		return starCount;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
