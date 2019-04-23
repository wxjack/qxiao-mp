package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import com.qxiao.wx.user.jpa.entity.QmPatriarchStudent;
import com.spring.jpa.dao.JPADao;

public interface QmPatriarchStudentDao extends JPADao<QmPatriarchStudent>{

	List<QmPatriarchStudent> findByStudentId(Long studentId);

	List<QmPatriarchStudent> findByPatriarchId(Long patriarchId);

}
