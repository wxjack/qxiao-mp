package com.qxiao.wx.openedition.jpa.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.qxiao.wx.openedition.dto.QmNewRemarkDTO;
import com.qxiao.wx.openedition.dto.QmRemarkDTO;
import com.qxiao.wx.openedition.jpa.entity.QmRemark;
import com.qxiao.wx.openedition.jpa.service.IQmRemarkService;
import com.qxiao.wx.openedition.sql.ConstSql;
import com.qxiao.wx.openedition.vo.QmRemarkVO;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmRemarkServiceImpl extends AbstractJdbcService<QmRemark> implements IQmRemarkService {

	@Override
	public JPADao<QmRemark> getDao() {
		return null;
	}

	@Override
	public Class<QmRemark> getEntityClass() {
		return null;
	}

	@Override
	public DataPage<QmRemarkDTO> queryRemarkListQuery(String openId, Long studentId, int page, int pageSize)
			throws ServiceException {
		if (studentId != null) {
			return (DataPage<QmRemarkDTO>) super.getPage(ConstSql.queryRemarksSql(), page, pageSize,
					new Object[] { studentId }, QmRemarkDTO.class);
		}
		return null;
	}

	@Override
	public QmNewRemarkDTO queryNewRemark(String openId, Long studentId) {

		if (studentId > 0) {
			QmNewRemarkDTO nr = new QmNewRemarkDTO();
			QmRemarkVO sysRm = null;
			QmRemarkVO tRm = null;
			int sysType = 0;
			int tType = 1;
			List<QmRemarkVO> findList = (List<QmRemarkVO>) findList(ConstSql.queryNewRemarkSql(),
					new Object[] { sysType, studentId }, QmRemarkVO.class); // 获取系统评语
			if (CollectionUtils.isNotEmpty(findList))
				sysRm = findList.get(0);

			List<QmRemarkVO> findList2 = (List<QmRemarkVO>) findList(ConstSql.queryNewRemarkSql(),
					new Object[] { tType, studentId }, QmRemarkVO.class); // 获取老师评语
			if (CollectionUtils.isNotEmpty(findList2))
				tRm = findList.get(0);

			if (sysRm == null && tRm == null)
				return null;

			if (sysRm != null) {
				nr.setSysText(sysRm.getTextContent());
				nr.setSysTime(sysRm.getPostTime());
			}
			if (tRm != null) {
				nr.setTeacherText(tRm.getTextContent());
				nr.setTeacherTime(tRm.getPostTime());
			}
			return nr;
		}
		return null;
	}

}
