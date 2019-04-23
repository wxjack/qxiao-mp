package com.qxiao.wx.user.jpa.service;

import org.springframework.stereotype.Service;

import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.vo.QueryBySchoolCodeVo;
import com.qxiao.wx.user.vo.SchoolInfoVo;
@Service
public interface QuerySchoolInfoService {
	
	SchoolInfoVo queryByOpenId(String openId) throws Exception;
	
	QueryBySchoolCodeVo queryBySchoolCode(String openId);
	
	QmPlaySchoolInfo querySchoolInfo(String schoolCode);
}
