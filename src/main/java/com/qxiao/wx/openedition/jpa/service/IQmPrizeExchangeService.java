package com.qxiao.wx.openedition.jpa.service;

import org.json.JSONArray;

import com.qxiao.wx.openedition.dto.QmPrizeExchangeRecordDTO;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmPrizeExchangeService {

	/**
	 * 奖励兑换
	 * @param openId
	 * @param prizeType
	 * @param studentId
	 * @param itemId
	 * @param times
	 * @param itemArray
	 * @throws ServiceException
	 */
	void changePrize(String openId, Long studentId, JSONArray itemArray) throws ServiceException;

	/**
	 * 兑奖记录查询
	 * @param openId
	 * @param studentId
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	DataPage<QmPrizeExchangeRecordDTO> QueryPrizeExchangeLog(String openId, Long studentId, int page, int pageSize)
			throws ServiceException;

}
