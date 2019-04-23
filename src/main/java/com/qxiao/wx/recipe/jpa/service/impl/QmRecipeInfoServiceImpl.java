package com.qxiao.wx.recipe.jpa.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qxiao.wx.common.DateUtil;
import com.qxiao.wx.common.JsonMapper;
import com.qxiao.wx.componse.GetIdentityService;
import com.qxiao.wx.componse.UserInfo;
import com.qxiao.wx.fresh.vo.QmImage;
import com.qxiao.wx.recipe.dto.RecipeDTO;
import com.qxiao.wx.recipe.dto.RecipeDetailDTO;
import com.qxiao.wx.recipe.jpa.dao.QmRecipeImageDao;
import com.qxiao.wx.recipe.jpa.dao.QmRecipeInfoDao;
import com.qxiao.wx.recipe.jpa.dao.QmRecipeReadDao;
import com.qxiao.wx.recipe.jpa.entity.QmRecipeImage;
import com.qxiao.wx.recipe.jpa.entity.QmRecipeInfo;
import com.qxiao.wx.recipe.jpa.entity.QmRecipeRead;
import com.qxiao.wx.recipe.jpa.service.IQmRecipeInfoService;
import com.qxiao.wx.user.jpa.dao.QmMessageSendDao;
import com.qxiao.wx.user.jpa.dao.QmPatriarchDao;
import com.qxiao.wx.user.jpa.dao.QmPlaySchoolTeacherDao;
import com.qxiao.wx.user.jpa.dao.QmStudentDao;
import com.qxiao.wx.user.jpa.entity.QmMessageSend;
import com.qxiao.wx.user.jpa.entity.QmPatriarch;
import com.qxiao.wx.user.jpa.entity.QmPlaySchoolTeacher;
import com.qxiao.wx.user.jpa.entity.QmStudent;
import com.spring.jpa.dao.JPADao;
import com.spring.jpa.service.AbstractJdbcService;

@Service
public class QmRecipeInfoServiceImpl extends AbstractJdbcService<QmRecipeInfo> implements IQmRecipeInfoService {

	@Autowired
	private QmRecipeInfoDao recipeInfoDao;
	@Autowired
	private QmRecipeImageDao imageDao;
	@Autowired
	private GetIdentityService idService;
	@Autowired
	private QmPatriarchDao patDao;
	@Autowired
	private QmMessageSendDao sendDao;
	@Autowired
	private QmRecipeReadDao readDao;
	@Autowired
	private QmStudentDao stuDao;
	@Autowired
	private QmPlaySchoolTeacherDao teachDao;

	@Override
	public JPADao<QmRecipeInfo> getDao() {
		return recipeInfoDao;
	}

	@Override
	public Class<QmRecipeInfo> getEntityClass() {
		return QmRecipeInfo.class;
	}

	// 发布食谱
	@Override
	public void recipeAdd(String openId, String title, String textContent, JSONArray images, String startDate,
			String endDate) throws ParseException {

		QmRecipeInfo info = this.addRecipe(openId, title, textContent, startDate, endDate);
		if (images != null && images.length() > 0)
			this.addImage(openId, images, info);
		// 模板推送
		this.sendMessage(info, openId);
	}

	private void sendMessage(QmRecipeInfo info, String openId) {
		UserInfo user = idService.getIdentity(openId);
		List<QmPatriarch> ps = patDao.findBySchoolId(user.getSchoolId());
		List<QmPlaySchoolTeacher> teas = teachDao.findBySchoolId(user.getSchoolId());
		for (QmPatriarch p : ps) {
			if (StringUtils.isNotBlank(p.getOpenId()))
				this.tempMessage(info, p.getOpenId());
		}
		for (QmPlaySchoolTeacher teacher : teas) {
			if (StringUtils.isNotBlank(teacher.getOpenId()))
				this.tempMessage(info, teacher.getOpenId());
		}
	}

	private void tempMessage(QmRecipeInfo info, String openId) {
		QmMessageSend message = sendDao.findByMessageIdAndOpenIdAndType(info.getRecipeId(), openId, 3);
		if (message == null) {
			message = new QmMessageSend();
			message.setMessageId(info.getRecipeId());
			message.setType(3);
			message.setOpenId(openId);
			message.setResult(0);
			message.setTitle(info.getTitle());
			message.setTextContent(info.getTextContent().length() > 50 ? info.getTextContent().substring(0, 50)
					: info.getTextContent());
			message.setPostTime(new Date());
			sendDao.save(message);
		}
	}

	private void addImage(String openId, JSONArray images, QmRecipeInfo info) {
		Iterator<Object> it = images.iterator();
		List<QmRecipeImage> imgs = new ArrayList<>();
		while (it.hasNext()) {
			QmRecipeImage image = new QmRecipeImage();
			String str = it.next().toString();
			QmImage img = JsonMapper.obj2Instance(str, QmImage.class);
			image.setImageUrl(img.getImageUrl());
			image.setSmallUrl(img.getImageUrl());
			image.setPostTime(new Date());
			image.setRecipeId(info.getRecipeId());
			imgs.add(image);
		}
		imageDao.save(imgs).iterator();
	}

