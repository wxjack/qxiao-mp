package com.qxiao.wx.user.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qxiao.wx.user.jpa.service.TeacherService;
import com.qxiao.wx.user.util.UploadFile;
import com.qxiao.wx.user.vo.AddTeacherVo;
import com.qxiao.wx.user.vo.QueryTeacherInfoVo;
import com.qxiao.wx.user.vo.QueryTeacherVo;
import com.qxiao.wx.user.vo.TeacherInfoVo;
import com.qxiao.wx.user.vo.TeacherJoinVo;
import com.qxiao.wx.user.vo.UpdateTeacherVo;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/manage")
public class TeacherController {
	private Logger log = Logger.getLogger(TeacherController.class);

	@Autowired
	TeacherService teacherService;
	@Autowired
	UploadFile upload;

	@RequestMapping(value="/updateMe.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage updateMe(HttpServletRequest request,HttpServletResponse response) {
		ResponseMessage rm=new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String name = object.get("teacherName").toString();
			String sex = object.get("sex").toString();
			teacherService.updateMe(openId, name, Integer.valueOf(sex));
		} catch (Exception e) {
		e.printStackTrace();
		rm.setErrorCode(-1);
		rm.setErrorMsg(e.getMessage());
		log.error(e.getMessage());
		}
		return rm;
	}
	/**
	 * 查询学校老师列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryTeacher.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryTeacher(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String schoolId = object.get("schoolId").toString();
			List<QueryTeacherVo> queryTeacher = teacherService.queryTeacher(Long.valueOf(schoolId));
			rm.setData(queryTeacher);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 添加老师(园长、校长添加接口)
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/teacherAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addTeacher(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String string = HttpServletRequestBody.toString(request);
			ObjectMapper om = new ObjectMapper();
			AddTeacherVo qm = om.readValue(string, AddTeacherVo.class);
			teacherService.AddTeacher(qm);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 批量添加
	 * 
	 * @param request
	 * @param response
	 * @param openId
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/teacherBatchAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addTeachers(Long schoolId,MultipartFile file) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String fileInfo = upload.getFileInfo(file);
			List<String> addTeachers = teacherService.AddTeachers(new File(fileInfo),schoolId);
			if(CollectionUtils.isEmpty(addTeachers)) {
				rm.setData("导入成功");
			}else {
				rm.setData("部分导入成功,号码："+addTeachers+" 已存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 编辑
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/teacherUpdate.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage updateTeacher(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String string = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			UpdateTeacherVo readValue = mapper.readValue(string, UpdateTeacherVo.class);
			teacherService.updateTeacher(readValue);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 查询教师信息
	 * 
	 * @param openId
	 * @param teacherId
	 * @return
	 */
	@RequestMapping(value = "/teacherQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryTeacherInfo(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String teacherId = object.get("teacherId").toString();
			QueryTeacherInfoVo query = teacherService.query(openId, Long.valueOf(teacherId));
			if (query != null) {
				rm.setData(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/teacherDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage deleteTeacher(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String openId = jsonObject.get("openId").toString();
			String teacherId = jsonObject.get("teacherId").toString();
			teacherService.deleteTeacher(openId, Long.valueOf(teacherId));
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 教师信息完善
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/teacherJoin.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage teacherJoin(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String string = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			TeacherJoinVo vo = mapper.readValue(string, TeacherJoinVo.class);
			Map<String, Object> teacherJoin = teacherService.teacherJoin(vo);
			rm.setData(teacherJoin);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}
	/**
	 * 查询园长预先录入老师的信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/queryTeacherInfoByTel.do",method=RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryTeacherInfoByTel(HttpServletRequest request) {
		ResponseMessage rm=new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String tel = object.get("tel").toString();
			String openId = object.get("openId").toString();
			TeacherInfoVo infoVo = teacherService.queryTeacherInfoBytel(tel, openId);
			rm.setData(infoVo);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}
}
