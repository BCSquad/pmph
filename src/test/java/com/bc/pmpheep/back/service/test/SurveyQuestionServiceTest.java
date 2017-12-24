package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.service.SurveyQuestionService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查问题业务层接口单元测试
 * @author tyc
 *
 */
public class SurveyQuestionServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(SurveyQuestionServiceTest.class);
	
	@Resource
	SurveyQuestionService surveyQuestionService;
	SurveyQuestion surveyQuestion = new SurveyQuestion(1L, "测试问题", (short) 1, 12, "测试", true, false, null, null);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyQuestion(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		Assert.assertNotNull("插入内容后返回的surveyQuestion.id不应为空", surveyQuestion.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurveyQuestion(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		surveyQuestion.setTitle("测试问题2");
		Assert.assertTrue("更新失败", 
				surveyQuestionService.updateSurveyQuestion(surveyQuestion) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyQuestionById(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		Assert.assertNotNull("获取数据失败", 
				surveyQuestionService.getSurveyQuestionById(surveyQuestion.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteSurveyQuestionById(){
		surveyQuestionService.addSurveyQuestion(surveyQuestion);
		Assert.assertNotNull("逻辑删除数据失败", 
				surveyQuestionService.deleteSurveyQuestionById(surveyQuestion.getId()));
	}
	
}
