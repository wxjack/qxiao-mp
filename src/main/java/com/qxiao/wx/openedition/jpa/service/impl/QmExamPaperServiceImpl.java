package com.qxiao.wx.openedition.jpa.service.impl;

import org.springframework.stereotype.Service;

import com.qxiao.wx.openedition.dto.QmExamPaperDTO;
import com.qxiao.wx.openedition.jpa.entity.QmExamPaper;
import com.qxiao.wx.openedition.jpa.service.IQmExamPaperService;
import com.qxiao.wx.openedition.sql.ConstSql;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmExamPaperServiceImpl extends AbstractJdbcService<QmExamPaper> implements IQmExamPaperService {

	@Override
	public JPADao<QmExamPaper> getDao() {
		return null;
	}

	@Override
	public Class<QmExamPaper> getEntityClass() {
		return QmExamPaper.class;
	}

	@Override
	public DataPage<QmExamPaperDTO> examPaperQuery(String openId, Long stageId, Long lessonId, int page, int pageSize)
			throws ServiceException {
		DataPage<QmExamPaperDTO> dataPage = (DataPage<QmExamPaperDTO>) this.getPage(ConstSql.examPaperQuerySql(), page,
				pageSize, new Object[] { stageId, lessonId }, QmExamPaperDTO.class);
		return dataPage;
	}

}
