package com.qxiao.wx.openedition.vo;

public class QmPrizeExchangeVO {

	private Long itemId;	// 奖项ID
	private int times;		// 奖项倍数
	private int prizeType;	// 0-缺省奖项 1-自定义奖项

	public Long getItemId() {
		return itemId;
	}

	public int getTimes() {
		return times;
	}

	public int getPrizeType() {
		return prizeType;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public void setPrizeType(int prizeType) {
		this.prizeType = prizeType;
	}

}
