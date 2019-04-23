package com.qxiao.wx.recipe.jpa.dao;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.recipe.jpa.entity.QmRecipeRead;
import com.spring.jpa.dao.JPADao;

public interface QmRecipeReadDao extends JPADao<QmRecipeRead> {

	@Query(value = "SELECT COUNT(r.student_id) FROM qm_recipe_read AS r "
			+ "JOIN qm_class_student AS stu ON stu.student_id = r.student_id "
			+ "JOIN qm_play_school_class AS sch ON sch.class_id = stu.class_id WHERE "
			+ "sch.school_id = ?1 and r.recipe_id = ?2 GROUP BY sch.school_id",nativeQuery=true)
	Integer findReadCountBySchoolId(Long schoolId,Long recipeId);
	
	QmRecipeRead findByStudentIdAndRecipeId(Long studentId, Long recipeId);

}
