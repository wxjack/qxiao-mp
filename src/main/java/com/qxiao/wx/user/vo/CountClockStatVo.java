package com.qxiao.wx.user.vo;

import com.qxiao.wx.user.jpa.entity.QmClockStat;

public class CountClockStatVo extends QmClockStat{
	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
}
