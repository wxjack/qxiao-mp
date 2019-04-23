package com.qxiao.wx.alnum.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qxiao.wx.alnum.jpa.service.QmAlbumChannelService;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/album")
public class QmAlbumController {
	private final Logger log = Logger.getLogger(QmAlbumController.class);
	@Autowired
	QmAlbumChannelService channelService;

	/**
	 * 相册图片或视频上传
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/albumImageAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage albumImageAdd(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String channelId = object.get("channelId").toString();
			String type = object.get("type").toString();
			JSONArray imageUrl = object.getJSONArray("imageUrl");
			String videoUrl = object.get("videoUrl").toString();
			channelService.albumImageAdd(1L, Long.valueOf(channelId), Integer.parseInt(type), imageUrl, videoUrl);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 添加相册栏目
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addChannel.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addChannel(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String classId = object.get("classId").toString();
			String title = object.get("title").toString();
			Long channel = channelService.addChannel(Long.valueOf(classId), title);
			Map<String, Object> map = new HashMap<>();
			map.put("channel", channel);
			rm.setData(map);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 查询相册栏目详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/albumChannelDetail.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage albumChannelDetail(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String channelId = object.get("channelId").toString();
			Map<String, Object> detail = channelService.albumChannelDetail(Long.valueOf(channelId));
			rm.setData(detail);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 查询班级所属的相册栏目
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/albumChannelQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage albumChannelQuery(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String classId = object.get("classId").toString();
			List<Object> query = channelService.albumChannelQuery(Long.valueOf(classId));
			rm.setData(query);
		} catch (Exception e) {
			rm.setData(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 查询相册所属班级
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/albumClassQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage albumClassQuery(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String roleType = object.get("roleType").toString();
			List<Object> album = new ArrayList<>();
			if (roleType.trim().equals("1") || roleType.trim().equals("4")) {
				album = channelService.albumClassQueryOfLeader(openId);
			}
			if (roleType.trim().equals("2") || roleType.trim().equals("5")) {
				album = channelService.albumClassQueryOfTeacher(openId);
			}
			if (roleType.trim().equals("3") || roleType.trim().equals("6")) {
				album = channelService.albumClassQueryOfStudent(openId);
			}
			rm.setData(album);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	@RequestMapping(value = "/imageDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage imageDelete(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String itemIds = object.get("itemIds").toString();
			String  replaceAll=itemIds.replaceAll("\\[", "");
			replaceAll=replaceAll.replaceAll("\\]","");
			String[] split = replaceAll.split(",");
			List<String> asList = Arrays.asList(split);
			channelService.imageDelete(asList);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

}
