package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmPlaySchoolInfo;
import com.spring.jpa.dao.JPADao;

public interface QmPlaySchoolInfoDao extends JPADao<QmPlaySchoolInfo>{

	QmPlaySchoolInfo findByTel(String tel);

	QmPlaySchoolInfo findByOpenId(String openId);

	QmPlaySchoolInfo findByleaderName(String leaderName);
	
	List<QmPlaySchoolInfo> findAll();

	QmPlaySchoolInfo findBySchoolCode(String schoolCode);
	
	@Query(value = "SELECT qs.* FROM qm_play_school_info AS qs JOIN qm_play_school_class AS qsc "
			+ "ON qs.school_id = qsc.school_id WHERE qsc.class_id = ?1 ",nativeQuery = true)
	QmPlaySchoolInfo findByClassId(Long classId);

	@Query(nativeQuery = true, value = "SELECT DISTINCT sch.* FROM qm_play_school_info AS sch "
			+ "JOIN qm_play_school_class AS cla ON sch.school_id = cla.school_id "
			+ "JOIN qm_class_teacher AS ct ON cla.class_id = ct.class_id "
			+ "JOIN qm_play_school_teacher AS tea ON tea.teacher_id = ct.teacher_id "
			+ "where tea.open_id = ?1 AND tea.status = 0 ")
	QmPlaySchoolInfo findWithTeacher(String openId);

	@Query(value = "SELECT sch.* FROM qm_play_school_info sch "
			+ "JOIN qm_play_school_class cls ON sch.school_id = cls.school_id "
			+ "JOIN qm_class_student qcs ON cls.class_id = qcs.class_id "
			+ "WHERE qcs.student_id = ?1 ", nativeQuery = true)
	QmPlaySchoolInfo findByStudentId(Long studentId);
	
	@Query(value="SELECT qpsc.class_id FROM qm_play_school_info AS qpsi JOIN qm_play_school_class AS qpsc " + 
			"WHERE qpsi.school_id = qpsc.school_id AND qpsi.open_id = ?1", nativeQuery = true)
	List<Long> findClassIdByOpenId(String openId);

	QmPlaySchoolInfo findBySchoolId(Long id);

	@Query(value = "SELECT a.* FROM qm_play_school_info AS a JOIN qm_notice_info AS b ON a.open_id = b.open_id WHERE b.notice_id = ?1", nativeQuery = true)
	QmPlaySchoolInfo findByNoticeId(Long noticeId);

	
	@Query(value="SELECT qpsi.school_id FROM qm_leader_init AS qli JOIN qm_play_school_info AS qpsi " + 
			"where qli.tel=qpsi.tel and qli.terminal_school_id=?",nativeQuery=true)
	Long findByterminalSchoolId(Long schoolId);

	@Query(value="SELECT a.* FROM qm_play_school_info AS a JOIN qm_notice_info AS b " + 
			"join qm_play_school_teacher as qpst where qpst.open_id=b.open_id and qpst.school_id=a.school_id " + 
			"and b.notice_id=?",nativeQuery=true)
	QmPlaySchoolInfo queryByTeacherOpenId(Long noticeId);
	
	@Query(value="SELECT a.* FROM qm_play_school_info AS a JOIN qm_recipe_info AS b JOIN qm_play_school_teacher AS qpst " + 
			"WHERE qpst.open_id = b.open_id AND qpst.school_id = a.school_id AND b.recipe_id =?",nativeQuery=true)
	QmPlaySchoolInfo queryByRecipe(Long recipeId);
}