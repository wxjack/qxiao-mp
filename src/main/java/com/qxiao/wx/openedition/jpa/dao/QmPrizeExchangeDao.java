package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.openedition.jpa.entity.QmPrizeExchange;
import com.spring.jpa.dao.JPADao;

public interface QmPrizeExchangeDao  extends JPADao<QmPrizeExchange>  {

	List<QmPrizeExchange> findByItemIdAndPrizeType(Long itemId, int prizeType);

	@Modifying
	@Query(value = "delete from QmPrizeExchange as q where q.itemId = ?1 and q.prizeType = ?2 ")
	void deleteByItemIdAndPrizeType(Long itemId, int prizeType);

}
