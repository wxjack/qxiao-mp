package com.qxiao.wx.user.jpa.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.qxiao.wx.user.vo.SchoolAddVo;
@Service
public interface SchoolAddService {
	 Map<String, Object> schoolAdd(SchoolAddVo vo);
	 void updateSchool(String openId,String name,String schoolName,String location);
	 
	 void updateIsOpen(Long schoolId,Boolean isOpen);
}
