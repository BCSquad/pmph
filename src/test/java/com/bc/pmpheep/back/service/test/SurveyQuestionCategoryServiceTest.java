package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.SurveyQuestionCategory;
import com.bc.pmpheep.back.service.SurveyQuestionCategoryService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查问题分类业务层接口单元测试
 * @author tyc
 *
 */
public class SurveyQuestionCategoryServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(SurveyQuestionCategoryServiceTest.class);
	
	@Resource
	SurveyQuestionCategoryService surveyQuestionCategoryService;
	SurveyQuestionCategory surveyQuestionCategory = new SurveyQuestionCategory("测试1", 999);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyQuestionCategory(){
		surveyQuestionCategoryService.addSurveyQuestionCategory(surveyQuestionCategory);
		Assert.assertNotNull("插入内容后返回的surveyQuestionCategory.id不应为空", surveyQuestionCategory.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurveyQuestionCategory(){
		surveyQuestionCategoryService.addSurveyQuestionCategory(surveyQuestionCategory);
		surveyQuestionCategory.setCategoryName("测试2");
		Assert.assertTrue("更新失败", 
				surveyQuestionCategoryService.updateSurveyQuestionCategory(surveyQuestionCategory) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyQuestionCategoryById(){
		surveyQuestionCategoryService.addSurveyQuestionCategory(surveyQuestionCategory);
		Assert.assertNotNull("获取数据失败", 
				surveyQuestionCategoryService.getSurveyQuestionCategoryById(surveyQuestionCategory.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteSurveyQuestionCategoryById(){
		surveyQuestionCategoryService.addSurveyQuestionCategory(surveyQuestionCategory);
		Assert.assertNotNull("删除数据失败", 
				surveyQuestionCategoryService.deleteSurveyQuestionCategoryById(surveyQuestionCategory.getId()));
	}
}
