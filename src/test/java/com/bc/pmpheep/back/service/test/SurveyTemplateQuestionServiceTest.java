package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.SurveyTemplateQuestion;
import com.bc.pmpheep.back.service.SurveyTemplateQuestionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查问题模版关联业务层单元测试
 * @author tyc
 *
 */
public class SurveyTemplateQuestionServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(SurveyTemplateQuestionServiceTest.class);
	
	@Resource
	SurveyTemplateQuestionService surveyTemplateQuestionService;
	SurveyTemplateQuestion surveyTemplateQuestion = new SurveyTemplateQuestion(1L, 2L);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyTemplateQuestion(){
		surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion);
		Assert.assertNotNull("插入内容后返回的surveyTemplateQuestion.id不应为空", surveyTemplateQuestion.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurveyTemplateQuestion(){
		surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion);
		surveyTemplateQuestion.setQuestionId(6L);
		Assert.assertTrue("更新失败", 
				surveyTemplateQuestionService.updateSurveyTemplateQuestion(surveyTemplateQuestion) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyTemplateQuestionById(){
		surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion);
		Assert.assertNotNull("获取数据失败", 
				surveyTemplateQuestionService.getSurveyTemplateQuestionById(surveyTemplateQuestion.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteSurveyTemplateQuestionById(){
		surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion);
		Assert.assertNotNull("删除数据失败", 
				surveyTemplateQuestionService.deleteSurveyTemplateQuestionById(surveyTemplateQuestion.getId()));
	}
}
