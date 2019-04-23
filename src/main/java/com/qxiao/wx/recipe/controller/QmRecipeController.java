package com.qxiao.wx.recipe.controller;

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

import com.qxiao.wx.recipe.dto.RecipeDTO;
import com.qxiao.wx.recipe.dto.RecipeDetailDTO;
import com.qxiao.wx.recipe.jpa.service.IQmRecipeInfoService;
import com.qxiao.wx.user.jpa.entity.QmAccount;
import com.qxiao.wx.user.jpa.service.IQmAccountService;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@RestController
@RequestMapping("/action/mod-xiaojiao/recipe")
public class QmRecipeController {
	private Logger log = Logger.getLogger(QmRecipeController.class);

	@Autowired
	private IQmAccountService accountService;
	@Autowired
	private IQmRecipeInfoService infoService;

	@PostMapping(value = "/recipeAdd.do", produces = "application/json")
	public ResponseMessage recipeAdd(HttpServletRequest req) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				String title = json.getString("title");
				String textContent = json.getString("textContent");
				JSONArray images = json.getJSONArray("images");
				String startDate = json.getString("startDate");
				String endDate = json.getString("endDate");
				infoService.recipeAdd(openId, title,textContent, images, startDate, endDate);
			} else
				rm.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	@PostMapping(value = "/recipeQuery.do", produces = "application/json")
	public ResponseMessage recipeQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			Long studentId = json.getLong("studentId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				List<RecipeDTO> dtos = infoService.recipeQuery(openId, studentId);
				if (CollectionUtils.isNotEmpty(dtos)) {
					for (RecipeDTO dto : dtos) {
						dto.setTextContent(dto.getTextContent());
						dto.setTitle(dto.getTitle());
					}
				}
				res.setData(dtos);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return res;
	}

	@PostMapping(value = "/recipeDetail.do", produces = "application/json")
	public ResponseMessage recipeDetail(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long recipeId = json.getLong("recipeId");
				Long studentId = json.getLong("studentId");
				RecipeDetailDTO detail = infoService.findDetail(openId, recipeId, studentId);
				if (detail != null) {
					detail.setTextContent(detail.getTextContent());
					detail.setTitle(detail.getTitle());
				}
				res.setData(detail);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return res;
	}

	@PostMapping(value = "/recipeDelete.do", produces = "application/json")
	public ResponseMessage recipeDelete(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			String openId = json.getString("openId");
			QmAccount account = accountService.exists(openId);
			if (account != null) {
				Long recipeId = json.getLong("recipeId");
				infoService.recipeDelete(openId, recipeId);
			} else
				res.setErrorCode(306);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return res;
	}
}
