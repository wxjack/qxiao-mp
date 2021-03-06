package com.qxiao.wx.community.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QmCommunityInfoDTO {

	private Long communityId;
	private String openId;
	private Long studentId;
	private String photo;
	private String name;
	private String videoUrl;
	private String textContent;
	private List<Map<String, Object>> images;
	private int praiseCount;
	private List<QmCommunityPraiseDTO> praiseList;
	private int commentCount;
	private List<QmCommunityCommentDTO> commentList;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date postTime;

	public Long getCommunityId() {
		return communityId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getOpenId() {
		return openId;
	}

	public String getPhoto() {
		return photo;
	}

	public String getName() {
		return name;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public String getTextContent() {
		return textContent;
	}

	public List<Map<String, Object>> getImages() {
		return images;
	}

	public int getPraiseCount() {
		return praiseCount;
	}

	public List<QmCommunityPraiseDTO> getPraiseList() {
		return praiseList;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public List<QmCommunityCommentDTO> getCommentList() {
		return commentList;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setImages(List<Map<String, Object>> images) {
		this.images = images;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public void setPraiseList(List<QmCommunityPraiseDTO> praiseList) {
		this.praiseList = praiseList;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public void setCommentList(List<QmCommunityCommentDTO> commentList) {
		this.commentList = commentList;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}

}
