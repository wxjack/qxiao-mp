package com.qxiao.wx.openedition.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qxiao.wx.openedition.dto.ActionAddDto;
import com.qxiao.wx.openedition.dto.AddMyActionDto;
import com.qxiao.wx.openedition.dto.HomeStatQueryDto;
import com.qxiao.wx.openedition.dto.QueryRuleDto;
import com.qxiao.wx.openedition.dto.RuleConnectDto;
import com.qxiao.wx.openedition.jpa.service.IQmExpressionActionService;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/expression")
public class QmExpressionActionController {

	@Autowired
	IQmExpressionActionService actionService;

	@RequestMapping(value = "/addActionDefual.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addActionDefual(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String object = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			ActionAddDto value = mapper.readValue(object, ActionAddDto.class);
			actionService.addActionDefual(value.getTitle(), value.getTextContent(), value.getRules());
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/addMyAction.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addMyAction(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String object = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			AddMyActionDto value = mapper.readValue(object, AddMyActionDto.class);
			actionService.addMyAction(value.getOpenId(), value.getStudentId(), value.getAction());
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/queryMyAction.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryMyAction(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String studentId = object.get("studentId").toString();
			String openId = object.get("openId").toString();
			Map<String, Object> action = actionService.queryStudentAction(openId, Long.valueOf(studentId));
			rm.setData(action);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/homeStatQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage homeStatQuery(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String studentId = object.get("studentId").toString();
			String actionId = object.get("actionId").toString();
			String actionType = object.get("actionType").toString();
			List<HomeStatQueryDto> list = actionService.homeStatQuery(Long.valueOf(studentId), Long.valueOf(actionId), Integer.valueOf(actionType));
			rm.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 查询未选中的默认行为（新增）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryStudentActionDefaultList.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryStudentActionDefaultList(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String studentId = object.get("studentId").toString();
			List<?> list = actionService.queryStudentActionDefaultList(openId, Long.valueOf(studentId));
			rm.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 查询未选中的自定义行为（新增）
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryStudentActionList.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryStudentActionList(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String studentId = object.get("studentId").toString();
			List<?> list = actionService.queryStudentActionList(openId, Long.valueOf(studentId));
			rm.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/ruleConnect.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage ruleConnect(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String object = HttpServletRequestBody.toString(request);
			ObjectMapper mapper=new ObjectMapper();
			RuleConnectDto dto = mapper.readValue(object,RuleConnectDto.class );
			actionService.ruleConnect(dto.getOpenId(), dto.getStudentId(), dto.getActionId(), dto.getActionType(), dto.getRules());
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/ruleDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage ruleDelete(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String ruleId = object.get("ruleId").toString();
			String actionType = object.get("actionType").toString();
			actionService.ruleDelete(openId, Long.valueOf(ruleId), Integer.valueOf(actionType));
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/ruleAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage ruleAdd(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String actionId = object.get("actionId").toString();
			String actionType = object.get("actionType").toString();
			String ruleText = object.get("ruleText").toString();
			String stressFlag = object.get("stressFlag").toString();
			actionService.ruleAdd(Integer.valueOf(actionType), openId, Long.valueOf(actionId), ruleText, Integer.valueOf(stressFlag));
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/actionDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage actionDelete(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String actionId = object.get("actionId").toString();
			String actionType = object.get("actionType").toString();
			actionService.actionDelete(openId, Long.valueOf(actionId), Integer.valueOf(actionType));
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	/**
	 * 查询学生的行为标准(新增)
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryStudentRule.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryStudentAction(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String actionId = object.get("actionId").toString();
			String studentId = object.get("studentId").toString();
			String actionType = object.get("actionType").toString();
			QueryRuleDto dto = actionService.queryStudentAction(openId, Long.valueOf(actionId), Long.valueOf(studentId), Integer.valueOf(actionType));
			rm.setData(dto);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping(value = "/actionUpdate.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage actionUpdate(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String actionId = object.get("actionId").toString();
			String title = object.get("title").toString();
			String actionType = object.get("actionType").toString();
			String textContent = object.get("textContent").toString();
			actionService.actionUpdate(openId, Long.valueOf(actionId), title, textContent, Integer.valueOf(actionType));
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}

	@RequestMapping(value = "/actionListQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage actionListQuery(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String studentId = object.get("studentId").toString();
			String day = object.get("day").toString();
			Map<String, Object> map = actionService.actionListQuery(openId, Long.valueOf(studentId),day);
			rm.setData(map);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}

	@RequestMapping(value = "/actionQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage actionQuery(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String actionId = object.get("actionId").toString();
			String actionType = object.get("actionType").toString();
			String studentId = object.get("studentId").toString();
			QueryRuleDto ruleDto = actionService.actionQuery(openId, Long.valueOf(actionId),
					Integer.valueOf(actionType),Long.valueOf(studentId));
			rm.setData(ruleDto);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}

	@RequestMapping(value = "/actionAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage actionAdd(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String object = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			ActionAddDto value = mapper.readValue(object, ActionAddDto.class);
			actionService.actionAdd(value.getStudentId(),value.getOpenId(), value.getTitle(), value.getTextContent(), value.getRules());
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
}
