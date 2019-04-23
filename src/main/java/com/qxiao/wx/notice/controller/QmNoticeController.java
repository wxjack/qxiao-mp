package com.qxiao.wx.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.qxiao.wx.notice.dto.QmNoticeDetailDTO;
import com.qxiao.wx.notice.dto.QmNoticeInfoDTO;
import com.qxiao.wx.notice.dto.QmNoticeInfoReadDTO;
import com.qxiao.wx.notice.jpa.entity.QmNoticeInfo;
import com.qxiao.wx.notice.jpa.service.IQmNoticeInfoService;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.service.IQmAccountService;
import com.spring.entity.DataPage;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@RestController
@RequestMapping(value = "action/mod-xiaojiao/notice")
public class QmNoticeController {
	private Logger log = Logger.getLogger(QmNoticeController.class);

	@Autowired
	private IQmNoticeInfoService noticeService;
	@Autowired
	private IQmAccountService accountService;

	@PostMapping(value = "/noticeAdd.do")
	public ResponseMessage addNotice(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				String title = json.getString("title");
				String textContent = json.getString("textContent");
				JSONArray images = json.getJSONArray("images");
				int needConfirm = json.getInt("needConfirm");
				JSONArray senders = json.getJSONArray("senders");
				int clockType = json.getInt("clockType");
				String clockTime = json.getString("clockTime");
				QmNoticeInfo noticeInfo = noticeService.addNoticeInfo(openId, title,
						 textContent, images, needConfirm, senders, clockType, clockTime);
				res.setErrorMsg("通知创建成功！");
				Map<String, Object> map = new HashMap<>();
				map.put("noticeId", noticeInfo.getNoticeId());
				res.setData(map);
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

	@PostMapping(value = "/noticeQuery.do")
	public ResponseMessage noticeQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				int type = json.getInt("type");
				Long classId = json.getLong("classId");
				Long studentId = json.getLong("studentId");
				int page = json.getInt("page");
				int pageSize = json.getInt("pageSize");
				DataPage<QmNoticeInfoDTO> dataPage = noticeService.noticeQuery(openId, classId, studentId, type, page,
						pageSize);
				List<QmNoticeInfoDTO> data = (List<QmNoticeInfoDTO>) dataPage.getData();
				if (CollectionUtils.isNotEmpty(data)) {
					for (QmNoticeInfoDTO dto : data) {
						dto.setTextContent(dto.getTextContent());
						dto.setTitle(dto.getTitle());
					}
				}
				res.setData(dataPage);
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

	@PostMapping(value = "/noticeDetail.do")
	// 查看通告详情
	public ResponseMessage noticeDetail(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long noticeId = json.getLong("noticeId");
				Long classId = json.getLong("classId");
				Long studentId = json.getLong("studentId");
				QmNoticeDetailDTO detail = noticeService.noticeDetail(openId, noticeId, classId, studentId);
				if (detail != null) {
					detail.setTextContent(detail.getTextContent());
					detail.setTitle(detail.getTitle());
				}
				res.setData(detail);
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

	@PostMapping(value = "/noticeReaders.do")
	public ResponseMessage noticeReaders(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long noticeId = json.getLong("noticeId");
				Long classId = json.getLong("classId");
				int readFlag = json.getInt("readFlag");
				QmNoticeInfoReadDTO noticeReaders = noticeService.noticeReaders(openId, noticeId, classId, readFlag);
				res.setData(noticeReaders);
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

	@PostMapping(value = "/confirm.do")
	public ResponseMessage confirm(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long noticeId = json.getLong("noticeId");
				Long studentId = json.getLong("studentId");
				noticeService.insertConfirm(studentId, noticeId, openId);
				res.setErrorMsg("已确认！");
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

	@PostMapping(value = "/deleteNotice.do")
	public ResponseMessage deleteNotice(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long noticeId = json.getLong("noticeId");
				Long classId = json.getLong("classId");
				noticeService.deleteNotice(openId, noticeId, classId);
				res.setErrorMsg("通知删除成功！");
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
