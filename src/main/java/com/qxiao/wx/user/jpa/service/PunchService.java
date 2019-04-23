package com.qxiao.wx.user.jpa.service;

import java.util.List;
import java.util.Map;

import com.qxiao.wx.user.jpa.entity.QmClockInfo;
import com.qxiao.wx.user.vo.ClassClockQueryVo;
import com.qxiao.wx.user.vo.QueryPunchTime;
import com.qxiao.wx.user.vo.RealShuttleVo;

public interface PunchService {

	Integer queryPunch(Long studentId) throws Exception;
	
	List<String> clockDate(String openId, String date);

	Map<String, Object> queryNotPunch(Long classId, String date);

	Map<String, Object> queryCount(Long classId, String date);

	List<QueryPunchTime> queryStudentTime(Long studentId, String date);

	/**
	 * 考勤打卡
	 * 
	 * @param type
	 * @param nfcId
	 * @param ibeaconId
	 */
	Map<String, Object> punchClock(Integer type, String nfcId, String ibeaconId) throws Exception;

	/**
	 * 月考勤记录查询
	 * 
	 * @param openId
	 * @param studentId
	 * @param month
	 * @return
	 */
	List<QmClockInfo> clockQuery(String openId, Long studentId, String month);

	/**
	 * 定时生成统计表
	 * 
	 * @param openId
	 * @param date
	 * @param classId
	 * @return
	 */
	void saveClockStat(String date);

	/**
	 * 考勤统计查询
	 * 
	 * @param date
	 * @return
	 */
	List<Object> queryStat(String date, String openId);

	/**
	 * 查询班级当天打卡记录
	 * 
	 * @param date
	 * @param classId
	 * @return
	 */
	List<ClassClockQueryVo> classClockQuery(String date, Long classId);

	/**
	 * 播报
	 * 
	 * @param openId
	 * @param classId
	 * @param date
	 * @return
	 */
	List<RealShuttleVo> realShuttle(String openId, Long classId, String date);
}
