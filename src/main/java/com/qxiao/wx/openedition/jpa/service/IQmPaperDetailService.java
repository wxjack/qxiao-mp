package com.qxiao.wx.openedition.jpa.service;

import com.qxiao.wx.openedition.dto.QmExamPaperDetailDTO;

public interface IQmPaperDetailService {

	/**
	 * 	试卷详情查询
	 * @param openId
	 * @param stageId
	 * @return
	 */
	QmExamPaperDetailDTO examPaperDetail(String openId, Long stageId);
	
}
