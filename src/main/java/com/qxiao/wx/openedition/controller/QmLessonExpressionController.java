package com.qxiao.wx.openedition.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qxiao.wx.openedition.dto.QmLessonScoreDTO;
import com.qxiao.wx.openedition.jpa.service.IQmLessonExpressionService;
import com.qxiao.wx.openedition.jpa.service.IQmLessonScoreService;
import com.spring.entity.DataPage;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@RestController
@RequestMapping("action/mod-xiaojiao/expression")
public class QmLessonExpressionController {

	@Autowired
	private IQmLessonExpressionService lessonService;
	@Autowired
	private IQmLessonScoreService scoreService;

	private Logger log = LoggerFactory.getLogger(QmLessonExpressionController.class);

	@PostMapping(value = "/lessonQuery.do", produces = "application/json;charser=utf-8")
	public ResponseMessage lessonQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long studentId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			studentId = json.getLong("studentId");
			String day = json.getString("day");
			res.setData(lessonService.lessonQuery(openId, studentId, day));
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【发布通知】:{},openId = {},studentId = {}", e.getMessage(), openId, studentId);
		}
		return res;
	}

	@PostMapping(value = "/lessonScoreQuery.do", produces = "application/json;charser=utf-8")
	public ResponseMessage lessonScoreQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long studentId = null;
		Long lessonId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			studentId = json.getLong("studentId");
			lessonId = json.getLong("lessonId");
			int page = json.getInt("page");
			int pageSize = json.getInt("pageSize");
			DataPage<QmLessonScoreDTO> scores = scoreService.lessonScoreQuery(openId, studentId, lessonId, page,
					pageSize);
			res.setData(scores);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【发布通知】:{},openId = {},account = {}", e.getMessage(), openId);
		}
		return res;
	}

}
