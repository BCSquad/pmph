package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.SurveyQuestionAnswer;
import com.bc.pmpheep.back.service.SurveyQuestionAnswerService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查问题回答业务层接口单元测试
 * @author tyc
 *
 */
public class SurveyQuestionAnswerServiceTest extends BaseTest {
	
	Logger logger = LoggerFactory.getLogger(SurveyQuestionAnswerServiceTest.class);
	
	@Resource
	SurveyQuestionAnswerService surveyQuestionAnswerService;
	SurveyQuestionAnswer surveyQuestionAnswer = new SurveyQuestionAnswer(1L, 2L, 2L, null, null, null);
	
	 @Test
	 @Rollback(Const.ISROLLBACK)
	 public void addSurveyQuestionAnswer(){
		 surveyQuestionAnswerService.addSurveyQuestionAnswer(surveyQuestionAnswer);
		 Assert.assertNotNull("插入内容后返回的surveyQuestionAnswer.id不应为空", surveyQuestionAnswer.getId());
	 }
	 
	 @Test
	 @Rollback(Const.ISROLLBACK)
	 public void updateSurveyQuestionAnswer(){
		 surveyQuestionAnswerService.addSurveyQuestionAnswer(surveyQuestionAnswer);
		 surveyQuestionAnswer.setOptionContent("aaa");
		 Assert.assertTrue("更新失败", 
				 surveyQuestionAnswerService.updateSurveyQuestionAnswer(surveyQuestionAnswer) > 0);
	 }
	 
	 @Test
	 @Rollback(Const.ISROLLBACK)
	 public void getSurveyQuestionAnswerById(){
		 surveyQuestionAnswerService.addSurveyQuestionAnswer(surveyQuestionAnswer);
		 Assert.assertNotNull("获取数据失败", 
				 surveyQuestionAnswerService.getSurveyQuestionAnswerById(surveyQuestionAnswer.getId()));
	 }
}
