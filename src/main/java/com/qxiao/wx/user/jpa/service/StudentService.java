package com.qxiao.wx.user.jpa.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.qxiao.wx.user.vo.QueryStudentByNotice;
import com.qxiao.wx.user.vo.QueryStudentListVo;
import com.qxiao.wx.user.vo.QueryStudentSupplyVo;
import com.qxiao.wx.user.vo.QueryStudentVo;
import com.qxiao.wx.user.vo.StudentAddJsonVo;
import com.qxiao.wx.user.vo.StudentInfoVo;
import com.qxiao.wx.user.vo.StudentSupplyVo;
import com.qxiao.wx.user.vo.StudentUpdateVo;
import com.spring.jpa.service.ServiceException;

public interface StudentService {

	 List<Map<String, Object>> queryOpenStudentList(String openId);
	
	void addStudentWithStudentId(String tel,Long studentId,Long classId,Integer relation);
	
	List<Map<String, Object>> queryStudentOpen(String tel);
	
	Map<String, Object> addStudentWithOpen(String openId, String studentName, Integer sex, Integer relation, String tel,
			HttpServletResponse response) throws Exception;

	List<QueryStudentByNotice> queryStudentByNotice(Long noticeId, String openId);

	List<Map<String, Object>> queryAllStudent(String openId);

	List<String> studentAddJson(StudentAddJsonVo vo) throws Exception;

	List<StudentInfoVo> queryStudentJson(String tel, Long studentId);

	void updateStudent(String openId, String name, Integer sex, Integer relation, Long studentId);

	List<QueryStudentListVo> queryClassIdByTeacherId(Long teacherId);

	Map<String, Object> studentSupply(StudentSupplyVo vo) throws Exception;

	List<QueryStudentVo> studentQuery(String openId, Long studentId) throws Exception;

	List<QueryStudentSupplyVo> queryStudentSupply(String tele);

	/**
	 * 批量新增
	 * 
	 * @param file
	 * @return
	 */
	List<String> studentBatchAdd(Long schoolId, File file) throws Exception;

	List<String> studentUpdate(StudentUpdateVo vo) throws Exception;

	void studentDelete(Long studentId, String tel, Long classId) throws ServiceException;

	List<QueryStudentVo> studentQueryInfo(String tel) throws Exception;
}
