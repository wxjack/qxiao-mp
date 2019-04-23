package com.qxiao.wx.recipe.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.recipe.jpa.entity.QmRecipeInfo;
import com.spring.jpa.dao.JPADao;

public interface QmRecipeInfoDao extends JPADao<QmRecipeInfo> {

	@Query(value = "select tmp.* from (SELECT a.* FROM qm_recipe_info AS a "
			+ "JOIN qm_play_school_teacher AS tea ON tea.open_id = a.open_id "
			+ "JOIN qm_class_teacher AS ct ON tea.teacher_id = ct.teacher_id "
			+ "JOIN qm_play_school_class AS sch ON sch.class_id = ct.class_id "
			+ "WHERE sch.school_id = ?1 and a.is_del = 0 "
			+ "UNION SELECT b.* FROM qm_recipe_info AS b "
			+ "JOIN qm_play_school_info AS sc ON sc.open_id = b.open_id "
			+ "WHERE sc.school_id = ?1 and b.is_del = 0 ) tmp order by tmp.post_time Desc ", nativeQuery = true)
	List<QmRecipeInfo> findBySchoolId(Long schoolId);

	QmRecipeInfo findByRecipeId(Long messageId);

}
