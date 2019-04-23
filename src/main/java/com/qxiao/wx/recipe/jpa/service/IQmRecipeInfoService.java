package com.qxiao.wx.recipe.jpa.service;

import java.text.ParseException;
import java.util.List;

import org.json.JSONArray;

import com.qxiao.wx.recipe.dto.RecipeDTO;
import com.qxiao.wx.recipe.dto.RecipeDetailDTO;

public interface IQmRecipeInfoService {

	void recipeAdd(String openId, String title, String textContent, JSONArray images, String startDate, String endDate)
			throws ParseException;

	List<RecipeDTO> recipeQuery(String openId, Long studentId);

	RecipeDetailDTO findDetail(String openId, Long recipeId, Long studentId);

	void recipeDelete(String openId, Long recipeId);

}
