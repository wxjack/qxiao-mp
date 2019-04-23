package com.qxiao.wx.notice.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmNoticeInfoReadDTO {

	private Long noticeId;
	private String title;
	private int readCount;
	private int unReadCount;
	private List<QmNoticeReadDTO> readers;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date postTime;
	
	public int getReadCount() {
		return readCount;
	}
	public int getUnReadCount() {
		return unReadCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public void setUnReadCount(int unReadCount) {
		this.unReadCount = unReadCount;
	}
	public Long getNoticeId() {
		return noticeId;
	}
	public String getTitle() {
		return title;
	}
	public Date getPostTime() {
		return postTime;
	}
	public List<QmNoticeReadDTO> getReaders() {
		return readers;
	}
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public void setReaders(List<QmNoticeReadDTO> readers) {
		this.readers = readers;
	}
}
