package com.qxiao.wx.fresh.jpa.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.qxiao.wx.fresh.dto.QmFreshCommentDTO;
import com.qxiao.wx.fresh.vo.QmCommentVO;
import com.spring.jpa.service.ServiceException;

public interface IQmFreshCommentService {

	/**
	 * 速报留言
	 * 
	 * @param openId
	 * @param textContent
	 * @param freshId
	 * @throws ServiceException
	 */
	QmCommentVO addComment(String openId, String textContent, Long freshId,Long classId, Long studentId) throws ServiceException, IOException ;

	/**
	 * 速报评论人员查询
	 * 
	 * @param freshId
	 * @param classId
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	QmFreshCommentDTO findFreshComment(Long freshId, Long classId, Long studentId)
			throws IllegalAccessException, InvocationTargetException;

	void deleteComment(Long commentId, String openId);

}
