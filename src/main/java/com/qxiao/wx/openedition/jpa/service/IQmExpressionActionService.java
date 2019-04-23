package com.qxiao.wx.openedition.jpa.service;

import java.util.List;
import java.util.Map;

import com.qxiao.wx.openedition.dto.HomeStatQueryDto;
import com.qxiao.wx.openedition.dto.QueryRuleDto;

import net.sf.json.JSONArray;

public interface IQmExpressionActionService {
	
	void addActionDefual(String title, String textContent, JSONArray rules);
	
	void addMyAction(String openId,Long studentId,JSONArray action);
	
	Map<String, Object> queryStudentAction(String openId,Long studentId);
	
	List<HomeStatQueryDto> homeStatQuery(Long studentId, Long actionId,Integer actionType);
	
	List<?> queryStudentActionDefaultList(String openId,Long studentId);
	
	List<?> queryStudentActionList(String openId,Long studentId);
	
	void ruleConnect(String openId, Long studentId, Long actionId, Integer actionType, List<Long> rules);
	
	void ruleDelete(String openId,Long ruleId,Integer actionType) throws Exception;
	
	void ruleAdd(Integer actionType,String openId, Long actionId, String ruleText, Integer stressFlag) throws Exception;
	
	QueryRuleDto queryStudentAction(String openId, Long actionId, Long studentId, Integer actionType);
	
	void actionUpdate(String openId,Long actionId,String title,String textContent,Integer actionType) throws Exception;
	
	Map<String, Object> actionListQuery(String openId,Long studentId,String day);
	
	void actionAdd(Long studentId,String openId, String title, String textContent, JSONArray rules);

	QueryRuleDto actionQuery(String openId, Long actionId, Integer actionType,Long studentId) throws Exception;
	
	void actionDelete(String openId, Long actionId,Integer actionType) throws Exception ;
}
