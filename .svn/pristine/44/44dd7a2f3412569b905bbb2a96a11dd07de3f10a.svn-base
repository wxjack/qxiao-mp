package com.qxiao.wx.user.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.qxiao.wx.user.jpa.entity.QmTeleVerifyCode;
import com.spring.jpa.dao.JPADao;

public interface QmTeleVerifyCodeDao extends JPADao<QmTeleVerifyCode>{

	@Query("SELECT a FROM QmTeleVerifyCode a where tel=?1 ORDER BY post_time DESC")
	List<QmTeleVerifyCode> findByTel(String tel);

}
