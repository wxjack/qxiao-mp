package com.qxiao.wx.openedition.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.openedition.jpa.entity.QmStrikStar;
import com.spring.jpa.dao.JPADao;

public interface QmStrikStarDao extends JPADao<QmStrikStar> {

	List<QmStrikStar> findByDayAndStudentId(String day, Long studentId);

	@Query(value="SELECT SUM(qss.star_count) AS starCount FROM qm_strik_star AS qss WHERE " + 
			"qss.action_id=?1 and qss.`day`=?2 and qss.student_id=?3",nativeQuery=true)
	Integer findByActionIdAndDay(Long actionId,String day,Long studentId);
	
	@Query(value="SELECT * FROM qm_strik_star WHERE student_id = ?1  GROUP BY `day` ",nativeQuery=true)
	List<QmStrikStar> findByStudentId(Long studentId);
	
}
