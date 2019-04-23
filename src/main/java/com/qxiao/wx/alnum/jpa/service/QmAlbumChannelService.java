package com.qxiao.wx.alnum.jpa.service;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

public interface QmAlbumChannelService {
	void imageDelete(List<String> itemId);
	
	List<Object> albumClassQueryOfLeader(String openId);

	List<Object> albumClassQueryOfTeacher(String openId);

	List<Object> albumClassQueryOfStudent(String openId);

	/**
	 * 根据栏目查询信息
	 * 
	 * @param classId
	 * @return
	 */
	List<Object> albumChannelQuery(Long classId);

	/**
	 * 查询相册栏目详情
	 * 
	 * @param channelId
	 * @return
	 */
	Map<String, Object> albumChannelDetail(Long channelId) throws Exception;

	/**
	 * 添加栏目
	 * 
	 * @param classId
	 * @param name
	 * @return
	 */
	Long addChannel(Long classId, String name);

	void albumImageAdd(Long teacherId, Long channelId, Integer type, JSONArray imageUrl, String videoUrl) throws Exception;

}
