package com.qxiao.wx.openedition.jpa.service;

import java.util.List;

import com.qxiao.wx.openedition.dto.QmPaperCommentDTO;
import com.qxiao.wx.openedition.jpa.entity.QmPaperComment;

public interface IQmPaperCommentService {

	List<QmPaperCommentDTO> examPaperCommentQuery(String openId, Long paperId);

	QmPaperComment addexamPaperComment(String openId, Long paperId, Long studentId, String textContent);

}
