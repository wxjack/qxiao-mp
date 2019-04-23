package com.qxiao.wx.openedition.jpa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.common.JsonMapper;
import com.qxiao.wx.openedition.dto.QmPrizeExchangeRecordDTO;
import com.qxiao.wx.openedition.jpa.dao.QmPrizeExchangeDao;
import com.qxiao.wx.openedition.jpa.dao.QmPrizeItemDao;
import com.qxiao.wx.openedition.jpa.dao.QmPrizeItemDefaultDao;
import com.qxiao.wx.openedition.jpa.entity.QmPrizeExchange;
import com.qxiao.wx.openedition.jpa.entity.QmPrizeItem;
import com.qxiao.wx.openedition.jpa.entity.QmPrizeItemDefault;
import com.qxiao.wx.openedition.jpa.service.IQmPrizeExchangeService;
import com.qxiao.wx.openedition.sql.ConstSql;
import com.qxiao.wx.openedition.vo.QmPrizeExchangeVO;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmPrizeExchangeServiceImpl extends AbstractJdbcService<QmPrizeExchange>
		implements IQmPrizeExchangeService {

	@Autowired
	private QmPrizeExchangeDao exchangeDao;
	@Autowired
	private QmStudentDao stuDao;
	@Autowired
	private QmPrizeItemDao itemDao;
	@Autowired
	private QmPrizeItemDefaultDao defaultDao;

	@Override
	public JPADao<QmPrizeExchange> getDao() {
		return exchangeDao;
	}

	@Override
	public Class<QmPrizeExchange> getEntityClass() {
		return QmPrizeExchange.class;
	}

	@Override
	@Transactional
	public void changePrize(String openId, Long studentId, JSONArray itemArray) throws ServiceException {
		if (studentId > 0 && itemArray.length() > 0) {
			QmStudent stu = stuDao.findOne(studentId);
			Iterator<Object> it = itemArray.iterator();
			int starCount = 0;
			List<QmPrizeExchangeVO> vos = new ArrayList<>();
			List<Integer> starCounts = new ArrayList<>();
			while (it.hasNext()) {
				QmPrizeExchangeVO vo = JsonMapper.obj2Instance(it.next().toString(), QmPrizeExchangeVO.class);
				QmPrizeItemDefault def = null;
				QmPrizeItem item = null;
				if (vo.getPrizeType() == 0) {
					// 获取奖项信息（自定义 + 默认）
					def = defaultDao.findOne(vo.getItemId());
				} 

				if (vo.getPrizeType() == 1) {
					item = itemDao.findOne(vo.getItemId());
				}
				if (item != null) {
					starCount += item.getStarCount() * vo.getTimes();
					starCounts.add(item.getStarCount());
				}
				if (def != null) {
					starCount += def.getStarCount() * vo.getTimes();
					starCounts.add(def.getStarCount());
				}
				vos.add(vo); 
			}
			if (stu.getTotalStarCount() - starCount < 0) {
				throw new ServiceException("亲，您的星星数量不足！加油哦，你是最棒的！");
			}
			List<QmPrizeExchange> prizes = new ArrayList<>();
			for (int i = 0; i < vos.size(); i++) {
				QmPrizeExchange c = new QmPrizeExchange();
				c.setItemId(vos.get(i).getItemId());
				c.setPrizeType(vos.get(i).getPrizeType());
				c.setTimes(vos.get(i).getTimes());
				c.setStarCount(starCounts.get(i));
				c.setStudentId(studentId);
				c.setOpenId(openId);
				c.setPostTime(new Date());
				prizes.add(c);
			}
			// 记录兑换记录
			exchangeDao.save(prizes);
			// 修改學生星星数量
			stu.setTotalStarCount(stu.getTotalStarCount() - starCount);
			stuDao.save(stu);
		}
	}

	@Override
	public DataPage<QmPrizeExchangeRecordDTO> QueryPrizeExchangeLog(String openId, Long studentId, int page,
			int pageSize) throws ServiceException {
		return (DataPage<QmPrizeExchangeRecordDTO>) super.getPage(ConstSql.QueryPrizeExchangeSql(), page, pageSize,
				new Object[] { studentId, studentId }, QmPrizeExchangeRecordDTO.class);
	}

}
