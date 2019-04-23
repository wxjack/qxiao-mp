package com.qxiao.wx.fresh.jpa.service;

import java.io.IOException;

import org.json.JSONArray;

import com.qxiao.wx.fresh.dto.QmFreshDetailDTO;
import com.qxiao.wx.fresh.dto.QmFreshInfoDTO;
import com.qxiao.wx.fresh.jpa.entity.QmFreshInfo;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmFreshInfoService {

	DataPage<QmFreshInfoDTO> freshQuery(String openId, Long classId, Long studentId, int page, int pageSize) throws ServiceException;

	QmFreshDetailDTO findFreshDetail(String openId, Long freshId, Long classId, Long studentId)throws IOException;

	QmFreshInfo addFresh(String openId, String title, String textContent, JSONArray images, JSONArray readers);

	void sendFresh(Long freshId, String openId);

	void deleteFresh(Long freshId, String openId);

}
