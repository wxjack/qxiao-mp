package com.qxiao.wx.openedition.jpa.service;

import com.qxiao.wx.openedition.dto.QmPrizeItemDTO;
import com.qxiao.wx.openedition.jpa.entity.QmPrizeItem;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmPrizeItemService {

	/**
	 * 新增奖励
	 * @param openId
	 * @param textContent
	 * @param starCount
	 * @return
	 */
	QmPrizeItem addPrize(String openId, String textContent, int starCount);

	/**
	 * 奖励列表查询
	 * 
	 * @param openId
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	DataPage<QmPrizeItemDTO> queryPrizeList(String openId, int page, int pageSize) throws ServiceException;

	/**
	 * 奖励删除接口
	 * @param openId
	 * @param itemId
	 */
	void deletePrize(String openId, Long itemId,int prizeType);

	QmStudent queryTotalCountStar(String openId, Long studentId);

}
