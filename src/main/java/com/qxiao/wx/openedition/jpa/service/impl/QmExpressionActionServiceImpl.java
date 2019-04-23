package com.qxiao.wx.openedition.jpa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.community.jpa.dao.QmClassStudentDao;
import com.qxiao.wx.community.jpa.entity.QmClassStudent;
import com.qxiao.wx.openedition.dto.ActionDto;
import com.qxiao.wx.openedition.dto.HomeStatQueryDto;
import com.qxiao.wx.openedition.dto.QueryRuleDto;
import com.qxiao.wx.openedition.jpa.dao.QmExpressionActionDao;
import com.qxiao.wx.openedition.jpa.dao.QmExpressionActionDefaultDao;
import com.qxiao.wx.openedition.jpa.dao.QmExpressionRuleDao;
import com.qxiao.wx.openedition.jpa.dao.QmExpressionRuleDefaultDao;
import com.qxiao.wx.openedition.jpa.dao.QmStrikStarDao;
import com.qxiao.wx.openedition.jpa.dao.QmStudentActionDao;
import com.qxiao.wx.openedition.jpa.dao.QmStudentRuleDao;
import com.qxiao.wx.openedition.jpa.entity.QmExpressionAction;
import com.qxiao.wx.openedition.jpa.entity.QmExpressionActionDefault;
import com.qxiao.wx.openedition.jpa.entity.QmExpressionRule;
import com.qxiao.wx.openedition.jpa.entity.QmExpressionRuleDefault;
import com.qxiao.wx.openedition.jpa.entity.QmStudentAction;
import com.qxiao.wx.openedition.jpa.entity.QmStudentRule;
import com.qxiao.wx.openedition.jpa.service.IQmExpressionActionService;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class QmExpressionActionServiceImpl extends AbstractJdbcService<QmExpressionAction>
		implements IQmExpressionActionService {

	@Autowired
	QmExpressionActionDao actionDao;
	@Autowired
	QmExpressionRuleDao ruleDao;
	@Autowired
	QmExpressionActionDefaultDao defaultDao;
	@Autowired
	QmExpressionRuleDefaultDao ruleDefaultDao;
	@Autowired
	QmStudentRuleDao stRuleDao;
	@Autowired
	QmStudentActionDao stActionDao;
	@Autowired
	QmStrikStarDao starDao;
	@Autowired
	QmClassStudentDao qcsDao;
	@Autowired
	QmStudentDao studentDao;
	
	public void addActionDefual(String title, String textContent, JSONArray rules) {
		QmExpressionActionDefault qea = new QmExpressionActionDefault();
		qea.setSerial(0);
		qea.setTextContent(textContent);
		qea.setTitle(title);
		qea.setActionType(0);
		qea.setPostTime(Calendar.getInstance().getTime());
		defaultDao.save(qea);
		if (rules.size() > 0) {
			for (int i = 0; i < rules.size(); i++) {
				QmExpressionRuleDefault qer = new QmExpressionRuleDefault();
				JSONObject job = rules.getJSONObject(i);
				String string = job.get("ruleText").toString();
				Object object = job.get("stressFlag");
				qer.setStressFlag(Integer.valueOf(object.toString()));
				qer.setTextContent(string);
				qer.setSerial(0);
				qer.setPostTime(Calendar.getInstance().getTime());
				qer.setActionId(qea.getActionId());
				ruleDefaultDao.save(qer);
				
			}
		}
	}
	

	public void addMyAction(String openId, Long studentId, JSONArray action) {
		if (action.size() > 0) {
			for (int i = 0; i < action.size(); i++) {
				JSONObject job = action.getJSONObject(i);
				String actionId = job.get("actionId").toString();
				String actionType = job.get("actionType").toString();
				QmStudentAction qsa = new QmStudentAction();
				qsa.setActionId(Long.valueOf(actionId));
				qsa.setActionType(Integer.valueOf(actionType));
				qsa.setStudentId(studentId);
				qsa.setPostTime(Calendar.getInstance().getTime());
				stActionDao.save(qsa);
				if (Integer.valueOf(actionType) == 0) {
					List<QmExpressionRuleDefault> list = ruleDefaultDao.findByActionId(Long.valueOf(actionId));
					for (QmExpressionRuleDefault qerd : list) {
						QmStudentRule qsr = new QmStudentRule();
						qsr.setActionType(0);
						qsr.setRuleId(qerd.getRuleId());
						qsr.setStudentId(studentId);
						qsr.setPostTime(Calendar.getInstance().getTime());
						stRuleDao.save(qsr);
					}
				}
				if (Integer.valueOf(actionType) == 1) {
					List<QmExpressionRule> list = ruleDao.findByActionId(Long.valueOf(actionId));
					for (QmExpressionRule qer : list) {
						QmStudentRule qsr = new QmStudentRule();
						qsr.setActionType(1);
						qsr.setRuleId(qer.getRuleId());
						qsr.setStudentId(studentId);
						qsr.setPostTime(Calendar.getInstance().getTime());
						stRuleDao.save(qsr);
					}
				}
			}
		}
	}

	public List<HomeStatQueryDto> homeStatQuery(Long studentId, Long actionId, Integer actionType) {
		List<QmClassStudent> st = qcsDao.queryClassByStudentId(studentId);
		List<String> li = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(st)) {
			for (QmClassStudent qcs : st) {
				li.add(qcs.getClassId().toString());
			}
		}
		if (CollectionUtils.isEmpty(st)) {
			li.add(0 + "");
		}
		String join = String.join(",", li);
		if (actionType == 1) {
			String sql = "SELECT qea.action_id,qea.title,qss.`day`,qss.star_count as myStartCount,qss.student_id,"
					+ "(SELECT AVG(qss.star_count) FROM qm_strik_star AS qss JOIN qm_expression_action AS qea "
					+ "WHERE qss.action_id = qea.action_id AND qss.student_id IN (" + join + ") AND qss.action_id =? "
					+ "and qss.action_type=1 ) AS aveStartCount FROM qm_expression_action AS qea JOIN qm_strik_star AS qss "
					+ "WHERE qea.action_id = qss.action_id AND qss.action_type =1 AND DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(DAY) "
					+ "and qss.action_id=? ";
			List<HomeStatQueryDto> list = (List<HomeStatQueryDto>) this.findList(sql,
					new Object[] { actionId, actionId }, HomeStatQueryDto.class);
			for (HomeStatQueryDto dto : list) {
				dto.setAveStartCount(0);
			}
			return list;
		}
		if (actionType == 0) {
			List<HomeStatQueryDto> lis = new ArrayList<>();
			String sql = "SELECT qea.action_id,qea.title,qss.`day`,qss.star_count AS myStartCount,"
					+ "qss.student_id,( SELECT avg(star_count) AS aveStartCount FROM qm_strik_star "
					+ "WHERE student_id IN (" + join
					+ ") AND action_id = ? AND action_type = 0 and day =? ) AS aveStartCount "
					+ "FROM qm_expression_action AS qea JOIN qm_strik_star AS qss WHERE qea.action_id = qss.action_id "
					+ "AND qss.action_type = 0 AND qss.`day`= ? AND qss.action_id = ?";
			for (int i = 0; i < 7; i++) {
				Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
				calendar.add(Calendar.DATE, -i); // 得到前i天
				String day = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
				List<HomeStatQueryDto> findList = (List<HomeStatQueryDto>) this.findList(sql,
						new Object[] { actionId, day, day, actionId }, HomeStatQueryDto.class);
				if (CollectionUtils.isNotEmpty(findList)) {
					for (HomeStatQueryDto dto : findList) {
						if (dto.getAveStartCount() == null) {
							dto.setAveStartCount(0);
						}
					}
				}
				lis.addAll(findList);
			}
			return lis;
		}

		return null;
	}

	public Integer avgStar(String studentId, Long actionId) {
		String sql = "select avg(star_count) AS aveStartCount from qm_strik_star  where student_id in (" + studentId
				+ ")and action_id=? and action_type=0";
		Map<String, Object> map = new HashMap<>();
		map.put("actionId", actionId);
		Integer aveStartCount = 0;
		List<Map<String, Object>> findList = this.findList(sql, map);
		if (CollectionUtils.isNotEmpty(findList)) {
			String object = findList.get(0).get("aveStartCount").toString();
			aveStartCount = Integer.valueOf(object);
		}
		return aveStartCount;
	}

	public Map<String, Object> queryStudentAction(String openId, Long studentId) {
		String sql1 = "SELECT qea.* FROM qm_expression_action_default AS qea "
				+ "JOIN qm_student_action AS qsa WHERE qea.action_id = qsa.action_id and qsa.action_type=0 "
				+ "AND qsa.student_id = ? ";
		List<ActionDto> list = (List<ActionDto>) this.findList(sql1, new Object[] { studentId }, ActionDto.class);
		String sql2 = "SELECT qea.* FROM qm_expression_action AS qea JOIN qm_student_action AS qsa "
				+ "WHERE qea.action_id = qsa.action_id and qsa.action_type=1 AND qsa.student_id = ?";
		List<ActionDto> findList = (List<ActionDto>) this.findList(sql2, new Object[] { studentId }, ActionDto.class);
		list.addAll(findList);
		List<ActionDto> list2 = this.queryStudentActionDefaultList(openId, studentId);
		List<ActionDto> list3 = this.queryStudentActionList(openId, studentId);
		list2.addAll(list3);
		Map<String, Object> map = new HashMap<>();
		map.put("myAction", list);
		map.put("noChoiceAction", list2);
		return map;
	}

	public List<ActionDto> queryStudentActionDefaultList(String openId, Long studentId) {
		String sql = "SELECT qeac.* FROM qm_expression_action_default as qeac WHERE "
				+ " action_id NOT IN ( SELECT qea.action_id FROM qm_expression_action_default AS qea "
				+ "JOIN qm_student_action AS qsa WHERE qea.action_id = qsa.action_id and qsa.action_type=0 "
				+ "AND qsa.student_id = ? )";
		List<ActionDto> list = (List<ActionDto>) this.findList(sql, new Object[] { studentId }, ActionDto.class);
		return list;
	}

	public List<ActionDto> queryStudentActionList(String openId, Long studentId) {
		String sql = "SELECT qeac.* FROM qm_expression_action as qeac WHERE open_id = ? AND action_id NOT IN ( "
				+ "SELECT qea.action_id FROM qm_expression_action AS qea JOIN qm_student_action AS qsa "
				+ "WHERE qea.action_id = qsa.action_id and qsa.action_type=1 AND qsa.student_id = ?)";
		List<ActionDto> list = (List<ActionDto>) this.findList(sql, new Object[] { openId, studentId },
				ActionDto.class);
		return list;
	}

	public void ruleDelete(String openId, Long ruleId, Integer actionType) throws Exception {
		if (actionType == 0) {
			throw new Exception("系统默认行为无法删除");
		}
		QmExpressionRule rule = ruleDao.findByRuleIdAndOpenId(ruleId, openId);
		if (rule != null) {
			ruleDao.delete(rule.getRuleId());
		}
	}

	public void ruleAdd(Integer actionType, String openId, Long actionId, String ruleText, Integer stressFlag)
			throws Exception {
		if (actionType == 0) {
			throw new Exception("系统默认行为，无法自行添加标准");
		}
		QmExpressionRule qer = new QmExpressionRule();
		qer.setActionId(actionId);
		qer.setOpenId(openId);
		qer.setSerial(0);
		qer.setStressFlag(stressFlag);
		qer.setPostTime(Calendar.getInstance().getTime());
		qer.setTextContent(ruleText);
		ruleDao.save(qer);
	}

	public void ruleConnect(String openId, Long studentId, Long actionId, Integer actionType, List<Long> rules) {
		List<QmStudentRule> list = stRuleDao.findByActionIdAndStudentId(actionId, studentId);
		if (CollectionUtils.isNotEmpty(list)) {
			for (QmStudentRule qst : list) {
				stRuleDao.delete(qst.getId());
			}
		}
		if (rules.size() > 0) {
			for (Long ruleId : rules) {
				QmStudentRule qsr = new QmStudentRule();
				qsr.setActionType(actionType);
				qsr.setPostTime(Calendar.getInstance().getTime());
				qsr.setRuleId(ruleId);
				qsr.setStudentId(studentId);
				qsr.setPostTime(Calendar.getInstance().getTime());
				stRuleDao.save(qsr);
			}
		}
		QmStudentAction action = stActionDao.findByActionIdAndStudentIdAndActionType(actionId,studentId,actionType);
		if(action!=null) {
			stActionDao.delete(action.getId());
		}
		QmStudentAction qsa=new QmStudentAction();
		qsa.setActionId(actionId);
		qsa.setActionType(actionType);
		qsa.setStudentId(studentId);
		qsa.setPostTime(Calendar.getInstance().getTime());
		stActionDao.save(qsa);
	}

	public void actionUpdate(String openId, Long actionId, String title, String textContent, Integer actionType)
			throws Exception {
		if(actionType==1) {
			QmExpressionAction action = actionDao.findByActionId(actionId);
			action.setTextContent(textContent);
			action.setTitle(title);
			action.setPostTime(Calendar.getInstance().getTime());
			actionDao.save(action);
		}
	}

	public Map<String, Object> actionListQuery(String openId, Long studentId, String day) {

		List<Map<String, Object>> li = new ArrayList<>();
		Integer starCount=0;
		if (studentId == 0 || studentId == null) {//未添加孩子
			List<QmExpressionActionDefault> findAll = defaultDao.findAll();
			for(QmExpressionActionDefault qead:findAll) {
				Map<String, Object> map = new HashMap<>();
				map.put("actionId", qead.getActionId());
				map.put("actionType", 0);
				map.put("title", qead.getTitle());
				map.put("studentId", studentId);
				map.put("openId", openId);
				map.put("starCount", starCount);
				li.add(map);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("myActions", li);
			if(starCount==0) {
				map.put("statu", 0);
			}else {
				map.put("statu", 1);
			}
			return map;
		}

		List<QmExpressionAction> list = actionDao.findByStudentId(studentId);
		if (CollectionUtils.isNotEmpty(list)) {
			for (QmExpressionAction qea : list) {
				starCount = starDao.findByActionIdAndDay(qea.getActionId(), day,studentId);
				Map<String, Object> map = new HashMap<>();
				map.put("actionId", qea.getActionId());
				map.put("actionType", 1);
				map.put("title", qea.getTitle());
				map.put("studentId", studentId);
				map.put("openId", openId);
				if (starCount == null) {
					starCount = 0;
				}
				map.put("starCount", starCount);
				li.add(map);
			}
		}
		List<QmExpressionActionDefault> list2 = defaultDao.findByStudentId(studentId);
		if (CollectionUtils.isNotEmpty(list2)) {
			for (QmExpressionActionDefault qea : list2) {
				starCount = starDao.findByActionIdAndDay(qea.getActionId(), day,studentId);
				Map<String, Object> map = new HashMap<>();
				map.put("actionId", qea.getActionId());
				map.put("actionType", 0);
				map.put("title", qea.getTitle());
				map.put("studentId", studentId);
				map.put("openId", openId);
				if (starCount == null) {
					starCount = 0;
				}
				map.put("starCount", starCount);
				li.add(map);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("myActions", li);
		if(starCount==0) {
			map.put("statu", 0);
		}else {
			map.put("statu", 1);
		}
		return map;
	}

	public QueryRuleDto queryStudentAction(String openId, Long actionId, Long studentId, Integer actionType) {
		if (actionType == 1) {
			List<QmExpressionRule> rules = ruleDao.findByActionId(actionId);
			QmExpressionAction action = actionDao.findByActionId(actionId);
			List<Map<String, Object>> li = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(rules)) {
				for (QmExpressionRule rule : rules) {
					QmStudentRule studentRule = stRuleDao.findByRuleIdAndStudentIdAndActionType(rule.getRuleId(),
							studentId, actionType);
					Map<String, Object> map = new HashMap<>();
					map.put("ruleId", rule.getRuleId());
					map.put("ruleText", rule.getTextContent());
					map.put("stressFlag", rule.getStressFlag());
					if (studentRule == null) {
						map.put("choice", 0);// 未选中
					} else {
						map.put("choice", 1);// 选中
					}
					li.add(map);
				}
			}
			QueryRuleDto dto = new QueryRuleDto();
			dto.setActionId(actionId);
			dto.setTextContent(action.getTextContent());
			dto.setTitle(action.getTitle());
			dto.setRules(li);
			dto.setActionType(actionType);
			return dto;
		}
		if (actionType == 0) {
			List<QmExpressionRuleDefault> rules = ruleDefaultDao.findByActionId(actionId);
			QmExpressionActionDefault action = defaultDao.findByActionId(actionId);
			List<Map<String, Object>> li = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(rules)) {
				for (QmExpressionRuleDefault rule : rules) {
					QmStudentRule studentRule = stRuleDao.findByRuleIdAndStudentIdAndActionType(rule.getRuleId(),
							studentId, actionType);
					Map<String, Object> map = new HashMap<>();
					map.put("ruleId", rule.getRuleId());
					map.put("ruleText", rule.getTextContent());
					map.put("stressFlag", rule.getStressFlag());
					if (studentRule == null) {
						map.put("choice", 0);// 未选中
					} else {
						map.put("choice", 1);// 选中
					}
					li.add(map);
				}
			}
			QueryRuleDto dto = new QueryRuleDto();
			dto.setActionId(actionId);
			dto.setTextContent(action.getTextContent());
			dto.setTitle(action.getTitle());
			dto.setRules(li);
			dto.setActionType(actionType);
			return dto;
		}
		return null;
	}

	public void actionDelete(String openId, Long actionId, Integer actionType) throws Exception {
//		if(actionType==0) {
//			throw new Exception("系统默认行为，无法删除");
//		}
//		QmExpressionAction action = actionDao.findByOpenIdAndAction(openId, actionId);
		QmStudentAction action = stActionDao.findByActionIdAndActionType(actionId, actionType);
//		List<QmExpressionRule> rule = ruleDao.findByActionId(actionId);
		if (actionType == 0) {
			List<QmStudentRule> list = stRuleDao.findByActionDefaultId(actionId);
			for (QmStudentRule qsr : list) {
				stRuleDao.delete(qsr.getId());
			}
		}
		if (actionType == 1) {
			List<QmStudentRule> stRule = stRuleDao.findByActionId(actionId);
			if (CollectionUtils.isNotEmpty(stRule)) {
				for (QmStudentRule qsr : stRule) {
					stRuleDao.delete(qsr.getId());
				}
			}
		}
//		if (CollectionUtils.isNotEmpty(rule)) {
//			for (QmExpressionRule qer : rule) {
//				ruleDao.delete(qer.getRuleId());
//			}
//		}
		if (action != null) {
			stActionDao.delete(action.getId());
		}
//		if (action != null) {
//			actionDao.delete(actionId);
//		}
	}

	public QueryRuleDto actionQuery(String openId, Long actionId, Integer actionType, Long studentId) throws Exception {
		if(studentId==0) {
			throw new Exception("请先添加孩子");
		}
		if (actionType == 1) {
			QmExpressionAction action = actionDao.findByActionId(actionId);
			List<QmExpressionRule> findByActionId = ruleDao.findByChoiceActionId(actionId, studentId);
			List<Map<String, Object>> li = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(findByActionId)) {
				for (QmExpressionRule qer : findByActionId) {
					Map<String, Object> map = new HashMap<>();
					map.put("ruleId", qer.getRuleId());
					map.put("ruleText", qer.getTextContent());
					map.put("stressFlag", qer.getStressFlag());
					li.add(map);
				}
			}
			QueryRuleDto dto = new QueryRuleDto();
			dto.setActionId(actionId);
			dto.setTextContent(action.getTextContent());
			dto.setTitle(action.getTitle());
			dto.setActionType(actionType);
			dto.setRules(li);
			return dto;
		}
		if (actionType == 0) {
			QmExpressionActionDefault actionDefault = defaultDao.findByActionId(actionId);
			List<QmExpressionRuleDefault> list = ruleDefaultDao.findByChoiceActionId(actionId, studentId);
			List<Map<String, Object>> li = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(list)) {
				for (QmExpressionRuleDefault qer : list) {
					Map<String, Object> map = new HashMap<>();
					map.put("ruleId", qer.getRuleId());
					map.put("ruleText", qer.getTextContent());
					map.put("stressFlag", qer.getStressFlag());
					li.add(map);
				}
			}
			QueryRuleDto dto = new QueryRuleDto();
			dto.setActionId(actionId);
			dto.setTextContent(actionDefault.getTextContent());
			dto.setTitle(actionDefault.getTitle());
			dto.setActionType(actionType);
			dto.setRules(li);
			return dto;
		}
		return null;
	}

	public void actionAdd(Long studentId, String openId, String title, String textContent, JSONArray rules) {
		QmExpressionAction qea = new QmExpressionAction();
		qea.setOpenId(openId);
		qea.setSerial(0);
		qea.setTextContent(textContent);
		qea.setTitle(title);
		qea.setActionType(1);
		qea.setPostTime(Calendar.getInstance().getTime());
		actionDao.save(qea);
		if (rules.size() > 0) {
			for (int i = 0; i < rules.size(); i++) {
				QmExpressionRule qer = new QmExpressionRule();
				JSONObject job = rules.getJSONObject(i);
				String string = job.get("ruleText").toString();
				Object object = job.get("stressFlag");
				qer.setOpenId(openId);
				qer.setStressFlag(Integer.valueOf(object.toString()));
				qer.setTextContent(string);
				qer.setSerial(0);
				qer.setPostTime(Calendar.getInstance().getTime());
				qer.setActionId(qea.getActionId());
				ruleDao.save(qer);
				QmStudentRule qst = new QmStudentRule();
				qst.setActionType(1);
				qst.setRuleId(qer.getRuleId());
				qst.setStudentId(studentId);
				qst.setPostTime(Calendar.getInstance().getTime());
				stRuleDao.save(qst);
			}
		}
		QmStudentAction qsa = new QmStudentAction();
		qsa.setActionId(qea.getActionId());
		qsa.setActionType(1);
		qsa.setStudentId(studentId);
		qsa.setPostTime(Calendar.getInstance().getTime());
		stActionDao.save(qsa);
	}

	@Override
	public JPADao<QmExpressionAction> getDao() {
		return null;
	}

	@Override
	public Class<QmExpressionAction> getEntityClass() {
		return null;
	}

}
