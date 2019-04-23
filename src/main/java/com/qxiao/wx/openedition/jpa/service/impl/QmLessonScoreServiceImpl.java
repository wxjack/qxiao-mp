package com.qxiao.wx.openedition.jpa.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.openedition.dto.QmLessonScoreDTO;
import com.qxiao.wx.openedition.jpa.dao.QmLessonScoreDao;
import com.qxiao.wx.openedition.jpa.entity.QmLessonScore;
import com.qxiao.wx.openedition.jpa.service.IQmLessonScoreService;
import com.qxiao.wx.openedition.sql.ConstSql;
import com.qxiao.wx.openedition.util.CalUtils;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

@Service
public class QmLessonScoreServiceImpl extends AbstractJdbcService<QmLessonScore> implements IQmLessonScoreService {

	@Autowired
	private QmClassStudentDao studentDao;
	@Autowired
	private QmLessonScoreDao scoreDao;

	@Override
	public JPADao<QmLessonScore> getDao() {
		return null;
	}

	@Override
	public Class<QmLessonScore> getEntityClass() {
		return null;
	}

	@Override
	public DataPage<QmLessonScoreDTO> lessonScoreQuery(String openId, Long studentId, Long lessonId, int page,
			int pageSize) throws ServiceException {
		DataPage<QmLessonScoreDTO> dataPage = (DataPage<QmLessonScoreDTO>) this.getPage(ConstSql.lessonScoreSql(), page,
				pageSize, new Object[] { studentId, lessonId }, QmLessonScoreDTO.class);
		// 计算排名
		List<QmLessonScoreDTO> data = (List<QmLessonScoreDTO>) dataPage.getData();
		if (CollectionUtils.isNotEmpty(data)) {
			for (QmLessonScoreDTO scoreDTO : data) {
				// 获取学生的课程成绩倒排序
				List<QmLessonScore> scores = this.findStudentLessonScore(lessonId, scoreDTO.getDay());
				int count = studentDao.findStudentCount(studentId); // 班级人数
				if (count > 0) {
					String scoreRank = this.calScoreRank(scores, count, studentId);
					scoreDTO.setScoreRank(scoreRank == null ? "--" : scoreRank);
				} else {
					scoreDTO.setScoreRank("--");
				}
			}
			dataPage.setData(data);
		}
		return dataPage;
	}

	private String calScoreRank(List<QmLessonScore> scores, int count, Long studentId) {
		return CalUtils.calScoreRank(scores, count, studentId);
	}

	private List<QmLessonScore> findStudentLessonScore(Long lessonId, String day) {
		return scoreDao.findByLessonIdAndDay(lessonId, day);
	}

}
