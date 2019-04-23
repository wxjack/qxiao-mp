package com.qxiao.wx.fresh.dto;

import java.util.Date;
import java.util.List;

import com.qxiao.wx.fresh.vo.QmCommentInfoVO;

public class QmFreshCommentDTO {

	private Long freshId;
	private String title;
	private Date postTime;
	private List<QmCommentInfoVO> contents;
	
	public Long getFreshId() {
		return freshId;
	}
	public String getTitle() {
		return title;
	}
	public Date getPostTime() {
		return postTime;
	}
	public List<QmCommentInfoVO> getContents() {
		return contents;
	}
	public void setFreshId(Long freshId) {
		this.freshId = freshId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	public void setContents(List<QmCommentInfoVO> contents) {
		this.contents = contents;
	}
	
}
