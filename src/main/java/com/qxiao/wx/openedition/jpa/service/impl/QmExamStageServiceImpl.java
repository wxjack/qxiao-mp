package com.qxiao.wx.openedition.jpa.service.impl;

import org.springframework.stereotype.Service;

import com.qxiao.wx.openedition.jpa.entity.QmExamStage;
import com.qxiao.wx.openedition.jpa.service.IQmExamStageService;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QmExamStageServiceImpl extends AbstractJdbcService<QmExamStage> implements IQmExamStageService {

	@Override
	public JPADao<QmExamStage> getDao() {
		return null;
	}

	@Override
	public Class<QmExamStage> getEntityClass() {
		return null;
	}

}
