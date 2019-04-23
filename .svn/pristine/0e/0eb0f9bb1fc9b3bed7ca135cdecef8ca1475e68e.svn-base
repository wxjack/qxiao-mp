package com.qxiao.wx.common.getclasses;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qxiao.wx.componse.GetIdentityService;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.service.IQmAccountService;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@RestController
@RequestMapping(value = "action/mod-xiaojiao")
public class GetTeacherInfoController {

	private Logger log = Logger.getLogger(GetClassInfoController.class);

	@Autowired
	private GetIdentityService identityService;
	@Autowired
	private IQmAccountService accountService;

	@PostMapping(value = "/teacher.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage getClassInfo(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				res.setData(identityService.getTeacherInfo(openId));
			} else {
				res.setErrorCode(306);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return res;
	}
	
}
