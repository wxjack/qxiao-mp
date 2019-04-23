package com.qxiao.wx.homework.jpa.service;

import java.io.IOException;

import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.qxiao.wx.homework.dto.QmHomeworkInfoDetailDTO;
import com.qxiao.wx.homework.dto.QmHomeworkInfoListDTO;
import com.qxiao.wx.homework.dto.QmHomeworkReaderDTO;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmHomeworkService {

	void homeworkAdd(String openId, String title, String textContent, JSONArray images, JSONArray senders,
			int needConfirm) throws ServiceException, JsonParseException, JsonMappingException, IOException;

	void homeworkDelete(String openId, Long homeId) throws ServiceException;

	DataPage<QmHomeworkInfoListDTO> homeworkQuery(String openId, Long classId, Long studentId, int page, int pageSize)
			throws ServiceException;

	QmHomeworkReaderDTO homeworkReaders(String openId, Long classId, Long homeId, int type);

	QmHomeworkInfoDetailDTO homeworkDetail(String openId, Long homeId, Long classId, Long studentId) throws ServiceException;

	void homeWorkConfirm(String openId, Long homeId, Long studentId);

}