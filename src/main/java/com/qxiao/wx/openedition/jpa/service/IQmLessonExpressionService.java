package com.qxiao.wx.openedition.jpa.service;

import java.util.List;

import com.qxiao.wx.openedition.dto.QmLessonExpressionDTO;

public interface IQmLessonExpressionService  {

	List<QmLessonExpressionDTO> lessonQuery(String openId, Long studentId, String day);

}
