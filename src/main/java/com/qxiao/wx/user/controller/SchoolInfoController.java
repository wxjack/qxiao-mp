package com.qxiao.wx.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qxiao.wx.user.jpa.service.SchoolAddService;
import com.qxiao.wx.user.vo.SchoolAddVo;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/manage")
public class SchoolInfoController {
	private Logger log = Logger.getLogger(SchoolInfoController.class);

	@Autowired
	SchoolAddService schoolService;
	
	@RequestMapping(value="/updateSchool.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage updateSchool(HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage rm=new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String name = object.get("leaderName").toString();
			String schoolName = object.get("schoolName").toString();
			String location = object.get("location").toString();
			schoolService.updateSchool(openId, name, schoolName, location);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value="/updateIsOpen.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage updateIsOpen(HttpServletRequest request) {
		ResponseMessage rm=new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String schoolId = object.get("schoolId").toString();
			boolean isOpen = object.getBoolean("isOpen");
			schoolService.updateIsOpen(Long.valueOf(schoolId),isOpen);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 添加学校
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/schoolAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addSchool(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String string = HttpServletRequestBody.toJSONObject(request).toString();
			ObjectMapper mapper = new ObjectMapper();
			SchoolAddVo schoolVo = mapper.readValue(string, SchoolAddVo.class);
			Map<String, Object> schoolAdd = schoolService.schoolAdd(schoolVo);
			rm.setData(schoolAdd);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}
}
