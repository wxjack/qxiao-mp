package com.qxiao.wx.openedition.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qxiao.wx.openedition.dto.QmPrizeExchangeRecordDTO;
import com.qxiao.wx.openedition.jpa.entity.QmPrizeItem;
import com.qxiao.wx.openedition.jpa.service.IQmPrizeExchangeService;
import com.qxiao.wx.openedition.jpa.service.IQmPrizeItemService;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.entity.DataPage;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@RestController
@RequestMapping("action/mod-xiaojiao/prize")
public class QmPrizeItemController {

	private Logger log = LoggerFactory.getLogger(QmPrizeItemController.class);

	@Autowired
	private IQmPrizeItemService prizeService;
	@Autowired
	private IQmPrizeExchangeService exchangeService;

	@PostMapping(value = "/prizeAdd.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage prizeAdd(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		String textContent = null;
		int starCount = 0;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			textContent = json.getString("textContent");
			starCount = json.getInt("starCount");
			if (StringUtils.isNotBlank(textContent) && starCount > 0) {
				QmPrizeItem prize = prizeService.addPrize(openId, textContent, starCount);
				Map map = new HashMap<>();
				if (prize != null) {
					map.put("itemId", prize.getItemId());
					res.setData(map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【添加奖励】:{},openId = {}, textContent = {}, starCount = {}", e.getMessage(), openId, textContent,
					starCount);
		}
		return res;
	}

	@PostMapping(value = "/prizeListQuery.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage prizeListQuery(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			int page = json.getInt("page");
			int pageSize = json.getInt("pageSize");
			res.setData(prizeService.queryPrizeList(openId, page, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【奖励列表】:{},openId = {}", e.getMessage(), openId);
		}
		return res;
	}

	@PostMapping(value = "/queryTotalCountStar.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage queryTotalCountStar(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long studentId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			studentId = json.getLong("studentId");
			QmStudent stu = prizeService.queryTotalCountStar(openId, studentId);
			if (stu != null) {
				Map map = new HashMap<>();
				map.put("totalCountStar", stu.getTotalStarCount());
				res.setData(map);
			}

		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【奖励列表】:{},openId = {}", e.getMessage(), openId);
		}
		return res;
	}

	@PostMapping(value = "/prizeDelete.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage prizeDelete(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long itemId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			itemId = json.getLong("itemId");
			int prizeType = json.getInt("prizeType");
			prizeService.deletePrize(openId, itemId, prizeType);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【奖励删除】:{},openId = {}, itemId = {}", e.getMessage(), openId, itemId);
		}
		return res;
	}

	@PostMapping(value = "/prizeExchange.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage prizeExchange(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long studentId = null;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			studentId = json.getLong("studentId");
			JSONArray itemArray = json.getJSONArray("itemArray");
			exchangeService.changePrize(openId, studentId, itemArray);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【奖励兑换】:{}, openId = {}, studentId = {}, itemId = {}", e.getMessage(), openId, studentId);
		}
		return res;
	}

	@PostMapping(value = "/prizeExchangeLog.do", produces = "application/json;charset=UTF-8")
	public ResponseMessage prizeExchangeLog(HttpServletRequest req) {
		ResponseMessage res = new ResponseMessage();
		String openId = null;
		Long studentId = null;
		int page = 0;
		int pageSize = 0;
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(req);
			openId = json.getString("openId");
			page = json.getInt("page");
			pageSize = json.getInt("pageSize");
			studentId = json.getLong("studentId");
			DataPage<QmPrizeExchangeRecordDTO> prizeRecords = exchangeService.QueryPrizeExchangeLog(openId, studentId,
					page, pageSize);
			res.setData(prizeRecords);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("【兑奖记录】:{}, openId = {}, studentId = {}", e.getMessage(), openId, studentId);
		}
		return res;
	}

}
