package com.qxiao.wx.openedition.jpa.service;

import com.qxiao.wx.openedition.dto.QmNewRemarkDTO;
import com.qxiao.wx.openedition.dto.QmRemarkDTO;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmRemarkService {

	DataPage<QmRemarkDTO> queryRemarkListQuery(String openId, Long studentId, int page, int pageSize)
			throws ServiceException;

	QmNewRemarkDTO queryNewRemark(String openId, Long studentId);

}
