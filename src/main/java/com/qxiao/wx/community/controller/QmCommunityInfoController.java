package com.qxiao.wx.community.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qxiao.wx.community.dto.QmCommunityCommentDTO;
import com.qxiao.wx.community.dto.QmCommunityInfoDTO;
import com.qxiao.wx.community.dto.QmCommunityPraiseDTO;
import com.qxiao.wx.community.jpa.service.IQmCommunityCommentService;
import com.qxiao.wx.community.jpa.service.IQmCommunityInfoService;
import com.qxiao.wx.community.jpa.service.IQmCommunityPraiseService;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.service.IQmAccountService;
import com.spring.entity.DataPage;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;
import com.vdurmont.emoji.EmojiParser;

@CrossOrigin
@RestController
@RequestMapping(value = "action/mod-xiaojiao/community")
public class QmCommunityInfoController {

	private Logger log = LoggerFactory.getLogger(QmCommunityInfoController.class);

	@Autowired
	private IQmCommunityInfoService communityInfoService;
	@Autowired
	private IQmAccountService accountService;
	@Autowired
	private IQmCommunityPraiseService praiseService;
	@Autowired
	private IQmCommunityCommentService commentService;

	// 班级圈信息查询
	@PostMapping(value = "/communityQuery.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage communityQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		JSONObject json = null;
		Long classId = null;
		Long studentId = null;
		try {
			json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				classId = json.getLong("classId");
				int page = json.getInt("page");
				studentId = json.getLong("studentId");
				int pageSize = json.getInt("pageSize");
				DataPage<QmCommunityInfoDTO> list = communityInfoService.communityQuery(openId, classId, studentId,
						page, pageSize);
				res.setData(list);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【查询班级圈】：{}, classId = {}, studentId = {}, json = {}", e.getMessage(), classId, studentId,
					json.toString());
		}
		return res;
	}

	// 新增班级圈
	@PostMapping(value = "/communityAdd.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage addCommunity(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		JSONObject json = null;
		Long classId = null;
		Long studentId = null;
		String openId = null;
		try {
			json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null && json != null) {
				String textContent = json.getString("textContent");
				JSONArray images = json.getJSONArray("images");
				String video = json.getString("video");
				int contentType = json.getInt("contentType");
				classId = json.getLong("classId");
				studentId = json.getLong("studentId");
				communityInfoService.addCommunityInfo(openId, textContent, images, video, contentType, classId,
						studentId);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【发布班级圈】：{}, classId = {}, studentId = {}, openId = {}, json = {}", e.getMessage(), classId, studentId, openId,
					json.toString());
		}
		return res;
	}

	// 发表评论
	@PostMapping(value = "/communityComment.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage communityComment(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		Long communityId = null;
		Long studentId = null;
		String openId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null && json != null) {
				String textContent = json.getString("textContent");
				communityId = json.getLong("communityId");
				studentId = json.getLong("studentId");
				QmCommunityCommentDTO comment = commentService.addCommunityComment(openId, textContent, communityId,
						studentId);
				res.setData(comment);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【发表评论】：{}, studentId = {}, openId = {}, communityId = {}", e.getMessage(), studentId, openId,
					communityId);
		}
		return res;
	}

	// 点赞
	@PostMapping(value = "/communityPraise.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage addCommunityPraise(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long communityId = null;
		Long studentId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null && json != null) {
				communityId = json.getLong("communityId");
				studentId = json.getLong("studentId");
				QmCommunityPraiseDTO praise = praiseService.addCommunityPraise(openId, communityId, studentId);
				res.setData(praise);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg("系统异常！__" + e.getMessage());
			log.error("【点赞】：{}, studentId = {}, openId = {}, communityId = {}", e.getMessage(), studentId, openId,
					communityId);
		}
		return res;
	}

	// 删除班级圈
	@PostMapping(value = "/communityDelete.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage communityDelete(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long communityId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null && json != null) {
				communityId = json.getLong("communityId");
				communityInfoService.deleteCommuityInfo(openId, communityId);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【删除班级圈】：{}, openId = {}, communityId = {}", e.getMessage(), openId, communityId);
		}
		return res;
	}

	// 删除评论
	@PostMapping(value = "/deleteComment.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage deleteComment(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long commentId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null && json != null) {
				commentId = json.getLong("commentId");
				if (commentId != null && commentId > 0)
					commentService.deleteComment(openId, commentId);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【删除班级圈】：{}, openId = {}, commentId = {}", e.getMessage(), openId, commentId);
		}
		return res;
	}
}
