package com.qxiao.wx.openedition.dto;

import java.util.List;

public class HistoryStrikeQueryDto {
	private String day;
	private Integer starCount;
	private List<StrikStarActionsDto> actions;
	public Integer getStarCount() {
		return starCount;
	}
	public void setStarCount(Integer starCount) {
		this.starCount = starCount;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public List<StrikStarActionsDto> getActions() {
		return actions;
	}
	public void setActions(List<StrikStarActionsDto> actions) {
		this.actions = actions;
	}
	
}
