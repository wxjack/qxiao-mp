package com.qxiao.wx.openedition.dto;

public class QmPrizeItemDTO {
	
	private Long itemId			;//奖项ID
	private int prizeType		;//奖项类型0-缺省奖项 1-自定义奖项
	private int starCount		;//对应的星星数量
	private String textContent	;//奖项说明
	
	public Long getItemId() {
		return itemId;
	}
	public int getPrizeType() {
		return prizeType;
	}
	public int getStarCount() {
		return starCount;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public void setPrizeType(int prizeType) {
		this.prizeType = prizeType;
	}
	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	
}
