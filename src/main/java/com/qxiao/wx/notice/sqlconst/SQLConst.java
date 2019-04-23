package com.qxiao.wx.notice.sqlconst;

/**
 * SQL 语句
 * 
 * @author xiaojiao
 *
 * @创建时间：2018年12月27日
 */
public class SQLConst {

	// 查询自己发布通告 1
	public static String queryTeacherNotice() {
		String sql = "SELECT temp.* FROM ( ( SELECT qni1.notice_id AS noticeId, qni1.title AS title, "
				+ "qni1.text_content AS textContent, ( SELECT COUNT(qntc1.student_id) FROM "
				+ "qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id "
				+ "WHERE stu.class_id = qns1.sender_id AND qntc1.notice_id = qni1.notice_id ) AS classReadCount, "
				+ "( SELECT COUNT(qntc1.student_id) FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id "
				+ "WHERE stu.class_id = qns1.sender_id AND qntc1.notice_id = qni1.notice_id "
				+ "AND qntc1.confirm_flag = 1 ) AS classConfirmCount, ( ( SELECT COUNT(qs1.student_id) FROM "
				+ "qm_student AS qs1 JOIN qm_class_student AS qcs1 ON qs1.student_id = qcs1.student_id "
				+ "WHERE qcs1.class_id = qns1.sender_id AND qs1.`status` = 0 ) - ( "
				+ "SELECT COUNT(qntc1.student_id) FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id "
				+ "WHERE stu.class_id = qns1.sender_id AND qntc1.notice_id = qni1.notice_id ) "
				+ ") AS classUnreadCount, qni1.need_confirm AS needConfirm, qni1.post_time AS postTime FROM "
				+ "qm_notice_info AS qni1 JOIN qm_notice_sender AS qns1 ON qns1.notice_id = qni1.notice_id WHERE "
				+ "qni1.is_del = 0 AND qns1.sender_id = ? AND qns1.sender_type = 1 AND qni1.open_id = ? GROUP BY "
				+ "noticeId ) UNION ( SELECT qni2.notice_id AS noticeId, qni2.title AS title, "
				+ "qni2.text_content AS textContent, 0 AS classReadCount, "
				+ "0 AS classConfirmCount, 0 AS classUnreadCount, "
				+ "qni2.need_confirm AS needConfirm, qni2.post_time AS postTime FROM qm_notice_info AS qni2 "
				+ "JOIN qm_notice_sender AS qns2 ON qni2.notice_id = qns2.notice_id WHERE qni2.is_del = 0 "
				+ "AND qns2.sender_type = 2 AND qni2.open_id = ? GROUP BY noticeId ) ) AS temp "
				+ "ORDER BY temp.postTime DESC ";
		return sql;
	}

	// 园长查询所有 OK
	public static String queryAll() {
		String sql = "SELECT temp.* FROM ( ( SELECT qni1.notice_id AS noticeId, qni1.title AS title, "
				+ "qni1.text_content AS textContent, IFNULL( ( SELECT "
				+ "	COUNT(qntc1.student_id) FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id WHERE "
				+ "qntc1.notice_id = qni1.notice_id AND stu.class_id = qns1.sender_id ), 0 "
				+ ") AS classReadCount, IFNULL( ( SELECT COUNT(qntc1.id) FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id WHERE "
				+ "qntc1.notice_id = qni1.notice_id AND stu.class_id = qns1.sender_id "
				+ "AND qntc1.confirm_flag = 1 ), 0 ) AS classConfirmCount, "
				+ "IFNULL( ( SELECT COUNT(qs1.student_id) FROM "
				+ "qm_student AS qs1 JOIN qm_class_student AS stu ON qs1.student_id = stu.student_id WHERE "
				+ "stu.class_id = qns1.sender_id ) - ( SELECT COUNT(qntc1.id) FROM "
				+ "qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id WHERE "
				+ "qntc1.notice_id = qni1.notice_id AND stu.class_id = qns1.sender_id ), 0 "
				+ ") AS classUnreadCount, qni1.need_confirm AS needConfirm, qni1.post_time AS postTime FROM "
				+ "qm_notice_info AS qni1 JOIN qm_notice_sender AS qns1 ON qns1.notice_id = qni1.notice_id WHERE "
				+ "qni1.is_del = 0 AND qns1.sender_id = ? AND qni1.clock_time IS NULL AND qns1.sender_type = 1 "
				+ "GROUP BY noticeId ) UNION ( SELECT qni2.notice_id AS noticeId, qni2.title AS title, "
				+ "qni2.text_content AS textContent, 0 AS classReadCount, 0 AS classConfirmCount, "
				+ "0 AS classUnreadCount, qni2.need_confirm AS needConfirm, qni2.post_time AS postTime "
				+ "FROM qm_notice_info AS qni2 JOIN qm_notice_sender AS qns2 ON qni2.notice_id = qns2.notice_id "
				+ "JOIN qm_class_teacher AS qct2 ON qct2.teacher_id = qns2.sender_id "
				+ "JOIN qm_play_school_class AS qpsc2 ON qpsc2.class_id = qct2.class_id "
				+ "JOIN qm_play_school_info AS qpsi2 ON qpsi2.school_id = qpsc2.school_id "
				+ "WHERE qpsi2.school_id = ? AND qni2.is_del = 0 AND qns2.sender_type = 2 "
				+ "AND qni2.clock_time IS NULL GROUP BY noticeId ) ) AS temp ORDER BY temp.postTime DESC ";
		return sql;
	}

