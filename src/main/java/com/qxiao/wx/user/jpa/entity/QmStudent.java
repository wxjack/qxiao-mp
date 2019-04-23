package com.qxiao.wx.user.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class QmStudent {
	@Id
	@GeneratedValue
	private Long studentId;
	private String studentName;
	private int sex;
	private String photo;
	private String nfcId;
	private String ibeaconId;
	private int status;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date postTime;
	private String url;
	private Integer totalStarCount;
	private String nfcNo;
	private String nfcId2;
	
	public String getNfcId2() {
		return nfcId2;
	}

	public void setNfcId2(String nfcId2) {
		this.nfcId2 = nfcId2;
	}

	public Integer getTotalStarCount() {
		return totalStarCount;
	}

	public void setTotalStarCount(Integer totalStarCount) {
		this.totalStarCount = totalStarCount;
	}

	public String getNfcNo() {
		return nfcNo;
	}

	public void setNfcNo(String nfcNo) {
		this.nfcNo = nfcNo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public QmStudent () {}

	public String getIbeaconId() {
		return ibeaconId;
	}

	public void setIbeaconId(String ibeaconId) {
		this.ibeaconId = ibeaconId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public int getSex() {
		return sex;
	}

	public String getPhoto() {
		return photo;
	}

	public String getNfcId() {
		return nfcId;
	}

	public int getStatus() {
		return status;
	}


	public Date getPostTime() {
		return postTime;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setNfcId(String nfcId) {
		this.nfcId = nfcId;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
