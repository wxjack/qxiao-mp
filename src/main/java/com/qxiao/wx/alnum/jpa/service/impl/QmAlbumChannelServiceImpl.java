package com.qxiao.wx.alnum.jpa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.alnum.jpa.dao.QmAlbumChannelDao;
import com.qxiao.wx.alnum.jpa.dao.QmAlbumPhotoVideoDao;
import com.qxiao.wx.alnum.jpa.entity.QmAlbumChannel;
import com.qxiao.wx.alnum.jpa.entity.QmAlbumPhotoVideo;
import com.qxiao.wx.alnum.jpa.service.QmAlbumChannelService;
import com.qxiao.wx.common.JsonMapper;
import com.qxiao.wx.fresh.vo.QmImage;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolInfoDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolClass;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.qxiao.wx.user.util.UploadFile;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QmAlbumChannelServiceImpl extends AbstractJdbcService<QmAlbumChannel> implements QmAlbumChannelService {

	@Autowired
	QmAlbumChannelDao channelDao;
	@Autowired
	QmAlbumPhotoVideoDao videoDao;
	@Autowired
	UploadFile upload;
	@Autowired
	QmPlaySchoolInfoDao infoDao;
	@Autowired
	QmPlaySchoolTeacherDao teacherDao;

	public void imageDelete(List<String> itemId) {
		for(String id:itemId) {
			videoDao.delete(Long.valueOf(id));
		}
	}

	public void albumImageAdd(Long teacherId, Long channelId, Integer type, JSONArray imageUrl, String videoUrl)
			throws Exception {
		QmAlbumChannel channel = channelDao.findByChannelId(channelId);
		if (type == 0) {
			Iterator<Object> iterator = imageUrl.iterator();
			while (iterator.hasNext()) {
				QmAlbumPhotoVideo qapv = new QmAlbumPhotoVideo();
				String img = iterator.next().toString();
				QmImage image = JsonMapper.obj2Instance(img, QmImage.class);
				qapv.setImageUrl(image.getImageUrl());
				qapv.setPostTime(new Date());
				qapv.setType(type);
				qapv.setChannelId(channelId);
				qapv.setTeacherId(teacherId);
				videoDao.save(qapv);
			}
		}
		if (type == 1) {
			QmAlbumPhotoVideo qapv = new QmAlbumPhotoVideo();
			qapv.setVideoUrl(videoUrl);
			qapv.setPostTime(new Date());
			qapv.setType(type);
			qapv.setChannelId(channelId);
			qapv.setTeacherId(teacherId);
			videoDao.save(qapv);
		}
		channel.setPostTime(new Date());
		channelDao.save(channel);
	}

	public Long addChannel(Long classId, String name) {
		QmAlbumChannel qac = new QmAlbumChannel();
		qac.setClassId(classId);
		qac.setReadCount(0);
		qac.setTitle(name);
		qac.setPostTime(new Date());
		QmAlbumChannel channel = channelDao.save(qac);
		return channel.getChannelId();
	}

	public void count(Long channelId) throws Exception {
		QmAlbumChannel channel = channelDao.findByChannelId(channelId);
		channel.setReadCount(channel.getReadCount() + 1);
		channelDao.save(channel);
	}

	public Map<String, Object> albumChannelDetail(Long channelId) throws Exception {
		QmAlbumChannel channel = channelDao.findByChannelId(channelId);
		List<QmAlbumPhotoVideo> findByChannel = videoDao.findByChannelId(channelId);
		List<Object> li = new ArrayList<>();
		if (findByChannel.size() > 0) {
			for (QmAlbumPhotoVideo qapv : findByChannel) {
				Map<String, Object> map = new HashMap<>();
				map.put("itemId", qapv.getId());
				map.put("type", qapv.getType());
				map.put("SmallImageUrl", qapv.getSmallUrl());
				if (qapv.getType() == 0) {
					map.put("imageUrl", qapv.getImageUrl());
					map.put("videoUrl", null);
				} else {
					map.put("imageUrl", null);
					map.put("videoUrl", qapv.getVideoUrl());
				}

				li.add(map);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("title", channel.getTitle());
		map.put("channelId", channel.getChannelId());
		map.put("postTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(channel.getPostTime()));
		map.put("items", li);
		this.count(channelId);
		return map;
	}

	public List<Object> albumChannelQuery(Long classId) {
		String sql = "SELECT qac.channel_id, qac.title, qac.read_count, "
				+ "	qac.post_time FROM qm_album_channel AS qac "
				+ "left JOIN qm_album_photo_video AS qapv ON qac.channel_id = qapv.channel_id WHERE "
				+ "	qac.class_id = ? GROUP BY qac.channel_id ORDER BY qac.post_time DESC";
		List<QmAlbumChannel> findList = (List<QmAlbumChannel>) this.findList(sql, new Object[] { classId },
				QmAlbumChannel.class);
		List<Object> li = new ArrayList<>();
		if (findList.size() > 0) {
			for (QmAlbumChannel qac : findList) {
				List<QmAlbumPhotoVideo> findByChannelIdAndType = videoDao.findByChannelIdAndType(qac.getChannelId(), 0);
				Map<String, Object> map = new HashMap<>();
				Integer count = channelDao.queryChannelImageCount(qac.getChannelId());
				map.put("channelId", qac.getChannelId());
				map.put("title", qac.getTitle());
				map.put("readCount", qac.getReadCount());
				map.put("updateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(qac.getPostTime()));
				map.put("imagesCount", count);
				if (findByChannelIdAndType.size() > 0) {
					map.put("image", findByChannelIdAndType.get(0).getImageUrl());
				} else {
					map.put("image", null);
				}
				li.add(map);
			}
		}
		return li;
	}

	public Integer queryImageCount(Long classId) {
		Integer queryImageCount = channelDao.queryImageCount(classId);
		return queryImageCount;
	}

	public String queryImage(Long classId) {
		List<QmAlbumPhotoVideo> list = videoDao.findByClassId(classId);
		if (list.size() > 0) {
			return list.get(0).getImageUrl();
		}
		return null;
	}

	public List<Object> albumClassQueryOfLeader(String openId) {// 园长
		QmPlaySchoolInfo findByOpenId = infoDao.findByOpenId(openId);
		if (findByOpenId != null) {
			String sql = "SELECT qpsc.class_id,qpsc.class_name FROM qm_play_school_info as qpsi "
					+ "join qm_play_school_class as qpsc where qpsi.school_id=qpsc.school_id and qpsi.open_id=?";
			List<QmPlaySchoolClass> findList = (List<QmPlaySchoolClass>) this.findList(sql, new Object[] { openId },
					QmPlaySchoolClass.class);
			List<Object> li = new ArrayList<>();
			if (findList.size() > 0) {
				for (QmPlaySchoolClass qpsc : findList) {
					int count = this.queryImageCount(qpsc.getClassId());
					String image = this.queryImage(qpsc.getClassId());
					Map<String, Object> map = new HashMap<>();
					map.put("classId", qpsc.getClassId());
					map.put("className", qpsc.getClassName());
					map.put("imagesCount", count);
					map.put("image", image);
					li.add(map);
				}
			}
			return li;
		} else {
			Long schoolId = teacherDao.findSchoolId(openId);
			String sql = "SELECT qpsc.class_id,qpsc.class_name FROM qm_play_school_info as qpsi "
					+ "join qm_play_school_class as qpsc where qpsi.school_id=qpsc.school_id and qpsi.school_id=?";
			List<QmPlaySchoolClass> findList = (List<QmPlaySchoolClass>) this.findList(sql, new Object[] { schoolId },
					QmPlaySchoolClass.class);
			List<Object> li = new ArrayList<>();
			if (findList.size() > 0) {
				for (QmPlaySchoolClass qpsc : findList) {
					int count = this.queryImageCount(qpsc.getClassId());
					String image = this.queryImage(qpsc.getClassId());
					Map<String, Object> map = new HashMap<>();
					map.put("classId", qpsc.getClassId());
					map.put("className", qpsc.getClassName());
					map.put("imagesCount", count);
					map.put("image", image);
					li.add(map);
				}
			}
			return li;
		}
	}

	public List<Object> albumClassQueryOfTeacher(String openId) {// 老师
		String sql = "SELECT qpsc.class_id,qpsc.class_name FROM qm_play_school_teacher as qpst "
				+ "join qm_class_teacher as qct join qm_play_school_class as qpsc where "
				+ "qpst.teacher_id=qct.teacher_id and qpsc.class_id=qct.class_id and qpst.open_id=?";
		List<QmPlaySchoolClass> findList = (List<QmPlaySchoolClass>) this.findList(sql, new Object[] { openId },
				QmPlaySchoolClass.class);
		List<Object> li = new ArrayList<>();
		if (findList.size() > 0) {
			for (QmPlaySchoolClass qpsc : findList) {
				int count = this.queryImageCount(qpsc.getClassId());
				String image = this.queryImage(qpsc.getClassId());
				Map<String, Object> map = new HashMap<>();
				map.put("classId", qpsc.getClassId());
				map.put("className", qpsc.getClassName());
				map.put("imagesCount", count);
				map.put("image", image);
				li.add(map);
			}
		}
		return li;
	}

	public List<Object> albumClassQueryOfStudent(String openId) {// 家长
		String sql = "SELECT qpsc.class_name,qpsc.class_id FROM qm_patriarch AS qp JOIN qm_patriarch_student AS qps "
				+ "JOIN qm_student AS qs JOIN qm_play_school_class AS qpsc JOIN qm_class_student AS qcs "
				+ "WHERE qp.id = qps.patriarch_id AND qps.student_id = qs.student_id AND qs.student_id = qcs.student_id "
				+ "AND qcs.class_id = qpsc.class_id AND qp.open_id = ?";
		List<QmPlaySchoolClass> findList = (List<QmPlaySchoolClass>) this.findList(sql, new Object[] { openId },
				QmPlaySchoolClass.class);
		List<Object> li = new ArrayList<>();
		if (findList.size() > 0) {
			for (QmPlaySchoolClass qpsc : findList) {
				int count = this.queryImageCount(qpsc.getClassId());
				String image = this.queryImage(qpsc.getClassId());
				Map<String, Object> map = new HashMap<>();
				map.put("classId", qpsc.getClassId());
				map.put("className", qpsc.getClassName());
				map.put("imagesCount", count);
				map.put("image", image);
				li.add(map);
			}
		}
		return li;
	}

	@Override
	public JPADao<QmAlbumChannel> getDao() {
		return null;
	}

	@Override
	public Class<QmAlbumChannel> getEntityClass() {
		return null;
	}
}
