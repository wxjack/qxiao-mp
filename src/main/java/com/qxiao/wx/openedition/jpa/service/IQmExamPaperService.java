package com.qxiao.wx.openedition.jpa.service;

import com.qxiao.wx.openedition.dto.QmExamPaperDTO;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmExamPaperService {

	/**
	 * 试卷列表查询
	 * @param openId
	 * @param stageId
	 * @param lessonId
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	DataPage<QmExamPaperDTO> examPaperQuery(String openId, Long stageId, Long lessonId, int page, int pageSize)
			throws ServiceException;

}
