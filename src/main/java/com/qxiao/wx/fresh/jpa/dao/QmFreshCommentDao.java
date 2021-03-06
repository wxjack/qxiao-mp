package com.qxiao.wx.fresh.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.fresh.jpa.entity.QmFreshComment;
import com.spring.jpa.dao.JPADao;

public interface QmFreshCommentDao extends JPADao<QmFreshComment> {

	List<QmFreshComment> findByFreshIdAndClassId(Long freshId, Long classId);

	@Modifying
	@Query(value = "delete from QmFreshComment q where q.commentId = ?1 and q.openId = ?2")
	void deleteComment(Long commentId, String openId);

	@Modifying
	@Query(value = "delete from QmFreshComment q where q.freshId = ?1")
	void deleteByFreshId(long freshId);

	@Query(value = "SELECT 	qfc.* FROM 	qm_fresh_comment AS qfc "
			+ "JOIN qm_fresh_sender AS qfs ON qfs.fresh_id = qfc.fresh_id "
			+ "JOIN qm_play_school_class AS qp on qp.class_id = qfs.class_id WHERE "
			+ "qfc.fresh_id = ?1 AND qp.school_id = ?2 GROUP BY qfc.comment_id ", nativeQuery = true)
	List<QmFreshComment> findByFreshIdAndSchoolId(Long freshId, Long schoolId);

}