	// 1
	public static String findSendClass() {
		String sql = "SELECT qni1.notice_id AS noticeId, qni1.title AS title, "
				+ "qni1.text_content AS textContent, ( SELECT COUNT(qntc1.student_id) "
				+ "FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id WHERE "
				+ "stu.class_id = qns1.sender_id AND qntc1.notice_id = qni1.notice_id "
				+ ") AS classReadCount, ( SELECT COUNT(qntc1.student_id ) FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id WHERE "
				+ "stu.class_id = qns1.sender_id AND qntc1.notice_id = qni1.notice_id "
				+ "AND qntc1.confirm_flag = 1 ) AS classConfirmCount, ( ( SELECT COUNT(qs1.student_id) FROM "
				+ "qm_student AS qs1 JOIN qm_class_student AS qcs1 ON qs1.student_id = qcs1.student_id "
				+ "WHERE qcs1.class_id = qns1.sender_id AND qs1.`status` = 0 ) - ( SELECT "
				+ "COUNT(qntc1.student_id) FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id "
				+ "WHERE stu.class_id = qns1.sender_id "
				+ "AND qntc1.notice_id = qni1.notice_id ) ) AS classUnreadCount, "
				+ "qni1.need_confirm AS needConfirm, qni1.post_time AS postTime FROM qm_notice_info AS qni1 "
				+ "JOIN qm_notice_sender AS qns1 ON qns1.notice_id = qni1.notice_id "
				+ "WHERE qni1.is_del = 0 AND qns1.sender_id = ? AND qns1.sender_type = 1 "
				+ "AND qni1.clock_time IS NULL GROUP BY noticeId ORDER BY postTime DESC ";
		return sql;
	}

	// 查询发送到班级的通知 + 老师的通知、老师端 O
	public static String teacherQuery() {
		String sql = "SELECT * FROM ( ( SELECT qni1.notice_id AS noticeId, qni1.title AS title, qni1.text_content "
				+ "AS textContent, qni1.need_confirm AS needConfirm, ( SELECT COUNT(q.student_id) FROM "
				+ "qm_notice_read_confirm AS q JOIN qm_class_student AS stu ON stu.student_id = q.student_id "
				+ "WHERE stu.class_id = qns1.sender_id AND q.notice_id = qni1.notice_id "
				+ ") AS classReadCount, ( SELECT COUNT(q.student_id) FROM qm_notice_read_confirm AS q "
				+ "JOIN qm_class_student AS stu ON stu.student_id = q.student_id WHERE q.confirm_flag = 1 "
				+ "AND stu.class_id = qns1.sender_id AND q.notice_id = qni1.notice_id ) AS classConfirmCount, ( SELECT "
				+ "COUNT(stu.student_id) FROM qm_student AS stu JOIN qm_class_student AS cl ON cl.student_id = stu.student_id "
				+ "WHERE stu.`status` = 0 AND cl.class_id = qns1.sender_id ) - ( SELECT COUNT(q.student_id) FROM "
				+ "qm_notice_read_confirm AS q JOIN qm_class_student AS stu ON stu.student_id = q.student_id "
				+ "WHERE stu.class_id = qns1.sender_id AND q.notice_id = qni1.notice_id "
				+ ") AS classUnreadCount, qni1.post_time AS postTime FROM qm_notice_info AS qni1 "
				+ "JOIN qm_notice_sender AS qns1 ON qns1.notice_id = qni1.notice_id WHERE "
				+ "qni1.is_del = 0 AND qni1.clock_time IS NULL AND qns1.sender_type = 1 AND qns1.sender_id = ? "
				+ "GROUP BY noticeId ) UNION ( SELECT qni2.notice_id AS noticeId, "
				+ "qni2.title AS title, qni2.text_content AS textContent, qni2.need_confirm AS needConfirm, "
				+ "0 AS classReadCount, 0 AS classConfirmCount, 0 AS classUnreadCount, "
				+ "qni2.post_time AS postTime FROM qm_notice_info AS qni2 "
				+ "JOIN qm_notice_sender AS qns2 ON qni2.notice_id = qns2.notice_id "
				+ "JOIN qm_play_school_teacher AS t ON t.teacher_id = qns2.sender_id WHERE t.open_id = ? "
				+ "AND qni2.is_del = 0 AND qns2.sender_type = 2 AND qni2.clock_time IS NULL GROUP BY "
				+ "noticeId ) ) AS temp ORDER BY temp.postTime DESC ";
		return sql;
	}

