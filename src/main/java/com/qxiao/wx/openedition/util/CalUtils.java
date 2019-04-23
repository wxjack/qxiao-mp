package com.qxiao.wx.openedition.util;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.qxiao.wx.openedition.jpa.entity.QmLessonScore;

public class CalUtils {

	private CalUtils() {}
	
	public static String calScoreRank(List<QmLessonScore> scores, int count, Long studentId) {
		String rank = null;
		if (CollectionUtils.isNotEmpty(scores)) {
			for (int i = 0; i < scores.size(); i++) {
				if (scores.get(i).getStudentId() == studentId) {
					// 计算排名
					rank = Math.round((float) (i + 1) / count * 100) + "%";
					break;
				}
			}
		}
		return rank;
	}
	
}
