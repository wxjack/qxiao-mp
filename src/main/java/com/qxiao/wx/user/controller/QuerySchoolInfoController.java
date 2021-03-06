package com.qxiao.wx.user.controller;

import java.util.HashMap;
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

import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.jpa.service.QuerySchoolInfoService;
import com.qxiao.wx.user.vo.QueryBySchoolCodeVo;
import com.qxiao.wx.user.vo.SchoolInfoVo;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/manage")
public class QuerySchoolInfoController {
	private Logger log = Logger.getLogger(QuerySchoolInfoController.class);

	@Autowired
	QuerySchoolInfoService infoService;

	/**
	 * 查询学校信息
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryByOpenId(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			Object object = jsonObject.get("openId");
			SchoolInfoVo info = infoService.queryByOpenId(object.toString());
			rm.setData(info);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 教师端- 我的
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryTeacherInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryBySchoolCode(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			QueryBySchoolCodeVo bySchoolCode = infoService.queryBySchoolCode(openId);
			rm.setData(bySchoolCode);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 根据Id码查询学校信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/querySchoolInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage querySchoolInfo(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String schoolCode = object.get("schoolCode").toString();
			QmPlaySchoolInfo schoolInfo = infoService.querySchoolInfo(schoolCode);
			Map<String, Object> map = new HashMap<>();
			if (schoolInfo != null) {
				map.put("location", schoolInfo.getLocation());
				map.put("schoolName", schoolInfo.getSchoolName());
				map.put("schoolCode", schoolInfo.getSchoolCode());
				map.put("schoolId", schoolInfo.getSchoolId());
				rm.setData(map);
			} else {
				rm.setErrorCode(-1);
				rm.setErrorMsg("Id码不正确");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}
}
