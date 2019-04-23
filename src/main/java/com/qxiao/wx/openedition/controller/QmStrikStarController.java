package com.qxiao.wx.openedition.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qxiao.wx.openedition.dto.HistoryStrikeQueryDto;
import com.qxiao.wx.openedition.dto.StarAddVo;
import com.qxiao.wx.openedition.jpa.service.IQmStrikStarService;
import com.spring.entity.DataPage;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("action/mod-xiaojiao/expression")
public class QmStrikStarController {

	@Autowired
	IQmStrikStarService starService;

	@RequestMapping(value = "/historyStrikeQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage historyStrikeQuery(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String openId = jsonObject.get("openId").toString();
			String studentId = jsonObject.get("studentId").toString();
			String page = jsonObject.get("page").toString();
			String pageSize = jsonObject.get("pageSize").toString();
			DataPage<HistoryStrikeQueryDto> dataPage = starService.historyStrikeQuery(openId, Long.valueOf(studentId),
					Integer.valueOf(page), Integer.valueOf(pageSize));
			rm.setData(dataPage);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}

	@RequestMapping(value = "/actionStrike.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage actionStrike(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String object = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			StarAddVo vo = mapper.readValue(object, StarAddVo.class);
			starService.actionStrike(vo.getOpenId(), vo.getStudentId(), vo.getDay(), vo.getActionArray());
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}

}
