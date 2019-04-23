package com.qxiao.wx.openedition.jpa.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.qxiao.wx.openedition.dto.QmExamPaperDetailDTO;
import com.qxiao.wx.openedition.jpa.dao.QmPaperDetailDao;
import com.qxiao.wx.openedition.jpa.entity.QmPaperDetail;
import com.qxiao.wx.openedition.jpa.service.IQmPaperDetailService;
import com.qxiao.wx.openedition.sql.ConstSql;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QmPaperDetailServiceImpl extends AbstractJdbcService<QmPaperDetail> implements IQmPaperDetailService {

	private QmPaperDetailDao detailDao;

	@Override
	public JPADao<QmPaperDetail> getDao() {
		return detailDao;
	}

	@Override
	public Class<QmPaperDetail> getEntityClass() {
		return QmPaperDetail.class;
	}

	@Override
	public QmExamPaperDetailDTO examPaperDetail(String openId, Long paperId) {
		if (paperId > 0) {
			List<QmExamPaperDetailDTO> dtos = (List<QmExamPaperDetailDTO>) findList(ConstSql.examPaperDetailSql(),
					new Object[] { paperId }, QmExamPaperDetailDTO.class);
			if (CollectionUtils.isNotEmpty(dtos)) {
				return dtos.get(0);
			}
		}
		return null;
	}

}
