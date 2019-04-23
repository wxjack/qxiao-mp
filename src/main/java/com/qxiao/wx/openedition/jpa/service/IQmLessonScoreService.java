package com.qxiao.wx.openedition.jpa.service;

import com.qxiao.wx.openedition.dto.QmLessonScoreDTO;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmLessonScoreService {

	DataPage<QmLessonScoreDTO> lessonScoreQuery(String openId, Long studentId, Long lessonId, int page, int pageSize)
			throws ServiceException;

}
