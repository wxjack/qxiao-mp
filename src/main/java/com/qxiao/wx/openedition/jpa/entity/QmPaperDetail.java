package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 试卷详情表Qm_Paper_Detail
 * 
 * @author xiaojiao
 *
 * @创建时间：2019年4月9日
 */
@Entity
public class QmPaperDetail {
	@Id
	@GeneratedValue
	private Long id;// ID
	private Long paperId;// 试卷ID
	private String textContent;// 试卷内容
	private String videoUrl;// 视频URL
	private float fee;// 试卷费用
	private int downloadCount;// 下载次数
	private Date postTime;// 时间

	public Long getId() {
		return id;
	}

	public Long getPaperId() {
		return paperId;
	}

	public String getTextContent() {
		return textContent;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public float getFee() {
		return fee;
	}

	public int getDownloadCount() {
		return downloadCount;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPaperId(Long paperId) {
		this.paperId = paperId;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
