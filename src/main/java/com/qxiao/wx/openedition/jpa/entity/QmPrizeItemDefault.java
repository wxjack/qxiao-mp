package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 表现缺省奖项表
 * @author xiaojiao 
 * @创建时间：2019年4月8日
 */
@Entity
public class QmPrizeItemDefault {

	@Id
	@GeneratedValue
	private Long itemId;// 奖项ID
	private String textContent;// 奖项说明
	private int starCount;// 星星兑换数量
	private Date postTime;

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
