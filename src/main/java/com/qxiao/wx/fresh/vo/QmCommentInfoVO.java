package com.qxiao.wx.fresh.vo;

import java.util.Date;

public class QmCommentInfoVO {

	private Long commentId;
    private String name; //评论人名称
    private int relation;
    private Date postTime; 
    
	public Long getCommentId() {
		return commentId;
	}
	public String getName() {
		return name;
	}
	public int getRelation() {
		return relation;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRelation(int relation) {
		this.relation = relation;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
    
}
