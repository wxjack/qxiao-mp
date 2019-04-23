package com.qxiao.wx.user.controller;

import java.io.File;
import java.util.ArrayList;
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
import com.qxiao.wx.user.jpa.service.StudentService;
import com.qxiao.wx.user.util.UploadFile;
import com.qxiao.wx.user.vo.QueryStudentByNotice;
import com.qxiao.wx.user.vo.QueryStudentListVo;
import com.qxiao.wx.user.vo.QueryStudentSupplyVo;
import com.qxiao.wx.user.vo.QueryStudentVo;
import com.qxiao.wx.user.vo.StudentAddJsonVo;
import com.qxiao.wx.user.vo.StudentInfoVo;
import com.qxiao.wx.user.vo.StudentSupplyVo;
import com.qxiao.wx.user.vo.StudentUpdateVo;
import com.spring.web.controller.ResponseMessage;
import com.spring.web.utils.HttpServletRequestBody;

@CrossOrigin
@Controller
@RequestMapping("/action/mod-xiaojiao/manage")
public class StudentController {
	private Logger log = Logger.getLogger(StudentController.class);

	@Autowired
	StudentService studentService;
	@Autowired
	UploadFile upload;

	@RequestMapping(value = "/queryOpenStudentList.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryOpenStudentList(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			List<Map<String,Object>> list = studentService.queryOpenStudentList(openId);
			rm.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}
	@RequestMapping(value = "/addStudentWithStudentId.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addStudentWithStudentId(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String studentId = object.get("studentId").toString();
			String classId = object.get("classId").toString();
			String relation = object.get("relation").toString();
			String tel = object.get("tel").toString();
			studentService.addStudentWithStudentId(tel, Long.valueOf(studentId), Long.valueOf(classId),
					Integer.valueOf(relation));
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 老师录入学生（查询存在的学生）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryStudentOpen.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryStudentOpen(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String tel = object.get("tel").toString();
			List<Map<String, Object>> list = studentService.queryStudentOpen(tel);
			rm.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;

	}

	@RequestMapping(value = "/addStudentWithOpen.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage addStudentWithOpen(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String studentName = object.get("studentName").toString();
			String openId = object.get("openId").toString();
			String sex = object.get("sex").toString();
			String relation = object.get("relation").toString();
			String tel = object.get("tel").toString();
			Map<String, Object> student = studentService.addStudentWithOpen(openId, studentName, Integer.valueOf(sex),
					Integer.valueOf(relation), tel, response);
			rm.setData(student);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;

	}

	/**
	 * 根据noticeId
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/studentInfo.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryStudentByNoticeId(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String noticeId = object.get("noticeId").toString();
			String openId = object.get("openId").toString();
			List<QueryStudentByNotice> notice = studentService.queryStudentByNotice(Long.valueOf(noticeId), openId);
			rm.setData(notice);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;

	}

	@RequestMapping(value = "/updateStudent.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage updateStudent(HttpServletRequest request, HttpServletResponse response) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String openId = object.get("openId").toString();
			String name = object.get("studentName").toString();
			String sex = object.get("sex").toString();
			String relation = object.get("relation").toString();
			String studentId = object.get("studentId").toString();
			studentService.updateStudent(openId, name, Integer.valueOf(sex), Integer.valueOf(relation),
					Long.valueOf(studentId));

		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;

	}

	/**
	 * 删除学生
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/studentDelete.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage studentDelete(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String studentId = object.get("studentId").toString();
			String tel = object.get("tel").toString();
			String classId = object.get("classId").toString();
			studentService.studentDelete(Long.valueOf(studentId), tel, Long.valueOf(classId));
			rm.setErrorMsg("删除成功");
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 查询班级所有学生
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryStudentList.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryStudentList(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject object = HttpServletRequestBody.toJSONObject(request);
			String teacherId = object.get("teacherId").toString();
			List<QueryStudentListVo> student = studentService.queryClassIdByTeacherId(Long.valueOf(teacherId));
			rm.setData(student);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 完善学生信息
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/studentSupply.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage studentSupply(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String string = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			StudentSupplyVo vo = mapper.readValue(string, StudentSupplyVo.class);
			Map<String, Object> studentSupply = studentService.studentSupply(vo);
			rm.setData(studentSupply);
		} catch (Exception e) {
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return rm;
	}

	@RequestMapping(value = "/queryAllStudent.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryAllStudent(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String openId = jsonObject.get("openId").toString();
			List<Map<String, Object>> student = studentService.queryAllStudent(openId);
			rm.setData(student);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
		}
		return rm;
	}

	@RequestMapping(value = "/studentInfoQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage studentInfo(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String tel = jsonObject.get("tel").toString();
			String student = jsonObject.get("studentId").toString();
			List<StudentInfoVo> studentQuery = studentService.queryStudentJson(tel, Long.valueOf(student));
			rm.setData(studentQuery);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	@RequestMapping(value = "/studentQueryMe.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage studentQueryOne(HttpServletRequest request) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String openId = jsonObject.get("openId").toString();
			String string = jsonObject.get("studentId").toString();
			List<QueryStudentVo> studentQuery = studentService.studentQuery(openId, Long.parseLong(string));// 家长端我的
			rm.setData(studentQuery.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 查询学生信息
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/studentQuery.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage studentQuery(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String openId = jsonObject.get("openId").toString();
			String tel = jsonObject.get("tel").toString();
			List<QueryStudentVo> studentQuery = new ArrayList<>();
//			if (tel.trim().equals("")) {
//				studentQuery = studentService.studentQuery(openId,0L);// 家长端我的
//			} else {
			studentQuery = studentService.studentQueryInfo(tel);// 列表详细信息
//			}
			rm.setData(studentQuery);
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;
	}

	/**
	 * 添加学生
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/studentAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage studentAdd(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String string = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			StudentAddJsonVo value = mapper.readValue(string, StudentAddJsonVo.class);
			List<String> list = studentService.studentAddJson(value);
			if (CollectionUtils.isNotEmpty(list)) {
				rm.setErrorCode(2);
				rm.setErrorMsg("号码：" + list + " 已有关联学生，请联系管理员。");
			} else {
				rm.setData("添加成功");
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
	 * 查询完善学生的信息
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/queryStudentSupply.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage queryStudentSupply(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			JSONObject jsonObject = HttpServletRequestBody.toJSONObject(request);
			String string = jsonObject.get("tel").toString();
			List<QueryStudentSupplyVo> studentSupply = studentService.queryStudentSupply(string);
			rm.setData(studentSupply);
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
	@RequestMapping(value = "/studentBatchAdd.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage studentBatchAdd(Long schoolId, MultipartFile file) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String fileInfo = upload.getFileInfo(file);
			List<String> batchAdd = studentService.studentBatchAdd(schoolId, new File(fileInfo));
			if (batchAdd.size() == 0) {
				rm.setData("全部导入成功");
			} else {
				rm.setData("号码：" + batchAdd + " 已有关联学生，请联系管理员。");
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
	 * 修改
	 * 
	 * @param request
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/studentUpdate.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMessage studentUpdate(HttpServletRequest request, HttpServletResponse res) {
		ResponseMessage rm = new ResponseMessage();
		try {
			String jsonObject = HttpServletRequestBody.toString(request);
			ObjectMapper mapper = new ObjectMapper();
			StudentUpdateVo vo = mapper.readValue(jsonObject, StudentUpdateVo.class);
			List<String> list = studentService.studentUpdate(vo);
			if (CollectionUtils.isEmpty(list)) {
				rm.setData("修改成功");
			} else {
				rm.setErrorCode(2);
				rm.setErrorMsg("号码：" + list + " 已有关联学生，请联系管理员。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rm.setErrorCode(-1);
			rm.setErrorMsg(e.getMessage());
			log.error(e.getMessage());
		}
		return rm;

	}
}
