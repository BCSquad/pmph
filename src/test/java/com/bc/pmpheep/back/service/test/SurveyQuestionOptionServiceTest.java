package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.SurveyQuestionOption;
import com.bc.pmpheep.back.service.SurveyQuestionOptionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查问题选项业务层接口单元测试
 * @author tyc
 *
 */
public class SurveyQuestionOptionServiceTest extends BaseTest {
	
	Logger logger = LoggerFactory.getLogger(SurveyQuestionOptionServiceTest.class);
	
	@Resource
	SurveyQuestionOptionService surveyQuestionOptionService;
	SurveyQuestionOption surveyQuestionOption = new SurveyQuestionOption(1L, "测试1", true, "测试备注");
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyQuestionOption(){
		surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption);
		Assert.assertNotNull("插入内容后返回的surveyQuestionOption.id不应为空", surveyQuestionOption.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurveyQuestionOption(){
		surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption);
		surveyQuestionOption.setOptionContent("测试2");
		Assert.assertTrue("更新失败", 
				surveyQuestionOptionService.updateSurveyQuestionOption(surveyQuestionOption) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyQuestionOptionById(){
		surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption);
		Assert.assertNotNull("获取数据失败", 
				surveyQuestionOptionService.getSurveyQuestionOptionById(surveyQuestionOption.getId()));
	}
}