	public static String findWithClass() {
		String sql = "SELECT qni.notice_id AS noticeId, qni.title AS title, "
				+ "qni.text_content AS textContent, qni.need_confirm AS needConfirm, IFNULL( "
				+ "( SELECT COUNT(a.student_id) FROM qm_notice_read_confirm AS a "
				+ "JOIN qm_class_student AS stu ON stu.student_id = a.student_id WHERE "
				+ "a.notice_id = qni.notice_id AND stu.class_id = qns.sender_id "
				+ "), 0 ) AS classReadCount, ( IFNULL( ( SELECT COUNT(a.id) FROM qm_notice_read_confirm AS a "
				+ "JOIN qm_class_student AS stu ON stu.student_id = a.student_id WHERE "
				+ "a.notice_id = qni.notice_id AND a.confirm_flag = 1 "
				+ "AND stu.class_id = qns.sender_id ), 0 ) ) AS classConfirmCount, IFNULL( ( SELECT "
				+ "COUNT(a.student_id) FROM qm_student AS a "
				+ "JOIN qm_class_student AS b ON a.student_id = b.student_id WHERE "
				+ "b.class_id = qns.sender_id AND a.`status` = 0 ) - ( "
				+ "SELECT COUNT(a.student_id) FROM qm_notice_read_confirm AS a "
				+ "JOIN qm_class_student AS stu ON stu.student_id = a.student_id WHERE "
				+ "a.notice_id = qni.notice_id AND stu.class_id = qns.sender_id "
				+ "), 0 ) AS classUnreadCount, qni.post_time AS postTime FROM "
				+ "qm_notice_info AS qni LEFT JOIN qm_notice_sender AS qns ON qns.notice_id = qni.notice_id "
				+ "WHERE qni.notice_id = ? AND qni.is_del = 0 AND qns.sender_id = ? "
				+ "AND qns.sender_type = 1 GROUP BY noticeId ";
		return sql;
	}

	public static String findWithTeacher() {
		String sql = "SELECT qni.notice_id AS noticeId, qni.title AS title, "
				+ "qni.text_content AS textContent, qni.need_confirm AS needConfirm, 0 AS classReadCount, "
				+ "0 AS classConfirmCount, 0 AS classUnreadCount, qni.post_time AS postTime FROM "
				+ "qm_notice_info AS qni JOIN qm_notice_sender AS qns ON qns.notice_id = qni.notice_id WHERE "
				+ "qni.notice_id = ? AND qni.is_del = 0 AND qns.sender_type = 2 "
				+ "AND qni.clock_time IS NULL GROUP BY noticeId";
		return sql;
	}

	// O
	public static String findWithPatriarch() {
		String sql = "SELECT qni.notice_id AS noticeId, qni.title AS title, "
				+ "qni.text_content AS textContent, qni.need_confirm AS needConfirm, (SELECT "
				+ "COUNT(a.student_id) FROM qm_notice_read_confirm AS a "
				+ "JOIN qm_class_student AS b ON a.student_id = b.student_id WHERE "
				+ "b.class_id = qns.sender_id AND a.notice_id = qni.notice_id "
				+ ") AS classReadCount, (SELECT COUNT(q.id) FROM qm_notice_read_confirm AS q "
				+ "JOIN qm_class_student b ON b.student_id = q.student_id WHERE "
				+ "q.confirm_flag = 1 AND b.class_id = qns.sender_id "
				+ "AND q.notice_id = qni.notice_id ) AS classConfirmCount, ( ( SELECT COUNT(a.student_id) FROM "
				+ "qm_student AS a JOIN qm_class_student AS b ON b.student_id = a.student_id WHERE "
				+ "b.class_id = qns.sender_id AND a.`status` = 0 ) - ( SELECT COUNT(a.student_id) FROM "
				+ "qm_notice_read_confirm AS a JOIN qm_class_student AS b ON a.student_id = b.student_id WHERE "
				+ "b.class_id = qns.sender_id AND a.notice_id = qni.notice_id "
				+ ") ) AS classUnreadCount, qni.post_time AS postTime FROM "
				+ "qm_notice_info AS qni JOIN qm_notice_sender AS qns ON qni.notice_id = qns.notice_id "
				+ "WHERE qni.notice_id = ? AND qns.sender_type = 1 AND qns.sender_id = ? "
				+ "AND qni.clock_time IS NULL GROUP BY noticeId";
		return sql;
	}

