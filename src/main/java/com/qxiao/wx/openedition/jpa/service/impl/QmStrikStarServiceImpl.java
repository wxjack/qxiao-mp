package com.qxiao.wx.openedition.jpa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.openedition.dto.HistoryStrikeQueryDto;
import com.qxiao.wx.openedition.dto.StrikStarActionsDto;
import com.qxiao.wx.openedition.jpa.dao.QmStrikStarDao;
import com.qxiao.wx.openedition.jpa.entity.QmExpressionAction;
import com.qxiao.wx.openedition.jpa.entity.QmStrikStar;
import com.qxiao.wx.openedition.jpa.service.IQmStrikStarService;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.entity.DataPage;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;
import com.spring.jpa.service.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class QmStrikStarServiceImpl extends AbstractJdbcService<QmStrikStar> implements IQmStrikStarService {

	@Autowired
	QmStrikStarDao starDao;
	@Autowired
	QmStudentDao qsDao;

	public DataPage<HistoryStrikeQueryDto> historyStrikeQuery(String openId, Long studentId, Integer page,
			Integer pageSize) throws ServiceException {
		String s = "SELECT DISTINCT DAY FROM qm_strik_star WHERE student_id =? ORDER BY day desc ";
		DataPage<HistoryStrikeQueryDto> page2 = (DataPage<HistoryStrikeQueryDto>) this.getPage(s, page, pageSize,
				new Object[] { studentId }, HistoryStrikeQueryDto.class);
		List<HistoryStrikeQueryDto> list2 = (List<HistoryStrikeQueryDto>) page2.getData();
		List<HistoryStrikeQueryDto> list4 = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(list2)) {
			for (int i = 0; i < 4; i++) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, -i);
				Date d = cal.getTime();
				SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
				String day = sp.format(d);
				if (list2.size() > i) {
					if (!list2.get(i).getDay().equals(day)) {
						HistoryStrikeQueryDto hs = new HistoryStrikeQueryDto();
						hs.setDay(day);
						hs.setStarCount(0);
						List<StrikStarActionsDto> li = new ArrayList<>();
						List<QmExpressionAction> action = this.queryAction(studentId);
						for (QmExpressionAction qea : action) {
							StrikStarActionsDto dt = new StrikStarActionsDto();
							dt.setActionId(qea.getActionId());
							dt.setTitle(qea.getTitle());
							dt.setStarCount(0);
							dt.setActionType(qea.getActionType());
							li.add(dt);
						}
						hs.setActions(li);
						list4.add(hs);
					}
				}
			}
			for (HistoryStrikeQueryDto hsq : list2) {
				String sql = "select SUM(qss.star_count) as starCount from qm_strik_star as qss where qss.student_id=? and day=? ";
				List<HistoryStrikeQueryDto> findList = (List<HistoryStrikeQueryDto>) this.findList(sql,
						new Object[] { studentId, hsq.getDay() }, HistoryStrikeQueryDto.class);
				if (CollectionUtils.isNotEmpty(findList)) {
					hsq.setStarCount(findList.get(0).getStarCount());
				}
				List<StrikStarActionsDto> list = this.strikeQuery(studentId, hsq.getDay());
				hsq.setActions(list);
			}
		}
		list2.addAll(list4);

		return page2;
	}

	public List<QmExpressionAction> queryAction(Long studentId) {
		String sql = "SELECT qea.title,qea.action_id,qea.action_type FROM qm_expression_action AS qea JOIN qm_student_action AS qsa "
				+ "WHERE qea.action_id = qsa.action_id AND qsa.student_id = ? AND qsa.action_type = 1";
		List<QmExpressionAction> list = (List<QmExpressionAction>) this.findList(sql, new Object[] { studentId },
				QmExpressionAction.class);
		String sql2 = "SELECT qea.title, qea.action_id,qea.action_type FROM qm_expression_action_default AS qea JOIN qm_student_action AS qsa "
				+ "WHERE qea.action_id = qsa.action_id AND qsa.student_id = ? AND qsa.action_type = 0";
		List<QmExpressionAction> list2 = (List<QmExpressionAction>) this.findList(sql2, new Object[] { studentId },
				QmExpressionAction.class);
		list.addAll(list2);
		return list;
	}

	public List<StrikStarActionsDto> strikeQuery(Long studentId, String day) {
		List<StrikStarActionsDto> li = new ArrayList<>();
		String sql = "SELECT qss.star_count,qea.title,qea.action_id,qss.action_type FROM qm_strik_star AS qss "
				+ "JOIN qm_expression_action AS qea where qss.action_id=qea.action_id "
				+ "and qss.action_type=1 and qss.student_id=? and qss.`day`=? ";
		List<StrikStarActionsDto> list = (List<StrikStarActionsDto>) this.findList(sql, new Object[] { studentId, day },
				StrikStarActionsDto.class);

		String sql2 = "SELECT qss.star_count,qea.title,qea.action_id,qss.action_type FROM qm_strik_star AS qss "
				+ "JOIN qm_expression_action_default AS qea where qss.action_id=qea.action_id "
				+ "and qss.action_type=0 and qss.student_id=? and qss.`day`=? ";
		List<StrikStarActionsDto> list2 = (List<StrikStarActionsDto>) this.findList(sql2,
				new Object[] { studentId, day }, StrikStarActionsDto.class);
		li.addAll(list);
		li.addAll(list2);
		return li;
	}

	public void actionStrike(String openId, Long studentId, String day, JSONArray actionArray) throws Exception {
		List<QmStrikStar> star = starDao.findByDayAndStudentId(day, studentId);
		if (CollectionUtils.isNotEmpty(star)) {
			throw new Exception("已经评过星星了");
		}
		if (actionArray.size() > 0) {
			Integer sCount = 0;
			for (int i = 0; i < actionArray.size(); i++) {
				JSONObject job = actionArray.getJSONObject(i);
				String actionId = job.get("actionId").toString();
				String actionType = job.get("actionType").toString();
				Integer starCount = Integer.valueOf(job.get("starCount").toString());
				QmStrikStar qss = new QmStrikStar();
				qss.setDay(day);
				qss.setStudentId(studentId);
				qss.setActionId(Long.valueOf(actionId));
				qss.setActionType(Integer.valueOf(actionType));
				qss.setPostTime(Calendar.getInstance().getTime());
				qss.setStarCount(starCount);
				starDao.save(qss);
				sCount += starCount;
			}
			QmStudent qs = qsDao.findByStudentId(studentId);
			qs.setTotalStarCount(qs.getTotalStarCount() + sCount);
			qsDao.save(qs);
		}
	}

	@Override
	public JPADao<QmStrikStar> getDao() {
		return null;
	}

	@Override
	public Class<QmStrikStar> getEntityClass() {
		return null;
	}

}
