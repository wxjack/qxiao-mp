package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.openedition.jpa.entity.QmLessonScore;
import com.spring.jpa.dao.JPADao;

public interface QmLessonScoreDao  extends JPADao<QmLessonScore>  {

	@Query(value = "select q from QmLessonScore as q where q.lessonId = ?1 and q.day = ?2 order by q.postTime desc ")
	List<QmLessonScore> findByLessonIdAndDay(Long lessonId, String day);

}
