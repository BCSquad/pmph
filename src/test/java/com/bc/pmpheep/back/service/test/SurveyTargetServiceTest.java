package com.bc.pmpheep.back.service.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.SurveyTarget;
import com.bc.pmpheep.back.service.SurveyTargetService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.test.BaseTest;

/**
 * 问卷调查发起问卷业务层单元测试
 * @author tyc
 *
 */
public class SurveyTargetServiceTest extends BaseTest {

	Logger logger = LoggerFactory.getLogger(SurveyTargetServiceTest.class);
	
	@Resource
	SurveyTargetService surveyTargetService;
	SurveyTarget surveyTarget = new SurveyTarget(1L, 2L, 3L, null);
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void addSurveyTarget(){
		surveyTargetService.addSurveyTarget(surveyTarget);
		Assert.assertNotNull("插入内容后返回的surveyTarget.id不应为空", surveyTarget.getId());
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void updateSurveyTarget(){
		surveyTargetService.addSurveyTarget(surveyTarget);
		surveyTarget.setOrgId(5L);
		Assert.assertTrue("更新失败", 
				surveyTargetService.updateSurveyTarget(surveyTarget) > 0);
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void getSurveyTargetById(){
		surveyTargetService.addSurveyTarget(surveyTarget);
		Assert.assertNotNull("获取数据失败", 
				surveyTargetService.getSurveyTargetById(surveyTarget.getId()));
	}
	
	@Test
	@Rollback(Const.ISROLLBACK)
	public void deleteSurveyTargetById(){
		surveyTargetService.addSurveyTarget(surveyTarget);
		Assert.assertNotNull("删除数据失败", 
				surveyTargetService.deleteSurveyTargetById(surveyTarget.getId()));
	}
}
