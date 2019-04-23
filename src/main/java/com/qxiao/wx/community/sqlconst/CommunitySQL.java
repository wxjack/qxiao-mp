package com.qxiao.wx.community.sqlconst;

/**
 * sql语句
 * 
 * @author xiaojiao
 *
 * @创建时间：2018年12月28日
 */
public class CommunitySQL {

	public static String findWirhTeacher() {
		String sql = "SELECT qci.community_id AS communityId, qci.open_id AS openId,qci.student_id as studentId, "
				+ "qa.photo AS photo, qcv.video_url AS videoUrl, qci.text_content AS textContent, "
				+ "qci.post_time AS postTime FROM qm_community_info AS qci "
				+ "JOIN qm_class_community AS qcc ON qcc.community_id = qci.community_id "
				+ "JOIN qm_account AS qa ON qci.open_id = qa.open_id "
				+ "LEFT JOIN qm_community_video AS qcv ON qcv.community_id = qci.community_id "
				+ "WHERE qcc.class_id = ? AND qci.is_del = 0 ORDER BY qci.post_time DESC ";
		return sql;
	}

	public static String getPraiseList() {
		String sql = "SELECT qcs.open_id AS openId, qcs.relation AS relation, qa.photo AS photo, "
				+ "	qcs.student_id AS studentId FROM qm_community_praise AS qcs "
				+ "JOIN qm_class_community AS qcc ON qcc.community_id = qcs.community_id "
				+ "JOIN qm_account AS qa ON qa.open_id = qcs.open_id WHERE qcs.community_id = ? "
				+ "AND qcc.class_id = ? ORDER BY qcs.post_time ";
		return sql;
	}

	public static String getCommentList() {
		String sql = "SELECT qcc.open_id AS openId, qcc.relation AS relation, qcc.text_content AS textContent, "
				+ "qcc.student_id as studentId ,qcc.photo AS photo, qcc.comment_id AS commentId FROM "
				+ "qm_community_comment AS qcc JOIN qm_class_community AS qc ON qcc.community_id = qc.community_id "
				+ "WHERE qcc.community_id = ? AND qc.class_id = ? ORDER BY qcc.post_time DESC ";
		return sql;
	}

	public static String findForStudent() {
		String sql = "SELECT qci.community_id AS communityId, qci.open_id AS openId, "
				+ "qa.photo AS photo, qcv.video_url AS videoUrl, qci.text_content AS textContent, "
				+ "qci.post_time AS postTime FROM qm_community_info AS qci "
				+ "JOIN qm_class_community AS qcc ON qcc.community_id = qci.community_id "
				+ "JOIN qm_account AS qa ON qci.open_id = qa.open_id "
				+ "LEFT JOIN qm_community_video AS qcv ON qcv.community_id = qci.community_id "
				+ "JOIN qm_class_student AS stu ON stu.class_id = qcc.class_id WHERE qcc.class_id = ? "
				+ "AND stu.student_id = ? AND qci.is_del = 0 ORDER BY qci.post_time DESC ";
		return sql;
	}

	public static String getPraiseListForStudent() {
		String sql = "SELECT qcs.open_id AS openId, qcs.relation AS relation, qcs.photo AS photo "
				+ "FROM qm_community_praise AS qcs JOIN qm_class_community AS qcc ON qcc.community_id = qcs.community_id "
				+ "WHERE qcs.community_id = ? AND qcc.class_id = ? ORDER BY qcs.post_time DESC ";
		return sql;
	}

}
