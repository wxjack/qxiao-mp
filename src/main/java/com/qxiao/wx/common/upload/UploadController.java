package com.qxiao.wx.common.upload;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;
import com.talkweb.weixin.main.StartOnLoad;
import com.talkweb.weixin.pojo.AccessToken;

@CrossOrigin
@Controller
@RequestMapping("action/mod-xiaojiao/upload")
public class UploadController {

	private Logger log = Logger.getLogger(UploadController.class);

	@PostMapping(value = "/imgIds.do")
	@ResponseBody
	public ResponseMessage testUpload(HttpServletRequest request) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);// media_id集合
			String imgIds = object.get("imgIds").toString();
			if (imgIds == null || imgIds.length() < 0) {
				res.setErrorCode(-1);
				res.setErrorMsg("上传失败！！");
				log.error("图片上传失败 ,图片Id为空...");
			} else {
				JSONArray list = new JSONArray(imgIds);
				AccessToken token = StartOnLoad.tokenThread1.accessToken;
				Map<String, List<Map<String, String>>> images = UploadUtils.downloadImage(token.getToken(), list);
				res.setData(images);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error("图片上传失败 ：" + e.getMessage());
		}
		return res;
	}

	// 获取JsSDK授权
	@PostMapping("/delete.do")
	public ResponseMessage deleteImage(HttpServletRequest request) {
		ResponseMessage res = new ResponseMessage();
		try {
			JSONObject json = HttpServletRequestBody.toJSONObject(request);
			String url = json.getString("url");
			UploadUtils.deleteImage(url);
		} catch (Exception e) {
			e.printStackTrace();
			res.setErrorCode(-1);
			res.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return res;
	}

}