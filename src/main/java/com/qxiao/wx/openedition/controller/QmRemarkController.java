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

import com.qxiao.wx.openedition.jpa.service.IQmRemarkService;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@RestController
@RequestMapping("action/mod-xiaojiao/remark")
public class QmRemarkController {

	private Logger log = LoggerFactory.getLogger(QmRemarkController.class);

	@Autowired
	private IQmRemarkService remarkService;

	@PostMapping(value = "/remarkListQuery.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage remarkListQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long studentId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			studentId = json.getLong("studentId");
			int page = json.getInt("page");
			int pageSize = json.getInt("pageSize");
			if (studentId > 0) {
				res.setData(remarkService.queryRemarkListQuery(openId, studentId, page, pageSize));
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【评语列表】:{},openId = {}, studentId = {}", e.getMessage(), openId, studentId);
		}
		return res;
	}

	@PostMapping(value = "/newRemarkQuery.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage newRemarkQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long studentId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			studentId = json.getLong("studentId");
			if (studentId != null) {
				res.setData(remarkService.queryNewRemark(openId, studentId));
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【最新评语】:{},openId = {}, studentId = {}", e.getMessage(), openId, studentId);
		}
		return res;
	}

}
