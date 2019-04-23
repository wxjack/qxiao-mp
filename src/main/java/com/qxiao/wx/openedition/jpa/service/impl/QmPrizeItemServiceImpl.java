package com.qxiao.wx.openedition.jpa.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.openedition.dto.QmPrizeItemDTO;
import com.qxiao.wx.openedition.jpa.dao.QmPrizeExchangeDao;
import com.qxiao.wx.openedition.jpa.dao.QmPrizeItemDao;
import com.qxiao.wx.openedition.jpa.entity.QmPrizeExchange;
import com.qxiao.wx.openedition.jpa.entity.QmPrizeItem;
import com.qxiao.wx.openedition.jpa.service.IQmPrizeItemService;
import com.qxiao.wx.openedition.sql.ConstSql;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmPrizeItemServiceImpl extends AbstractJdbcService<QmPrizeItem> implements IQmPrizeItemService {

	@Autowired
	private QmPrizeItemDao itemDao;
	@Autowired
	private QmPrizeExchangeDao exchangeDao;
	@Autowired
	private QmStudentDao stuDao;

	@Override
	public JPADao<QmPrizeItem> getDao() {
		return null;
	}

	@Override
	public Class<QmPrizeItem> getEntityClass() {
		return null;
	}

	@Override
	@Transactional
	public QmPrizeItem addPrize(String openId, String textContent, int starCount) {
		QmPrizeItem pi = new QmPrizeItem();
		pi.setOpenId(openId);
		pi.setTextContent(textContent);
		pi.setStarCount(starCount);
		pi.setPostTime(new Date());
		return itemDao.save(pi);
	}

	@Override
	public DataPage<QmPrizeItemDTO> queryPrizeList(String openId, int page, int pageSize) throws ServiceException {
		DataPage<QmPrizeItemDTO> itemDTO = (DataPage<QmPrizeItemDTO>) super.getPage(ConstSql.queryPrizesSql(), page,
				pageSize, new Object[] { openId }, QmPrizeItemDTO.class);
		return itemDTO;
	}

	@Override
	@Transactional
	public void deletePrize(String openId, Long itemId, int prizeType) {
		if (itemId > 0) {
			if (prizeType == 1 && itemDao.findOne(itemId) != null) {
				itemDao.delete(itemId);
				List<QmPrizeExchange> prizes = exchangeDao.findByItemIdAndPrizeType(itemId, prizeType);
				if (CollectionUtils.isNotEmpty(prizes)) {
					exchangeDao.deleteByItemIdAndPrizeType(itemId, prizeType);
				}
			}
		}
	}

	@Override
	public QmStudent queryTotalCountStar(String openId, Long studentId) {
		if (studentId > 0) {
			return stuDao.findOne(studentId);
		}
		return null;
	}
}
