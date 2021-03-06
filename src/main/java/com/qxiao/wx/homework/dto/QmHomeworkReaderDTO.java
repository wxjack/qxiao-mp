package com.qxiao.wx.homework.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qxiao.wx.homework.jpa.vo.QmReaderVO;

public class QmHomeworkReaderDTO {

	private Long homeId;
    private String title;
    private int readCount;
    private int unReadCount;
    private List<QmReaderVO> readers;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date postTime;
    
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public int getUnReadCount() {
		return unReadCount;
	}
	public void setUnReadCount(int unReadCount) {
		this.unReadCount = unReadCount;
	}
	public Long getHomeId() {
		return homeId;
	}
	public String getTitle() {
		return title;
	}
	public Date getPostTime() {
		return postTime;
	}
	public List<QmReaderVO> getReaders() {
		return readers;
	}
	public void setHomeId(Long homeId) {
		this.homeId = homeId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public void setReaders(List<QmReaderVO> readers) {
		this.readers = readers;
	}
    
}