	private QmRecipeInfo addRecipe(String openId, String title, String textContent, String startDate, String endDate)
			throws ParseException {
		QmRecipeInfo recipe = new QmRecipeInfo();
		recipe.setIsDel(0);
		recipe.setTitle(title);
		recipe.setTextContent(textContent);
		recipe.setOpenId(openId);
		recipe.setMessageSend(0);
		recipe.setStartDate(DateUtil.parsrTime(startDate));
		recipe.setEndDate(DateUtil.parsrTime(endDate));
		recipe.setPostTime(new Date());
		return recipeInfoDao.save(recipe);
	}

	// 查询所有食谱
	@Override
	public List<RecipeDTO> recipeQuery(String openId, Long studentId) {
		UserInfo user = idService.getIdentity(openId, studentId);
		List<QmRecipeInfo> recipes = recipeInfoDao.findBySchoolId(user.getSchoolId());
		List<RecipeDTO> dtos = new ArrayList<>();
		for (QmRecipeInfo recipe : recipes) {
			RecipeDTO dto = new RecipeDTO();
			try {
				BeanUtils.copyProperties(dto, recipe);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			UserInfo userInfo = idService.getIdentity(recipe.getOpenId(), studentId);
			String relation = user.getRelation() == 7 ? "(园长)" : "(老师)";
			if (userInfo.getType() == 1)
				relation = user.getRelation() == 7 ? "(校长)" : "(老师)";
			dto.setStudentId(studentId);
			dto.setName(userInfo.getUsername() + relation);
			this.setImage(dto);
			this.setStatus(dto, studentId);
			this.setReadCount(dto, user.getSchoolId());
			dtos.add(dto);
		}
		return dtos;
	}

	private void setReadCount(RecipeDTO dto, Long schoolId) {
		Integer count = readDao.findReadCountBySchoolId(schoolId, dto.getRecipeId());
		dto.setReadCount(count == null ? 0 : count);
	}

	private void setStatus(RecipeDTO dto, Long studentId) {
		QmStudent stu = stuDao.findOne(studentId);
		if (stu != null) {
			QmRecipeRead read = readDao.findByStudentIdAndRecipeId(studentId, dto.getRecipeId());
			dto.setStatus(read == null ? 0 : 1);
		} else
			dto.setStatus(1);
	}

	private void setImage(RecipeDTO dto) {
		List<QmRecipeImage> images = imageDao.findByRecipeId(dto.getRecipeId());
		if (CollectionUtils.isNotEmpty(images))
			dto.setTopImage(images.get(0).getImageUrl());
	}

	// 食谱详情
	@Override
	public RecipeDetailDTO findDetail(String openId, Long recipeId, Long studentId) {
		RecipeDetailDTO dto = this.findRecipeDetail(recipeId);
		QmRecipeInfo info = recipeInfoDao.findByRecipeId(recipeId);
		String sql = "select a.image_url as imageUrl from qm_recipe_image as a where a.recipe_id = :recipeId ";
		Map<String, Object> paras = new HashMap<>();
		paras.put("recipeId", recipeId);
		List<Map<String, Object>> images = this.findList(sql, paras);
		dto.setImages(images);
		UserInfo user = idService.getIdentity(openId, studentId);
		if (user == null)
			return null;

		if (user.getType() == 3)
			this.addQmRecipeRead(studentId, recipeId, openId);

		this.setReadCount(dto, user.getSchoolId());
		dto.setIsDel(info.getIsDel());
		return dto;
	}

	private void addQmRecipeRead(Long studentId, Long recipeId, String openId) {
		QmRecipeRead recipeRead = readDao.findByStudentIdAndRecipeId(studentId, recipeId);
		if (recipeRead == null) {
			QmRecipeRead read = new QmRecipeRead();
			read.setRecipeId(recipeId);
			read.setStudentId(studentId);
			read.setOpenId(openId);
			read.setPostTime(new Date());
			readDao.save(read);
		}
	}

	private void setReadCount(RecipeDetailDTO dto, Long schoolId) {
		Integer count = readDao.findReadCountBySchoolId(schoolId, dto.getRecipeId());
		dto.setReadCount(count == null ? 0 : count);
	}

	private RecipeDetailDTO findRecipeDetail(Long recipeId) {
		QmRecipeInfo recipe = recipeInfoDao.findOne(recipeId);
		RecipeDetailDTO dto = new RecipeDetailDTO();
		dto.setEndDate(recipe.getEndDate());
		dto.setStartDate(recipe.getStartDate());
		dto.setPostTime(recipe.getPostTime());
		dto.setTitle(recipe.getTitle());
		dto.setTextContent(recipe.getTextContent());
		dto.setRecipeId(recipeId);
		return dto;
	}

	@Override
	public void recipeDelete(String openId, Long recipeId) {
		if (recipeId != null) {
			QmRecipeInfo recipeInfo = recipeInfoDao.findOne(recipeId);
			recipeInfo.setIsDel(1);
			recipeInfoDao.save(recipeInfo);
		}
	}

}
