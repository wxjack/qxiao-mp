package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 学生和行为对应
 * @author xiaojiao
 *
 * @创建时间：2019年4月8日
 */
@Entity
public class QmStudentAction {
	
	@Id
	@GeneratedValue
	private Long id;
	private Long studentId;//学生ID
	private Long actionId;//行为ID
	private int actionType;//行为类型 0-缺省行为 1-自定义行为
	private Date postTime;//时间
	
	public Long getId() {
		return id;
	}
	public Long getStudentId() {
		return studentId;
	}
	public Long getActionId() {
		return actionId;
	}
	public int getActionType() {
		return actionType;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
}
