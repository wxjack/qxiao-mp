package com.qxiao.wx.notice.vo;

/**
 * 	发送对象 
 * @author xiaojiao
 *
 * @创建时间：2018年12月14日
 */
public class SenderVO {
	
	private int sendType;
	private Long senderId;
	
	public int getSendType() {
		return sendType;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSendType(int sendType) {
		this.sendType = sendType;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
}
