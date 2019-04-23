package com.qxiao.wx.alnum.jpa.dao;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.alnum.jpa.entity.QmAlbumChannel;
import com.spring.jpa.dao.JPADao;

public interface QmAlbumChannelDao extends JPADao<QmAlbumChannel>{
		
	@Query("SELECT COUNT(*) FROM QmAlbumChannel AS qac, QmAlbumPhotoVideo AS qapv " + 
		"WHERE qac.channelId = qapv.channelId AND qac.classId =?")
	Integer queryImageCount(Long classId);
	
	@Query("select count(*) from QmAlbumChannel as qac,QmAlbumPhotoVideo as qapv "+
			"WHERE qac.channelId = qapv.channelId AND qac.channelId =?")
	Integer queryChannelImageCount(Long channelId);

	QmAlbumChannel findByChannelId(Long channelId);
}
