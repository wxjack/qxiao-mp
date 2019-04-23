package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;


import com.qxiao.wx.openedition.jpa.entity.QmStudentAction;
import com.spring.jpa.dao.JPADao;

public interface QmStudentActionDao extends JPADao<QmStudentAction> {

	List<QmStudentAction> findByActionId(Long actionId);

	QmStudentAction findByActionIdAndActionType(Long actionId,Integer actionType);

	QmStudentAction findByActionIdAndStudentIdAndActionType(Long actionId, Long studentId,Integer actionType);
	
}
