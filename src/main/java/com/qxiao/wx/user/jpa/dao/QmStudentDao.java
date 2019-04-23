package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.jpa.dao.JPADao;

public interface QmStudentDao extends JPADao<QmStudent> {

	
	@Query(value="SELECT COUNT(*) FROM QmStudent as qs , QmClockInfo as qci "
			+ "where (qci.ibeaconId=qs.ibeaconId or qci.nfcId=qs.nfcId) "
			+ "and qs.studentId=?1 and DATE_FORMAT(qci.postTime,'%Y-%m-%d')=?2",nativeQuery = true)
	Integer findByStudentId(Long studentId, String postTime);

	@Query(nativeQuery = true, value = "SELECT qs.*  FROM 	qm_student AS qs "
			+ "JOIN qm_class_student AS qcs ON qcs.student_id = qs.student_id "
			+ "JOIN qm_patriarch_student AS qps ON qps.student_id = qs.student_id "
			+ "JOIN qm_patriarch AS qp ON qp.id = qps.patriarch_id WHERE "
			+ "	qp.open_id = ?1 AND qp.is_del = 0 AND qcs.class_id = ?2 "
			+ "GROUP BY qs.student_id")
	List<QmStudent> findByOpenIdAndClassId(String openId, Long classId);

	@Query(value = "SELECT qs.* FROM qm_student AS qs JOIN qm_patriarch_student AS qps "
			+ "ON qs.student_id = qps.student_id JOIN qm_patriarch AS qp ON qp.id = qps.patriarch_id "
			+ "WHERE qp.open_id = ?1 and qs.student_id=?2 GROUP BY qs.student_id", nativeQuery = true)
	QmStudent findByOpenId(String openId, Long studentId);

	@Query(value = "SELECT qs.* FROM qm_student AS qs JOIN qm_patriarch_student AS qps "
			+ "ON qs.student_id = qps.student_id JOIN qm_patriarch AS qp ON qp.id = qps.patriarch_id "
			+ "WHERE qp.open_id = ?1 and qs.status=0 GROUP BY qs.student_id", nativeQuery = true)
	List<QmStudent> findWithOpenId(String openId);

	QmStudent findByStudentId(Long studentId);

//	@Query(nativeQuery = true, value = "select * from qm_student where nfc_id=?1 and (ibeacon_id = ?2 or 1=1)")
//	QmStudent findByNfcId(String nfcId, String ibeaconId);
	
	QmStudent findByNfcIdOrNfcId2(String nfcId,String nfcId2);
	@Query(value = "SELECT qcs.* FROM qm_student AS qcs JOIN qm_patriarch_student AS qps "
			+ "JOIN qm_patriarch AS qp WHERE qp.id = qps.patriarch_id AND qps.student_id = qcs.student_id "
			+ "AND qp.tel =?1 ", nativeQuery = true)
	QmStudent findByTel(String tel);

	QmStudent findByStudentName(String studentName);

	@Query(value = "SELECT qs.* FROM qm_message_send AS qms JOIN qm_clock_info AS qci "
			+ "JOIN qm_student AS qs WHERE qms.message_id = qci.clock_id AND qci.nfc_id = qs.nfc_id "
			+ "and qms.type=4 AND qms.message_id=?1 and qms.open_id=?2", nativeQuery = true)
	QmStudent findByClockId(Long messageId, String openId);


	@Query(value = "SELECT COUNT(qs.student_id) AS classReadCount FROM qm_student AS qs "
			+ "JOIN qm_class_student AS qcs ON qcs.student_id = qs.student_id "
			+ "JOIN qm_homework_read_confirm AS qh ON qh.student_id = qs.student_id WHERE "
			+ "qh.home_id = ?1 AND qcs.class_id = ?2 ", nativeQuery = true)
	Integer findForReadCount(Long homeId, Long classId);

	@Query(nativeQuery = true, value = "SELECT COUNT(a.student_id) as totalCount FROM qm_student a "
			+ "JOIN qm_class_student b ON a.student_id = b.student_id WHERE b.class_id = ?1 ")
	Integer findForTotalCount(Long classId);

	QmStudent findByIbeaconId(String ibeaconId);

	@Query(value="SELECT qs.* FROM qm_message_send AS qms JOIN qm_clock_info AS qci " + 
		"JOIN qm_student AS qs WHERE qms.message_id = qci.clock_id AND qci.ibeacon_id = qs.ibeacon_id " + 
		"and qms.type=4 AND qms.message_id=?1 and qms.open_id=?2",nativeQuery = true)
	QmStudent findWithIbeaconId(Long messageId, String openId);

	@Query(value="SELECT qs.* FROM qm_student AS qs JOIN qm_patriarch AS qp JOIN qm_patriarch_student AS qps " + 
			"where qs.student_id=qps.student_id and qps.patriarch_id=qp.id and qp.open_id= ?1 and qs.`status`=0",nativeQuery=true)
	List<QmStudent> findByOpenId(String openId);
}
