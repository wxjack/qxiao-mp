package com.qxiao.wx.openedition.sql;

public class ConstSql {

	public static String examPaperQuerySql() {
		String examPaperQuerySql = "SELECT a.paper_id AS paperId, a.school_name AS schoolName, "
				+ "a.title AS title, b.title AS stageTitle, a.post_time AS postTime FROM "
				+ "qm_exam_paper AS a JOIN qm_exam_stage AS b ON a.stage_id = b.stage_id "
				+ "WHERE a.stage_id = ? AND a.lesson_id = ? ORDER BY postTime desc";
		return examPaperQuerySql;
	}

	public static String lessonScoreSql() {
		String lessonScoreSql = "SELECT b.title AS stageTitle, b.stage_id AS stageId, a.score AS score, "
				+ "a.`day` AS day FROM qm_lesson_score AS a JOIN qm_exam_stage AS b ON b.stage_id = a.stage_id "
				+ "WHERE a.student_id = ? AND a.lesson_id = ? ";
		return lessonScoreSql;
	}

	public static String findComments() {
		String findCommentsSql = "SELECT a.comment_id AS commentId, a.text_content AS textContent, "
				+ "b.student_name AS name, c.photo AS photo, a.post_time AS postTime "
				+ "FROM qm_paper_comment AS a JOIN qm_account AS c ON a.open_id = c.open_id "
				+ "JOIN qm_student AS b ON b.student_id = a.student_id WHERE a.paper_id = ? order by postTime desc ";
		return findCommentsSql;
	}

	public static String queryRemarksSql() {
		String queryRemarksSql = "SELECT remark_type AS remarkType, text_content AS textContent, "
				+ "post_time AS postTime FROM qm_remark WHERE student_id = ? ORDER BY postTime DESC ";
		return queryRemarksSql;
	}

	public static String queryPrizesSql() {
		String queryPrizesSql = "SELECT item_id AS itemId, 1 AS prizeType, "
				+ "star_count AS starCount, text_content AS textContent FROM qm_prize_item "
				+ "WHERE open_id = ? UNION SELECT item_id AS itemId, "
				+ "0 AS prizeType, star_count AS starCount, text_content AS textContent "
				+ "FROM qm_prize_item_default ";
		return queryPrizesSql;
	}

	public static String QueryPrizeExchangeSql() {
		String QueryPrizeExchangeSql = "SELECT * FROM (SELECT a.exchange_id AS exchangeId, "
				+ "b.text_content AS textContent, a.times AS times, a.star_count AS starCount, "
				+ "a.post_time AS postTime FROM qm_prize_exchange AS a "
				+ "JOIN qm_prize_item AS b ON a.item_id = b.item_id WHERE a.student_id = ? AND a.prize_type = 1 "
				+ "UNION SELECT c.exchange_id AS exchangeId, d.text_content AS textContent, "
				+ "c.times AS times, c.star_count AS starCount, c.post_time AS postTime "
				+ "FROM qm_prize_exchange AS c JOIN qm_prize_item_default AS d ON c.item_id = d.item_id "
				+ "WHERE c.student_id = ? AND c.prize_type = 0 ) tmp order by exchangeId ";
		return QueryPrizeExchangeSql;
	}

	public static String lessonQuerySql() {
		String lessonQuerySql = "SELECT a.lesson_id AS lessonId, a.star_count AS starCount, b.title AS title "
				+ "FROM qm_lesson_expression AS a JOIN qm_lesson AS b ON a.lesson_id = b.lesson_id "
				+ "WHERE a.student_id = ? AND a.`day` = ? ";
		return lessonQuerySql;
	}

	public static String examPaperDetailSql() {
		String examPaperDetailSql = "SELECT b.title AS title, a.text_content AS textContent, "
				+ "a.video_url AS videoUrl, a.fee AS fee, a.download_count AS downloadCount, "
				+ "DATE_FORMAT(a.post_time, '%Y-%m-%d %k:%i:%s') AS postTime FROM qm_paper_detail AS a "
				+ "JOIN qm_exam_paper AS b ON a.paper_id = b.paper_id WHERE a.paper_id = ? ";
		return examPaperDetailSql;
	}

	public static String queryNewRemarkSql() {
		String queryNewRemarkSql = "SELECT text_content AS textContent, post_time AS postTime FROM "
				+ "qm_remark WHERE remark_type = ? AND student_id = ? ORDER BY post_time DESC LIMIT 1";
		return queryNewRemarkSql;
	}
}