	public static String findWithSchool() {
		String sql = "SELECT qni.notice_id AS noticeId, qni.title AS title, "
				+ "qni.text_content AS textContent,qni.need_confirm AS needConfirm, "
				+ "0 AS classReadCount, 0 AS classConfirmCount, 0 AS classUnreadCount, "
				+ "qni.post_time AS postTime FROM qm_notice_info AS qni WHERE "
				+ "qni.notice_id = ? AND qni.is_del = 0 ";
		return sql;
	}

	public static String findReader() {
		String sql = "SELECT qs.student_id AS studentId, qs.student_name AS studentName, "
				+ "qa.photo AS photo, qhr.confirm_flag AS confirmFlag, qhr.post_time AS postTime "
				+ "FROM qm_student AS qs JOIN qm_notice_read_confirm AS qhr ON qs.student_id = qhr.student_id "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qs.student_id "
				+ "JOIN qm_account AS qa ON qa.open_id = qhr.open_id WHERE qs.`status` = 0 "
				+ "AND stu.class_id = ? AND qhr.notice_id = ? ";
		return sql;
	}

	public static String findUnReader() {
		String sql = "SELECT qs.student_id AS studentId, qs.student_name AS studentName, "
				+ "qs.photo AS photo FROM qm_student AS qs "
				+ "JOIN qm_class_student AS st ON st.student_id = qs.student_id join qm_patriarch_student as qps on qs.student_id=qps.student_id " + 
				"join qm_patriarch as qp on qps.patriarch_id=qp.id WHERE st.class_id = ? "
				+ "AND qs.student_id NOT IN ( SELECT q.student_id FROM qm_notice_read_confirm AS q "
				+ "JOIN qm_class_student AS stu ON q.student_id = stu.student_id WHERE "
				+ "q.notice_id = ? AND stu.class_id = st.class_id )";
		return sql;
	}

	public static String queryLeaderNotice() {
		String sql = "SELECT temp.* FROM ( ( SELECT qni1.notice_id AS noticeId, qni1.title AS title, "
				+ "qni1.text_content AS textContent, ( SELECT COUNT(qntc1.student_id) "
				+ "FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id "
				+ "WHERE qntc1.notice_id = qni1.notice_id AND stu.class_id = qns1.sender_id ) AS classReadCount, "
				+ "( SELECT COUNT(qntc1.student_id) FROM qm_notice_read_confirm AS qntc1 "
				+ "JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id "
				+ "WHERE stu.class_id = qns1.sender_id AND qntc1.notice_id = qni1.notice_id "
				+ "AND qntc1.confirm_flag = 1 ) AS classConfirmCount,(( SELECT COUNT(qs1.student_id) "
				+ "FROM qm_student AS qs1 JOIN qm_class_student AS qcs1 ON qs1.student_id = qcs1.student_id "
				+ "WHERE qcs1.class_id = qns1.sender_id ) - ( SELECT COUNT(qntc1.student_id) FROM "
				+ "qm_notice_read_confirm AS qntc1 JOIN qm_class_student AS stu ON stu.student_id = qntc1.student_id "
				+ "WHERE qntc1.notice_id = qni1.notice_id AND stu.class_id = qns1.sender_id ) "
				+ ") AS classUnreadCount, qni1.need_confirm AS needConfirm, qni1.post_time AS postTime FROM "
				+ "qm_notice_info AS qni1 JOIN qm_notice_sender AS qns1 ON qns1.notice_id = qni1.notice_id WHERE "
				+ "qni1.is_del = 0 AND qns1.sender_id = ? AND qns1.sender_type = 1 GROUP BY noticeId ) "
				+ "UNION ( SELECT qni2.notice_id AS noticeId, qni2.title AS title, "
				+ "qni2.text_content AS textContent, 0 AS classReadCount, 0 AS classConfirmCount, 0 AS classUnreadCount, "
				+ "qni2.need_confirm AS needConfirm, qni2.post_time AS postTime FROM qm_notice_info AS qni2 "
				+ "JOIN qm_notice_sender AS qns2 ON qni2.notice_id = qns2.notice_id "
				+ "WHERE qni2.open_id = ? AND qni2.is_del = 0 AND qns2.sender_type = 2 GROUP BY noticeId "
				+ ") ) AS temp ORDER BY temp.postTime DESC ";
		return sql;
	}

}
