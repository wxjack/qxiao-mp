package com.qxiao.wx.openedition.jpa.service;

import com.qxiao.wx.openedition.dto.HistoryStrikeQueryDto;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

import net.sf.json.JSONArray;

public interface IQmStrikStarService {
	
	void actionStrike(String openId,Long studentId,String day,JSONArray actionArray) throws Exception;

	DataPage<HistoryStrikeQueryDto> historyStrikeQuery(String openId, Long studentId, Integer page, Integer pageSize)
			throws ServiceException;
}
