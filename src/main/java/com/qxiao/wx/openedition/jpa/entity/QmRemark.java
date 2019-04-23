package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * 表现评语Qm_Remark
 * @author xiaojiao
 *
 * @创建时间：2019年4月9日
 */
@Entity
public class QmRemark {
	@Id
	@GeneratedValue
	private Long remarkId		;//评语ID主键ID
	private Long teacherId		;//老师ID
	private String textContent	;//评语内容
	private Long studentId		;//	学生ID
	private int remarkType		;//评语类型 0-系统评语 1-老师评语
	private Date postTime;// 评语时间

	public Long getRemarkId() {
		return remarkId;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public String getTextContent() {
		return textContent;
	}

	public Long getStudentId() {
		return studentId;
	}

	public int getRemarkType() {
		return remarkType;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void setRemarkType(int remarkType) {
		this.remarkType = remarkType;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
