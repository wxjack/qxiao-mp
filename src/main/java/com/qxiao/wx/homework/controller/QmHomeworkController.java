package com.qxiao.wx.homework.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qxiao.wx.homework.dto.QmHomeworkInfoDetailDTO;
import com.qxiao.wx.homework.dto.QmHomeworkInfoListDTO;
import com.qxiao.wx.homework.dto.QmHomeworkReaderDTO;
import com.qxiao.wx.homework.jpa.service.IQmHomeworkService;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.service.IQmAccountService;
import com.spring.entity.DataPage;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@RestController
@RequestMapping(value = "action/mod-xiaojiao/homework")
public class QmHomeworkController {

	private Logger log = Logger.getLogger(QmHomeworkController.class);

	@Autowired
	private IQmAccountService accountService;
	@Autowired
	private IQmHomeworkService homeworkService;

	@PostMapping(value = "/homeworkAdd.do", produces = "application/json;charset=utf-8")
	public ResponseMessage homeworkAdd(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				int needConfirm = json.getInt("needConfirm");
				JSONArray images = json.getJSONArray("images");
				String title = json.getString("title");
				String textContent = json.getString("textContent");
				JSONArray senders = json.getJSONArray("senders");
				homeworkService.homeworkAdd(openId, title, textContent, images, senders,
						needConfirm);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.info(e.getMessage());
		}
		return res;
	}

	/* 作业列表 */
	@PostMapping(value = "/homeworkQuery.do", produces = "application/json;charset=utf-8")
	public ResponseMessage homeworkQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long classId = json.getLong("classId");
				Long studentId = json.getLong("studentId");
				int page = json.getInt("page");
				int pageSize = json.getInt("pageSize");
				DataPage<QmHomeworkInfoListDTO> dataPage = homeworkService.homeworkQuery(openId, classId, studentId,
						page, pageSize);
				List<QmHomeworkInfoListDTO> data = (List<QmHomeworkInfoListDTO>) dataPage.getData();
				if (CollectionUtils.isNotEmpty(data)) {
					for (QmHomeworkInfoListDTO dto : data) {
						dto.setTextContent(dto.getTextContent());
						dto.setTitle(dto.getTitle());
					}
				}
				res.setData(dataPage);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/* 作业详情 */
	@PostMapping(value = "/homeworkDetail.do", produces = "application/json;charset=utf-8")
	public ResponseMessage homeworkDetail(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long homeId = json.getLong("homeId");
				Long classId = json.getLong("classId");
				Long studentId = json.getLong("studentId");
				QmHomeworkInfoDetailDTO detailDTO = homeworkService.homeworkDetail(openId, homeId, classId, studentId);
				if (detailDTO != null) {
					detailDTO.setTextContent(detailDTO.getTextContent());
					detailDTO.setTitle(detailDTO.getTitle());
				}
				res.setData(detailDTO);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/* 查询作业阅读人员明细 */
	@PostMapping(value = "/homeworkReaders.do", produces = "application/json;charset=utf-8")
	public ResponseMessage homeworkReaders(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long homeId = json.getLong("homeId");
				Long classId = json.getLong("classId");
				int readFlag = json.getInt("readFlag");
				QmHomeworkReaderDTO readers = homeworkService.homeworkReaders(openId, classId, homeId, readFlag);
				res.setData(readers);
			} else {
				res.setErrorCode(306);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/* 删除作业 */
	@PostMapping(value = "/homeworkDelete.do", produces = "application/json;charset=utf-8")
	public ResponseMessage homeworkDelete(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long homeId = json.getLong("homeId");
				homeworkService.homeworkDelete(openId, homeId);
			} else {
				res.setErrorCode(306);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

	/* 作业确认 */
	@PostMapping(value = "/homeWorkConfirm.do", produces = "application/json;charset=utf-8")
	public ResponseMessage homeWorkConfirm(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long homeId = json.getLong("homeId");
				Long studentId = json.getLong("studentId");
				homeworkService.homeWorkConfirm(openId, homeId,studentId);
			} else {
				res.setErrorCode(306);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
		}
		return res;
	}

}
