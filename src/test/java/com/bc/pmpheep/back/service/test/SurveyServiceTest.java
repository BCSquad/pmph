package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.service.SurveyService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查问卷业务层接口单元测试
 * @author tyc
 *
 */
public class SurveyServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(SurveyServiceTest.class);
	
	@Resource
	SurveyService surveyService;
	Survey survey = new Survey("测试标题", "测试副标题", "测试简介", 1L, 2L, 1L, null, null, 999, false,
		    null, null);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurvey(){
		surveyService.addSurvey(survey);
		Assert.assertNotNull("插入内容后返回的survey.id不应为空", survey.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurvey(){
		surveyService.addSurvey(survey);
		survey.setTitle("更改标题1");
		Assert.assertTrue("更新失败", 
				surveyService.updateSurvey(survey) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyById(){
		surveyService.addSurvey(survey);
		Assert.assertNotNull("获取数据失败", 
				surveyService.getSurveyById(survey.getId()));
	}
	
}
