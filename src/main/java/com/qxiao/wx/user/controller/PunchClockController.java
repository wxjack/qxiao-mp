package com.qxiao.wx.user.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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

import com.qxiao.wx.user.jpa.entity.QmClockInfo;
import com.qxiao.wx.user.jpa.service.PunchService;
import com.qxiao.wx.user.vo.ClassClockQueryVo;
import com.qxiao.wx.user.vo.RealShuttleVo;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/clock")
public class PunchClockController {
	private Logger log = Logger.getLogger(PunchClockController.class);

	@Autowired
	PunchService punchService;

	@RequestMapping(value = "/addPunch.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addPunch(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String student = object.get("studentId").toString();
			Integer queryPunch = punchService.queryPunch(Long.valueOf(student));
			if(queryPunch==0) {
				rm.setErrorMsg("打卡成功");
			}else {
				rm.setErrorMsg("暂时不能打卡，请联系管理员");
			}
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 考勤统计详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryAttendance.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryAttendance(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String classId = object.get("classId").toString();
			String date = object.get("date").toString();
			String type = object.get("type").toString();
			Map<String, Object> count = new HashMap<>();
			if (type.trim().equals("1")) {
				count = punchService.queryCount(Long.valueOf(classId), date);
			}
			if (type.trim().equals("0")) {
				count = punchService.queryNotPunch(Long.valueOf(classId), date);
			}
			rm.setData(count);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 实时播报
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/realShuttle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage realShuttle(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String classId = object.get("classId").toString();
			String date = object.get("date").toString();
			List<RealShuttleVo> list = punchService.realShuttle(openId, Long.valueOf(classId), date);
			rm.setData(list);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 考勤统计查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/clockStat.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage clockStat(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String date = object.get("date").toString();
			String openId = object.get("openId").toString();
			List<Object> stat = punchService.queryStat(date,openId);
			rm.setData(stat);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 考勤打卡
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/punchClock.do", method = RequestMethod.GET,produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String punchClock(HttpServletRequest request, HttpServletResponse res) {
		String message="";
		try {
//			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String uri = request.getParameter("nfcId");
			String parameter = request.getParameter("jsoncallback");
//			String type = jsonObject.get("type").toString();
//			String nfcId = jsonObject.get("nfcId").toString();
//			String ibeaconId = jsonObject.get("ibeaconId").toString();
			Map<String, Object> punchClock = punchService.punchClock(0, uri, "");
			message=parameter+"("+new JSONObject(punchClock)+")";
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return message;
	}

	/**
	 * 查询学生当天打卡记录
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/clockQueryDate.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage clockQueryDate(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm=new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String openId = jsonObject.get("openId").toString();
			String month = jsonObject.get("data").toString();
			List<String> clockDate = punchService.clockDate(openId, month);
			rm.setData(clockDate);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 月考勤记录查询
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/clockQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage clockQuery(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String studentId = jsonObject.get("studentId").toString();
			String openId = jsonObject.get("openId").toString();
			String month = jsonObject.get("month").toString();
			List<QmClockInfo> clockQuery = punchService.clockQuery(openId, Long.valueOf(studentId), month);
			List<String> li=new ArrayList<>();
			if(clockQuery.size()>0) {
				for(QmClockInfo vo:clockQuery) {
					li.add(new SimpleDateFormat("yyyy-MM-dd").format(vo.getPostTime()));
				}
			}
			rm.setData(li);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 查询班级当天打卡记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/classClockQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage classClockQuery(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
//			String openId = object.get("openId").toString();
			String classId = object.get("classId").toString();
			List<ClassClockQueryVo> classClockQuery = punchService.classClockQuery(
					new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()), Long.valueOf(classId));
			rm.setData(classClockQuery);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}
	@RequestMapping(value = "/query.do", method = RequestMethod.POST)
	@ResponseBody
	public void clockQuery() {
		punchService.saveClockStat("2019-03-22");
	}
	
}
