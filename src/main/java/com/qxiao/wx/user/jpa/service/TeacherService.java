package com.qxiao.wx.user.jpa.service;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.qxiao.wx.user.vo.AddTeacherVo;
import com.qxiao.wx.user.vo.QueryTeacherInfoVo;
import com.qxiao.wx.user.vo.QueryTeacherVo;
import com.qxiao.wx.user.vo.TeacherInfoVo;
import com.qxiao.wx.user.vo.TeacherJoinVo;
import com.qxiao.wx.user.vo.UpdateTeacherVo;

@Service
public interface TeacherService {
	
	void updateMe(String openId,String name,Integer sex);
	
	TeacherInfoVo queryTeacherInfoBytel(String tel, String openId);
	/**
	 * 添加教师
	 * @param qm
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	void AddTeacher(AddTeacherVo qm) throws Exception;

	/**
	 * 批量添加
	 * @param file
	 */
	List<String> AddTeachers(File file,Long schoolId);
	/**
	 * 修改教师资料
	 * @param vo
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	void updateTeacher(UpdateTeacherVo vo) throws Exception;

	/**
	 * 查询老师信息
	 * @param openId
	 * @param teacherId
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	QueryTeacherInfoVo query(String openId,Long teacherId) throws IllegalAccessException, InvocationTargetException;
	/**
	 * 删除教师信息
	 * @param openId
	 * @param teacherId
	 */
	void deleteTeacher(String openId,Long teacherId) throws Exception;
	
	/**
	 * 完善教师信息
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> teacherJoin(TeacherJoinVo vo) throws Exception;
	
	List<QueryTeacherVo> queryTeacher(Long schoolId);
}
