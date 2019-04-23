package com.qxiao.wx.alnum.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.alnum.jpa.entity.QmAlbumPhotoVideo;
import com.spring.jpa.dao.JPADao;

public interface QmAlbumPhotoVideoDao extends JPADao<QmAlbumPhotoVideo>{

	List<QmAlbumPhotoVideo> findByChannelId(Long channelId);

	@Query(value="SELECT qapv.* FROM qm_album_channel AS qac JOIN qm_album_photo_video AS qapv " + 
			"where qac.channel_id=qapv.channel_id and qac.class_id=? and qapv.type=0",nativeQuery = true)
	List<QmAlbumPhotoVideo> findByClassId(Long classId);
	
	List<QmAlbumPhotoVideo> findByChannelIdAndType(Long channelId,Integer type);
}
