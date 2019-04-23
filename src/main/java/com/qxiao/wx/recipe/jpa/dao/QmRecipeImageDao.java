package com.qxiao.wx.recipe.jpa.dao;

import java.util.List;

import com.qxiao.wx.recipe.jpa.entity.QmRecipeImage;
import com.spring.jpa.dao.JPADao;

public interface QmRecipeImageDao extends JPADao<QmRecipeImage> {

	List<QmRecipeImage> findByRecipeId(Long recipeId);

}
