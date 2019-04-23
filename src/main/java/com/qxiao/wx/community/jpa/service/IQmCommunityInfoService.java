package com.qxiao.wx.community.jpa.service;

import org.json.JSONArray;

import com.qxiao.wx.community.dto.QmCommunityInfoDTO;
import com.qxiao.wx.community.jpa.entity.QmCommunityInfo;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmCommunityInfoService {

	QmCommunityInfo addCommunityInfo(String openId, String textContent, JSONArray images, String video, int contentType,
			Long classId,Long studentId);
	
	/**
	 * 	老师端班级圈
	 * @param openId
	 * @param classId
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	DataPage<QmCommunityInfoDTO> communityQuery(String openId, Long classId, Long studentId, int page, int pageSize)
			throws Exception;

	void deleteCommuityInfo(String openId, Long communityId) throws ServiceException;

}
