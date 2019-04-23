package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 表现兑换记录
 * 
 * @author xiaojiao
 *
 * @创建时间：2019年4月8日
 */
@Entity
public class QmPrizeExchange {
	@Id
	@GeneratedValue
	private Long exchangeId; // 竞换ID
	private Long studentId; // 学生ID
	private Long itemId; // 奖项ID
	private int times; // 倍数
	private int starCount; // 消耗的星星数量
	private String openId; // 用户Openid
	private int prizeType;//0-缺省奖项 1-自定义奖项
	private Date postTime;

	public String getOpenId() {
		return openId;
	}

	public int getPrizeType() {
		return prizeType;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setPrizeType(int prizeType) {
		this.prizeType = prizeType;
	}

	public Long getExchangeId() {
		return exchangeId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public Long getItemId() {
		return itemId;
	}

	public int getTimes() {
		return times;
	}

	public int getStarCount() {
		return starCount;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setExchangeId(Long exchangeId) {
		this.exchangeId = exchangeId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
