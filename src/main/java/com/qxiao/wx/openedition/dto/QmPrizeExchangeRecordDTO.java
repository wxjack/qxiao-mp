package com.qxiao.wx.openedition.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmPrizeExchangeRecordDTO {

	private Long exchangeId;// 兑奖ID
	private String textContent;// 奖项描述
	private int times;// 奖项倍数
	private int starCount;// 消耗的星星数量
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;// 时间

	public Long getExchangeId() {
		return exchangeId;
	}

	public String getTextContent() {
		return textContent;
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

	public void setTextContent(String textContent) {
		this.textContent = textContent;
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
