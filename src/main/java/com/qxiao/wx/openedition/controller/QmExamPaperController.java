package com.qxiao.wx.openedition.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qxiao.wx.openedition.jpa.entity.QmPaperComment;
import com.qxiao.wx.openedition.jpa.service.IQmExamPaperService;
import com.qxiao.wx.openedition.jpa.service.IQmPaperCommentService;
import com.qxiao.wx.openedition.jpa.service.IQmPaperDetailService;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@RestController
@RequestMapping("action/mod-xiaojiao/exam")
public class QmExamPaperController {

	private Logger log = LoggerFactory.getLogger(QmExamPaperController.class);

	@Autowired
	private IQmExamPaperService paperService;
	@Autowired
	private IQmPaperDetailService paperDetailService;
	@Autowired
	private IQmPaperCommentService commentService;

	@PostMapping(value = "/examPaperQuery.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage examPaperQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long stageId = null;
		Long lessonId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			stageId = json.getLong("stageId");
			lessonId = json.getLong("lessonId");
			int page = json.getInt("page");
			int pageSize = json.getInt("pageSize");
			if (lessonId != null && stageId != null) {
				res.setData(paperService.examPaperQuery(openId, stageId, lessonId, page, pageSize));
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【试卷列表】:{},openId = {}, lessonId = {}, stageId = {}", e.getMessage(), openId, lessonId, stageId);
		}
		return res;
	}

	@PostMapping(value = "/examPaperDetail.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage examPaperDetail(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long paperId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			paperId = json.getLong("paperId");
			if (paperId != null) {
				res.setData(paperDetailService.examPaperDetail(openId, paperId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【试卷详情】:{},openId = {}, paperId = {}", e.getMessage(), openId, paperId);
		}
		return res;
	}

	@PostMapping(value = "/examPaperCommentQuery.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage examPaperCommentQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long paperId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			paperId = json.getLong("paperId");
			if (paperId != null) {
				res.setData(commentService.examPaperCommentQuery(openId, paperId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【评论列表】:{},openId = {}, paperId = {}", e.getMessage(), openId, paperId);
		}
		return res;
	}

	@PostMapping(value = "/examPaperComment.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage examPaperComment(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		String textContent = null;
		Long paperId = null;
		Long studentId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			textContent = json.getString("textContent");
			paperId = json.getLong("paperId");
			studentId = json.getLong("studentId");
			if (paperId != null) {
				QmPaperComment comment = commentService.addexamPaperComment(openId, paperId, studentId, textContent);
				if (comment != null) {
					Map map = new HashMap<>();
					map.put("commengId", comment.getCommentId());
					res.setData(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【发表评论】:{},openId = {}, paperId = {}", e.getMessage(), openId, paperId);
		}
		return res;
	}

}
