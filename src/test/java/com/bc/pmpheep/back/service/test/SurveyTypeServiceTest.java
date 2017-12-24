package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.SurveyType;
import com.bc.pmpheep.back.service.SurveyTypeService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查类型业务层接口单元测试
 * @author tyc
 *
 */
public class SurveyTypeServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(SurveyTypeServiceTest.class);
	
	@Resource
	SurveyTypeService  surveyTypeService;
	SurveyType surveyType = new SurveyType("测试", 999);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyType(){
		surveyTypeService.addSurveyType(surveyType);
		Assert.assertNotNull("插入内容后返回的surveyType.id不应为空", surveyType.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurveyType(){
		surveyTypeService.addSurveyType(surveyType);
		surveyType.setSurveyName("测试1");
		Assert.assertTrue("更新失败", 
				surveyTypeService.updateSurveyType(surveyType) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyTypeById(){
		surveyTypeService.addSurveyType(surveyType);
		Assert.assertNotNull("获取数据失败", 
				surveyTypeService.getSurveyTypeById(surveyType.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteSurveyTypeById(){
		surveyTypeService.addSurveyType(surveyType);
		Assert.assertNotNull("删除数据失败", 
				surveyTypeService.deleteSurveyTypeById(surveyType.getId()));
	}
}
