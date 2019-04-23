package com.qxiao.wx.componse;

import java.util.List;
import java.util.Map;

/**
 * 获取登录用户身份信息
 * 
 * @author xiaojiao
 *
 * @创建时间：2018年12月28日
 */
public interface GetIdentityService {

	/**
	 * 	家长端
	 * @param openId
	 * @param studentId
	 * @return
	 */
	UserInfo getIdentity(String openId, Long studentId);
	
	List<Map<String, Object>> getClassInfo(String openId);

	List<Map<String, Object>> getTeacherInfo(String openId);

//	List<Map<String, Object>> getStudentInfo(String openId,Long studentId);

	UserInfo getIdentity(String openId);

}
