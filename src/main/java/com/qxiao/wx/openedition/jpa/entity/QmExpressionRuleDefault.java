package com.qxiao.wx.openedition.jpa.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 小Q表现缺省行为标准
 * @author xiaojiao
 *
 * @创建时间：2019年4月8日
 */
@Entity
public class QmExpressionRuleDefault {
	
	@Id
	@GeneratedValue
	private Long ruleId;
	private int serial;
	private String textContent;
	private int stressFlag;
	private Long actionId;
	private Date postTime;

	public Long getRuleId() {
		return ruleId;
	}

	public int getSerial() {
		return serial;
	}

	public String getTextContent() {
		return textContent;
	}

	public int getStressFlag() {
		return stressFlag;
	}

	public Long getActionId() {
		return actionId;
	}

	public Date getPostTime() {
		return postTime;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public void setStressFlag(int stressFlag) {
		this.stressFlag = stressFlag;
	}

	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	public void setPostTime(Date postTime) {
		this.postTime = postTime;
	}
	
}
