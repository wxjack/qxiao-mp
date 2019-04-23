package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.spring.jpa.dao.JPADao;

public interface QmPatriarchDao extends JPADao<QmPatriarch> {

	QmPatriarch findByTel(String tel);

	QmPatriarch findByTelAndIsDel(String tel, int isDel);

	QmPatriarch findByOpenId(String openId);

	@Query(value = "SELECT qp.* FROM qm_patriarch AS qp JOIN qm_patriarch_student AS qps JOIN qm_student AS qs "
			+ "WHERE qp.id = qps.patriarch_id and qps.student_id = qs.student_id AND qs.student_id = ?1 "
			+ "AND qp.is_del = ?2", nativeQuery = true)
	List<QmPatriarch> findByStudentIdAndStatus(Long studentId, Integer isDel);

	@Query(nativeQuery = true, value = "SELECT qp.* FROM  qm_patriarch AS qp "
			+ "JOIN qm_patriarch_student AS qps ON qp.id = qps.patriarch_id "
			+ "JOIN qm_class_student AS qcs ON qcs.student_id = qps.student_id where qcs.class_id = ?1")
	List<QmPatriarch> findByClassId(Long classId);

	@Query(nativeQuery = true, value = "SELECT qp.* FROM qm_student as qs JOIN qm_patriarch_student as qps JOIN qm_patriarch as qp "
			+ "where qs.student_id=qps.student_id and qps.patriarch_id=qp.id and ( qs.nfc_id=?1 or qs.nfc_id2=?2) GROUP BY qp.id")
	List<QmPatriarch> findBynfcIdOrNfcId2( String nfcId,String nfcId2);

	@Query(value = "SELECT qp.* FROM qm_student AS qs JOIN qm_patriarch_student AS qps JOIN qm_patriarch AS qp "
			+ "WHERE qs.student_id = qps.student_id AND qps.patriarch_id = qp.id AND qp.tel=?1  "
			+ "AND qp.is_del = 0", nativeQuery = true)
	QmPatriarch findByOne(String tel);

	@Query(value = "SELECT p.* FROM qm_patriarch AS p " + "JOIN qm_patriarch_student AS ps ON ps.patriarch_id = p.id "
			+ "JOIN qm_class_student AS cs ON cs.student_id = ps.student_id "
			+ "JOIN qm_play_school_class AS sch ON sch.class_id = cs.class_id "
			+ "WHERE sch.school_id = ?1 ", nativeQuery = true)
	List<QmPatriarch> findBySchoolId(Long schoolId);

	@Query(value = "SELECT qp.* FROM qm_student AS qs JOIN qm_patriarch_student AS qps "
			+ "JOIN qm_patriarch AS qp where qs.student_id=qps.student_id and qps.patriarch_id=qp.id "
			+ "and qs.student_id=?1", nativeQuery = true)
	List<QmPatriarch> findByStudentId(Long studentId);

	@Query(value = "SELECT qp.* FROM qm_patriarch AS qp JOIN qm_patriarch_student AS qps JOIN qm_student AS qs where  "
			+ "qp.id=qps.patriarch_id and  qps.student_id=qs.student_id and qp.tel=?1 and qs.student_id=?2",nativeQuery=true)
	QmPatriarch findBytelAndStudentId(String tel, Long studentId);

	QmPatriarch findByTelAndOpenId(String tel, String openId);
}
