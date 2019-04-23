package com.qxiao.wx.notice.jpa.service;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;

import com.qxiao.wx.notice.dto.QmNoticeDetailDTO;
import com.qxiao.wx.notice.dto.QmNoticeInfoDTO;
import com.qxiao.wx.notice.dto.QmNoticeInfoReadDTO;
import com.qxiao.wx.notice.jpa.entity.QmNoticeInfo;
import com.spring.entity.DataPage;
import com.spring.jpa.service.ServiceException;

public interface IQmNoticeInfoService {

	void sendNotice(String openId, Long noticeId);

	DataPage<QmNoticeInfoDTO> noticeQuery(String openId, Long classId, Long studentId, int type, int page, int pageSize)
			throws ServiceException;

	QmNoticeDetailDTO noticeDetail(String openId, Long noticeId, Long classId, Long studentId);

	void deleteNotice(String openId, Long noticeId, Long classId);

	void insertConfirm(Long studentId, Long noticeId, String openId);

	QmNoticeInfoReadDTO noticeReaders(String openId, Long noticeId, Long classId, int readFlag);

	QmNoticeInfo addNoticeInfo(String openId, String title, String textContent, JSONArray images, int needConfirm,
			JSONArray senders, int clockType, String clockTime) throws Exception;

	List<QmNoticeInfo> findByClockTime(Date date);

	boolean update(List<QmNoticeInfo> notices);

}
