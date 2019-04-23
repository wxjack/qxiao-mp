package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 小Q表现在家表现统计 Qm_Home_Stat
 * @author xiaojiao
 * @创建时间：2019年4月12日
 */
@Entity
public class QmHomeStat {
	
	@Id
	@GeneratedValue
	private Long id;// ID
	private Long classId;// 班级ID
	private Long actionId;// 行为ID
	private int actionType;// 行为类型 0-缺省行为标准 1-自定义行为标准
	private String day;// 表现日期
	private Long starCount;// 打星数量
	private Date postTime;// 时间
	
	public Long getId() {
		return id;
	}
	public Long getClassId() {
		return classId;
	}
	public Long getActionId() {
		return actionId;
	}
	public int getActionType() {
		return actionType;
	}
	public String getDay() {
		return day;
	}
	public Long getStarCount() {
		return starCount;
	}
	public Date getPostTime() {
		return postTime;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setStarCount(Long starCount) {
		this.starCount = starCount;
	}
	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
