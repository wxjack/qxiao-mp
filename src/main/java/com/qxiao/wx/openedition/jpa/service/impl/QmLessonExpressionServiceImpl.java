package com.qxiao.wx.openedition.jpa.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.openedition.dto.QmLessonExpressionDTO;
import com.qxiao.wx.openedition.jpa.dao.QmLessonScoreDao;
import com.qxiao.wx.openedition.jpa.entity.QmLessonExpression;
import com.qxiao.wx.openedition.jpa.entity.QmLessonScore;
import com.qxiao.wx.openedition.jpa.service.IQmLessonExpressionService;
import com.qxiao.wx.openedition.sql.ConstSql;
import com.qxiao.wx.openedition.util.CalUtils;
import com.qxiao.wx.openedition.vo.QmLessonVO;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QmLessonExpressionServiceImpl extends AbstractJdbcService<QmLessonExpression>
		implements IQmLessonExpressionService {

	@Autowired
	private QmLessonScoreDao scoreDao;
	@Autowired
	private QmClassStudentDao studentDao;

	@Override
	public JPADao<QmLessonExpression> getDao() {
		return null;
	}

	@Override
	public Class<QmLessonExpression> getEntityClass() {
		return null;
	}

	@Override
	public List<QmLessonExpressionDTO> lessonQuery(String openId, Long studentId, String day) {

		List<QmLessonVO> lvs = (List<QmLessonVO>) super.findList(ConstSql.lessonQuerySql(),
				new Object[] { studentId, day }, QmLessonVO.class);
		List<QmLessonExpressionDTO> list = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(lvs)) {
			for (QmLessonVO lesson : lvs) {
				QmLessonExpressionDTO dto = new QmLessonExpressionDTO();
				try {
					BeanUtils.copyProperties(dto, lesson);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
				// 获取学生的课程成绩倒排序
				List<QmLessonScore> scores = this.findStudentLessonScore(lesson.getLessonId(), day);
				int count = studentDao.findStudentCount(studentId); // 班级人数
				if (count > 0) {
					String scoreRank = this.calScoreRank(scores, count, studentId);
					dto.setScoreRank(scoreRank == null ? "--" : scoreRank);
				} else {
					dto.setScoreRank("--");
				}
				list.add(dto);
			}
		}
		return list;
	}

	private List<QmLessonScore> findStudentLessonScore(Long lessonId, String day) {
		return scoreDao.findByLessonIdAndDay(lessonId, day);
	}

	private String calScoreRank(List<QmLessonScore> scores, int count, Long studentId) {
		return CalUtils.calScoreRank(scores, count, studentId);
	}

}
